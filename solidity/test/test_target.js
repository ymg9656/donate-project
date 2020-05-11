const Target = artifacts.require("TargetContract");
require("chai").should();


contract("TargetContract",function(accounts){
  beforeEach(async function(){
      this.target=await Target.new();
  });

   it("should set owner", async function(){
       const owner=await this.target.owner();
       owner.should.be.eq(accounts[0]);
   });

    it("should set target", async function(){

        var newTarget = {
            "addr":"0xEEd6DAA60E941b03B7a4239C7eEAA2CB5783BaC8",
            "name":"김지온",
            "thumbnail":"https://www.sc.or.kr/upload/campaign/campaign_1587022944281.jpg",
            "gender":"여",
            "birthday":"2016.03.15",
            "country":"대한민국",
            "contents":"https://www.sc.or.kr/upload/campaign/campaign_1587081446561.jpg"
        }


        const owner=await this.target.addTarget(newTarget.addr
            ,newTarget.name
            ,newTarget.thumbnail
            ,newTarget.gender
            ,newTarget.birthday
            ,newTarget.country
            ,newTarget.contents
            ,{from:accounts[0]});


        var getTarget = await this.target.getTarget('0xEEd6DAA60E941b03B7a4239C7eEAA2CB5783BaC8',{from:accounts[0]});
        assert.equal(newTarget.addr, getTarget.addr, "target should match");

    });
});