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
        'Warning',
        {
            confirmButtonText: '確定',
            cancelButtonText:'取消',
            type: 'warning',
        }
    )
}