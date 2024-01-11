// import request.js
import request from '@/utils/request.js'

// provide function for sign-up API
export const userSignUpService = (signUpData)=>{
    // user urlSearchParams to complete our transmission
    // by converting data type from json into x-www-form-data
    const params = new URLSearchParams();
    for (let key in signUpData) { params.append(key, signUpData[key]); }

    return request.post('/user/register', params);
}

// provide function for login API
export const userLoginService = (signUpData)=>{
    const params = new URLSearchParams();
    for (let key in signUpData) { params.append(key, signUpData[key]); }


    return request.post('/user/login', params);
}

// obtain user-info API
export const userInfoService = () => {
    return request.get('/user/userInfo');
}

// change user info
export const userInfoUpdateService = (userInfoData) => {
    return request.put('/user/update', userInfoData);
}

// change user avatar
export const userAvatarUpdateService = (avatarUrl) => {
    const params = new URLSearchParams();
    params.append('avatarUrl', avatarUrl);
    return request.patch('/user/updateAvatar', params);
}

// change password
export const userPwdUpdateService = (passwordData) => {
    return request.patch('/user/updatePwd', passwordData);
}