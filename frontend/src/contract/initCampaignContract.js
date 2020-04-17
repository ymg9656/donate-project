export default function initCampaignContract(WEB3){
    const campaignContract = new WEB3.eth.Contract([
        {
            "inputs": [],
            "payable": false,
            "stateMutability": "nonpayable",
            "type": "constructor"
        },
        {
            "anonymous": false,
            "inputs": [
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "id",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "address",
                    "name": "targetAddress",
                    "type": "address"
                },
                {
                    "indexed": false,
                    "internalType": "string",
                    "name": "name",
                    "type": "string"
                }
            ],
            "name": "AddCampaign",
            "type": "event"
        },
        {
            "anonymous": false,
            "inputs": [
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "productId",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "uint8",
                    "name": "step",
                    "type": "uint8"
                }
            ],
            "name": "CreateInvoice",
            "type": "event"
        },
        {
            "anonymous": false,
            "inputs": [
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "address",
                    "name": "donor",
                    "type": "address"
                },
                {
                    "indexed": false,
                    "internalType": "uint256",
                    "name": "amount",
                    "type": "uint256"
                }
            ],
            "name": "DoDonate",
            "type": "event"
        },
        {
            "anonymous": false,
            "inputs": [
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "productId",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "uint8",
                    "name": "step",
                    "type": "uint8"
                }
            ],
            "name": "DoWithdrawal",
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
            "anonymous": false,
            "inputs": [
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "address",
                    "name": "donor",
                    "type": "address"
                },
                {
                    "indexed": false,
                    "internalType": "uint256",
                    "name": "amount",
                    "type": "uint256"
                }
            ],
            "name": "Refund",
            "type": "event"
        },
        {
            "anonymous": false,
            "inputs": [
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "bytes32",
                    "name": "productId",
                    "type": "bytes32"
                },
                {
                    "indexed": false,
                    "internalType": "uint8",
                    "name": "step",
                    "type": "uint8"
                }
            ],
            "name": "Submit",
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
            "constant": true,
            "inputs": [],
            "name": "productContract",
            "outputs": [
                {
                    "internalType": "contract ProductContract",
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
            "constant": true,
            "inputs": [],
            "name": "targetContract",
            "outputs": [
                {
                    "internalType": "contract TargetContract",
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
                    "name": "_targetAddress",
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
                    "internalType": "uint256",
                    "name": "_cap",
                    "type": "uint256"
                },
                {
                    "internalType": "uint256",
                    "name": "_startTime",
                    "type": "uint256"
                },
                {
                    "internalType": "uint256",
                    "name": "_endTime",
                    "type": "uint256"
                },
                {
                    "internalType": "string",
                    "name": "_contents",
                    "type": "string"
                },
                {
                    "internalType": "bytes32[]",
                    "name": "_productIdList",
                    "type": "bytes32[]"
                }
            ],
            "name": "addCampaign",
            "outputs": [],
            "payable": false,
            "stateMutability": "nonpayable",
            "type": "function"
        },
        {
            "constant": true,
            "inputs": [],
            "name": "getCampaignList",
            "outputs": [
                {
                    "components": [
                        {
                            "internalType": "bytes32",
                            "name": "id",
                            "type": "bytes32"
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
                            "internalType": "uint256",
                            "name": "cap",
                            "type": "uint256"
                        },
                        {
                            "internalType": "uint256",
                            "name": "totalAmount",
                            "type": "uint256"
                        },
                        {
                            "internalType": "uint256",
                            "name": "startTime",
                            "type": "uint256"
                        },
                        {
                            "internalType": "uint256",
                            "name": "endTime",
                            "type": "uint256"
                        },
                        {
                            "internalType": "string",
                            "name": "contents",
                            "type": "string"
                        },
                        {
                            "internalType": "address",
                            "name": "targetAddress",
                            "type": "address"
                        },
                        {
                            "internalType": "bytes32[]",
                            "name": "productIdList",
                            "type": "bytes32[]"
                        },
                        {
                            "internalType": "address[]",
                            "name": "donorAddressList",
                            "type": "address[]"
                        }
                    ],
                    "internalType": "struct CampaignContract.Campaign[]",
                    "name": "",
                    "type": "tuple[]"
                }
            ],
            "payable": false,
            "stateMutability": "view",
            "type": "function"
        },
        {
            "constant": true,
            "inputs": [
                {
                    "internalType": "bytes32",
                    "name": "_id",
                    "type": "bytes32"
                }
            ],
            "name": "getCampaign",
            "outputs": [
                {
                    "components": [
                        {
                            "internalType": "bytes32",
                            "name": "id",
                            "type": "bytes32"
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
                            "internalType": "uint256",
                            "name": "cap",
                            "type": "uint256"
                        },
                        {
                            "internalType": "uint256",
                            "name": "totalAmount",
                            "type": "uint256"
                        },
                        {
                            "internalType": "uint256",
                            "name": "startTime",
                            "type": "uint256"
                        },
                        {
                            "internalType": "uint256",
                            "name": "endTime",
                            "type": "uint256"
                        },
                        {
                            "internalType": "string",
                            "name": "contents",
                            "type": "string"
                        },
                        {
                            "internalType": "address",
                            "name": "targetAddress",
                            "type": "address"
                        },
                        {
                            "internalType": "bytes32[]",
                            "name": "productIdList",
                            "type": "bytes32[]"
                        },
                        {
                            "internalType": "address[]",
                            "name": "donorAddressList",
                            "type": "address[]"
                        }
                    ],
                    "internalType": "struct CampaignContract.Campaign",
                    "name": "",
                    "type": "tuple"
                }
            ],
            "payable": false,
            "stateMutability": "view",
            "type": "function"
        },
        {
            "constant": false,
            "inputs": [
                {
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                }
            ],
            "name": "donate",
            "outputs": [],
            "payable": true,
            "stateMutability": "payable",
            "type": "function"
        },
        {
            "constant": false,
            "inputs": [
                {
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                }
            ],
            "name": "refund",
            "outputs": [],
            "payable": false,
            "stateMutability": "nonpayable",
            "type": "function"
        },
        {
            "constant": false,
            "inputs": [
                {
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "internalType": "bytes32",
                    "name": "productId",
                    "type": "bytes32"
                }
            ],
            "name": "createInvoice",
            "outputs": [],
            "payable": false,
            "stateMutability": "nonpayable",
            "type": "function"
        },
        {
            "constant": true,
            "inputs": [
                {
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "internalType": "bytes32",
                    "name": "productId",
                    "type": "bytes32"
                }
            ],
            "name": "getInvoiceStepByCampaign",
            "outputs": [
                {
                    "internalType": "uint8",
                    "name": "",
                    "type": "uint8"
                }
            ],
            "payable": false,
            "stateMutability": "view",
            "type": "function"
        },
        {
            "constant": false,
            "inputs": [
                {
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "internalType": "bytes32",
                    "name": "productId",
                    "type": "bytes32"
                },
                {
                    "internalType": "uint8",
                    "name": "_v",
                    "type": "uint8"
                },
                {
                    "internalType": "bytes32",
                    "name": "_r",
                    "type": "bytes32"
                },
                {
                    "internalType": "bytes32",
                    "name": "_s",
                    "type": "bytes32"
                }
            ],
            "name": "submitReceipt",
            "outputs": [],
            "payable": false,
            "stateMutability": "nonpayable",
            "type": "function"
        },
        {
            "constant": false,
            "inputs": [
                {
                    "internalType": "bytes32",
                    "name": "campaignId",
                    "type": "bytes32"
                },
                {
                    "internalType": "bytes32",
                    "name": "productId",
                    "type": "bytes32"
                }
            ],
            "name": "withdrawal",
            "outputs": [],
            "payable": false,
            "stateMutability": "nonpayable",
            "type": "function"
        }
    ]);
    campaignContract.options.address = "0x2FC55110B03a1ad260BA2259f3fD4cAD850A950A";
    campaignContract.options.gasPrice ='20000000000';
    campaignContract.options.from =WEB3.eth.defaultAccount;
    return campaignContract;
}

