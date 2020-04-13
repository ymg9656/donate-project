pragma solidity ^0.5.16;
pragma experimental ABIEncoderV2;


contract ProductContract {

    bytes32[] public productIdList;
    mapping (bytes32 => Product) internal productMap;

    event AddProduct(bytes32 indexed id, address supplier, string name, uint price);

    struct Product {
        bytes32 id;
        address supplier;//판매자
        string name;//상품명
        string thumbnail;//상품 썸네일 URL
        uint price;//상품금액 wei
        string contents;//상품 상세소개 이미지 URL
    }

    function addProduct(
        address _supplier,
        string memory _name,
        string memory _thumbnail,
        uint _price,
        string memory _contents
    ) public {
        bytes32 id = keccak256(abi.encodePacked(now, _supplier, _name, _price));

        Product memory pc = Product(id, _supplier, _name, _thumbnail, _price, _contents);

        productIdList.push(id);
        productMap[id] = pc;

        emit AddProduct(pc.id, pc.supplier, pc.name, pc.price);
    }

    function getProduct(bytes32 productId) public view returns (Product memory) {
        return productMap[productId];
    }

    function getProductList() public view returns (Product[] memory) {
        uint arrayLength = productIdList.length;
        Product[] memory productList = new Product[](arrayLength);

        for (uint i = 0; i < arrayLength; i++) {
            Product memory pc = productMap[productIdList[i]];
            productList[i] = pc;
        }
        return productList;
    }

    function getProductList(bytes32[] memory productIdList) public view returns (Product[] memory) {
        Product[] memory productList = new Product[](productIdList.length);

        for (uint i = 0; i < productIdList.length; i++) {
            Product memory pc = productMap[productIdList[i]];
            productList[i] = pc;
        }
        return productList;
    }
}
