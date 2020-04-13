var contractWeb=null;
var targetContract=null;
var targetAbi= [
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": true,
                "internalType": "address",
                "name": "addr",
                "type": "address"
            },
            {
                "indexed": false,
                "internalType": "string",
                "name": "name",
                "type": "string"
            }
        ],
        "name": "AddTarget",
        "type": "event"
    },
    {
        "anonymous": false,
        "inputs": [
            {
                "indexed": true,
                "internalType": "address",
                "name": "previousOwner",
                "type": "address"
            },
            {
                "indexed": true,
                "internalType": "address",
                "name": "newOwner",
                "type": "address"
            }
        ],
        "name": "OwnershipTransferred",
        "type": "event"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "isOwner",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "owner",
        "outputs": [
            {
                "internalType": "address",
                "name": "",
                "type": "address"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [],
        "name": "renounceOwnership",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "internalType": "address",
                "name": "newOwner",
                "type": "address"
            }
        ],
        "name": "transferOwnership",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "internalType": "address",
                "name": "_addr",
                "type": "address"
            },
            {
                "internalType": "string",
                "name": "_name",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "_thumbnail",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "_gender",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "_birthday",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "_country",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "_contents",
                "type": "string"
            }
        ],
        "name": "addTarget",
        "outputs": [],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "address",
                "name": "_targetAddress",
                "type": "address"
            }
        ],
        "name": "getTarget",
        "outputs": [
            {
                "components": [
                    {
                        "internalType": "address",
                        "name": "addr",
                        "type": "address"
                    },
                    {
                        "internalType": "string",
                        "name": "name",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "thumbnail",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "gender",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "birthday",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "country",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "contents",
                        "type": "string"
                    }
                ],
                "internalType": "struct TargetContract.Target",
                "name": "",
                "type": "tuple"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "getTargetList",
        "outputs": [
            {
                "components": [
                    {
                        "internalType": "address",
                        "name": "addr",
                        "type": "address"
                    },
                    {
                        "internalType": "string",
                        "name": "name",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "thumbnail",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "gender",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "birthday",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "country",
                        "type": "string"
                    },
                    {
                        "internalType": "string",
                        "name": "contents",
                        "type": "string"
                    }
                ],
                "internalType": "struct TargetContract.Target[]",
                "name": "",
                "type": "tuple[]"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    }
];
function initTargetContract(WEB3){
    contractWeb = WEB3;
    targetContract = new contractWeb.eth.Contract(targetAbi);
    targetContract.options.address = "0x2A8974F673345644a6564f32E9b521C31FBA9187";
    targetContract.options.gasPrice ='20000000000';
    targetContract.options.gas =6721975;
}

