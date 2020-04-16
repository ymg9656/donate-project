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
        <div class="container">
            <div class="header">
                <h1 id="forms" class="pull-left text-white" style="color: #ffff;">캠페인</h1>
                <router-link class="pull-right btn btn-info btn-icon-clipboard" to="/campaign/register"
                             data-clipboard-text="fat-add">
                    <div><i class="ni ni-fat-add"></i> <span>신규등록</span></div>
                </router-link>
                <div style="clear: both;"></div>
            </div>


            <div class="row justify-content-center">
                <div class="col-lg-12">
                    <div class="row row-grid">

                        <div class="col-lg-4 margin-bottom-20" v-for="campaign in data.campaignList">
                            <div class="card border-0 card-lift--hover shadow"><!---->
                                <div class="card-body py-4">
                                    <span class="badge text-uppercase badge-warning margin-bottom-10">{{campaign.status}}</span>
                                    <span class="pic" v-bind:style="{'background-image': 'url('+campaign.thumbnail+')'}" style="background-repeat: no-repeat; background-position: center top; width:100%; height: 200px !important;border-radius: 0 !important; ">캠페인이미지</span>


                                    <h6 class="text-primary text-uppercase">{{campaign.name}}</h6>
                                    <div class="progress-wrapper">
                                        <div class="progress-success">
                                            <div class="progress-label"><span>목표금액 : {{campaign.cap}}</span></div>
                                        </div>
                                        <div class="progress" style="height: 8px;margin: 1rem 0 0 0; ">
                                            <div role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                                 aria-valuemax="100" class="progress-bar bg-success"
                                                 v-bind:aria-valuenow="campaign.capPercent"  v-bind:style="{ width: campaign.capPercent+'%'}"></div>
                                        </div>

                                        <div class="progress-percentage">
                                            <span class="pull-left">{{campaign.totalAmount}}</span>
                                            <span>{{campaign.capPercent}}%</span>

                                        </div>
                                    </div>

                                    <router-link v-bind:to="'/campaign/detail?campaignId='+campaign.id" class="btn mt-4 btn-primary" style="width:100%;">상세보기</router-link>
                                </div><!----><!----></div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </section>

</template>

<script>
    import axios from 'axios'
    import Web3 from "web3";

    export default {
        data() {
            return {
                data:{
                    campaignList: []
                }
            };
        },
        created(){

            if(campaignContract==null){
                initCampaignContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
            }
            var list=this.data.campaignList;
            campaignContract.methods.getCampaignList().call({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'}, function(error, result){
                window.console.log("getList "+ error);
                window.console.log("getList "+ result);
                window.console.log("getList "+ JSON.stringify(result));
                for(var i =0; i<result.length;i++){

                    var cap=result[i][3];
                    var totalAmount= result[i][4];
                    var percent=(totalAmount/cap) * 100;
                    window.console.log("result[i][2] : "+result[i][2]);

                    var status="진행중";
                    var startTime=result[i][5];
                    var endTime=result[i][6];
                    var now=new Date().getTime()/1000;
                    if(now<startTime){
                        status="진행전";
                    }else if(now > endTime && totalAmount < cap){
                        status="종료(실패)";
                    }else if(now > endTime && totalAmount >= cap){
                        status="종료";
                    }


                    var target={
                        "id": result[i][0],
                        "name": result[i][1],
                        "thumbnail": result[i][2],
                        "cap": cap,
                        "totalAmount":totalAmount,
                        "startTime": startTime,
                        "endTime": endTime,
                        "contents": result[i][7],
                        "targetAddress":result[i][8],
                        "productIdList":result[i][9],
                        "donorAddressList":result[i][10],
                        "capPercent": percent,
                        "status":status

                    };
                    list.push(target);
                }
            });
            window.console.log(JSON.stringify(list));

        },
        methods: {

        },

    };
</script>
<style>
</style>
