<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "dashBoardView",

  data(){
    return{
      //Menu是否折疊
      isCollapse: false
    }
  },

  mounted() {
    //加載當前登入用戶
    this.loadLoginUser();
  },

  methods:{
    //折疊或展開menu
    showMenu(){
      this.isCollapse = !this.isCollapse;
    },

    //加載當前登入用戶
    loadLoginUser(){
      doGet("/api/login/info",{}).then((resp)=>{
        console.log(resp);
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
        default-active="2"
        text-color="#fff"
        style="border-right: solid 0px"
        :unique-opened="true"
        :collapse="isCollapse"
        :collapse-transition="false"
    >
      <el-sub-menu index="1">
        <template #title>
          <el-icon><OfficeBuilding /></el-icon>
          <span>市場活動</span>
        </template>
          <el-menu-item index="1-1">市場活動</el-menu-item>
          <el-menu-item index="1-2">市場統計</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="2">
        <template #title>
          <el-icon><Operation /></el-icon>
          <span>線索管理</span>
        </template>
        <el-menu-item index="2-1">線索管理</el-menu-item>
        <el-menu-item index="2-2">線索統計</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="3">
        <template #title>
          <el-icon><User /></el-icon>
          <span>客戶管理</span>
        </template>
        <el-menu-item index="3-1">客戶管理</el-menu-item>
        <el-menu-item index="3-2">客戶管理</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="4">
        <template #title>
          <el-icon><Money /></el-icon>
          <span>交易管理</span>
        </template>
        <el-menu-item index="4-1">交易管理</el-menu-item>
        <el-menu-item index="4-2">客戶管理</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="5">
        <template #title>
          <el-icon><Box /></el-icon>
          <span>產品管理</span>
        </template>
        <el-menu-item index="5-1">產品管理</el-menu-item>
        <el-menu-item index="5-2">產品管理</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="6">
        <template #title>
          <el-icon><Notebook /></el-icon>
          <span>字典管理</span>
        </template>
        <el-menu-item index="6-1">交易管理</el-menu-item>
        <el-menu-item index="6-2">客戶管理</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="7">
        <template #title>
          <el-icon><Stamp /></el-icon>
          <span>用戶管理</span>
        </template>
        <el-menu-item index="7-1">產品管理</el-menu-item>
        <el-menu-item index="7-2">產品管理</el-menu-item>
      </el-sub-menu>

      <el-sub-menu index="8">
        <template #title>
          <el-icon><Tools /></el-icon>
          <span>系統管理</span>
        </template>
        <el-menu-item index="8-1">系統管理</el-menu-item>
        <el-menu-item index="8-2">系統管理</el-menu-item>
      </el-sub-menu>
    </el-menu>



    </el-aside>
    <el-container>

      <el-header>
        <el-icon class="showMenu" @click="showMenu()"><Fold /></el-icon>

        <el-dropdown :hide-on-click="false">
          <span class="el-dropdown-link">
            Dropdown List
            <el-icon class="el-icon--right"><arrow-down /></el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item>我的資料</el-dropdown-item>
              <el-dropdown-item>修改密碼</el-dropdown-item>
              <el-dropdown-item divided>退出登入</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

      </el-header>

      <el-main class="content">Main</el-main>

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