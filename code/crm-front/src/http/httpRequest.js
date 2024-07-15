import axios from "axios";
import {getTokenName, messageConfirm, messageTip, removeToken} from "../utils/util.js";
import {ElMessage, ElMessageBox} from "element-plus";

axios.defaults.baseURL = "http://localhost:8088"
export function doGet(url, params){
    return axios({
        method: "get",
        url: url,
        params: params,
        dataType: "json"
    })
}

export function doPost(url, data){
    return axios({
        method: "post",
        url: url,
        data: data,
        dataType: "json"
    })
}

export function doPut(url, data){
    axios({
        method: "put",
        url: url,
        data: data,
        dataType: "json"
    }).then(function(rep){
        var s = "";
        rep.data.forEach(function (stu){
            s += stu.name + "-----------" + stu.age +"<br>";
        });
        document.getElementById("mydiv").innerHTML = s;
    })
}

export function doDelete(url, params){
    axios({
        method: "delete",
        url: url,
        params: params,
        dataType: "json"
    }).then(function(rep){
        var s = "";
        rep.data.forEach(function (stu){
            s += stu.name + "-----------" + stu.age +"<br>";
        });
        document.getElementById("mydiv").innerHTML = s;
    })
}

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    let token = window.sessionStorage.getItem(getTokenName());
    if(!token){
        token = window.localStorage.getItem(getTokenName());
        if(token){
            config.headers['rememberMe'] = true;
        }
    }
    if(token){
        config.headers['Authorization'] = token;
    }
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
axios.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 攔截token驗證的結果，進行相應的提示跳轉
    if(response.data.code > 900){

        messageConfirm(response.data.msg + '， 是否重新登入？').then(() => {
            //後端token驗證未通過，前端token肯定有問題，沒必要儲存在瀏覽器內
            removeToken();
            window.location.href="/";
        }).catch(() => {
            messageTip("取消登入","warning");
        })
        return ;
    }

    return response;
}, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
});