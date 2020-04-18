export default function initProductContract(WEB3){

    const json=require("./ProductContract");
    const productContract = new WEB3.eth.Contract(json.abi);
    productContract.options.address = json.networks["5777"].address;
    productContract.options.gasPrice ='20000000000';
    productContract.options.from =WEB3.eth.defaultAccount;
    return productContract;

}

