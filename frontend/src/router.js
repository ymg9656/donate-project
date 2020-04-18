import Vue from "vue";
import Router from "vue-router";
import AppHeader from "./layout/AppHeader";
import AppFooter from "./layout/AppFooter";
import Components from "./views/Components.vue";
import Landing from "./views/Landing.vue";
import Login from "./views/Login.vue";
import Register from "./views/Register.vue";
import List from "./views/List.vue";
import Detail from "./views/Detail.vue";
import Profile from "./views/Profile.vue";


import Product from "./views/product/List.vue";
import ProductRegister from "./views/product/Register.vue";

import Campaign from "./views/campaign/List.vue";
import CampaignDetail from "./views/campaign/Detail.vue";
import CampaignRegister from "./views/campaign/Register.vue";


import Target from "./views/targets/List.vue";
import TargetRegister from "./views/targets/Register.vue";




Vue.use(Router);

export default new Router({
  linkExactActiveClass: "active",
  routes: [
    {
      path: "/",
      name: "Campaign",
      components: {
        header: AppHeader,
        default: Campaign,
        footer: AppFooter
      }
    },
    {
      path: "/landing",
      name: "landing",
      components: {
        header: AppHeader,
        default: Landing,
        footer: AppFooter
      }
    },
    {
      path: "/login",
      name: "login",
      components: {
        header: AppHeader,
        default: Login,
        footer: AppFooter
      }
    },
    {
      path: "/register",
      name: "register",
      components: {
        header: AppHeader,
        default: Register,
        footer: AppFooter
      }
    },
    {
      path: "/list",
      name: "list",
      components: {
        default: List,
      }
    },
    {
      path: "/detail",
      name: "detail",
      components: {
        default: Detail,
      }
    },
    {
      path: "/profile",
      name: "profile",
      components: {
        header: AppHeader,
        default: Profile,
        footer: AppFooter
      }
    },
    {
      path: "/product",
      name: "product",
      components: {
        header: AppHeader,
        default: Product,
        footer: AppFooter
      }
    },
    {
      path: "/product/register",
      name: "productRegister",
      components: {
        header: AppHeader,
        default: ProductRegister,
        footer: AppFooter
      }
    },
    {
      path: "/campaign",
      name: "campaign",
      components: {
        header: AppHeader,
        default: Campaign,
        footer: AppFooter
      }
    },
    {
      path: "/campaign/register",
      name: "campaignRegister",
      components: {
        header: AppHeader,
        default: CampaignRegister,
        footer: AppFooter
      }
    },
    {
      path: "/campaign/detail",
      name: "campaignDetail",
      components: {
        header: AppHeader,
        default: CampaignDetail,
        footer: AppFooter
      }
    },
    {
      path: "/target",
      name: "target",
      components: {
        header: AppHeader,
        default: Target,
        footer: AppFooter
      }
    },
    {
      path: "/target/register",
      name: "targetRegister",
      components: {
        header: AppHeader,
        default: TargetRegister,
        footer: AppFooter
      }
    },
  ],
  scrollBehavior: to => {
    if (to.hash) {
      return { selector: to.hash };
    } else {
      return { x: 0, y: 0 };
    }
  }
});
