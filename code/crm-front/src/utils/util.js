import {ElMessage, ElMessageBox} from "element-plus";

export function messageTip(msg,type) {
    ElMessage({
        showClose: true,
        center: true,
        message: msg,
        type: type,
    })
}

export function getTokenName(){
    return "crm_token";
}

export function removeToken(){
    window.localStorage.removeItem(getTokenName());
    window.sessionStorage.removeItem(getTokenName());
}

export function messageConfirm(msg){
    return ElMessageBox.confirm(
        msg,
        '系統提醒',
        {
            confirmButtonText: '確定',
            cancelButtonText:'取消',
            type: 'warning',
        }
    )
}

export function goBack(){
    this.$router.go(-1);
}

export function getToken(){
    let token = window.sessionStorage.getItem(getTokenName());
    if(!token){
        token = window.localStorage.getItem(getTokenName());
    }
    if(token){
       return token;
    } else {
        messageConfirm(  '請求token為空，是否重新登入？').then(() => {
            removeToken();
            window.location.href="/";
        }).catch(() => {
            messageTip("取消登入","warning");
        })
    }
}