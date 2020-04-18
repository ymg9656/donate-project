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
                <card shadow class="card-profile mt--300" no-body style="margin-top: -495px !important;">
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
                                                        <span v-if="getInvoiceStepByProductId(product.id) == 3" class="badge text-uppercase badge-warning margin-bottom-10">완료</span>
                                                        <span class="pic" v-bind:style="{'background-image': 'url('+product.thumbnail+')'}" style="background-repeat: no-repeat; background-position: center top; background-size: 150px; ">{{product.name}}</span>
                                                        <h6 class="text-primary text-uppercase text-left">{{product.name}}</h6>
                                                        <p class="description mt-3 txt-overflow-3  text-left text-danger">{{product.price}}원</p>
                                                        <div class="form-group">
                                                            <button  type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" v-on:click="openContents(product.contents)">상세보기</button>
                                                            <div class="pull-right">
                                                            <button  v-if="getInvoiceStepByProductId(product.id) == 0" type="button" class="btn btn-danger" data-toggle="modal" data-target="#confirmModal" v-on:click="openConfirm(product.id)">인보이스</button>
                                                            <button  v-if="getInvoiceStepByProductId(product.id) == 1" type="button" class="btn btn-danger" data-toggle="modal" data-target="#confirmModal" v-on:click="openConfirm(product.id)">수령확인</button>
                                                            <button  v-if="getInvoiceStepByProductId(product.id) == 2" type="button" class="btn btn-danger" data-toggle="modal" data-target="#confirmModal" v-on:click="openConfirm(product.id)">출금</button>
                                                            </div>
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
                            <input type="hidden" ref="campaignId" v-bind:value="data.campaignDetail[0].id">
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
                            <button v-if="getInvoiceStep == 0" type="button" class="btn btn-danger" v-on:click="createInvoice()">인보이스</button>
                            <button v-if="getInvoiceStep == 1" type="button" class="btn btn-danger" v-on:click="submitReceipt()">수령완료</button>
                            <button v-if="getInvoiceStep == 2" type="button" class="btn btn-danger" v-on:click="withdrawal()">출금</button>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>
</template>
<script>
    import Web3 from "web3";
    import initCampaignContract from "../../contract/initCampaignContract";
    import initWeb3 from "../../contract/initWeb3";
    import initProductContract from "../../contract/initProductContract";
    import initTargetContract from "../../contract/initTargetContract";

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
                    invoiceStep:0,
                    campaignContract:null,
                    productContract:null,
                    targetContract:null,
                    invoiceList:[]
                }
            };
        },
        computed:{
            getInvoiceStep:function(){
                return this.data.invoiceStep;
            },
        },
        methods: {
            getInvoiceStepByProductId:function(productId){
                window.console.log("getInvoiceStepByProductId: "+this.data.invoiceList.length);
                for(var i=0; i < this.data.invoiceList.length; i++){
                    var invoice=this.data.invoiceList[i];
                    window.console.log("getInvoiceStepByProductId : "+productId+" / "+invoice.productId);
                    if(invoice.productId==productId){
                        window.console.log("getInvoiceStepByProductId return : "+invoice.step);
                        return invoice.step;
                    }
                }
                var defaultStep=this.data.campaignDetail[0].status==1 ? 0 : -1;
                return defaultStep;
            },
            donate:function(campaignId){
                alert("donate call : "+campaignId);
                this.data.campaignContract.methods.donate(campaignId).send({value:"1000000000000000000"},function(error, result) {
                    window.console.log("donate send " + error);
                    if(result!=null){
                        alert("정상적으로 기부 되었습니다.");
                    }
                    window.console.log("donate send " + result);
                    window.console.log("donate send " + JSON.stringify(result));
                });
            },
            refund:function(campaignId){
              alert("refund call : "+campaignId);

                this.data.campaignContract.methods.refund(campaignId).send(function(error, result) {
                    window.console.log("getList " + error);
                    if(result!=null){
                        alert("정상적으로 환불 되었습니다.");
                    }
                    window.console.log("getList " + result);
                    window.console.log("getList " + JSON.stringify(result));
                });
            },
            createInvoice : function(){
                var campaignId=this.$refs.campaignId.value;
                var productId=this.$refs.productId.value;


                window.console.log("createInvoice");
                window.console.log("campaignId : "+campaignId);
                window.console.log("productId : "+productId);

                var invoiceMessageR=this.$refs.invoiceMessageR;
                var invoiceMessageV=this.$refs.invoiceMessageV;
                var invoiceMessageS=this.$refs.invoiceMessageS;

                var targetAddress=this.data.campaignDetail[0].targetAddress;
                this.data.campaignContract.methods.createInvoice(campaignId,productId).send()
                    .on('transactionHash', function(hash){
                        window.console.log("hash: "+hash);
                    })
                    .on('receipt', async function(receipt){
                        window.console.log("receipt: "+JSON.stringify(receipt));


                        var soliditySha3Msg=window.web3.utils.soliditySha3(campaignId,productId,targetAddress);
                        window.web3.eth.personal.sign(soliditySha3Msg, window.web3.eth.defaultAccount).then((result) => {

                            invoiceMessageR.value = result.slice(0,66);
                            invoiceMessageS.value ='0x' + result.slice(66,130);
                            invoiceMessageV.value ='0x' + result.slice(130,132);

                            alert("정상적으로 인보이스 발행 되었습니다.");


                        });

                    })
                    .on('confirmation', function(confirmationNumber, receipt){
                        window.console.log("confirmationNumber: "+ confirmationNumber);
                        window.console.log("receipt: "+receipt);
                    })
                    .on('error', window.console.log(console.error));
            },
            submitReceipt: function(){

                var campaignId=this.$refs.campaignId.value;
                var productId=this.$refs.productId.value;
                var v=this.$refs.invoiceVerifyV.value;
                var r=this.$refs.invoiceVerifyR.value;
                var s=this.$refs.invoiceVerifyS.value;

                window.console.log("------submitReceipt-------");
                window.console.log("campaignId: "+campaignId);
                window.console.log("productId: "+productId);
                window.console.log("v: "+v);
                window.console.log("r: "+r);
                window.console.log("s: "+s);


                this.data.campaignContract.methods.submitReceipt(campaignId,productId,v,r,s).send()
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


                var campaignId=this.$refs.campaignId.value;
                var productId=this.$refs.productId.value;

                this.data.campaignContract.methods.withdrawal(campaignId,productId).send()
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
            openConfirm:function(productId){
                this.$refs.productId.value=productId;
                for(var i=0; i < this.data.invoiceList.length; i++){
                    var invoice=this.data.invoiceList[i];
                    if(invoice.productId==productId){
                        this.data.invoiceStep=invoice.step;
                    }
                }
                $("#confirmModal").show();

            },
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
            var detailThis=this;

            var detail=this.data.campaignDetail;
            var invoiceList=this.data.invoiceList;

            var productList=this.data.productList;
            var campaignId=this.$route.query.campaignId;
            var targetDetail=this.data.targetDetail;

            var productContract=this.data.productContract;
            var targetContract=this.data.targetContract;


            this.data.campaignContract.methods.getCampaignWithInvoice(campaignId).call(function(error, result){
                window.console.log("getCampaign "+ error);
                window.console.log("getCampaign "+ result);

                var _id=result[0].id;
                var _name=result[0].name;
                var _thumbnail=result[0].thumbnail;
                var _cap=Number(result[0].cap);
                var _totalAmount=Number(result[0].totalAmount);
                var _startTime=Number(result[0].startTime);
                var _endTime=Number(result[0].endTime);
                var _contents=result[0].contents;
                var _tagetAddress=result[0].targetAddress;
                var _productIdList=result[0].productIdList;
                var percent=(_totalAmount/_cap) * 100;


                var invoiceArray=new Array();
                invoiceArray.push(result[1]);


                window.console.log("invoice result = "+result[1].step);
                window.console.log("invoice result = "+result[1][0].step);




                window.console.log("getCampaign InvoiceList "+ result[1]);

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

                for(var l=0;l<result[1].length;l++){
                    var invoiceEntity={
                        "productId":result[1][l].productId,
                        "step":result[1][l].step
                    }

                    invoiceList.push(invoiceEntity);
                }

                productContract.methods.getProductList(_productIdList).call(function(error, result){

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



                targetContract.methods.getTarget(_tagetAddress).call(function(error, result){
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
