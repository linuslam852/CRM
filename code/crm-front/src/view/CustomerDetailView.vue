<script>
import {goBack, messageConfirm, messageTip} from "../utils/util.js";
import {doDelete, doGet, doPost, doPut} from "../http/httpRequest.js";

export default {
  name: "CustomerDetailView",
  inject:["reload"],
  data(){
    return{
      clueQuery:{
        customerRemark:{},
        ownerDO: {},
        activityDO : {},
        appellationDO : {},
        needLoanDO : {},
        intentionStateDO : {},
        intentionProductDO : {},
        stateDO : {},
        sourceDO : {}
      },
      customerQuery:{
        intentionProductDO : {},
      },
      customerRemark:{},
      noteWayOptions:[{}],
      pageSize: 0,
      total:0,
      customerRemarkList:[{
        createByDO:{},
        editByDO:{},
        noteWayDO:{},
      }],
      customerRemarkDialogVisible:false,
      productOptions : [{}],

      editCustomerRemarkRules:{
        noteContent:[
          {min: 5, max:255, message:'備註內容長度為5-255個字符',trigger:'blur'}
        ]
      },

      customerRemarkRules:{
        noteContent:[
          {min: 5, max:255, message:'備註內容長度為5-255個字符',trigger:'blur'}
        ]
      },

      createTranRules : {
        money : [
          { required: true, message: '請輸入交易金額', trigger: 'blur' },
          { pattern : /^[0-9]+(\.[0-9]{2})?$/, message: '交易金額必須是整數或者只有兩位小數', trigger: 'blur'}
        ],
        expectedDate : [
          { required: true, message: '請選擇預計成交時間', trigger: 'blur' },
        ],
        stage : [
          { required: true, message: '請選擇交易階段', trigger: 'blur' },
        ],
        description : [
          { required: true, message: '描述不能為空', trigger: 'blur' },
          { min: 5, max: 255, message: '描述長度範圍為5-255個字符', trigger: 'blur' }
        ],
        nextContactTime : [
          { required: true, message: '請選擇下次跟蹤時間', trigger: 'blur' },
        ]
      },
      customerEditRemark:{},
      clueId:0,
      createTranDialogVisible:false,
      tranQuery : {},
      stageOptions : [{}],
    }
  },

  mounted() {
    this.loadCustomerDetail();
    this.loadCustomerRemarkList(1);
  },
  methods:{
    goBack,

    loadClueDetail(){
      doGet("/api/clue/detail/"+ this.clueId,{}).then(resp=>{
        if(resp.data.code === 200){
          this.clueQuery = resp.data.data;
        }
      })
    },

    loadCustomerDetail(){
      let id = this.$route.params.id;
      doGet("/api/customer/detail/"+id,{}).then(resp=>{
        if(resp.data.code === 200){
          this.customerQuery = resp.data.data;
          this.clueId = this.customerQuery.clueId;
          this.loadClueDetail();
          this.loadCustomerRemarkList();
        }
      })
    },

    customerRemarkSubmit(){
      this.$refs.customerRemarkRefForm.validate(isValid => {
        if(isValid){
          doPost("/api/customer/remark",{
            customerId : this.customerQuery.id,
            noteContent: this.customerRemark.noteContent,
            noteWay: this.customerRemark.noteWay
          }).then(resp=>{
            if(resp.data.code === 200){
              messageTip("提交成功","success");
              this.customerRemark.noteContent = "";
              this.reload();
            }else{
              messageTip("提交失敗","error");
            }
          })
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
          } else if (typeCode === 'stage') {
            this.stageOptions = resp.data.data;
          }
        }
      })
    },

    toPage(current){
      this.loadCustomerRemarkList(current);
    },


    loadCustomerRemarkList(current){
      doGet("/api/customer/remark",{
        current: current, //當前是第幾頁
        customerId: this.$route.params.id,
      }).then(resp=> {
        if(resp.data.code === 200){
          this.customerRemarkList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    edit(id){
      this.customerRemarkDialogVisible = true;
      doGet("/api/customer/remark/"+id,{}).then(resp=>{
        if(resp.data.code === 200){
          this.customerEditRemark = resp.data.data;
        }
      })
    },

    editCustomerRemarkSubmit(){
      this.$refs.editCustomerRemarkRefForm.validate(isValid => {
        if(isValid){
          doPut("/api/customer/remark",{
            id: this.customerEditRemark.id,
            noteContent: this.customerEditRemark.noteContent
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
        doDelete("/api/customer/remark/"+id,{}).then(resp=>{
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

    createTran() {
      this.createTranDialogVisible = true;
    },

    createTranSubmit(){
      this.$refs.createTranRefForm.validate((isValid)=>{
        if(isValid){
          doPost("/api/customer/tran",{
            customerId : this.customerQuery.id,
            money : this.tranQuery.money,
            expectedDate : this.tranQuery.expectedDate,
            stage : this.tranQuery.stage,
            description : this.tranQuery.description,
            nextContactTime : this.tranQuery.nextContactTime
          }).then(resp=>{
            if(resp.data.code === 200){
              messageTip("創建交易成功","success");
              this.reload();
            }else{
              messageTip("創建交易失敗","error");
            }
          })
        }
      })
    },

  }
}
</script>

<template>
  <el-form
      ref="clueRefForm"
      :model="clueQuery"
      label-width="110px"
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

    <el-form-item label="線索狀態">
      <div class="detail">{{clueQuery.stateDO.typeValue}}</div>
    </el-form-item>

    <el-form-item label="線索來源">
      <div class="detail">{{clueQuery.sourceDO.typeValue}}</div>
    </el-form-item>
  </el-form>


  <el-form
      ref="customerRemarkRefForm"
      :model="customerRemark"
      label-width="110px"
      :rules="customerRemarkRules"
      style="max-width: 95%;">

    <el-form-item label="意向產品">
      <div class="detail">{{customerQuery.intentionProductDO.name}}</div>
    </el-form-item>

    <el-form-item label="客戶描述" prop="description">
      <div class="detail">{{customerQuery.description}}</div>
    </el-form-item>

    <el-form-item label="下次聯絡時間">
      <div class="detail">{{customerQuery.nextContactTime}}</div>
    </el-form-item>


    <el-form-item label="填寫跟蹤記錄" prop="noteContent">
      <el-input
          v-model="customerRemark.noteContent"
          :rows="8"
          type="textarea"
          placeholder="請輸入活動備註"
          style="width:50%"
      />
    </el-form-item>


    <el-form-item label="跟蹤方式" prop="noteWay">
      <el-select
          v-model="customerRemark.noteWay"
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
      <el-button type="primary" @click="customerRemarkSubmit">提 交</el-button>
      <el-button type="success" @click="createTran" >創建交易</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
    </el-form-item>


  </el-form>

  <el-table
      :data="customerRemarkList"
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

  <el-dialog v-model="customerRemarkDialogVisible" title="編輯備註" center draggable>
    <el-form ref="editCustomerRemarkRefForm" :model="customerEditRemark" label-width="110px" :rules="editCustomerRemarkRules">
      <el-form-item label="備註" prop="noteContent">
        <el-input
            v-model="customerEditRemark.noteContent"
            :rows="8"
            type="textarea"
            placeholder="請輸入活動備註"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="customerRemarkDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="editCustomerRemarkSubmit">提 交</el-button>
      </span>
    </template>
  </el-dialog>

  <!--創建交易對話框-->
  <el-dialog v-model="createTranDialogVisible" title="創建交易" width="55%" center>
    <el-form ref="createTranRefForm" :model="tranQuery" label-width="110px" :rules="createTranRules">

      <el-form-item label="交易金額" prop="money">
        <el-input v-model="tranQuery.money"/>
      </el-form-item>

      <el-form-item label="預計成交時間" prop="expectedDate">
        <el-date-picker
            v-model="tranQuery.expectedDate"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="請選擇預計成交時間"/>
      </el-form-item>

      <el-form-item label="交易階段" prop="stage">
        <el-select v-model="tranQuery.stage" placeholder="請選擇交易階段" style="width: 100%;" @click="loadDicValue('stage')">
          <el-option
              v-for="item in stageOptions"
              :key="item.id"
              :label="item.typeValue"
              :value="item.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="交易描述" prop="description">
        <el-input
            v-model="tranQuery.description"
            :rows="8"
            type="textarea"
            placeholder="請輸入交易描述"/>
      </el-form-item>

      <el-form-item label="下次跟蹤時間" prop="nextContactTime">
        <el-date-picker
            v-model="tranQuery.nextContactTime"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="請選擇下次跟蹤時間"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="createTranDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="createTranSubmit">提 交</el-button>
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