const TargetContract = artifacts.require("TargetContract");

module.exports = function(deployer) {
  return deployer.deploy(TargetContract);
};
