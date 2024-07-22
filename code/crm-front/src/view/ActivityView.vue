<script>
import {doGet} from "../http/httpRequest.js";

export default {
  name: "ActivityView",

  data(){
    return{
      activityQuery:{},
      activityList:[{
        ownerDO:{},
      }],
      pageSize:0,
      total:0,
    }
  },

  mounted() {
    this.getData(1);
  },

  methods:{
    getData(current){
      doGet("/api/activitys",{
        current: current //當前是第幾頁
      }).then(resp=> {
        if(resp.data.code === 200){
          console.log(resp)
          this.activityList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    toPage(current){
      this.getData(current);
    },
  }
}
</script>

<template>
  <el-form :inline="true" :model="activityQuery">

    <el-form-item label="負責人">
      <el-select
          v-model="activityQuery.ownerId"
          placeholder="請選擇負責人"
          clearable>
        <el-option label="w" value="shanghai" />
        <el-option label="z" value="beijing" />
      </el-select>
    </el-form-item>

    <el-form-item label="活動名稱">
      <el-input v-model="activityQuery.user" placeholder="請輸入活動名稱" clearable />
    </el-form-item>


    <el-form-item label="活動時間">
      <el-date-picker
          v-model="activityQuery.startTime"
          type="datetimerange"
          start-placeholder="開始時間"
          end-placeholder="結束時間"
          format="YYYY-MM-DD HH:mm:ss"
          date-format="YYYY/MM/DD ddd"
          time-format="A hh:mm:ss"
      />
    </el-form-item>

    <el-form-item label="活動預算">
      <el-input v-model="activityQuery.cost" placeholder="請輸入活動預算" clearable />
    </el-form-item>

    <el-form-item label="創建時間">
      <el-date-picker
          v-model="activityQuery.createTime"
          type="datetime"
          placeholder="請選擇創建時間"
      />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSearch">搜 索</el-button>
      <el-button type="primary" plain @click="onReset">重 設</el-button>
    </el-form-item>
  </el-form>

  <el-button type="primary" @click="add()">添加市場活動</el-button>
  <el-button type="danger" @click="batchDel()">批量刪除</el-button>
  <el-table
      :data="activityList"
      style="width: 100%"
  >
    <el-table-column type="selection" width="55" />
    <el-table-column type="index" label="序號" width="60" />
    <el-table-column property="ownerDO.name" label="負責人"/>
    <el-table-column property="name" label="活動名稱" />
    <el-table-column property="startTime" label="開始時間" />
    <el-table-column property="endTime" label="結束時間" />
    <el-table-column property="cost" label="活動預算" />
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
.el-form{
  margin-bottom: 20px;
}
.el-select {
  --el-select-width: 150px;
}
</style>