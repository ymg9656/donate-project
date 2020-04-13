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
                <div style="clear: both;"></div>
            </div>


            <div class="row justify-content-center">
                <div class="col-lg-12">
                    <div class="row row-grid">
                        <div class="col-lg-4">
                            <div class="card border-0 card-lift--hover shadow"><!---->
                                <div class="card-body py-5">
                                    <img alt="Rounded image" class="margin-bottom-10 img-fluid rounded shadow"
                                         data-src="https://www.sc.or.kr/webPub/sc_2018/images/donate/main/main_1.png"
                                         src="https://www.sc.or.kr/webPub/sc_2018/images/donate/main/main_1.png"
                                         lazy="loaded" style="width: 150px;">
                                    <h6 class="text-primary text-uppercase">결손아동 후원 캠페인</h6>
                                    <p class="description mt-3 txt-overflow-3">학교에 가려면 보통 3시간을 걸어가야 했는데 이제 자전거로 학교에 갈 수
                                        있어서 얼마나 좋은지 몰라요. 덕분에 공부도 하고, 식구들도 자전거를 이용해 소득활동을 할 수 있게 되었습니다.</p>
                                    <div class="progress-wrapper">
                                        <div class="progress-success">
                                            <div class="progress-label"><span>목표금액 : 50,000</span></div>
                                        </div>
                                        <div class="progress" style="height: 8px;margin: 1rem 0 0 0; ">
                                            <div role="progressbar" aria-valuenow="60" aria-valuemin="0"
                                                 aria-valuemax="100" class="progress-bar bg-success"
                                                 style="width: 60%;"></div>
                                        </div>
                                        <div class="progress-percentage">
                                            <span class="pull-left">30,000</span>
                                            <span>60%</span>
                                        </div>
                                    </div>
                                    <a type="" class="btn mt-4 btn-primary" style="width: 100%;"
                                       href="/vue/index.html#/donate/detail?testData=1234"><!---->
                                        <!----><!---->
                                        기부하기
                                    </a></div><!----><!----></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</template>

<script>
    import axios from 'axios'

    export default {
        methods: {
            memberRegister: function () {
                alert('회원가입 클릭');

                var data = new FormData();
                data.append("id", this.$refs.id.value);
                data.append("pwd", this.$refs.pwd.value);

                window.console.log(data);


                axios.post('/rest/member/register', data)
                    .then((res) => {
                        alert('성공');
                    })
                    .catch((ex) => {
                        alert('실패');
                    });
            },
        }
    };
</script>
<style>
</style>
