<script>
import {goBack, messageConfirm, messageTip} from "../utils/util.js";
import {doDelete, doGet, doPost, doPut} from "../http/httpRequest.js";

export default {
  name: "TranDetailView",
  inject : ['reload'],

  data() {
    return {

      tranDetail : {
        createByDO : {},
        editByDO : {},
        stageDO:{},
      },

      tranRemark : {},

      noteWayOptions : [{}],

      tranRemarkRules : {
        noteContent : [
          { required: true, message: '跟蹤內容不能為空', trigger: 'blur' },
          { min: 5, max: 255, message: '跟蹤內容長度度為5-255個字符', trigger: 'blur' }
        ],
        noteWay : [
          { required: true, message: '請選擇跟蹤方式', trigger: ['blur', 'change'] }
        ]
      },

      editTranRemarkRules:{
        noteContent:[
          {min: 5, max:255, message:'備註內容長度為5-255個字符',trigger:'blur'}
        ]
      },

      tranRemarkDialogVisible : false,
      tranHistoryDialogVisible :false,

      tranHistoryQuery : {},
      tranEditRemark:{},
      stageOptions : [{}],

      tranHistoryRules : {
        stage : [
          { required: true, message: '請選擇交易階段', trigger: ['blur', 'change'] }
        ],
        money : [
          { required: true, message: '請輸入交易金額', trigger: 'blur' },
          { pattern : /^[0-9]+(\.[0-9]{2})?$/, message: '交易金額必須是整數或者只有兩位小數', trigger: 'blur'}
        ],
        expectedDate : [
          { required: true, message: '請選擇預計成交時間', trigger: ['blur', 'change'] }
        ]
      },

      tranRemarkList : [{
        noteWayDO : {},
        createByDO : {},
        editByDO : {}
      }],
      remarkPageSize : 0,
      remarkTotal : 0,
      historyPageSize : 0,
      historyTotal : 0,

      tranHistoryList : [{
        stageDO : {},
        createByDO : {}
      }]
    }
  },

  mounted() {
    this.loadTranDetail();
    this.loadDicValue('noteWay');
    this.loadTranRemarkList(1);
    this.loadTranHistoryList(1);
  },

  methods : {
    goBack,

    loadTranDetail() {
      let tranId = this.$route.params.id;
      doGet("/api/tran/detail/" + tranId, {}).then(resp => {
        if (resp.data.code === 200) {
          this.tranDetail = resp.data.data;
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


    tranRemarkSubmit() {
      this.$refs.tranRefForm.validate((isValid) => {
        if (isValid) {
          doPost("/api/tran/remark", {
            tranId : this.tranDetail.id,
            noteWay : this.tranRemark.noteWay,
            noteContent : this.tranRemark.noteContent
          }).then(resp => {
            if (resp.data.code === 200) {
              messageTip("提交成功", "success");
              this.reload();
            } else {
              messageTip("提交失敗", "error");
            }
          })
        }
      })
    },


    updateTranStage() {
      this.tranHistoryQuery.money = this.tranDetail.money;
      this.tranHistoryDialogVisible = true;
    },


    tranHistorySubmit() {
      this.$refs.tranHistoryRefForm.validate((isValid) => {
        if (isValid) {
          doPost("/api/tran/history", {
            tranId : this.tranDetail.id,
            stage : this.tranHistoryQuery.stage,
            money : this.tranHistoryQuery.money,
            expectedDate : this.tranHistoryQuery.expectedDate
          }).then(resp => {
            if (resp.data.code === 200) {
              messageTip("更新交易階段成功", "success");
              this.reload();
            } else {
              messageTip("更新交易階段失敗，原因：" + resp.data.msg, "error");
            }
          })
        }
      })
    },


    loadTranRemarkList(current) {
      doGet("/api/tran/remark", {
        current : current,
        tranId: this.$route.params.id,
      }).then(resp => {
        if (resp.data.code === 200) {
          this.tranRemarkList = resp.data.data.list;
          this.remarkPageSize = resp.data.data.pageSize;
          this.remarkTotal = resp.data.data.total;
        }
      })
    },


    remarkPage(number) {
      this.loadTranRemarkList(number);
    },

    historyPage(number) {
      this.loadTranHistoryList(number);
    },


    loadTranHistoryList(current) {
      doGet("/api/tran/history", {
        current : current,
        tranId: this.$route.params.id,
      }).then(resp => {
        if (resp.data.code === 200) {
          this.tranHistoryList = resp.data.data.list;
          this.historyPageSize = resp.data.data.pageSize;
          this.historyTotal = resp.data.data.total;
        }
      })
    },


    edit(id){
      this.tranRemarkDialogVisible = true;
      doGet("/api/tran/remark/"+id,{}).then(resp=>{
        if(resp.data.code === 200){
          this.tranEditRemark = resp.data.data;
        }
      })
    },

    editTranRemarkSubmit(){
      this.$refs.editTranRemarkRefForm.validate(isValid => {
        if(isValid){
          doPut("/api/tran/remark",{
            id: this.tranEditRemark.id,
            noteContent: this.tranEditRemark.noteContent
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
        doDelete("/api/tran/remark/"+id,{}).then(resp=>{
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

  }
}
</script>

<template>
  <el-form ref="tranRefForm" :model="tranRemark" label-width="120px" :rules="tranRemarkRules">
    <el-form-item label="ID：">
      <div class="detail">{{ tranDetail.id }}</div>
    </el-form-item>

    <el-form-item label="交易流水號：">
      <div class="detail">{{ tranDetail.tranNo }}</div>
    </el-form-item>

    <el-form-item label="交易金額">
      <div class="detail">${{ tranDetail.money }}</div>
    </el-form-item>

    <el-form-item label="預計成交時間">
      <div class="detail">{{ tranDetail.expectedDate }}</div>
    </el-form-item>

    <el-form-item label="交易阶段" >
      <div class="detail">{{ tranDetail.stageDO.typeValue }}</div>
    </el-form-item>

    <el-form-item label="交易描述">
      <div class="detail">{{ tranDetail.description }}</div>
    </el-form-item>

    <el-form-item label="下次跟蹤時間">
      <div class="detail">{{ tranDetail.nextContactTime }}</div>
    </el-form-item>

    <el-form-item label="創建時間">
      <div class="detail">{{ tranDetail.nextContactTime }}</div>
    </el-form-item>

    <el-form-item label="創建人">
      <div class="detail">{{ tranDetail.createByDO.name }}</div>
    </el-form-item>

    <el-form-item label="編輯時間">
      <div class="detail">{{ tranDetail.editTime }}</div>
    </el-form-item>

    <el-form-item label="編輯人">
      <div class="detail">{{ tranDetail.editByDO.name }}</div>
    </el-form-item>

    <el-form-item>
      <el-table
          :data="tranRemarkList"
          style="width: 100%">
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
      <p>
        <el-pagination
            background
            layout="prev, pager, next"
            :page-size="remarkPageSize"
            :total="remarkTotal"
            @prev-click="remarkPage"
            @next-click="remarkPage"
            @current-change="remarkPage"/>
      </p>
    </el-form-item>

    <el-form-item label="填寫跟蹤記錄" prop="noteContent">
      <el-input
          v-model="tranRemark.noteContent"
          :rows="8"
          type="textarea"/>
    </el-form-item>
    <el-form-item label="跟蹤方式" prop="noteWay">
      <el-select
          v-model="tranRemark.noteWay"
          placeholder="請選擇跟蹤方式"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in noteWayOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="tranRemarkSubmit">提 交</el-button>
      <el-button type="success" @click="updateTranStage">更新交易階段</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
    </el-form-item>

    <el-form-item>
      <el-table
          :data="tranHistoryList"
          style="width: 100%">
        <el-table-column type="index" label="序號" width="100"/>
        <el-table-column property="stageDO.typeValue" label="交易階段"/>
        <el-table-column property="money" label="交易金額"/>
        <el-table-column property="expectedDate" label="預計成交時間"/>
        <el-table-column property="createTime" label="創建時間"/>
        <el-table-column property="createByDO.name" label="創建人"/>
      </el-table>
      <p>
        <el-pagination
            background
            layout="prev, pager, next"
            :page-size="historyPageSize"
            :total="historyTotal"
            @prev-click="historyPage"
            @next-click="historyPage"
            @current-change="historyPage"/>
      </p>
    </el-form-item>
  </el-form>


  <el-dialog v-model="tranHistoryDialogVisible" title="更新交易階段" width="55%" center>
    <el-form ref="tranHistoryRefForm" :model="tranHistoryQuery" label-width="110px" :rules="tranHistoryRules">
      <el-form-item label="交易階段" prop="stage">
        <el-select v-model="tranHistoryQuery.stage" placeholder="請選擇交易階段" style="width: 100%;" @click="loadDicValue('stage')">
          <el-option
              v-for="item in stageOptions"
              :key="item.id"
              :label="item.typeValue"
              :value="item.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="交易金額" prop="money">
        <el-input v-model="tranHistoryQuery.money"/>
      </el-form-item>

      <el-form-item label="預計成交時間" prop="expectedDate">
        <el-date-picker
            v-model="tranHistoryQuery.expectedDate"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="請選擇預計成交時間"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="tranHistoryDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="tranHistorySubmit">提 交</el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog v-model="tranRemarkDialogVisible" title="編輯備註" center draggable>
    <el-form ref="editTranRemarkRefForm" :model="tranEditRemark" label-width="110px" :rules="editTranRemarkRules">
      <el-form-item label="備註" prop="noteContent">
        <el-input
            v-model="tranEditRemark.noteContent"
            :rows="8"
            type="textarea"
            placeholder="請輸入活動備註"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="tranRemarkDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="editTranRemarkSubmit">提 交</el-button>
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