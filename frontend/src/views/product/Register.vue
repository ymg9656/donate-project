<template>
    <section class="section section-shaped section-lg my-0">
        <div class="shape shape-style-1 bg-gradient-default">
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
        </div>
        <div class="container pt-lg-md">
            <div class="header">
                <h1 id="forms" class="pull-left text-white" style="color: #ffff;">물품 등록</h1>
                <div style="clear: both;"></div>
            </div>


            <div class="row justify-content-center">
                <div class="col-lg-12">
                    <card type="secondary" shadow
                          header-classes="bg-white pb-5"
                          body-classes="px-lg-12 py-lg-12"
                          class="border-0">
                        <template>
                            <form role="form" name="registerForm" id="registerForm" action="/rest/member/register" method="POST" onsubmit="return false;">

                                <div class="input-group input-group-alternative mb-3">
                                    <select class="form-control" id="sampleSelect" ref="sampleData"  v-on:change="sampleChange()">
                                        <option value="-1">샘플 데이터를 선택하세요.</option>
                                        <option v-bind:value="index"  v-bind:key="sample" v-for="(sample,index) in data.sampleDataList">{{sample.name}}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="이더리움 계정" type="text" ref="supplier">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="상품명" type="text" ref="name">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="상품 이미지 URL" type="text" ref="thumbnail">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="금액" type="text" ref="price">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="상품상세 이미지 URL" type="text" ref="contents">
                                    </div>
                                </div>

                                <div class="pull-left">
                                    <button type="button" class="btn btn-default mt-4" >목록</button>
                                </div>
                                <div class="pull-right">
                                    <button type="button" class="btn btn-primary mt-4" v-on:click="contractConnect()">등록</button>
                                </div>

                            </form>
                        </template>
                    </card>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
    import axios from 'axios';
    import Web3 from 'web3';
    import initProductContract from "../../contract/initProductContract";
    import initWeb3 from "../../contract/initWeb3";






    export default {
        data() {
            return {
                data:{
                    productContract: null,
                    sampleDataList:[//네이버 쇼핑 데이터
                        {
                            "supplier":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"[크리넥스]★무료베송★ 크리넥스/마이비데/스카트 생필품특가전",
                            "thumbnail":"http://image2.lotteimall.com/goods/75/71/43/1226437175_L.jpg",
                            "price":500000000000000000,
                            "contents":"http://www.unitoa.com/img/kleenex/KN_bottom.jpg"
                        },
                        {
                            "supplier":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"아동용 일회용 마스크 50매 어린이 소형",
                            "thumbnail":"https://shop-phinf.pstatic.net/20200409_117/1586393306188JbO90_JPEG/23755639759781867_1982667702.jpg?type=m510",
                            "price":300000000000000000,
                            "contents":"https://shop-phinf.pstatic.net/20200401_80/15857018206601AecF_JPEG/%BC%D2%BE%C6%B8%B6%BD%BA%C5%A92.jpg?type=w860"
                        },
                        {
                            "supplier":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"신학기 캐릭터 문구세트/초등학교 필통 학용품세트",
                            "thumbnail":"https://shop-phinf.pstatic.net/20200129_180/15802781513125OnWu_JPEG/17630776971659014_943406410.jpg?type=m510",
                            "price":1000000000000000000,
                            "contents":"http://gi.esmplus.com/plusworld/sy/ch/intro.jpg"
                        },
                    ],
                }
            }
        },
        mounted() {

        },
        methods: {
            sampleChange:function(){

                var sampleDataObj=this.data.sampleDataList[$("#sampleSelect").val()];
                window.console.log("sampleDataObj "+sampleDataObj);

                this.$refs.supplier.value=sampleDataObj.supplier;
                this.$refs.name.value=sampleDataObj.name;
                this.$refs.thumbnail.value=sampleDataObj.thumbnail;
                this.$refs.price.value=sampleDataObj.price;
                this.$refs.contents.value=sampleDataObj.contents;

            },
            contractConnect: async function (){

                await initWeb3();
                if(this.data.productContract==null){
                    this.data.productContract=initProductContract(window.web3);
                }



                //addProduct(address _supplier,  string memory _name, string memory _thumbnail,uint _price, string memory _contents) public {
                var _supplier=this.$refs.supplier.value;
                var _name=this.$refs.name.value;
                var _thumbnail=this.$refs.thumbnail.value;
                var _price=this.$refs.price.value;
                var _contents=this.$refs.contents.value;

                this.data.productContract.methods.addProduct(_supplier,_name,_thumbnail,_price,_contents).send()
                    .on('transactionHash', function(hash){
                        window.console.log("hash: "+hash);
                    })
                    .on('receipt', function(receipt){
                        window.console.log("receipt: "+receipt);
                        alert("정상 등록 되었습니다.");
                        location.href="http://localhost:8080/vue/index.html#/product";
                    })
                    .on('confirmation', function(confirmationNumber, receipt){
                        window.console.log("confirmationNumber: "+ confirmationNumber);
                        window.console.log("receipt: "+receipt);

                    })
                    .on('error', window.console.log(console.error));
            },
        }
    };
</script>
<style>
</style>
