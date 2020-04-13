pragma solidity ^0.5.16;
pragma experimental ABIEncoderV2;

import "@openzeppelin/contracts/ownership/Ownable.sol";


contract TargetContract is Ownable {
    address[] targetAddressList;
    mapping (address => Target) targetMap;

    event AddTarget(address indexed addr, string name);

    struct Target {
        address addr;//기부대상
        string name;//이름
        string thumbnail;//썸네일 URL
        string gender;//성별(남,여)
        string birthday;//생년월일(yyyy.MM.dd)
        string country;//국가
        string contents;//상세소개 이미지 URL
    }

    function addTarget(
        address _addr,
        string memory _name,
        string memory _thumbnail,
        string memory _gender,
        string memory _birthday,
        string memory _country,
        string memory _contents
    ) public onlyOwner() {

        Target memory t = Target(_addr, _name, _thumbnail, _gender, _birthday, _country, _contents);

        targetAddressList.push(_addr);
        targetMap[_addr] = t;

        emit AddTarget(t.addr, t.name);
    }

    function getTarget(address _targetAddress) public view returns (Target memory) {
        Target memory t = targetMap[_targetAddress];
        return t;
    }

    function getTargetList() public view returns (Target[] memory) {
        uint arrayLength = targetAddressList.length;
        Target[] memory targetList = new Target[](arrayLength);

        for (uint i = 0; i < arrayLength; i++) {
            Target memory t=targetMap[targetAddressList[i]];
            targetList[i] = t;
        }
        return targetList;
    }
}
