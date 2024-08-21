<script>
import {doDelete, doGet, doPost, doPut} from "../http/httpRequest.js";
import {goBack, messageConfirm, messageTip} from "../utils/util.js";

export default {
  name: "ClueDetailView",
  inject:["reload"],
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
      pageSize: 0,
      total:0,
      clueRemarkList:[{
        createByDO:{},
        editByDO:{},
        noteWayDO:{},
      }],
      convertCustomerDialogVisible:false,
      clueRemarkDialogVisible:false,
      customerQuery:{},
      productOptions : [{}],
      convertCustomerRules : {
        product : [
          { required: true, message: '請選擇意向產品', trigger: ['blur', 'change'] }
        ],
        description : [
          { required: true, message: '客戶描述不能為空', trigger: 'blur' },
          { min: 5, max: 255, message: '客户描述長度為5-255個字', trigger: 'blur' }
        ],
        nextContactTime : [
          { required: true, message: '請選擇下次聯絡時間', trigger: 'blur' }
        ]
      },
      editClueRemarkRules:{
        noteContent:[
          {min: 5, max:255, message:'備註內容長度為5-255個字符',trigger:'blur'}
        ]
      },
      clueEditRemark:{},



    }
  },

  mounted() {
    this.loadClueDetail();
    this.loadClueRemarkList(1);
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
          } else if(typeCode === 'product'){
            this.productOptions = resp.data.data;
          }
        }
      })
    },

    toPage(current){
      this.loadClueRemarkList(current);
    },

    loadClueRemarkList(current){
      doGet("/api/clue/remark",{
        current: current, //當前是第幾頁
        clueId: this.$route.params.id,
      }).then(resp=> {
        if(resp.data.code === 200){
          console.log(resp)
          this.clueRemarkList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    convertCustomer() {
      this.convertCustomerDialogVisible = true;
    },

    convertCustomerSubmit(){
      this.$refs.convertCustomerRefForm.validate((isValid)=>{
        if(isValid){
          doPost("/api/clue/customer",{
            clueId:this.clueQuery.id,
            product:this.customerQuery.product,
            description: this.customerQuery.description,
            nextContactTime:this.customerQuery.nextContactTime
          }).then(resp=>{
              if(resp.data.code === 200){
                messageTip("轉換成功","success");
                this.reload();
              }else{
                messageTip("轉換失敗","error");
              }
          })
        }
      })
    },

    edit(id){
      this.clueRemarkDialogVisible = true;
      doGet("/api/clue/remark/"+id,{}).then(resp=>{
        if(resp.data.code === 200){
          this.clueEditRemark = resp.data.data;
        }
      })
    },

    editClueRemarkSubmit(){
      this.$refs.editClueRemarkRefForm.validate(isValid => {
        if(isValid){
          doPut("/api/clue/remark",{
            id: this.clueEditRemark.id,
            noteContent: this.clueEditRemark.noteContent
          }).then(resp=>{
            if(resp.data.code === 200){
              messageTip("修改成功","success");
              this.reload();
            }else{
              messageTip("修改失敗","error");
            }
          })
        }
      })
    },

    del(id){
      messageConfirm("你確定要刪除該數據嗎？").then(()=>{
        doDelete("/api/clue/remark/"+id,{}).then(resp=>{
          if(resp.data.code === 200){
            messageTip("刪除成功","success");
            this.reload();
          }else{
            messageTip("刪除失敗， 原因" + resp.data.msg,"error");
          }
        }).catch(()=>{
          messageTip("取消刪除","warning");
        })
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
      <el-button type="success" @click="convertCustomer" v-if="clueQuery.state !== -1">轉為客戶</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
    </el-form-item>


  </el-form>

  <el-table
      :data="clueRemarkList"
      style="width: 100%"
  >
    <el-table-column type="index" label="序號" width="60" />
    <el-table-column property="noteWayDO.typeValue" label="跟蹤方式"/>
    <el-table-column property="noteContent" label="跟蹤內容" />
    <el-table-column property="createTime" label="跟蹤時間" />
    <el-table-column property="createDO.name" label="跟蹤人" />
    <el-table-column property="editTime" label="編輯時間" />
    <el-table-column property="editDO.name" label="編輯人" />

    <el-table-column label="操作" width="230">
      <template #default="scope" >
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

  <el-dialog v-model="convertCustomerDialogVisible" title="編輯備註" center draggable>
    <el-form ref="convertCustomerRefForm" :model="customerQuery" label-width="110px" :rules="convertCustomerRules">
      <el-form-item label="意向產品" prop="product">
        <el-select v-model="customerQuery.product" placeholder="請選擇" style="width: 100%;" @click="loadDicValue('product')">
          <el-option
            v-for="item in productOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="客戶描述" prop="description">
        <el-input
            v-model="customerQuery.description"
            :rows="8"
            type="textarea"
            placeholder="請輸入客戶描述"
        />
      </el-form-item>
      <el-form-item label="下次跟蹤時間" prop="nextContactTime">
        <el-date-picker
            v-model="customerQuery.nextContactTime"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="請選擇下次跟蹤時間"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="convertCustomerDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="convertCustomerSubmit">提 交</el-button>
      </span>
    </template>
  </el-dialog>


  <el-dialog v-model="clueRemarkDialogVisible" title="編輯備註" center draggable>
    <el-form ref="editClueRemarkRefForm" :model="clueEditRemark" label-width="110px" :rules="editClueRemarkRules">
      <el-form-item label="備註" prop="noteContent">
        <el-input
            v-model="clueEditRemark.noteContent"
            :rows="8"
            type="textarea"
            placeholder="請輸入活動備註"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="clueRemarkDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="editClueRemarkSubmit">提 交</el-button>
      </span>
    </template>
  </el-dialog>


</template>

<style scoped>
.detail{
  background-color: #F0FFFF;
  width: 100%;
  padding-left: 15px;
}
</style>