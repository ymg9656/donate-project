// const ProductContract = artifacts.require("ProductContract");
// const TargetContract = artifacts.require("TargetContract");
const CampaignContract = artifacts.require("CampaignContract");

module.exports = function(deployer) {
  // const productContractAddress = ProductContract.address;
  // const targetContractAddress = TargetContract.address;
  // return deployer.deploy(CampaignContract, productContractAddress, targetContractAddress);
  return deployer.deploy(CampaignContract);
};
