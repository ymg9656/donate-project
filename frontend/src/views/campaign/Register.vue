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
                <h1 id="forms" class="pull-left text-white" style="color: #ffff;">캠페인 등록</h1>
                <div style="clear: both;"></div>
            </div>

            <div class="row justify-content-center">
                <div class="col-lg-12">
                    <card type="secondary" shadow
                          header-classes="bg-white pb-5"
                          body-classes="px-lg-12 py-lg-12"
                          class="border-0">
                        <template>
                            <form role="form" name="registerForm" id="registerForm" action="/rest/member/register"
                                  method="POST" onsubmit="return false;">

                                <div class="input-group input-group-alternative mb-3">
                                    <select class="form-control" id="sampleSelect" ref="sampleData"  v-on:change="sampleChange()">
                                        <option value="-1">샘플 데이터를 선택하세요.</option>
                                        <option v-bind:value="index"  v-bind:key="sample" v-for="(sample,index) in data.sampleDataList">{{sample.name}}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="캠페인명" type="text" ref="name" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="캠페인 이미지 URL" type="text" ref="thumbnail">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <select class="form-control" name="target" ref="targetAddress">
                                            <option value="-1">후원 대상을 선택하세요.</option>
                                            <option v-bind:key="target" v-for="target in data.targetList" v-bind:value="target.addr">{{target.name}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <select class="form-control select2" name="product" id="product"
                                                multiple="multiple" ref="productSeqList">
                                            <option v-bind:key="product" v-for="product in data.productList" v-bind:value="product.id">{{product.name}}</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="목표금액" type="number" ref="cap" >
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="datefrom_group" readonly="readonly"
                                               size="24" placeholder="캠페인 기간" value="">
                                        <div class="input-group-btn custom-input-group-btn">
                                            <button class="btn btn_clear_date" type="button">X</button>
                                        </div>
                                        <input type="hidden" class="form-control" id="startYmd" name="startYmd"
                                               placeholder="yyyy-mm-dd" value="" ref="startTime">
                                        <input type="hidden" class="form-control" id="endYmd" name="endYmd"
                                               placeholder="yyyy-mm-dd" value="" ref="endTime">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="상품상세 이미지 URL" type="text" ref="contents" >
                                    </div>
                                </div>

                                <div class="pull-left">
                                    <button type="button" class="btn btn-default mt-4">목록</button>
                                </div>
                                <div class="pull-right">
                                    <button type="button" class="btn btn-primary mt-4" v-on:click="contractConnect()">
                                        등록
                                    </button>
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
    import initCampaignContract from "../../contract/initCampaignContract";
    import initProductContract from "../../contract/initProductContract";
    import initTargetContract from "../../contract/initTargetContract";
    import initWeb3 from "../../contract/initWeb3";

    export default {
        data() {
            return {
                data:{
                    targetList: [],
                    productList: [],
                    campaignContract:null,
                    productContract:null,
                    targetContract:null,
                    sampleDataList:[//세이브더칠드런 데이터
                        {
                            "targetAddress":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"홀로, 오롯이 아픔을 견디는 지온이",
                            "thumbnail":"https://www.sc.or.kr/upload/campaign/campaign_1587022944281.jpg",
                            "cap":3000000000000000000,
                            "contents":"https://www.sc.or.kr/upload/campaign/campaign_1587081501820.jpg"
                        },


                        {
                            "targetAddress":"0x5c2227605a689BfeEf2AD126cC53660F3d19e306",
                            "name":"까막눈 할배와 재윤이의 봄",
                            "thumbnail":"https://www.sc.or.kr/upload/campaign/campaign_1583309975531.jpg",
                            "cap":3000000000000000000,
                            "contents":"https://www.sc.or.kr/upload/campaign/campaign_1583309757954.jpg"
                        },
                        {

                            "targetAddress":"0xc6776Feb4ddc0ece798240aad63116B322C5c8f9",
                            "name":"여섯 살 서연이 이야기",
                            "thumbnail":"https://www.sc.or.kr/upload/campaign/campaign_1586390569310.jpg",
                            "cap":3000000000000000000,
                            "contents":"https://www.sc.or.kr/upload/Editor/2020/editor_1586390592895.jpg"
                        },
                    ],

                }
            };
        },

        async created(){
            await initWeb3();
            if(this.data.campaignContract==null){
                this.data.campaignContract=initCampaignContract(window.web3);
            }
            window.console.log("campaignContract: "+ this.data.campaignContract.options.from);
            if(this.data.productContract==null){
                this.data.productContract=initProductContract(window.web3);
            }
            if(this.data.targetContract==null){
                this.data.targetContract=initTargetContract(window.web3);
            }

            var productList=this.data.productList;
            this.data.productContract.methods.getProductList().call(function(error, result){
                for(var i =0; i<result.length;i++){
                    var target={
                        "id":result[i][0],
                        "supplier": result[i][1],
                        "name": result[i][2],
                        "thumbnail": result[i][3],
                        "price": result[i][4],
                        "contents":result[i][5],
                    };
                    productList.push(target);
                }
            });

            var targetList=this.data.targetList;
            this.data.targetContract.methods.getTargetList().call(function(error, result){
                window.console.log("getList "+ error);
                window.console.log("getList "+ result);
                window.console.log("getList "+ JSON.stringify(result));

                for(var i =0; i<result.length;i++){
                    var target={
                        "addr": result[i][0],
                        "name": result[i][1],
                        "thumbnail": result[i][2],
                        "gender": result[i][3],
                        "birthday": result[i][4],
                        "country": result[i][5],
                        "contents": result[i][6]
                    };
                    targetList.push(target);
                }
            });

        },
        mounted() {
            $(document).ready(function () {
                $('#datefrom_group').daterangepicker({
                    format: 'YYYY-MM-DD',
                    timeZone: '+09:00',
                    ranges: {
                        '오늘': [moment(), moment()],
                        '1주일': [moment().subtract(6, 'days'), moment()],
                        '1개월': [moment().subtract(29, 'days'), moment()],
                        '3개월': [moment().subtract(89, 'days'), moment()],
                        '6개월': [moment().subtract(179, 'days'), moment()],
                    },
                    maxDate: moment().add(6, "month"),
                    locale: daterangepickerlocales.kr
                });
                $('#datefrom_group').on('apply.daterangepicker', function (ev, picker) {
                    $('#startYmd').val(picker.startDate.format('YYYY-MM-DD'));
                    $('#endYmd').val(picker.endDate.format('YYYY-MM-DD'));
                });
                $("button.btn_clear_date").click(function () {
                    $("#datefrom_group").val('');
                    $("#startYmd").val('');
                    $("#endYmd").val('');
                });

                $("#product").select2({
                    placeholder: "캠페인 상품을 선택하세요.",
                });
            });
        },

        methods: {
            sampleChange:function(){


                var sampleDataObj=this.data.sampleDataList[$("#sampleSelect").val()];

                this.$refs.targetAddress.value=sampleDataObj.targetAddress;
                this.$refs.name.value=sampleDataObj.name;
                this.$refs.thumbnail.value=sampleDataObj.thumbnail;
                this.$refs.cap.value=sampleDataObj.cap;
                this.$refs.contents.value=sampleDataObj.contents;


                var startDt=moment().subtract(15, 'days').format("YYYY-MM-DD");
                var endDt=moment().add(15, 'days').format("YYYY-MM-DD");

                $("#datefrom_group").data('daterangepicker').setStartDate(startDt);
                $("#datefrom_group").data('daterangepicker').setEndDate(endDt);

                $('#startYmd').val(startDt);
                $('#endYmd').val(endDt);



            },
            contractConnect: function (){


                //address _targetAddress,  string memory _name, string memory _thumbnail,uint _cap, uint _startTime,uint _endTime ,string memory _contents,uint[] memory _productSeqList) public onlyOwner() {
                var _targetAddress=this.$refs.targetAddress.value;
                var _name=this.$refs.name.value;
                var _thumbnail=this.$refs.thumbnail.value;
                var _cap=this.$refs.cap.value;
                var _startTime=new Date(this.$refs.startTime.value).getTime()/1000;
                var _endTime=new Date(this.$refs.endTime.value).getTime()/1000;
                var _contents=this.$refs.contents.value;
                var _productSeqList=$("#product").val();
                window.console.log(_productSeqList);
                this.data.campaignContract.methods.addCampaign(_targetAddress,_name,_thumbnail,_cap,_startTime,_endTime,_contents,_productSeqList).send()
                    .on('transactionHash', function(hash){
                        window.console.log("hash: "+hash);
                    })
                    .on('receipt', function(receipt){
                        window.console.log("receipt: "+receipt);
                        alert("정상 등록 되었습니다.");
                        location.href="#/campaign";
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
