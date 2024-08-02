import { createApp } from 'vue'
//import './style.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router/router'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import {doGet} from "./http/httpRequest.js";

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.directive("hasPermission",(el, binding)=>{
    doGet("/api/login/info",{}).then(resp=>{
        let user = resp.data.data;
        let permissionList = user.permissionList;
        let flag = false;

        for(let key in permissionList){
            if(permissionList[key] === binding.value){
                flag = true;
                break;
            }
        }
        if(!flag){
            // el.style.display = 'none';
            el.parentNode && el.parentNode.removeChild(el);
        }
    })

})
app.use(ElementPlus).use(router).mount('#app')