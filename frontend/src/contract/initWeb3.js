import Web3 from "web3";

export default async function initWeb3(){
    // Modern dapp browsers...
    if (window.ethereum) {
        window.web3 = new Web3(ethereum);
        try {
            // Request account access if needed
            await ethereum.enable();

            const accounts=await web3.eth.getAccounts();
            window.web3.eth.defaultAccount = accounts[0];
            window.console.log("defaultAccount "+window.web3.eth.defaultAccount);
            } catch (error) {
            // User denied account access...
            alert(error);
        }
    }
    // Legacy dapp browsers...
    else if (window.web3) {
        window.web3 = new Web3(web3.currentProvider);
    }
    // Non-dapp browsers...
    else {
        console.log('Non-Ethereum browser detected. You should consider trying MetaMask!');
    }
}

