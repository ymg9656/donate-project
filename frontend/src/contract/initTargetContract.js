export default function initTargetContract(WEB3){
    const json=require("./TargetContract");
    const targetContract = new WEB3.eth.Contract(json.abi);
    targetContract.options.address = json.networks["5777"].address;
    targetContract.options.from =WEB3.eth.defaultAccount;
    return targetContract;
}