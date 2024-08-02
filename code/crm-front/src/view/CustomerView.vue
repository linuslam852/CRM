<script>
import {doGet} from "../http/httpRequest.js";
import {getToken, messageTip} from "../utils/util.js";
import axios from "axios";

export default {
  name: "CustomerView",
  data(){
    return{
      customerList:[{
        clueDO:{},
        ownerDO : {},
        activityDO : {},
        appellationDO : {},
        needLoanDO : {},
        intentionStateDO : {},
        intentionProductDO : {},
        stateDO : {},
        sourceDO : {}
      }],
      pageSize: 0,
      total:0,
      customerIdArray:[],
    }
  },

  mounted() {
    this.getData(1);
  },

  methods:{
    handleSelectionChange(selectionDataArray){
      this.customerIdArray = [];
      selectionDataArray.forEach(data=>{
        let customerId = data.id;
        this.customerIdArray.push(customerId);
      })
    },

    getData(current){
      doGet("/api/customers",{
        current: current //當前是第幾頁
      }).then(resp=> {
        if(resp.data.code === 200){
          console.log(resp)
          this.customerList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    toPage(current){
      this.getData(current);
    },

    exportExcel(ids){
      let token = getToken();
      let iframe = document.createElement("iframe");
      if(ids){
        iframe.src = axios.defaults.baseURL + "/api/exportExcel?Authorization="+token + "&ids=" + ids;
      } else{
        iframe.src = axios.defaults.baseURL + "/api/exportExcel?Authorization="+token;
      }
      iframe.style.display="none";
      document.body.appendChild(iframe);
    },

    batchExportExcel(){
      this.exportExcel(null);
    },

    chooseExportExcel(){
        if(this.customerIdArray.length <= 0){
            messageTip("請選擇要導出的數據","warning");
            return;
        }
        let ids = this.customerIdArray.join(",");
        this.exportExcel(ids);
    },

  },

}
</script>

<template>
  <el-button type="primary" class="btn" @click="batchExportExcel()">批量導出(Excel)</el-button>
  <el-button type="success" class="btn" @click="chooseExportExcel()">選擇導出(Excel)</el-button>

  <el-table
      :data="customerList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="40"/>
    <el-table-column type="index" label="序號" width="55"/>
    <el-table-column property="ownerDO.name" label="負責人" width="90" />
    <el-table-column property="activityDO.name" label="所屬活動" width="100"/>
    <el-table-column label="姓名">
      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.clueDO.fullName }}</a>
      </template>
    </el-table-column>
    <el-table-column property="appellationDO.typeValue" label="稱呼"/>
    <el-table-column property="clueDO.phone" label="手機" width="120"/>

    <el-table-column property="needLoanDO.typeValue" label="是否貸款"/>
    <el-table-column property="intentionStateDO.typeValue" label="意向狀態"/>
    <el-table-column label="線索狀態">
      <template #default="scope">
        <span style="background: chartreuse" v-if="scope.row.state === -1">{{scope.row.stateDO.typeValue}}</span>
        <span v-else>{{scope.row.stateDO.typeValue}}</span>
      </template>
    </el-table-column>
    <el-table-column property="sourceDO.typeValue" label="線索來源"/>
    <el-table-column property="intentionProductDO.name" label="意向產品"/>
    <el-table-column property="nextContactTime" label="下次聯絡時間" width="165"/>
    <el-table-column label="操作" width="230">
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)" >詳情</el-button>
        <el-button type="success" @click="edit(scope.row.id)" >編輯</el-button>
        <el-button type="danger" @click="del(scope.row.id)">刪除</el-button>
      </template>
    </el-table-column>
  </el-table>


  <el-pagination
      background
      layout="prev, pager, next"
      :page-size="pageSize"
      :total="total"
      @prev-click="toPage"
      @next-click="toPage"
      @current-change="toPage"/>
</template>

<style scoped>

</style>