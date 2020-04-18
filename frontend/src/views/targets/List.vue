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
                <h1 id="forms" class="pull-left text-white" style="color: #ffff;">후원대상 목록</h1>
                <router-link class="pull-right btn btn-info btn-icon-clipboard" to="/target/register"
                             data-clipboard-text="fat-add">
                    <div><i class="ni ni-fat-add"></i> <span>신규등록</span></div>
                </router-link>
                <!--<button class="pull-right btn btn-danger" v-on:click="dataInit()">데이터 셋팅</button>-->
                <div style="clear: both;"></div>
            </div>


            <div class="row justify-content-center">


                <div class="col-lg-12">


                    <div class="row row-grid">
                        <div class="col-lg-4 margin-bottom-20" v-bind:key="target" v-for="target in data.targetList">
                            <div class="card border-0 card-lift--hover shadow"><!---->
                                <div class="card-body py-5">
                                    <span class="pic" v-bind:style="{'background-image': 'url('+target.thumbnail+')'}" style="background-repeat: no-repeat; background-position: center top; background-size: 150px; ">양육어린이사진</span>
                                    <h6 class="text-primary text-uppercase">{{target.name}}({{target.country}})</h6>
                                    <p class="description mt-3 txt-overflow-3">생년월일: {{target.birthday}} | 성별: {{target.gender}}</p>

                                    <button  type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" v-on:click="openContents(target.contents)">상세보기</button>
                                </div><!----><!----></div>
                        </div>

                    </div>
                </div>


            </div>
        </div>

        <!-- Modal -->
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
</template>

<script>
    import axios from 'axios'
    import Web3 from "web3";
    import initWeb3 from "../../contract/initWeb3";
    import initTargetContract from "../../contract/initTargetContract";

    export default {
        data() {
            return {
                data:{
                    targetContract:null,
                    targetList: [],
                    sampleDataList:[
                        {
                            "addr":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"김지온",
                            "thumbnail":"https://www.sc.or.kr/upload/campaign/campaign_1587022944281.jpg",
                            "gender":"여",
                            "birthday":"2016.03.15",
                            "country":"대한민국",
                            "contents":"https://www.sc.or.kr/upload/campaign/campaign_1587081446561.jpg"
                        },
                        /*{
                            "addr":"0x5c2227605a689BfeEf2AD126cC53660F3d19e306",
                            "name":"조제프",
                            "thumbnail":"https://media.ci.org/w_300/v1554827321/ChildPhotos/Published/07848121_bcsakh.jpg",
                            "gender":"남",
                            "birthday":"2018.01.29",
                            "country":"토고",
                            "contents":"http://localhost:8080/static/조제프.png"
                        },
                        {
                            "addr":"0xc6776Feb4ddc0ece798240aad63116B322C5c8f9",
                            "name":"레오넬",
                            "thumbnail":"https://media.ci.org/w_300/v1560350564/ChildPhotos/Published/07853402_idtubx.jpg",
                            "gender":"남",
                            "birthday":"2014.10.03",
                            "country":"부르키나 파소",
                            "contents":"http://localhost:8080/static/레오넬.png"
                        },
                        {
                            "addr":"0xEEd6DAA60E941b03B7a4239C7eEAA2CB5783BaC8",
                            "name":"에릭",
                            "thumbnail":"https://media.ci.org/w_300/v1554829355/ChildPhotos/Published/07894714_dotlwf.jpg",
                            "gender":"남",
                            "birthday":"2018.01.12",
                            "country":"에콰도르",
                            "contents":"http://localhost:8080/static/에릭.png"
                        },
                        {
                            "addr":"0xFC92CeFEaC386caF5E1dF6F07Ba667356bfd7555",
                            "name":"라시드",
                            "thumbnail":"https://media.ci.org/w_300/v1554481443/ChildPhotos/Published/08101179_mgupdt.jpg",
                            "gender":"남",
                            "birthday":"2018.02.14",
                            "country":"탄자니아",
                            "contents":"http://localhost:8080/static/라시드.png"
                        },*/
                    ]
                }
            };
        },
        async created(){
            await initWeb3();
            if(this.data.targetContract==null){
                this.data.targetContract=initTargetContract(window.web3);
            }

            var list=this.data.targetList;
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
                    list.push(target);
                }
            });
            window.console.log(JSON.stringify(list));
            //this.data.targetList.push(list);
            window.console.log(JSON.stringify(this.data.targetList));
        },
        methods: {
            openContents:function(contents){
                window.console.log("openContents :"+contents);
                $("#myModal").show();
                $("#contentsImg").attr("src", contents);

            },
            dataInit: function (){
                var list=this.data.sampleDataList;
                for(var i = 0; i<list.length; i++){
                    var _addr=list[i].addr;
                    var _name=list[i].name;
                    var _thumbnail=list[i].thumbnail;
                    var _gender=list[i].gender;
                    var _birthday=list[i].birthday;
                    var _country=list[i].country;
                    var _contents=list[i].contents;

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
                }
            },
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
