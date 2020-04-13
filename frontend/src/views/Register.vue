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
                                            <input class="form-control" placeholder="Email" type="email" ref="id">
                                        </div>
                                    </div>
                                    <div class="form-group focused">
                                        <div class="input-group input-group-alternative">
                                            <div class="input-group-prepend">
                                                <span class="input-group-text"><i class="ni ni-lock-circle-open"></i></span>
                                            </div>
                                            <input class="form-control" placeholder="Password" type="password" ref="pwd">
                                        </div>
                                    </div>
                                    <div class="text-center">
                                        <button type="button" class="btn btn-primary mt-4" v-on:click="memberRegister()">Create account</button>
                                        <button type="button" class="btn btn-primary mt-4" v-on:click="contractConnect()">Smart contract Connect</button>
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

        methods: {
            contractConnect: function (){
                var web3 = new Web3(Web3.givenProvider || "ws://localhost:8545");
                var abi= JSON.parse('[{"constant":false,"inputs":[{"internalType":"uint256","name":"_amount","type":"uint256"},{"internalType":"string","name":"_beneficiaryId","type":"string"}],"name":"addContents","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"getContents1","outputs":[{"internalType":"uint256","name":"","type":"uint256"}],"payable":false,"stateMutability":"pure","type":"function"}]');
                var contract = new web3.eth.Contract(abi);
                contract.options.address = "0x6acEa838b9Bc6E36CC84710Ac86728E053328280";

                contract.methods.getContents1().call().then((f => {
                   alert(f);
                }));
            },
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
            }
        }
    };
</script>
<style>
</style>
