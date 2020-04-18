pragma solidity ^0.5.16;
pragma experimental ABIEncoderV2;

import "@openzeppelin/contracts/ownership/Ownable.sol";
import "./ProductContract.sol";
import "./TargetContract.sol";

contract CampaignContract is Ownable {

    ProductContract public productContract;
    TargetContract public targetContract;

    struct Invoice {
        bytes32 productId;
        uint8 step; //0:준비,1:배송,2:완료
    }

    struct Donate {
        address addr;//기부자 공개키
        uint amount;//기부금액
        bool refunded;//환불여부(기부가 취소되었을 경우
    }

    struct Campaign {
        bytes32 id;//캠페인 순번
        string name;//캠페인명
        string thumbnail;//썸네일 URL
        uint cap;//목표금액 wei
        uint totalAmount;//기부된금액
        uint startTime;//캠페인 시작기간
        uint endTime;//캠페인 종료기간
        string contents;//캠페인 상세소개 이미지 URL
        address targetAddress;//기부대상
        bytes32[] productIdList;//상품목록

        address[] donorAddressList;
    }

    event AddCampaign(bytes32 id, address targetAddress, string name);
    event DoDonate(bytes32 campaignId, address donor, uint256 amount);
    event Refund(bytes32 campaignId, address donor, uint256 amount);
    event CreateInvoice(bytes32 campaignId, bytes32 productId, uint8 step);
    event Submit(bytes32 campaignId,bytes32 productId,uint8 step);
    event DoWithdrawal(bytes32 campaignId,bytes32 productId,uint8 step);


    constructor(ProductContract _productContract, TargetContract _targetContract) public {
        productContract = _productContract;
        targetContract = _targetContract;
        //targetContract.transferOwnership(msg.sender);
    }

    bytes32[] campaignIdList;
    mapping(bytes32 => Campaign) campaignMap;
    mapping(bytes32 => mapping(bytes32 => Invoice)) invoiceMap;
    mapping(bytes32 => mapping(address => Donate[])) donateListMap;//기부목록

    function addCampaign(
        address _targetAddress,
        string memory _name,
        string memory _thumbnail,
        uint _cap,
        uint _startTime,
        uint _endTime,
        string memory _contents,
        bytes32[] memory _productIdList
    ) public onlyOwner() {
        bytes32 id = keccak256(abi.encodePacked(now, _targetAddress, _name));

    Campaign storage cam = campaignMap[id];
        require(cam.id == bytes32(0));

        cam.id = id;
        cam.name = _name;
        cam.thumbnail = _thumbnail;
        cam.cap = _cap;
        cam.startTime = _startTime;
        cam.endTime = _endTime;
        cam.contents = _contents;
        cam.targetAddress = _targetAddress;
        cam.productIdList = _productIdList;

        campaignIdList.push(id);

        emit AddCampaign(id, _targetAddress, _name);
    }

    function getCampaignList() public view returns (Campaign[] memory) {
        uint arrayLength = campaignIdList.length;
        Campaign[] memory campaignList = new Campaign[](arrayLength);
        for (uint i = 0; i < arrayLength; i++) {
            Campaign memory cam = campaignMap[campaignIdList[i]];
            campaignList[i] = cam;
        }
        return campaignList;
    }
    function getCampaign(bytes32 _id) public view returns (Campaign memory){
        return campaignMap[_id];
    }
    function getCampaignWithInvoice(bytes32 _id) public view returns (Campaign memory,Invoice[] memory){

        Campaign memory c=campaignMap[_id];

        uint productLength=c.productIdList.length;
        Invoice[] memory invoiceList=new Invoice[](productLength);

        for(uint i = 0; i < productLength; i++){
            Invoice memory memInvoice;
            bytes32 productid=c.productIdList[i];
            memInvoice.productId=productid;
            Invoice storage invoice = invoiceMap[c.id][productid];
			memInvoice.step=invoice.step;
			invoiceList[i]=memInvoice;
        }
        return (c,invoiceList);
    }
    function donate(bytes32 campaignId) public payable {
        Campaign storage campaign = campaignMap[campaignId];
        require(campaign.id != bytes32(0));
        require(campaign.cap >= campaign.totalAmount + msg.value);
        require(now >= campaign.startTime && now <= campaign.endTime);

        Donate[] storage donateList = donateListMap[campaignId][msg.sender];
        donateList.length += 1;
        Donate storage donate = donateList[donateList.length - 1];
        donate.addr = msg.sender;
        donate.amount = msg.value;
        campaign.donorAddressList.push(msg.sender);
        campaign.totalAmount += msg.value;

        emit DoDonate(campaignId, msg.sender, msg.value);
    }

    function refund(bytes32 campaignId) public {
        Campaign storage campaign = campaignMap[campaignId];
        require(campaign.id != bytes32(0));
        require(campaign.cap > campaign.totalAmount);
        require(now > campaign.endTime);

        Donate[] storage donateList = donateListMap[campaignId][msg.sender];
        require(donateList.length > 0);

        for (uint i = 0; i < donateList.length; i++) {
            uint256 amount = donateList[i].amount;
            msg.sender.transfer(amount);
            donateList[i].refunded = true;

            emit Refund(campaignId, msg.sender, amount);
        }
    }

    function createInvoice(bytes32 campaignId, bytes32 productId) public {
        Campaign storage campaign = campaignMap[campaignId];
        require(campaign.id != bytes32(0));

        Invoice storage invoice = invoiceMap[campaignId][productId];
        require(invoice.productId == bytes32(0));

        invoice.productId = productId;
        invoice.step = 1;

        emit CreateInvoice(campaignId, productId, 1);
    }
    function getInvoiceStepByCampaign(bytes32 campaignId, bytes32 productId) public view returns(uint8){


        Invoice storage invoice = invoiceMap[campaignId][productId];

        return invoice.step;

    }
    function submitReceipt(
        bytes32 campaignId,
        bytes32 productId,
        uint8 _v,
        bytes32 _r,
        bytes32 _s
    ) public {
        require(campaignMap[campaignId].targetAddress == msg.sender);
        Invoice storage invoice = invoiceMap[campaignId][productId];
        require(invoice.step == 1);

        bytes32 message = keccak256(abi.encodePacked(campaignId, productId, msg.sender));
        bytes32 prefixedHash = keccak256(abi.encodePacked("\x19Ethereum Signed Message:\n", "32", message));
        address signer = ecrecover(prefixedHash, _v, _r, _s);

        ProductContract.Product memory product = productContract.getProduct(productId);
        require(product.supplier == signer);
        invoice.step=2;
        emit Submit(campaignId,productId,2);
    }


    function withdrawal(bytes32 campaignId,bytes32 productId) public {
        Invoice storage invoice = invoiceMap[campaignId][productId];
        bytes32 orgProductId=invoice.productId;
        require(orgProductId != bytes32(0) && invoice.step==2);
        ProductContract.Product memory product = productContract.getProduct(orgProductId);
        require(product.supplier == msg.sender);
        msg.sender.transfer(product.price);
        invoice.step=3;
        emit DoWithdrawal(campaignId, productId, 3);
    }
    function uint2str(uint i) internal pure returns (string memory) {
        if (i == 0) return "0";
        uint j = i;
        uint length;
        while (j != 0){
            length++;
            j /= 10;
        }
        bytes memory bstr = new bytes(length);
        uint k = length - 1;
        while (i != 0){
            bstr[k--] = byte(uint8(48 + i % 10));
            i /= 10;
        }
        return string(bstr);
    }
}
