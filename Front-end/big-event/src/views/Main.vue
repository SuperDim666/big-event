<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom
} from '@element-plus/icons-vue';
import avatarMale from '@/assets/profile_default_male.png';
import avatarFemale from '@/assets/profile_default_female.png';

// obtain user-info globally using pinia
import { userInfoService } from '@/api/user.js';
import useUserInfoStore from '@/stores/userInfo.js';
import { useTokenStore } from '@/stores/token.js';
const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();
const getUserInfo = async () => {
    let userInfo = await userInfoService();
    // store data into pinia
    userInfoStore.setInfo(userInfo.data);
}
getUserInfo();

// provide handleCommand to handling commands to router
import { useRouter } from 'vue-router';
import { ElMessage, ElMessageBox } from "element-plus";
const router = useRouter();
const handleCommand = (command) => {
    if (command === 'logout') {
        // logout: notify user to confirm
        // a notification pop-up is shown
        ElMessageBox.confirm(
            'Are you sure to log out?',
            'Please reminder',
            {
                confirmationButtonText: 'Yes',
                cancelButtonText: 'No',
                type: 'warning'
            }
        ).then(async ()=>{
            // 1. delete JWT-token and user-info in Pinia
            tokenStore.removeToken();
            userInfoStore.removeInfo();

            // 2.Jump back to login page
            await router.push('/login');
            ElMessage.success('Log out successful');
        }).catch(()=>{
            ElMessage.info('Log out cancelled');
        });
    } else {
        // router
        router.push('/user/'+command);
    }
}
</script>

<template>
    <el-container class="layout-container">

        <!-- Left-side Menu -->
        <el-aside width="220px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b"
                     background-color="#232323"
                     text-color="#fff"
                     router>
                <el-menu-item index="/article/category">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>Article Category</span>
                </el-menu-item>
                <el-menu-item index="/article/manage">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>Article Management</span>
                </el-menu-item>
                <el-sub-menu >
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>Personal Center</span>
                    </template>
                    <el-menu-item index="/user/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>Basic Info</span>
                    </el-menu-item>
                    <el-menu-item index="/user/avatar">
                        <el-icon>
                            <Crop />
                        </el-icon>
                        <span>Change Avatar</span>
                    </el-menu-item>
                    <el-menu-item index="/user/password">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>Reset Password</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>

        <!-- Right main area -->
        <el-container>

            <!-- Header Area -->
            <el-header>
                <div v-if="userInfoStore.info.nickname">
                    Current User:
                    <strong> {{ userInfoStore.info.nickname }} </strong>
                </div>
                <div v-else>
                    <em>NULL (empty nickname)</em>
                </div>
                <!-- pull-down menu -->
                <!--
                    command (re-direct to 'router/index.js'):
                        1. triggered when drop-down is clicked
                        2. define a func on event to obtain commands from drop-down
                -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="
                            userInfoStore.info.userPic || (
                                avatarMale
                            )
                        " />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info"
                                              :icon="User"
                            >
                                Basic Info
                            </el-dropdown-item>
                            <el-dropdown-item command="avatar"
                                              :icon="Crop"
                            >
                                Change Avatar
                            </el-dropdown-item>
                            <el-dropdown-item command="password"
                                              :icon="EditPen"
                            >
                                Reset Password
                            </el-dropdown-item>
                            <el-dropdown-item command="logout"
                                              :icon="SwitchButton"
                            >
                                Logout
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>

            <!-- Middle Area -->
            <el-main>

                <!-- Info Display Area -->
                <router-view/>
            </el-main>

            <!-- Bottom Area -->
            <el-footer>Big Event ©2023 Created by Zixiang Xu</el-footer>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/logo.png') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>