// Customize instances of request
// import axios: npm install axios
import axios from 'axios';
import {ElMessage} from "element-plus";
import {useTokenStore} from "@/stores/token.js";

// Get our defined router
// to jump to login page if not logged in
import router from "@/router";

// Define a variable to record the public prefix: baseURL
const baseURL = '/api';
const instance = axios.create({baseURL})

// Add a Request-Interceptor: deal with JWT token
instance.interceptors.request.use(
    (config) => {
        // set JWT token before sending request
        let tokenStore = useTokenStore();
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    (err)=> {
        return Promise.reject(err);
    }
);

// Add a Response-Interceptor
instance.interceptors.response.use(
    result=>{
        if (result.data.code === 0) { // success
            return result.data;
        }
        // not zero: failed
        ElMessage({
            message: result.data.message || 'Service Anomaly',
            type: "error"
        });
        return Promise.reject(result.data);
    },
    err=>{
        // Check if the failure is caused by non-login
        if (err.response.status === 401) {
            ElMessage.error("Please login first!");
            router.push('/login');
        } else {
            ElMessage.error(err.message || 'Service Anomaly');
        }
        // Asynchronous states are transformed into failed states
        return Promise.reject(err);
    }
)

export default instance;