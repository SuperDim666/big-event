import { createApp } from 'vue';
import App from './App.vue';
import './assets/main.scss'
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import router from "@/router";
import { createPinia } from 'pinia';
import { createPersistedState } from "pinia-persistedstate-plugin";

const app = createApp(App);
const pinia = createPinia();
const persist = createPersistedState();

// 1. Pinia is in-memory storage;
// 2. Pinia will lose data when refreshing the browser page;
// 3. If refreshing page, we will lose JWT login token stored in Pinia.
//
// Persist plugin of Pinia can make data stored in Pinia persistent.
pinia.use(persist);

app.use(pinia);   // for saving token
app.use(ElementPlus);
app.use(router); // for routers: switching between vue components
app.mount('#app');
