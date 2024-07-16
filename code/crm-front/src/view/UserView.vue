<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "UserView",
  data(){
    return{
      userList:[{}],
      pageSize: 0,
      total:0,
    }
  },

  mounted() {
    this.getData(1);
  },

  methods :{
    //勾選或者取消勾選時，觸發該方法
    handleSelectionChange(){

    },

    getData(current){
      doGet("/api/users",{
        current: current //當前是第幾頁
      }).then(resp=> {
        if(resp.data.code === 200){
          console.log(resp)
          this.userList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    //current是element-plus傳過來的
    toPage(current){
      this.getData(current);
    },

    view(id){
      this.$router.push("/dashboard/user/"+id);
    }
  }
}
</script>

<template>
  <el-button type="primary">添加用戶</el-button>
  <el-button type="danger">批量刪除</el-button>
  <el-table
      :data="userList"
      style="width: 100%"
      @selection-change="handleSelectionChange"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column type="index" label="序號" width="60" />
    <el-table-column property="loginAct" label="帳號"/>
    <el-table-column property="name" label="姓名" />
    <el-table-column property="phone" label="手機" />
    <el-table-column property="email" label="郵箱" />
    <el-table-column property="createTime" label="創建時間" />
    <el-table-column label="操作" width="230">
      <template #default="scope" >
        <el-button type="primary" @click="view(scope.row.id)">詳情</el-button>
        <el-button type="success" @click="edit(scope.row.id)">編輯</el-button>
        <el-button type="danger" @click="del(scope.row.id)">刪除</el-button>
      </template>

    </el-table-column>
  </el-table>

  <el-pagination
      background
      layout="prev, pager, next"
      page-size="pageSize"
      :total="total"
      @prev-click="toPage"
      @next-click="toPage"
      @current-change="toPage"/>
</template>

<style scoped>
.el-pagination{
  margin-top: 12px;
}
.el-table{
  margin-top: 12px;
}
</style>