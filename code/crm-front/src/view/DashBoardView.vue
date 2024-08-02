<script>
import {doGet} from "../http/httpRequest.js";
import {
  ArrowDown,
  Box,
  Fold,
  Money,
  Notebook,
  OfficeBuilding,
  Operation,
  Stamp,
  Tools,
  User
} from "@element-plus/icons-vue";
import {messageConfirm, messageTip, removeToken} from "../utils/util.js";

export default {
  name: "dashBoardView",
  components: {ArrowDown, Fold, Tools, Stamp, Notebook, Box, Money, User, Operation, OfficeBuilding},

  data(){
    return{
      //Menu是否折疊
      isCollapse: false,
      user : {},
      //控制主體內容是否顯示
      isRouterAlive: true,
      currentRouterPath:'',
    }
  },

  provide(){
    return {
      reload: ()=>{
        this.isRouterAlive = false;
        this.$nextTick(function(){
          this.isRouterAlive = true;
        })
      }
    }
  },

  mounted() {
    //加載當前登入用戶
    this.loadLoginUser();
    this.loadCurrentRouterPath();
  },

  methods:{
    loadCurrentRouterPath(){
      let path = this.$route.path;
      let arr = path.split("/");
      if(arr.length > 3){
        this.currentRouterPath = "/" + arr[1] + "/" + arr[2];
      }else{
        this.currentRouterPath = path;
      }
    },
    //折疊或展開menu
    showMenu(){
      this.isCollapse = !this.isCollapse;
    },

    //加載當前登入用戶
    loadLoginUser(){
      doGet("/api/login/info",{}).then((resp)=>{
        console.log(resp)
        this.user = resp.data.data;
      })
    },
    logout(){
      doGet("/api/logout",{}).then(resp=>{
        if(resp.data.code === 200){
          removeToken();
          window.location.href="/";
          messageTip("退出成功","success");
        } else {
          messageConfirm("退出異常，是否強制退出").then(() => {
            //後端token驗證未通過，前端token肯定有問題，沒必要儲存在瀏覽器內
            removeToken();
            window.location.href="/";
          }).catch(() => {
            messageTip("取消強制退出","warning");
          })
        }
      })
    }
  }
}
</script>

<template>
  <el-container>
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <div class="menuTitle">@萊納斯的CRM</div>
      <el-menu
        active-text-color="#ffd04b"
        background-color="#334157"
        :default-active="currentRouterPath"
        text-color="#fff"
        style="border-right: solid 0"
        :unique-opened="true"
        :collapse="isCollapse"
        :collapse-transition="false"
        :router="true"
    >
        <el-sub-menu :index="index" v-for="(menuPermission, index) in user.memuPermissionList" :key="menuPermission.id">
          <template #title>
            <el-icon><component :is="menuPermission.icon"></component></el-icon>
            <span>{{ menuPermission.name }}</span>

          </template>
          <el-menu-item v-for="subPermission in menuPermission.subPermissionList" :key="subPermission.id" :index="subPermission.url" >
            <el-icon><component :is="subPermission.icon"></component></el-icon>
            {{subPermission.name}}
          </el-menu-item>
        </el-sub-menu>
    </el-menu>



    </el-aside>
    <el-container>

      <el-header>
        <el-icon class="showMenu" @click="showMenu()"><Fold /></el-icon>

        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">
            {{user.name}}
            <el-icon class="el-icon--right"><arrow-down /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>我的資料</el-dropdown-item>
              <el-dropdown-item>修改密碼</el-dropdown-item>
              <el-dropdown-item divided @click="logout()">退出登入</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

      </el-header>

      <el-main class="content">
        <router-view v-if="isRouterAlive"/>
      </el-main>

      <el-footer>@Copyright 2024 萊納斯學習用途</el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>
.el-aside{
  background-color: black;
}
.el-header{
  background-color: azure;
  height: 38px;
  line-height: 38px;
}
.el-footer{
  background-color: aliceblue;
  height: 35px;
  line-height: 35px;
  text-align: center;
}
.content{
  height: calc(100vh);
}
.menuTitle{
  height: 38px;
  line-height: 38px;
  text-align: center;
  cursor: pointer;
  color: white;
}
.showMenu{
  cursor: pointer;
}
.el-dropdown{
  float: right;
  line-height: 38px;
}
</style>