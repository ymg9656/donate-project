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
                <h1 id="forms" class="pull-left text-white" style="color: #ffff;">후원대상 등록</h1>
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
                                        <input class="form-control" placeholder="이더리움 계정" type="text" ref="addr">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="이름" type="text" ref="name">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="프로필 이미지 URL" type="text" ref="thumbnail">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="생년월일(xxxx-xx-xx)" type="text" ref="birthday">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <select class="form-control" ref="gender">
                                            <option value="0">성별을 선택하세요.</option>
                                            <option value="남">남</option>
                                            <option value="여">여</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <select class="form-control" ref="country">
                                            <option value="0">국가를 선택하세요.</option>
                                            <option value="대한민국">대한민국</option>
                                            <option value="가나">가나</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="상세내용 이미지 URL" type="text" ref="contents">
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
                if(initTargetContract==null){
                    initTargetContract(new Web3(new Web3.providers.HttpProvider('http://localhost:8545')));
                }

                var _addr=this.$refs.addr.value;
                var _thumbnail=this.$refs.thumbnail.value;
                var _name=this.$refs.name.value;
                var _gender=this.$refs.gender.value;
                var _birthday=this.$refs.birthday.value;
                var _country=this.$refs.country.value;
                var _contents=this.$refs.contents.value;

                initTargetContract.methods.addTarget(_addr,_name,_thumbnail,_gender,_birthday,_country,_contents).send({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'})
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
                        location.href="http://localhost:8080/vue/index.html#/target";
                    })
                    .on('error', window.console.log(console.error));
            },
        }
    };
</script>
<style>
</style>
