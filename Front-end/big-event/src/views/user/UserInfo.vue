<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js';
const userInfoStore = useUserInfoStore();

// giving user-info in Pinia to this responsive variable
const userInfo = ref({...userInfoStore.info});
const rules = {
    nickname: [
        {
            required: true,
            message: 'Please enter a user nickname.',
            trigger: 'blur'
        },
        {
            pattern: /^.{3,30}$/,
            message: 'Nickname must be a non-empty string of 3-30 chars.',
            trigger: 'blur'
        }
    ],
    email: [
        {
            required: true,
            message: 'Please enter your E-mail address.',
            trigger: 'blur'
        },
        {
            type: 'email',
            message: 'Incorrect mailbox format.',
            trigger: 'blur'
        },
        {
            pattern: /^.{0,256}$/,
            message: 'E-mail is at most 256 chars.',
            trigger: 'blur'
        }
    ]
};
// change user-info
import { userInfoUpdateService } from '@/api/user.js';
import { ElMessage } from "element-plus";

const updateUserInfo = async () => {
    // invoke API
    let result = await userInfoUpdateService(userInfo.value);
    ElMessage.success(result.msg || 'Change user-info successful');

    // change user-info in Pinia
    userInfoStore.setInfo(userInfo.value);
};
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span><strong>General Information</strong></span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo"
                         :rules="rules"
                         label-width="100px"
                         size="large">
                    <el-form-item label="Username">
                        <el-input v-model="userInfo.username"
                                  disabled></el-input>
                    </el-form-item>
                    <el-form-item label="Nickname"
                                  prop="nickname">
                        <el-input v-model="userInfo.nickname"></el-input>
                    </el-form-item>
                    <el-form-item label="Email"
                                  prop="email">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary"
                                   @click="updateUserInfo"
                        >
                            Submit changes
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>