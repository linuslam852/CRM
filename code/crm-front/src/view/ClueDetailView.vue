<script>
import {doGet, doPost} from "../http/httpRequest.js";
import {goBack, messageTip} from "../utils/util.js";

export default {
  name: "ClueDetailView",
  data(){
    return{
      clueQuery:{
        clueRemark:{},
        ownerDO: {},
        activityDO : {},
        appellationDO : {},
        needLoanDO : {},
        intentionStateDO : {},
        intentionProductDO : {},
        stateDO : {},
        sourceDO : {}
      },
      clueRemark:{},
      noteWayOptions:[{}],

    }
  },

  mounted() {
    this.loadClueDetail();
  },
  methods:{
    goBack,

    loadClueDetail(){
      let id = this.$route.params.id;
      doGet("/api/clue/detail/"+id,{}).then(resp=>{
        if(resp.data.code === 200){
          console.log(resp);
          this.clueQuery = resp.data.data;
        }
      })
    },

    clueRemarkSubmit(){
      doPost("/api/clue/remark",{
        clueId : this.clueQuery.id,
        noteContent: this.clueRemark.noteContent,
        noteWay: this.clueRemark.noteWay
      }).then(resp=>{
        if(resp.data.code === 200){
          messageTip("提交成功","success");
          this.clueRemark.noteContent = "";
          this.reload();
        }else{
          messageTip("提交失敗","error");
        }
      })
    },

    loadDicValue(typeCode) {
      doGet("/api/dicvalue/" + typeCode, {}).then( resp => {
        if (resp.data.code === 200) {
          if (typeCode === 'noteWay') {
            this.noteWayOptions = resp.data.data;
          }
        }
      })
    },
  },

}
</script>

<template>
  <el-form
      ref="clueRefForm"
      :model="clueQuery"
      :rules="clueRules"
      label-width="100px"
      style="max-width: 95%;">

    <el-form-item label="負責人" >
      <div class="detail">{{clueQuery.ownerDO.name}}</div>
    </el-form-item>

    <el-form-item label="所屬活動">
      <div class="detail">{{clueQuery.activityDO.name}}</div>
    </el-form-item>

    <el-form-item label="姓名">
      <div class="detail">{{clueQuery.fullName}}</div>
    </el-form-item>

    <el-form-item label="稱呼">
      <div class="detail">{{clueQuery.appellationDO.typeValue}}</div>
    </el-form-item>

    <el-form-item label="手機" ><!--此时是编辑-->
      <div class="detail">{{clueQuery.phone}}</div>
    </el-form-item>


    <el-form-item label="微信">
      <div class="detail">{{clueQuery.weixin}}</div>
    </el-form-item>

    <el-form-item label="QQ" prop="qq">
      <div class="detail">{{clueQuery.qq}}</div>
    </el-form-item>

    <el-form-item label="郵箱" >
      <div class="detail">{{clueQuery.email}}</div>
    </el-form-item>

    <el-form-item label="年齡" >
      <div class="detail">{{clueQuery.age}}</div>
    </el-form-item>

    <el-form-item label="職業">
      <div class="detail">{{clueQuery.job}}</div>
    </el-form-item>

    <el-form-item label="年收入">
      <div class="detail">{{clueQuery.yearIncome}}</div>
    </el-form-item>

    <el-form-item label="住址">
      <div class="detail">{{clueQuery.address}}</div>
    </el-form-item>

    <el-form-item label="貸款">
      <div class="detail">{{clueQuery.needLoanDO.typeValue}}</div>
    </el-form-item>

    <el-form-item label="意向狀態">
      <div class="detail">{{clueQuery.intentionStateDO.typeValue}}</div>
    </el-form-item>

    <el-form-item label="意向產品">
      <div class="detail">{{clueQuery.intentionProductDO.name}}</div>
    </el-form-item>

    <el-form-item label="線索狀態">
      <div class="detail">{{clueQuery.stateDO.typeValue}}</div>
    </el-form-item>

    <el-form-item label="線索來源">
      <div class="detail">{{clueQuery.sourceDO.typeValue}}</div>
    </el-form-item>

    <el-form-item label="線索描述" prop="description">
      <div class="detail">{{clueQuery.description}}</div>
    </el-form-item>

    <el-form-item label="下次聯絡時間">
      <div class="detail">{{clueQuery.nextContactTime}}</div>
    </el-form-item>

    <el-form-item label="填寫跟蹤記錄" prop="noteContent">
      <el-input
          v-model="clueRemark.noteContent"
          :rows="8"
          type="textarea"
          placeholder="請輸入活動備註"
          style="width:50%"
      />
    </el-form-item>


    <el-form-item label="跟蹤方式" prop="noteWay">
      <el-select
          v-model="clueRemark.noteWay"
          placeholder="請選擇跟蹤方式"
          style="width: 100%"
          @click="loadDicValue('noteWay')"
          clearable>
        <el-option
            v-for="item in noteWayOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="clueRemarkSubmit">提 交</el-button>
      <el-button type="success" @click="convertCustomer" v-if="clueQuery.state !== -1">轉換客戶</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
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