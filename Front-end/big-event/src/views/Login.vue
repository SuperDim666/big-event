<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import {ElMessage} from "element-plus";

// Define and set-up token-store
import { useTokenStore } from "@/stores/token.js";
const tokenStore = useTokenStore();

// Controls the display of the registration and login forms;
// Sign-up is displayed by default.
const isSignUp = ref(false)

// Define Data Module
const signUpData = ref({
    username: '',
    password: '',
    rePassword: ''
})

// Password Confirm checking function
const checkRePassword = (rule, value, callback)=>{
    if (value === '') {
        callback(new Error('Please re-enter your password.'))
    } else if (value !== signUpData.value.password) {
        callback(new Error('Re-entered password not match.'))
    } else {
        callback()
    }
}

// Define form rules
const checkingRules={
    username: [
        {
            required: true,
            message: 'Please enter username.',
            triggered: 'blur'
        },
        {
            min: 5, max: 30,
            message: 'Username must be 5-30 chars long.',
            triggered: 'blur'
        }
    ],
    password: [
        {
            required: true,
            message: 'Please enter password.',
            triggered: 'blur'
        },
        {
            min: 5, max: 32,
            message: 'Password must be 5-32 chars long.',
            triggered: 'blur'
        }
    ],
    rePassword: [
        {
            validator: checkRePassword,
            triggered: 'blur'
        }
    ]
}

// get routers
import { useRouter } from 'vue-router'
const router = useRouter();

// call back-side API to complete sign up
import { userSignUpService, userLoginService } from '@/api/user.js'
const signUp = async ()=>{
    // note: signUpData is a responsive object
    // to get value, should add '.value'
    let result = await userSignUpService(signUpData.value);
    ElMessage.success(result.msg || 'Sign-Up successful!');
}

// For login page...
// Binding data: reuse the module of data form in sign-up
// Validate module rules: reuse 'rules' again

// Function for login
const login = async ()=>{
    let result = await userLoginService(signUpData.value);
    ElMessage({
        message: result.msg || 'Login successful!',
        type: "success"
    });
    // Store JWT token into Pinia
    tokenStore.setToken(result.data);
    // Jump to management page using router
    await router.push('/main');
}

// clear up username/password data while switching login/sign-up page
const clearData = ()=> {
    signUpData.value = {
        username: '',
        password: '',
        rePassword: ''
    }
}
</script>

<template>
    <el-row class="login-page">
        <el-col :span="12" class="bg"></el-col>
        <el-col :span="6" :offset="3" class="form">
            <!-- Sign-Up Form -->
            <el-form ref="form"
                     size="large"
                     autocomplete="off"
                     v-if="isSignUp"
                     :model="signUpData"
                     :rules="checkingRules"
            >
                <el-form-item>
                    <h1>Sign Up</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User"
                              placeholder="Please enter your user name"
                              v-model="signUpData.username"
                    />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock"
                              type="password"
                              placeholder="Please enter your password"
                              v-model="signUpData.password"
                    />
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock"
                              type="password"
                              placeholder="Please re-enter your password"
                              v-model="signUpData.rePassword"
                    />
                </el-form-item>
                <!-- Sign Up button -->
                <el-form-item>
                    <el-button class="button"
                               type="primary"
                               auto-insert-space
                               @click="signUp"
                    >
                        Sign Up
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isSignUp=false; clearData()">
                        ← Go Back
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- Login Form -->
            <el-form ref="form"
                     size="large"
                     autocomplete="off"
                     v-else
                        :model="signUpData"
                        :rules="checkingRules"
            >
                <el-form-item>
                    <h1>Login</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User"
                              placeholder="Please enter your username"
                              v-model="signUpData.username"
                    />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password"
                              :prefix-icon="Lock"
                              type="password"
                              placeholder="Please enter your password"
                              v-model="signUpData.password"
                    />
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>Remember me</el-checkbox>
                        <el-link type="primary" :underline="false">Forget password?</el-link>
                    </div>
                </el-form-item>
                <!-- Login Button -->
                <el-form-item>
                    <el-button class="button"
                               type="primary"
                               auto-insert-space
                               @click="login"
                    >
                        Login
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isSignUp=true; clearData()">
                        Sign Up →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
/* Style */
.login-page {
    height: 100vh;
    background-color: #fff;

    .bg {
        background: url('@/assets/logo.png') no-repeat 50% center / 500px auto,
        url('@/assets/background_soft.png') no-repeat center / cover;
        border-radius: 0 20px 20px 0;
    }

    .form {
        display: flex;
        flex-direction: column;
        justify-content: center;
        user-select: none;

        .title {
            margin: 0 auto;
        }

        .button {
            width: 100%;
        }

        .flex {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }
    }
}
</style>