<script setup>
import { Plus, Upload } from '@element-plus/icons-vue';
import {ref} from 'vue';
import avatarMale from '@/assets/profile_default_male.png';
import avatarFemale from '@/assets/profile_default_female.png';
const uploadRef = ref();

import useUserInfoStore from "@/stores/userInfo.js";
import {useTokenStore} from "@/stores/token.js";
const userInfoStore = useUserInfoStore();
const tokenStore = useTokenStore();

// address of user avatar
const imgUrl= ref(userInfoStore.info.userPic || avatarMale);

// call-back func after uploading successfully
const uploadSuccess = (result) => {
    imgUrl.value = result.data;
}
import {userAvatarUpdateService} from "@/api/user.js";
import {ElMessage} from "element-plus";

const updateAvatar = async () => {
    // invoke API
    let result = await userAvatarUpdateService(imgUrl.value);
    ElMessage.success(result.msg || 'Change avatar successful');
    // change user-info in Pinia
    userInfoStore.info.userPic = imgUrl.value;
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span><strong>Change your avatar</strong></span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-upload
                    ref="uploadRef"
                    class="avatar-uploader"
                    :show-file-list="false"
                    :auto-upload="true"
                    action="/api/upload"
                    name="file"
                    :headers="{'Authorization': tokenStore.token}"
                    :on-success="uploadSuccess"
                >
                    <img
                        v-if="imgUrl"
                        :src="imgUrl"
                        class="avatar"
                    />
                    <img v-else
                         :src="avatarMale"
                         width="278"
                    />
                </el-upload>
                <br />
                <el-button type="primary"
                           :icon="Plus"
                           size="large"
                           @click="uploadRef.$el.querySelector('input').click()"
                >
                    Choose image
                </el-button>
                <el-button type="success"
                           :icon="Upload"
                           size="large"
                           @click="updateAvatar"
                >
                    Upload avatar
                </el-button>
            </el-col>
        </el-row>
    </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
    :deep() {
        .avatar {
            width: 278px;
            height: 278px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
</style>