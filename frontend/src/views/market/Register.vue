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
                <h1 id="forms" class="pull-left text-white" style="color: #ffff;">상품 등록</h1>
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





    export default {
        mounted() {

        },
        methods: {
            contractConnect: function (){
                if(productContract==null){
                    initProductContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }


                //addProduct(address _supplier,  string memory _name, string memory _thumbnail,uint _price, string memory _contents) public {
                var _supplier=this.$refs.supplier.value;
                var _name=this.$refs.name.value;
                var _thumbnail=this.$refs.thumbnail.value;
                var _price=this.$refs.price.value;
                var _contents=this.$refs.contents.value;

                productContract.methods.addProduct(_supplier,_name,_thumbnail,_price,_contents).send({from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B'})
                    .on('transactionHash', function(hash){
                        window.console.log("hash: "+hash);
                    })
                    .on('receipt', function(receipt){
                        window.console.log("receipt: "+receipt);
                    })
                    .on('confirmation', function(confirmationNumber, receipt){
                        window.console.log("confirmationNumber: "+ confirmationNumber);
                        window.console.log("receipt: "+receipt);
                        alert("정상 등록 되었습니다.");
                        location.href="http://localhost:8080/vue/index.html#/product";
                    })
                    .on('error', window.console.log(console.error));
            },
        }
    };
</script>
<style>
</style>
