// import vue-router
import {createRouter, createWebHistory} from "vue-router";

// import component
import LoginVue from '@/views/Login.vue'
import MainVue from '@/views/Main.vue'
import ArticleCategoryVue from "@/views/article/ArticleCategory.vue";
import ArticleManageVue from "@/views/article/ArticleManage.vue";
import UserAvatarVue from "@/views/user/UserAvatar.vue";
import UserInfoVue from "@/views/user/UserInfo.vue";
import UserResetPasswordVue from "@/views/user/UserResetPassword.vue";

// define router relations: routes
const routes = [
    { path: '/', redirect: '/login' }, // default start page is Login
    { path: '/login', component: LoginVue },
    {
        path: '/main',
        component: MainVue,
        redirect: '/article/manage', // default page
        // add child routes
        children: [
            {path: '/article/category', component: ArticleCategoryVue},
            {path: '/article/manage', component: ArticleManageVue},
            {path: '/user/avatar', component: UserAvatarVue},
            {path: '/user/info', component: UserInfoVue},
            {path: '/user/password', component: UserResetPasswordVue},
        ]
    }
];

// create routers
const router = createRouter({
    // two modes: hash / history
    // we adopt history here: createWebHistory()
    history: createWebHistory(),
    routes: routes
});

export default router