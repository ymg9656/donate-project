const ProductContract = artifacts.require("ProductContract");

module.exports = function(deployer) {
  return deployer.deploy(ProductContract);
};
