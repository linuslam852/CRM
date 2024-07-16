<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "UserDetailView.vue",

  data(){
    return{
      userDetail:{
        createByDo: {},
        editByDo: {}
      },
    }
  },

  mounted() {
    this.loadUserDetail();
  },

  methods:{
    loadUserDetail(){
      let id = this.$route.params.id; //必須和路由配置的path "user/id"一樣
      doGet("/api/user/"+id, {}).then(resp=>{
        if(resp.data.code === 200){
          this.userDetail = resp.data.data;
          if(!this.userDetail.createByDo){
            this.userDetail.createByDo = {};
          }
          if(!this.userDetail.editByDo){
            this.userDetail.editByDo = {};
          }
        }
      })
    },

    //返回上一頁
    goBack(){
      this.$router.go(-1);
    }
  }
}
</script>

<template>
  <el-form ref="loginRefForm" :model="userDetail" label-width="120px">
    <el-form-item label="ID">
      <div class="detail">&nbsp;{{userDetail.id}}</div>
    </el-form-item>

    <el-form-item label="帳號">
      <div class="detail">&nbsp;{{userDetail.loginAct}}</div>
    </el-form-item>

    <el-form-item label="姓名">
      <div class="detail">&nbsp;{{userDetail.name}}</div>
    </el-form-item>

    <el-form-item label="手機">
      <div class="detail">&nbsp;{{userDetail.phone}}</div>
    </el-form-item>

    <el-form-item label="郵箱">
      <div class="detail">&nbsp;{{userDetail.email}}</div>
    </el-form-item>

    <el-form-item label="帳號未過期">
      <div class="detail">&nbsp;{{userDetail.accountNoExpired === 1 ? '是' : '否'}}</div>
    </el-form-item>

    <el-form-item label="密碼未過期">
      <div class="detail">&nbsp;{{userDetail.credentialsNoExpired === 1 ? '是' : '否'}}</div>
    </el-form-item>

    <el-form-item label="帳號未鎖定">
      <div class="detail">&nbsp;{{userDetail.accountNoLocked === 1 ? '是' : '否'}}</div>
    </el-form-item>

    <el-form-item label="帳號是否啟用">
      <div class="detail">&nbsp;{{userDetail.accountEnabled === 1 ? '是' : '否'}}</div>
    </el-form-item>

    <el-form-item label="創建時間">
      <div class="detail">&nbsp;{{userDetail.createTime}}</div>
    </el-form-item>

    <el-form-item label="創建人">
      <div class="detail">&nbsp;{{userDetail.createByDo.name}}</div>
    </el-form-item>

    <el-form-item label="修改時間">
      <div class="detail">&nbsp;{{userDetail.editTime}}</div>
    </el-form-item>

    <el-form-item label="修改者">
      <div class="detail">&nbsp;{{userDetail.editByDo.name}}</div>
    </el-form-item>

    <el-form-item label="最近登入時間">
      <div class="detail">&nbsp;{{userDetail.lastLoginTime}}</div>
    </el-form-item>

    <el-form-item>
      <el-button type="success" @click="goBack">返 回</el-button>
    </el-form-item>

  </el-form>
</template>

<style scoped>
.detail{
  background-color: #F0FFFF;
  width: 100%;
  padding-left: 15px;
}
</style>