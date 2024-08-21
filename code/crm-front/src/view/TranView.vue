<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "TranView",
  data() {
    return {
      tranList : [{
        cluePO : {},
        stageDO :{},
      }],
      pageSize : 0,
      total : 0,
    }
  },

  mounted() {
    this.getData(1);
  },

  methods : {

    getData(current) {
      doGet("/api/trans", {
        current : current
      }).then(resp => {
        if (resp.data.code === 200) {
          this.tranList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    toPage(current){
      this.getData(current);
    },

    view(id) {
      this.$router.push("/dashboard/tran/detail/" + id);
    }
  }
}
</script>

<template>
  <el-table
      :data="tranList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序號" width="65"/>
    <el-table-column label="交易流水號">
      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.tranNo }}</a>
      </template>
    </el-table-column>
    <el-table-column property="clueDO.fullName" label="客戶姓名"/>
    <el-table-column property="clueDO.phone" label="客戶手機"/>
    <el-table-column property="money" label="交易金額"/>
    <el-table-column property="expectedDate" label="預計成交時間"/>
    <el-table-column property="stageDO.typeValue" label="交易階段"/>
    <el-table-column property="nextContactTime" label="下次跟蹤時間"/>
    <el-table-column property="createTime" label="創建時間"/>
    <el-table-column label="操作" width="90">
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)">詳情</el-button>
      </template>
    </el-table-column>
  </el-table>

  <p>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="total"
        @prev-click="toPage"
        @next-click="toPage"
        @current-change="toPage"/>
  </p>
</template>

<style scoped>

</style>