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


                                        <div role="progressbar" aria-valuemin="0"
                                             aria-valuemax="100" class="progress-bar bg-success"
                                             v-bind:aria-valuenow="data.campaignDetail[0].capPercent"  v-bind:style="{width: data.campaignDetail[0].capPercent+'%'}"></div>
                                    </div>

                                    <div class="progress-percentage">
                                        <span class="pull-left">{{data.campaignDetail[0].totalAmount}}</span>
                                        <span>{{data.campaignDetail[0].capPercent}}%</span>
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



                                            <div class="col-lg-4 margin-bottom-10" v-bind:key="product" v-for="product in data.productList">
                                                <div class="card border-0 shadow"><!---->
                                                    <div class="card-body">
                                                        <span class="pic" v-bind:style="{'background-image': 'url('+product.thumbnail+')'}" style="background-repeat: no-repeat; background-position: center top; background-size: 150px; ">{{product.name}}</span>
                                                        <h6 class="text-primary text-uppercase text-left">{{product.name}}</h6>
                                                        <p class="description mt-3 txt-overflow-3  text-left text-danger">{{product.price}}원</p>
                                                        <div class="form-inline">
                                                            <button  type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" v-on:click="openContents(product.contents)">상세보기</button>
                                                            <button  v-if="data.campaignDetail[0].status == 1" type="button" class="btn btn-danger" data-toggle="modal" data-target="#confirmModal" v-on:click="openConfirm(data.campaignDetail[0].id,product.id)">인증처리</button>
                                                        </div>
                                                    </div><!----><!----></div>
                                            </div>
                                        </div>
                                    </tab-pane>
                                </card>
                            </tabs>
                        </div>
                    </div>
                    <div class="card-footer">
                        <button v-if="data.campaignDetail[0].status == 0" type="button" class="btn mt-4 btn-danger" style="width:100%;" v-on:click="donate(data.campaignDetail[0].id)">기부하기</button>
                        <button v-if="data.campaignDetail[0].status == 2" type="button" class="btn mt-4 btn-danger" style="width:100%;" v-on:click="refund(data.campaignDetail[0].id)">환불하기</button>
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

            <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="confirmModalLabel">인증처리</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" ref="campaignId">
                            <input type="hidden" ref="productId">

                            <div v-if="getInvoiceStep == 0">
                                <div class="form-group">
                                    <lable>물품 발송을 하실때 인보이스에서 생성되는 번호를 꼭 스티커 부착하여 발송하세요.</lable>
                                    <input type="text" class="form-control" ref="invoiceMessageV" readonly>
                                    <input type="text" class="form-control" ref="invoiceMessageR" readonly>
                                    <input type="text" class="form-control" ref="invoiceMessageS" readonly>
                                </div>

                            </div>
                            <div v-if="getInvoiceStep == 1">
                                <div class="form-group">
                                    <lable>수신하신 물품에 부착된 스티커에 작성된 번호를 입력하여 처리 완료를 해주세요.</lable>
                                    <input type="text" class="form-control" ref="invoiceVerifyV">
                                    <input type="text" class="form-control" ref="invoiceVerifyR">
                                    <input type="text" class="form-control" ref="invoiceVerifyS">
                                </div>
                            </div>
                            <div v-if="getInvoiceStep == 2">모든 처리가 완료되었습니다.</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-danger" v-on:click="checkInvoice()">체크</button>
                            <button v-if="getInvoiceStep == 0" type="button" class="btn btn-danger" v-on:click="createInvoice()">인보이스</button>
                            <button v-if="getInvoiceStep == 1" type="button" class="btn btn-danger" v-on:click="submitReceipt()">수령완료</button>
                            <button v-if="getInvoiceStep == 2" type="button" class="btn btn-danger" v-on:click="createInvoice()">출금</button>
                        </div>
                    </div>
                </div>
            </div>

            <button type="button" class="btn btn-danger" v-on:click="testInvoice()">인보이스</button>

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
                    targetDetail:[],
                    invoiceStep:0
                }
            };
        },
        computed:{
            getInvoiceStep:function(){
                return this.data.invoiceStep;
            }
        },
        methods: {
            checkInvoice: function(){
              alert(this.data.invoiceStep) ;
            },
            donate:function(campaignId){
                alert("donate call : "+campaignId);
                if(campaignContract==null){
                    initCampaignContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }
                var options={from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B',value:1000000000000000000};
                window.console.log("options " + options);

                campaignContract.methods.donate(campaignId).send(options, function(error, result) {
                    window.console.log("getList " + error);
                    if(result!=null){
                        alert("정상적으로 기부 되었습니다.");
                    }
                    window.console.log("getList " + result);
                    window.console.log("getList " + JSON.stringify(result));
                });
            },
            refund:function(campaignId){
              alert("refund call : "+campaignId);
                if(campaignContract==null){
                    initCampaignContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }
                var options={from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B',value:0};
                window.console.log("options " + options);

                campaignContract.methods.refund(campaignId).send(options, function(error, result) {
                    window.console.log("getList " + error);
                    if(result!=null){
                        alert("정상적으로 환불 되었습니다.");
                    }
                    window.console.log("getList " + result);
                    window.console.log("getList " + JSON.stringify(result));
                });
            },
            testInvoice : function(){
                var signMessage=campaignContractWeb.utils.keccak256(campaignContractWeb.eth.abi.encodeParameters(['bytes32','bytes32','address'],["0x0eb37a4bce6581244ed7c5fc7f948ff80912c882a648cf8a327fd1468936f0b5","0x09e0c8d8b9d21204a316b736373eab7807703dee37104b436dfe5c3861b9ec21","0x449962EceECE14cDa0EA7FaC770AAE5991a8048B"]));
                window.console.log( "signMessage : "+signMessage);
                var signMessage="abcd";
                var sign=campaignContractWeb.eth.accounts.sign(signMessage,"6a42628b93c43df10b7efd1c2389ebc234e7b7ba1699cc5a908cd1bebf04686c");
                                                                            /*6a42628b93c43df10b7efd1c2389ebc234e7b7ba1699cc5a908cd1bebf04686c*/


                window.console.log( JSON.stringify(sign));

                var recover_1 = campaignContractWeb.eth.accounts.recover({
                    messageHash: sign.messageHash,
                    v: sign.v,
                    r: sign.r,
                    s: sign.s
                });
                window.console.log("recover 1 :" +JSON.stringify(recover_1));



                // message, signature
                var recover_2 = campaignContractWeb.eth.accounts.recover(signMessage, sign.signature);
                window.console.log("recover 2 :" +JSON.stringify(recover_2));


                // message, v, r, s
                var recover_3 = campaignContractWeb.eth.accounts.recover(signMessage, sign.v, sign.r, sign.s);
                window.console.log("recover 3 :" +JSON.stringify(recover_3));

            },
            createInvoice : function(){
                var campaignId=this.$refs.campaignId.value;
                var productId=this.$refs.productId.value;


                window.console.log("createInvoice");
                window.console.log("campaignId : "+campaignId);
                window.console.log("productId : "+productId);

                if(campaignContract==null){
                    initCampaignContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }
                var options={from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B'};
                window.console.log("options " + options);

                var invoiceMessageV = this.$refs.invoiceMessageV;
                var invoiceMessageR = this.$refs.invoiceMessageR;
                var invoiceMessageS = this.$refs.invoiceMessageS;



                campaignContract.methods.createInvoice(campaignId,productId).send(options)
                    .on('transactionHash', function(hash){
                        window.console.log("hash: "+hash);
                    })
                    .on('receipt', function(receipt){
                        window.console.log("receipt: "+receipt);
                    })
                    .on('confirmation', function(confirmationNumber, receipt){
                        window.console.log("confirmationNumber: "+ confirmationNumber);
                        window.console.log("receipt: "+receipt);
                        var signMessage=campaignContractWeb.utils.keccak256(campaignContractWeb.eth.abi.encodeParameter(campaignId,productId,0x449962EceECE14cDa0EA7FaC770AAE5991a8048B));
                        var sign=campaignContractWeb.eth.accounts.sign(signMessage,"6a42628b93c43df10b7efd1c2389ebc234e7b7ba1699cc5a908cd1bebf04686c");
                        invoiceMessageV.value=sign.v;
                        invoiceMessageR.value=sign.r;
                        invoiceMessageS.value=sign.s;

                        alert("정상적으로 인보이스 발행 되었습니다.");
                    })
                    .on('error', window.console.log(console.error));
            },
            submitReceipt: function(){
                if(campaignContract==null){
                    initCampaignContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }
                var options={from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B'};
                window.console.log("options " + options);
                var campaignId=this.$refs.campaignId.value;
                var productId=this.$refs.productId.value;
                var v=this.$refs.invoiceVerifyV.value;
                var r=this.$refs.invoiceVerifyR.value;
                var s=this.$refs.invoiceVerifyS.value;

                campaignContract.methods.submitReceipt(campaignId,productId,v,r,s).send(options)
                    .on('transactionHash', function(hash){
                        window.console.log("hash: "+hash);
                    })
                    .on('receipt', function(receipt){
                        window.console.log("receipt: "+receipt);
                    })
                    .on('confirmation', function(confirmationNumber, receipt){
                        window.console.log("confirmationNumber: "+ confirmationNumber);
                        window.console.log("receipt: "+receipt);
                    })
                    .on('error', window.console.log(console.error));
            },
            withdrawal: function(){
                if(campaignContract==null){
                    initCampaignContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }
                var options={from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B'};
                window.console.log("options " + options);
                var campaignId=this.$refs.campaignId.value;
                var productId=this.$refs.productId.value;

                campaignContract.methods.withdrawal(campaignId,productId).send(options)
                    .on('transactionHash', function(hash){
                        window.console.log("hash: "+hash);
                    })
                    .on('receipt', function(receipt){
                        window.console.log("receipt: "+receipt);
                    })
                    .on('confirmation', function(confirmationNumber, receipt){
                        window.console.log("confirmationNumber: "+ confirmationNumber);
                        window.console.log("receipt: "+receipt);
                    })
                    .on('error', window.console.log(console.error));
            },
            openContents:function(contents){
                window.console.log("openContents :"+contents);
                $("#myModal").show();
                $("#contentsImg").attr("src", contents);
            },
            openConfirm:function(campaignId,productId){

                window.console.log("openConfirm :"+campaignId+" / "+productId);
                this.$refs.campaignId.value=campaignId;
                this.$refs.productId.value=productId;

                if(campaignContract==null){
                    initCampaignContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }
                var options={from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B'};
                window.console.log("options " + options);


                var invoiceStep=this.data.invoiceStep;

                campaignContract.methods.getInvoiceStepByCampaign(campaignId,productId).call(options, (error, result) => {
                    window.console.log("getList " + error);
                    window.console.log("getList " + result);
                    this.data.invoiceStep=result;
                    window.console.log("this.data.invoiceStep " + this.data.invoiceStep);
                });


                $("#confirmModal").show();

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


            campaignContract.methods.getCampaign(campaignId).call({from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B'}, function(error, result){
                window.console.log("getList "+ error);
                window.console.log("getList "+ result);
                window.console.log("getList "+ JSON.stringify(result));

                var _id=result.id;
                var _name=result.name;
                var _thumbnail=result.thumbnail;
                var _cap=Number(result.cap);
                var _totalAmount=Number(result.totalAmount);
                var _startTime=Number(result.startTime);
                var _endTime=Number(result.endTime);
                var _contents=result.contents;
                var _tagetAddress=result.targetAddress;
                var _productIdList=result.productIdList;
                var percent=(_totalAmount/_cap) * 100;


                var status=0;
                var now=new Date().getTime()/1000;
                if(now<_startTime){
                    status=-1;
                }else if(now > _endTime && _totalAmount < _cap){
                    status=2;
                }else if(_totalAmount >= _cap){
                    window.console.log("_totalAmount: "+_totalAmount+" / cap : "+_cap);
                    status=1;
                }
                window.console.log("status : "+status);
                var startTimeStr=moment((_startTime*1000)).format("YYYY-MM-DD");
                var endTimeStr=moment((_endTime*1000)).format("YYYY-MM-DD");
                var dday=moment((_endTime*1000)).endOf('day').fromNow();
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
                    "capPercent": percent,
                    "status":status
                };
                detail[0]=detailData;

                if(productContract==null){
                    initProductContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }
                productContract.methods.getProductList(_productIdList).call({from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B'}, function(error, result){

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

                targetContract.methods.getTarget(_tagetAddress).call({from: '0x449962EceECE14cDa0EA7FaC770AAE5991a8048B'}, function(error, result){
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


                $('#confirmModal').on('shown.bs.modal', function () {
                    window.console.log("shown.bs.modal");
                    $('#confirmModal').addClass("show");
                    $('#confirmModal').removeClass("fade");
                });
                $('#confirmModal').on('hidden.bs.modal', function () {
                    window.console.log("hidden.bs.modal");
                    $('#confirmModal').addClass("fade");
                    $('#confirmModal').removeClass("show");
                });
            });

        }

    };
</script>
<style>
</style>
