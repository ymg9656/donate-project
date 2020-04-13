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
            <div class="row justify-content-center">
                <div class="col-lg-5">
                    <card type="secondary" shadow
                          header-classes="bg-white pb-5"
                          body-classes="px-lg-5 py-lg-5"
                          class="border-0">
                        <template>
                            <form role="form" name="registerForm" id="registerForm" action="/rest/member/register" method="POST" onsubmit="return false;">
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                                            </div>
                                            <input class="form-control" placeholder="상품명" type="text" ref="productName" value="기부 테스트 상품">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                                            </div>
                                            <input class="form-control" placeholder="후원금액" type="text" ref="price" value="1000">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                                            </div>
                                            <input class="form-control" placeholder="후원자명" type="text" ref="name" value="이다운">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                                            </div>
                                            <input class="form-control" placeholder="이메일 주소" type="email" ref="email" value="ymg9656@gmail.com">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="input-group input-group-alternative mb-3">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="ni ni-email-83"></i></span>
                                            </div>
                                            <input class="form-control" placeholder="휴대폰 번호" type="text" ref="phone" value="010-4078-2236">
                                        </div>
                                    </div>


                                    <div class="text-center">
                                        <button type="button" class="btn btn-primary mt-4" v-on:click="donate()">Create account</button>
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
    import axios from 'axios'
    import BootPay from "bootpay-js";
    export default {
        methods: {
            donate: function(){
                //실제 복사하여 사용시에는 모든 주석을 지운 후 사용하세요
                BootPay.request({
                    price: this.$refs.price.value, //실제 결제되는 가격
                    application_id: "5e75950e5ade16002a765e1c",
                    name: '블링블링 마스카라', //결제창에서 보여질 이름
                    pg: '',
                    method: '', //결제수단, 입력하지 않으면 결제수단 선택부터 화면이 시작합니다.
                    show_agree_window: 0, // 부트페이 정보 동의 창 보이기 여부
                    items: [
                        {
                            item_name: '나는 아이템', //상품명
                            qty: 1, //수량
                            unique: '123', //해당 상품을 구분짓는 primary key
                            price: this.$refs.price.value, //상품 단가
                            cat1: 'TOP', // 대표 상품의 카테고리 상, 50글자 이내
                            cat2: '티셔츠', // 대표 상품의 카테고리 중, 50글자 이내
                            cat3: '라운드 티', // 대표상품의 카테고리 하, 50글자 이내
                        }
                    ],
                    user_info: {
                        username: this.$refs.name.value,
                        email: this.$refs.email.value,
                        addr: '사용자 주소',
                        phone: this.$refs.phone.value
                    },
                    order_id: '1234', //고유 주문번호로, 생성하신 값을 보내주셔야 합니다.
                    params: {callback1: '그대로 콜백받을 변수 1', callback2: '그대로 콜백받을 변수 2', customvar1234: '변수명도 마음대로'},
                    account_expire_at: '', // 가상계좌 입금기간 제한 ( yyyy-mm-dd 포멧으로 입력해주세요. 가상계좌만 적용됩니다. )
                    extra: {}
                }).error(function (data) {
                    //결제 진행시 에러가 발생하면 수행됩니다.
                    var d=JSON.stringify(data);
                    console.log("error : "+d);
                }).cancel(function (data) {
                    //결제가 취소되면 수행됩니다.
                    var d=JSON.stringify(data);
                    console.log("cancel: "+d);
                }).ready(function (data) {
                    // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                    var d=JSON.stringify(data);
                    console.log("ready: "+d);
                }).confirm(function (data) {
                    //결제가 실행되기 전에 수행되며, 주로 재고를 확인하는 로직이 들어갑니다.
                    //주의 - 카드 수기결제일 경우 이 부분이 실행되지 않습니다.
                    var d=JSON.stringify(d);
                    console.log("confirm: "+d);
                    var enable = true; // 재고 수량 관리 로직 혹은 다른 처리
                    if (enable) {
                        BootPay.transactionConfirm(data); // 조건이 맞으면 승인 처리를 한다.
                    } else {
                        BootPay.removePaymentWindow(); // 조건이 맞지 않으면 결제 창을 닫고 결제를 승인하지 않는다.
                    }
                }).close(function (data) {
                    // 결제창이 닫힐때 수행됩니다. (성공,실패,취소에 상관없이 모두 수행됨)
                    var d=JSON.stringify(data);
                    console.log("close: "+d);
                }).done(function (data) {
                    //결제가 정상적으로 완료되면 수행됩니다
                    //비즈니스 로직을 수행하기 전에 결제 유효성 검증을 하시길 추천합니다.
                    var d=JSON.stringify(data);
                    console.log("done: "+d);
                });
            }
        }
    };
</script>
<style>
</style>
