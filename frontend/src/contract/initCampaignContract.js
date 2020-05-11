export default function initCampaignContract(WEB3){
    const json=require("./CampaignContract");
    const campaignContract = new WEB3.eth.Contract(json.abi);
    campaignContract.options.address = json.networks["5777"].address;
    campaignContract.options.from =WEB3.eth.defaultAccount;
    return campaignContract;
}

