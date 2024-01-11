import { defineStore } from "pinia";
import { ref } from 'vue';

// Store global-use user info data: avatar, username, etc.

const useUserInfoStore = defineStore('userInfo', ()=>
{
    // define user-state-related info
    const info = ref({});
    const setInfo = (newInfo)=>{ info.value = newInfo; };
    const removeInfo = ()=>{ info.value=''; };
    return { info, setInfo, removeInfo };
},
{
    persist: true
});

export default useUserInfoStore;