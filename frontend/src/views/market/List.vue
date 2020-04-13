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
                <h1 id="forms" class="pull-left text-white" style="color: #ffff;">마켓상품 목록</h1>
                <router-link class="pull-right btn btn-info btn-icon-clipboard" to="/market/register"
                             data-clipboard-text="fat-add">
                    <div><i class="ni ni-fat-add"></i> <span>신규등록</span></div>
                </router-link>
                <button class="pull-right btn btn-danger" v-on:click="dataInit()">데이터 셋팅</button>
                <div style="clear: both;"></div>
            </div>

            <div class="row justify-content-center">


                <div class="col-lg-12">


                    <div class="row row-grid">

                        <div class="col-lg-4 margin-bottom-20" v-for="product in data.productList">
                            <div class="card border-0 card-lift--hover shadow"><!---->
                                <div class="card-body py-5">
                                    <span class="pic" v-bind:style="{'background-image': 'url('+product.thumbnail+')'}" style="background-repeat: no-repeat; background-position: center top; background-size: 150px; ">{{product.name}}</span>
                                     <h6 class="text-primary text-uppercase">{{product.name}}</h6>
                                    <p class="description mt-3 txt-overflow-3">{{product.price}}</p>

                                    <button  type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal" v-on:click="openContents(product.contents)">상세보기</button>
                                   </div><!----><!----></div>
                        </div>

                    </div>
                </div>
            </div>
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
</template>

<script>
    import axios from 'axios'
    import Web3 from "web3";
    export default {
        data() {
            return {
                data:{
                    productList: [],
                    sampleDataList:[
                        {
                            "supplier":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"다우니 섬유유연제 초고농축 퍼퓸 보타니스 엑스퍼트 1L 모음전",
                            "thumbnail":"https://shop-phinf.pstatic.net/20200405_219/1586094947506TLUzr_JPEG/23457280919448821_381174649.jpg?type=m510",
                            "price":10,
                            "contents":"http://localhost:8080/static/다우니.png"
                        },
                        {
                            "supplier":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"멜트블로운 MB 부직포 마스크 3중필터 일회용마스크 50매 100매",
                            "thumbnail":"https://shop-phinf.pstatic.net/20200320_291/15847125987822TAJO_PNG/22074932336972960_1577279868.png?type=m510",
                            "price":10,
                            "contents":"http://localhost:8080/static/멜트블로운.png"
                        },
                        {
                            "supplier":"0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851",
                            "name":"농심 백산수 2L 24병 생수",
                            "thumbnail":"https://shop-phinf.pstatic.net/20190924_72/1569303464086YQpP8_JPEG/6664202718233170_1659006007.jpg?type=m510",
                            "price":10,
                            "contents":"http://localhost:8080/static/생수.png"
                        },
                    ]
                }
            };
        },
        created(){

            if(productContract==null){
                initProductContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
            }
            var list=this.data.productList;
            productContract.methods.getProductList().call({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'}, function(error, result){

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
                    list.push(target);
                }
            });
            window.console.log(JSON.stringify(list));
            //this.data.targetList.push(list);

        },
        methods: {
            openContents:function(contents){
                window.console.log("openContents :"+contents);
                $("#myModal").show();
                $("#contentsImg").attr("src", contents);
            },
            dataInit: function (){
                if(productContract==null){
                    initProductContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }

                var list=this.data.sampleDataList;
                for(var i = 0; i<list.length; i++){
                    var _supplier=list[i].supplier;
                    var _name=list[i].name;
                    var _thumbnail=list[i].thumbnail;
                    var _price=list[i].price;
                    var _contents=list[i].contents;
                    productContract.methods.addProduct(_supplier,_name,_thumbnail,_price,_contents).send({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'})
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
                            location.href="http://localhost:8080/vue/index.html#/market";
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
