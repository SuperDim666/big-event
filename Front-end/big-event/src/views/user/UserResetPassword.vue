<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js';
import { useTokenStore } from '@/stores/token.js';
const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();

// giving user-info in Pinia to this responsive variable
const userPwds = ref({
    oldPassword: '',
    newPassword: '',
    rePassword: ''
});
const validateRePassword = (rule, value, callback) => {
    // 'this.newPassword' holds the value of the newPassword field
    // 'value' is the value of the rePassword field
    if (value !== userPwds.value.newPassword) {
        callback(new Error('Please match the new password.'));
    } else {
        callback();
    }
}
const rules = {
    oldPassword: [
        {
            required: true,
            message: 'Please enter your old password.',
            trigger: 'blur'
        },
        {
            pattern: /^\S{5,32}$/,
            message: 'Password must be no-whitespace and 5-32 chars long.',
            trigger: 'blur'
        }
    ],
    newPassword: [
        {
            required: true,
            message: 'Please enter your new password',
            trigger: 'blur'
        },
        {
            pattern: /^\S{5,32}$/,
            message: 'Password must be no-whitespace and 5-32 chars long.',
            trigger: 'blur'
        }
    ],
    rePassword: [
        {
            required: true,
            message: 'Please re-enter your new password.',
            trigger: 'blur'
        },
        {
            validator: validateRePassword,
            trigger: 'blur'
        },
        {
            pattern: /^\S{5,32}$/,
            message: 'Password must be no-whitespace and 5-32 chars long.',
            trigger: 'blur'
        }
    ]
};
// change user-info
import { userPwdUpdateService } from '@/api/user.js';
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router/index.js";

const updateUserPwd = async () => {
    ElMessageBox.confirm(
        'Are you sure to change password?',
        'Please reminder',
        {
            confirmationButtonText: 'Yes',
            cancelButtonText: 'No',
            type: 'warning'
        }
    ).then(async ()=>{
        // invoke API
        let result = await userPwdUpdateService(userPwds.value);

        // 1. delete JWT-token and user-info in Pinia
        tokenStore.removeToken();
        userInfoStore.removeInfo();

        // 2.Jump back to login page
        await router.push('/login');
        ElMessage.success(result.msg || 'Change password successful');
    }).catch(err=>{
        if (err === null || err.message === null || err.message === '')
            ElMessage.info('Change password cancelled');
    });
};
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span><strong>Reset Password</strong></span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userPwds"
                         :rules="rules"
                         label-width="160px"
                         size="large">
                    <el-form-item label="Old password:"
                                  prop="oldPassword">
                        <el-input v-model="userPwds.oldPassword"></el-input>
                    </el-form-item>
                    <el-form-item label="New password:"
                                  prop="newPassword">
                        <el-input v-model="userPwds.newPassword"></el-input>
                    </el-form-item>
                    <el-form-item label="Re-enter password:"
                                  prop="rePassword">
                        <el-input v-model="userPwds.rePassword"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary"
                                   @click="updateUserPwd"
                        >
                            Change password
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>