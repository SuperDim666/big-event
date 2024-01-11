import { defineStore } from "pinia";
import { ref } from 'vue';

/*
    defineStore() using Pinia to store our token:
        1. params:
            1st param: unique name for the state;
            2nd param: function that defines what is owned in the state.
        2. return:
            a function that can be called in the future
            to get the content returned in the 2nd param,
            SUCH AS { token, setToken(), removeToken() }.
*/
export const useTokenStore = defineStore('token',()=>{
    // 1. Define token
    const token = ref('');

    // 2. Change token
    const setToken = (newToken)=>{ token.value = newToken; };

    // 3. Remove token
    const removeToken = ()=>{ token.value=''; };

    return { token, setToken, removeToken };
}, {
    // enable persist for this datum stored in Pinia
    persist: true
});