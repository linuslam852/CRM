<script>
import {doGet, doPost} from "../http/httpRequest";
import {getTokenName, messageTip, removeToken} from "../utils/util.js";


export default {
  name: "LoginView",

  data(){
    return{
      user : {},
      name : '',
      loginRules: {
        loginAct : [
          { required: true, message: '請輸入登入帳號', trigger: 'blur' },
        ],
        loginPwd : [
          { required: true, message: '請輸入密碼', trigger: 'blur' },
          { min: 6, max: 16, message: '密碼長度應為6-16位', trigger: 'blur' },
        ]
      }
    }
  },

  mounted(){
    this.freeLogin();
  },

  methods:{
    login(){
      //提交前驗證輸入框的合法性
      this.$refs.loginRefForm.validate((isValid) =>{
        //isValid是驗證後的結果，如果是true表示驗證通過，否則未通過
        if(isValid){
          //驗證通過，可以提交登入
          let formData = new FormData();
          formData.append("loginAct",this.user.loginAct);
          formData.append("loginPwd",this.user.loginPwd);
          formData.append("rememberMe",this.user.rememberMe);
          doPost("/api/login",formData).then((resp)=>{
            console.log(resp);
            if(resp.data.code === 200){
              //登入成功
              messageTip("登入成功","success");
              //刪除歷史的登入jwt
              removeToken();

              //存儲jwt
              if(this.user.rememberMe === true){
                window.localStorage.setItem(getTokenName(),resp.data.data);
              } else {
                window.sessionStorage.setItem(getTokenName(),resp.data.data);
              }

              //跳轉到系統主頁面
              window.location.href = "./dashboard";
            }else{
              //登入失敗
              messageTip("登入失敗","error");
            }
          });
        }
      })
    },

    //免登入（自動登入）
    freeLogin(){
      let token = window.localStorage.getItem(getTokenName());
      if(token){
        //需要自動登入，不需要用帳號去數據庫查詢，只需要將token發送給後台，後台驗證一下token如果是合法的，就自動完成登入
        doGet("/api/login/free",{}).then(resp=>{
          if(resp.data.code === 200){
            window.localStorage.href = "/dashboard";
          }
        })
      }
    }
  }
}

</script>

<template>
  <el-container>
    <el-aside width="200px">
      <img src="../assets/loginBox.svg">
      <p class="imgTitile">
        歡迎使用CRM系統
      </p>
    </el-aside>

    <el-main>
      <div class="loginTitle">歡迎登入</div>

      <el-form ref="loginRefForm" :model="user" label-width="120px" :rules="loginRules">
        <el-form-item label="帳號" prop="loginAct">
          <el-input v-model="user.loginAct" />
        </el-form-item>

        <el-form-item label="密碼" prop="loginPwd">
          <el-input type="password" v-model="user.loginPwd" @keydown.enter="login"/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="login">登 入</el-button>
        </el-form-item>

        <el-form-item>
          <el-checkbox label="記住我" v-model="user.rememberMe">
          </el-checkbox>
        </el-form-item>

      </el-form>
    </el-main>
  </el-container>
</template>

<style scoped>
.el-aside{
  background: #1a1a1a;
  width: 40%;
  text-align: center;
}
.el-main{
  height: calc(100vh);
}
img{
  height: 400px;
}
.imgTitile{
  color: white;
  font-size: 28px;
}
.el-form{
  width: 60%;
  margin: auto;
}
.loginTitle{
  text-align: center;
  margin-top: 100px;
  margin-bottom: 25px;
  font-weight: bold;
}
.el-button{
  width: 100%;
}
</style>