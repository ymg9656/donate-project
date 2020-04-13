const Hello = artifacts.require("Hello");
require("chai").should();


contract("Hello",function(accounts){
  beforeEach(async function(){
      this.hello=await Hello.new();
  });

   it("should set owner", async function(){
       const owner=await this.hello.owner();
       owner.should.be.eq(accounts[0]);
   });
});