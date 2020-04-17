<template>
    <div id="app">
        <router-view name="header"></router-view>
        <main>
            <fade-transition origin="center" mode="out-in" :duration="250">
                <router-view/>
            </fade-transition>
        </main>
        <router-view name="footer"></router-view>
    </div>
</template>
<script>
    import {FadeTransition} from "vue2-transitions";
    import Web3 from "web3";
    import initWeb3 from "./contract/initWeb3";

    export default {

        components: {
            FadeTransition
        },
        created() {
            if (!window.web3) {
                initWeb3();
            }
        },
        mounted() {

        }
    };
</script>
