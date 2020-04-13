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
                <h1 id="forms" class="pull-left text-white" style="color: #ffff;">후원대상 상세</h1>
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
                                        <input class="form-control" placeholder="이더리움 계정" type="text" ref="etherAccount" v-bind:value="data.target.address">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="프로필 이미지 URL" type="text" ref="profileImage"  v-bind:value="data.target.profileUrl">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="이름" type="text" ref="name" v-bind:value="data.target.name">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <input class="form-control" placeholder="생년월일(xxxx-xx-xx)" type="text" ref="birthday" v-bind:value="data.target.birthday">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <select class="form-control" ref="gender"  v-bind:value="data.target.gender">
                                            <option value="0">성별을 선택하세요.</option>
                                            <option value="남">남</option>
                                            <option value="여">여</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="input-group input-group-alternative mb-3">
                                        <select class="form-control" ref="country" v-bind:value="data.target.country">
                                            <option value="0">국가를 선택하세요.</option>
                                            <option value="대한민국">대한민국</option>
                                            <option value="가나">가나</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div id="introduceDiv" ref="introduceWrapper"></div>
                                    <input type="hidden" id="introduce" name="introduce"  ref="introduce">
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
        data() {
            return {
                data:{
                    target:{
                        "idx": "0",
                        "profileUrl": "https://support.childfund.or.kr/resources/images/support/img_concentrated_child01.png",
                        "name": "이촐",
                        "gender": "남",
                        "birthday": "2000-01-04",
                        "country": "가나",
                        "introduce": "학교에 가려면 보통 3시간을 걸어가야 했는데 이제 자전거로 학교에 갈 수 있어서 얼마나 좋은지 몰라요. 덕분에 공부도 하고, 식구들도 자전거를 이용해 소득활동을 할 수 있게 되었습니다.",
                        "address": "123",
                    }}
            };
        },
        created(){

            if(targetContract==null){
                initTargetContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
            }

            var _idx=this.$route.params.idx;
            var target=this.data.target;
            targetContract.methods.getTarget(_idx).call({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'}, function(error, result){
                window.console.log("getList "+ error);
                window.console.log("getList "+ result);
                window.console.log("getList "+ JSON.stringify(result));
                target.idx=result.idx;
                target.profileUrl=result.profileUrl;
                target.name=result.name;
                target.gender=result.gender;
                target.birthday=result.birthday;
                target.country=result.country;
                target.introduce=result.introduce;
                target.address=result.targetId;



            });

        },
        mounted() {
            var introduceHtml=this.data.target.introduce;

            $(document).ready(function(){
                $("#introduceDiv").summernote({
                    height: 300,
                    lang : 'ko-KR',
                    toolbar: [
                        ['style', ['style']],
                        ['font', ['bold','underline', 'clear']],
                        ['fontname', ['fontname']],
                        ['color', ['color']],
                        ['para', ['ul', 'ol', 'paragraph']],
                        ['table', ['table']],
                        ['insert', ['link', 'picture', 'video']],
                        ['view', ['fullscreen']],
                        ['help', ['help']]
                    ],
                    callbacks : {
                        onImageUpload: function(files) {
                            sendFile(files[0]);
                        }
                    },
                    popover: {
                        image: [
                            ['imagesize', ['imageSize100', 'imageSize50', 'imageSize25']],
                            ['float', ['floatLeft', 'floatRight', 'floatNone']],
                            ['custom', ['imageAttributes', 'imageShape']],
                            ['remove', ['removeMedia']]
                        ],
                    }
                });
                $('#introduceDiv').summernote('pasteHTML', introduceHtml);
            });

        },
        methods: {
            contractConnect: function (){
                if(targetContract==null){
                    initTargetContract(new Web3(new Web3.providers.HttpProvider('http://localhost:7545')));
                }

                var etherAccount=this.$refs.etherAccount.value;
                var profileImage=this.$refs.profileImage.value;
                var name=this.$refs.name.value;
                var gender=this.$refs.gender.value;
                var birthday=this.$refs.birthday.value;
                var country=this.$refs.country.value;

                var introduce=$('#introduceDiv').summernote('code');


                targetContract.methods.addTarget(etherAccount,profileImage,name,gender,birthday,country,introduce).send({from: '0xe84A7beD02428f3Feb2b7141a74be2DDD1b7C851'})
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
            memberRegister: function () {
                alert($('#introduceDiv').summernote('code'));
                alert('회원가입 클릭');

                var data = new FormData();
                data.append("id", this.$refs.id.value);
                data.append("pwd", this.$refs.pwd.value);

                window.console.log(data);
                /*

                                axios.post('/rest/member/register', data)
                                    .then((res) => {
                                        alert('성공');
                                    })
                                    .catch((ex) => {
                                        alert('실패');
                                    });*/
            }
        }
    };
</script>
<style>
</style>
