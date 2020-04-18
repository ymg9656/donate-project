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
                            <form role="form" name="registerForm" id="registerForm" onsubmit="return false;">
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <select class="form-control" id="sampleSelect" ref="sampleData"  v-on:change="sampleChange()">
                                            <option value="-1">샘플 데이터를 선택하세요.</option>
                                            <option v-bind:value="index"  v-bind:key="sample" v-for="(sample,index) in data.sampleDataList">{{sample.name}}</option>
                                        </select>
                                    </div>
                                </div>
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
    import initWeb3 from "../../contract/initWeb3";
    import initTargetContract from "../../contract/initTargetContract";





    export default {
        data() {
            return {
                data:{
                    targetContract:null,
                    sampleDataList:[//세이브더칠드런 데이터
                        {
                            "addr":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"김지온",
                            "thumbnail":"https://www.sc.or.kr/upload/campaign/campaign_1587022944281.jpg",
                            "gender":"여",
                            "birthday":"2016.03.15",
                            "country":"대한민국",
                            "contents":"https://www.sc.or.kr/upload/campaign/campaign_1587081446561.jpg"
                        },
                        {
                            "addr":"0x5c2227605a689BfeEf2AD126cC53660F3d19e306",
                            "name":"김재윤",
                            "thumbnail":"https://www.sc.or.kr/upload/campaign/campaign_1583309975531.jpg",
                            "gender":"남",
                            "birthday":"2010.05.15",
                            "country":"대한민국",
                            "contents":"https://www.sc.or.kr/upload/campaign/campaign_1583309753392.jpg"
                        },
                        {
                            "addr":"0xc6776Feb4ddc0ece798240aad63116B322C5c8f9",
                            "name":"이서연",
                            "thumbnail":"https://www.sc.or.kr/upload/campaign/campaign_1586390569310.jpg",
                            "gender":"여",
                            "birthday":"2012.05.08",
                            "country":"대한민국",
                            "contents":"https://www.sc.or.kr/upload/Editor/2020/editor_1586390586918.jpg"
                        },
                        ]
                }
            };
        },
        mounted() {

        },
        methods: {
            sampleChange:function(){

                var sampleDataObj=this.data.sampleDataList[$("#sampleSelect").val()];
                window.console.log("sampleDataObj "+sampleDataObj);
                this.$refs.addr.value=sampleDataObj.addr;
                this.$refs.thumbnail.value=sampleDataObj.thumbnail;
                this.$refs.name.value=sampleDataObj.name;
                this.$refs.gender.value=sampleDataObj.gender;
                this.$refs.birthday.value=sampleDataObj.birthday;
                this.$refs.country.value=sampleDataObj.country;
                this.$refs.contents.value=sampleDataObj.contents;

            },
            contractConnect: async function (){

                await initWeb3();
                if(this.data.targetContract==null){
                    this.data.targetContract=initTargetContract(window.web3);
                }


                var _addr=this.$refs.addr.value;
                var _thumbnail=this.$refs.thumbnail.value;
                var _name=this.$refs.name.value;
                var _gender=this.$refs.gender.value;
                var _birthday=this.$refs.birthday.value;
                var _country=this.$refs.country.value;
                var _contents=this.$refs.contents.value;

                this.data.targetContract.methods.addTarget(_addr,_name,_thumbnail,_gender,_birthday,_country,_contents).send()
                    .on('transactionHash', function(hash){
                        window.console.log("hash: "+hash);
                    })
                    .on('receipt', function(receipt){
                        window.console.log("receipt: "+receipt);
                        alert("정상 등록 되었습니다.");
                        location.href="http://localhost:8080/vue/index.html#/target";
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
