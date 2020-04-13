<template>
    <div class="profile-page">
        <section class="section-profile-cover section-shaped my-0">
            <div class="shape shape-style-1 shape-primary shape-skew alpha-4">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </div>
        </section>
        <section class="section section-skew">
            <div class="container">
                <card shadow class="card-profile mt--300" no-body>
                    <div class="px-4">
                        <div class="row justify-content-center">
                            <div class="col-lg-12 order-lg-1">
                                <div class="card-profile-stats d-flex justify-content-center">
                                    <div>
                                        <span class="heading">모집기간 - {{data.campaignDetail[0].d_day}}</span>
                                        <span class="description">{{data.campaignDetail[0].startTimeStr}} ~ {{data.campaignDetail[0].endTimeStr}}</span>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <div class="col-lg-12 order-lg-1">
                                <img style="width:100%;" v-bind:src="data.campaignDetail[0].thumbnail">
                            </div>
                            <div class="col-lg-12 order-lg-1">
                                <div class="text-left">
                                    <h3>{{data.campaignDetail[0].name}}</h3>
                                </div>
                            </div>



                            <div class="col-lg-12 order-lg-1">
                                <div class="progress-wrapper">
                                    <div class="progress-success">
                                        <div class="progress-label"><span>목표금액 : {{data.campaignDetail[0].cap}}</span></div>
                                    </div>
                                    <div class="progress" style="height: 8px;margin: 1rem 0 0 0; ">
                                        <div role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                             aria-valuemax="100" class="progress-bar bg-success"
                                             v-bind:aria-valuenow="data.campaignDetail[0].capPercent" v-bind:style="{'width': data.campaignDetail[0].capPercent+'px;'}"></div>
                                    </div>

                                    <div class="progress-percentage">
                                        <span class="pull-left">{{data.campaignDetail[0].totalAmount}}</span>
                                        <span>{{data.campaignDetail[0].capPercent}}</span>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="mt-5 border-top text-center">
                            <tabs fill class="flex-column flex-md-row">
                                <card shadow>
                                    <tab-pane>
                                        <span slot="title">소개</span>
                                        <div class="nation_info fn_country_info" id="countryInfo">
                                            <!-- 에티오피아 -->
                                            <img style="width:100%;" v-bind:src="data.campaignDetail[0].contents">
                                        </div>
                                    </tab-pane>

                                    <tab-pane title="Profile">
                                        <span slot="title">후원대상</span>
                                        <div class="col-lg-12 margin-bottom-20" v-if="data.targetDetail.length > 0">
                                            <div class="card border-0 card-lift--hover shadow"><!---->
                                                <div class="card-body py-5">
                                                    <span class="pic" v-bind:style="{'background-image': 'url('+data.targetDetail[0].thumbnail+')'}" style="background-repeat: no-repeat; background-position: center top; background-size: 150px; ">양육어린이사진</span>
                                                    <h6 class="text-primary text-uppercase">{{data.targetDetail[0].name}}({{data.targetDetail[0].country}})</h6>
                                                    <small>생년월일: {{data.targetDetail[0].birthday}} | 성별: {{data.targetDetail[0].gender}}</small>
                                                </div>
                                                <div class="card-body py-5">
                                                    <img style="width:100%;" v-bind:src="data.targetDetail[0].contents">
                                                </div>
                                            </div>
                                        </div>
                                    </tab-pane>

                                    <tab-pane>
                                        <span slot="title">후원물품</span>
                                        <div class="row justify-content-left">



                                            <div class="col-lg-4 margin-bottom-10" v-for="product in data.productList">
                                                <div class="card border-0 shadow"><!---->
                                                    <div class="card-body">
                                                        <span class="pic" v-bind:style="{'background-image': 'url('+product.thumbnail+')'}" style="background-repeat: no-repeat; background-position: center top; background-size: 150px; ">{{product.name}}</span>
                                                        <h6 class="text-primary text-uppercase text-left">{{product.name}}</h6>
                                                        <p class="description mt-3 txt-overflow-3  text-left text-danger">{{product.price}}원</p>

                                                        <button  type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" v-on:click="openContents(product.contents)">상세보기</button>
                                                    </div><!----><!----></div>
                                            </div>
                                        </div>
                                    </tab-pane>
                                </card>
                            </tabs>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a type="" href="/vue/index.html#/donate/detail?testData=1234" class="btn mt-4 btn-danger"
                           style="width: 100%;">
                            기부하기
                        </a>
                    </div>
                </card>
            </div>


            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">상세보기</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <img id="contentsImg" style="width: 100%;height:100%;">
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>
<script>
    import Web3 from "web3";

    export default {
        data() { 
            return {
                data:{
                    campaignDetail: [
                        {
                            "id": "sample",
                            "name":"sample",
                            "thumbnail": "sample",
                            "cap": 0,
                            "totalAmount": 0,
                            "startTime": 0,
                            "endTime": 0,
                            "startTimeStr": "sample",
                            "endTimeStr": "sample",
                            "d_day": "sample",
                            "contents": "sample",
                            "targetAddress": "sample",
                            "productIdList": [],
                            "capPercent": 10
                        }
                    ],
                    productList: [],
                    targetDetail:[]
                }
            };
        },
        methods: {
            openContents:function(contents){
                window.console.log("openContents :"+contents);
                $("#myModal").show();
                $("#contentsImg").attr("src", contents);
            },
        },
        created(){

            if(campaignContract==null){
                initCampaignContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
            }
            var detailThis=this;

            var detail=this.data.campaignDetail;

            var productList=this.data.productList;
            var campaignId=this.$route.query.campaignId;
            var targetDetail=this.data.targetDetail;


            campaignContract.methods.getCampaign(campaignId).call({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'}, function(error, result){
                window.console.log("getList "+ error);
                window.console.log("getList "+ result);
                window.console.log("getList "+ JSON.stringify(result));

                var _id=result.id;
                var _name=result.name;
                var _thumbnail=result.thumbnail;
                var _cap=result.cap;
                var _totalAmount=result.totalAmount;
                var _startTime=Number(result.startTime);
                var _endTime=Number(result.endTime);
                var _contents=result.contents;
                var _tagetAddress=result.targetAddress;
                var _productIdList=result.productIdList;
                var percent=(_totalAmount/_cap) * 100;

                var startTimeStr=moment(_startTime).format("YYYY-MM-DD");
                var endTimeStr=moment(_endTime).format("YYYY-MM-DD");
                var dday=moment(_endTime).endOf('day').fromNow();
                var detailData={
                    "id": _id,
                    "name": _name,
                    "thumbnail": _thumbnail,
                    "cap": _cap,
                    "totalAmount": _totalAmount,
                    "startTime": _startTime,
                    "endTime": _endTime,
                    "startTimeStr": startTimeStr,
                    "endTimeStr": endTimeStr,
                    "d_day": dday,
                    "contents": _contents,
                    "targetAddress": _tagetAddress,
                    "productIdList": _productIdList,
                    "capPercent": percent
                };
                detail[0]=detailData;

                if(productContract==null){
                    initProductContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }
                productContract.methods.getProductList(_productIdList).call({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'}, function(error, result){

                    window.console.log("getList "+ error);
                    window.console.log("getList "+ result);
                    window.console.log("getList "+ JSON.stringify(result));

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


                if(targetContract==null){
                    initTargetContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }

                targetContract.methods.getTarget(_tagetAddress).call({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'}, function(error, result){
                    var target={
                        "addr": result.addr,
                        "name": result.name,
                        "thumbnail": result.thumbnail,
                        "gender": result.gender,
                        "birthday": result.birthday,
                        "country": result.country,
                        "contents": result.contents,
                    };

                    targetDetail.push(target);
                });

            });
        },
        watch: {
            // 질문이 변경될 때 마다 이 기능이 실행됩니다.
            campaignDetail: function (newCampaignDetail) {
                window.console.log("campaignDetail change : "+newCampaignDetail);
                this.methods.targetInit(newCampaignDetail[0].targetAddress);
                this.methods.productInit(newCampaignDetail[0]._productSeqList);
            }
        },
        mounted() {
            $(document).ready(function(){
                $('#myModal').on('shown.bs.modal', function () {
                    window.console.log("shown.bs.modal");
                    $('#myModal').addClass("show");
                    $('#myModal').removeClass("fade");
                });
                $('#myModal').on('hidden.bs.modal', function () {
                    window.console.log("hidden.bs.modal");
                    $('#myModal').addClass("fade");
                    $('#myModal').removeClass("show");
                });
            });

        }

    };
</script>
<style>
</style>
