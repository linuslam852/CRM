<script>
import {doDelete, doGet, doPost, doPut} from "../http/httpRequest.js";
import {goBack, messageConfirm, messageTip} from "../utils/util.js";

export default {
  inject:['reload'],
  name: "ActivityDetailView",
  data(){
    return{
      activityDetail:{
        ownerDO:{},
        createDO:{},
        editDO:{},
      },
      activityRemarkRules:{
        noteContent:[
          {min: 5, max:255, message:'備註內容長度為5-255個字符',trigger:'blur'}
        ]
      },
      editActivityRemarkRules:{
        noteContent:[
          {min: 5, max:255, message:'備註內容長度為5-255個字符',trigger:'blur'}
        ]
      },
      activityRemark:{},
      activityEditRemark:{},
      activityRemarkList:[{
        createDO:{},
        editDO:{},
      }],
      pageSize: 0,
      total:0,
      activityRemarkDialogVisible: false,
    }
  },

  mounted() {
    this.loadActivityDetail();
    this.loadActivityRemarkList(1);
  },

  methods:{
    goBack,

    loadActivityDetail(){
      let id = this.$route.params.id;
      doGet("/api/activity/"+id,{}).then(resp=>{
        if(resp.data.code === 200){
          this.activityDetail = resp.data.data;
          if(!this.activityDetail.ownerDO){
            this.activityDetail.ownerDO = {};
          }
          if(!this.activityDetail.createDO){
            this.activityDetail.createDO = {};
          }
          if(!this.activityDetail.editDO){
            this.activityDetail.editDO = {};
          }
        }
      })
    },

    activityRemarkSubmit(){
      this.$refs.activityRemarkRefForm.validate(isValid => {
        if(isValid){
          doPost("/api/activity/remark",{
            activityId: this.activityDetail.id,
            noteContent: this.activityRemark.noteContent
          }).then(resp=>{
            if(resp.data.code === 200){
              messageTip("提交成功","success");
              this.reload();
            }else{
              messageTip("提交失敗","error");
            }
          })
        }
      })
    },

    loadActivityRemarkList(current){
      doGet("/api/activity/remark",{
        current: current, //當前是第幾頁
        activityId: this.$route.params.id,
      }).then(resp=> {
        if(resp.data.code === 200){
          console.log(resp)
          this.activityRemarkList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    toPage(current){
      this.loadActivityRemarkList(current);
    },

    edit(id){
      this.activityRemarkDialogVisible = true;
      doGet("/api/activity/remark/"+id,{}).then(resp=>{
        if(resp.data.code === 200){
          this.activityEditRemark = resp.data.data;
        }
      })
    },

    editActivityRemarkSubmit(){
      this.$refs.editActivityRemarkRefForm.validate(isValid => {
        if(isValid){
          doPut("/api/activity/remark",{
            id: this.activityEditRemark.id,
            noteContent: this.activityEditRemark.noteContent
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
        doDelete("/api/activity/remark/"+id,{}).then(resp=>{
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
  <el-form ref="activityRemarkRefForm" :model="activityRemark" label-width="120px" :rules="activityRemarkRules">
    <el-form-item label="活動ID">
      <div class="detail">&nbsp;{{activityDetail.id}}</div>
    </el-form-item>

    <el-form-item label="負責人">
      <div class="detail">&nbsp;{{activityDetail.ownerDO.name}}</div>
    </el-form-item>

    <el-form-item label="活動名稱">
      <div class="detail">&nbsp;{{activityDetail.name}}</div>
    </el-form-item>

    <el-form-item label="開始時間">
      <div class="detail">&nbsp;{{activityDetail.startTime}}</div>
    </el-form-item>

    <el-form-item label="結束時間">
      <div class="detail">&nbsp;{{activityDetail.endTime}}</div>
    </el-form-item>

    <el-form-item label="活動預算">
      <div class="detail">&nbsp;{{activityDetail.cost}}</div>
    </el-form-item>

    <el-form-item label="活動描述">
      <div class="detail">&nbsp;{{activityDetail.description}}</div>
    </el-form-item>

    <el-form-item label="創建時間">
      <div class="detail">&nbsp;{{activityDetail.createTime}}</div>
    </el-form-item>

    <el-form-item label="創建人">
      <div class="detail">&nbsp;{{activityDetail.createDO.name}}</div>
    </el-form-item>


    <el-form-item label="編輯時間">
      <div class="detail">&nbsp;{{activityDetail.editTime}}</div>
    </el-form-item>

    <el-form-item label="編輯人">
      <div class="detail">&nbsp;{{activityDetail.editDO.name}}</div>
    </el-form-item>

    <el-form-item label="備註" prop="noteContent">
      <el-input
          v-model="activityRemark.noteContent"
          :rows="8"
          type="textarea"
          placeholder="請輸入活動備註"
          style="width:50%"
      />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="activityRemarkSubmit()">提 交</el-button>
      <el-button type="success" @click="goBack()">返 回</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="activityRemarkList"
      style="width: 100%"
  >
    <el-table-column type="index" label="序號" width="60" />
    <el-table-column property="noteContent" label="備註內容"/>
    <el-table-column property="createTime" label="備註時間" />
    <el-table-column property="createDO.name" label="備註人" />
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

  <el-dialog v-model="activityRemarkDialogVisible" :title="編輯備註" center draggable>
    <el-form ref="editActivityRemarkRefForm" :model="activityEditRemark" label-width="110px" :rules="editActivityRemarkRules">
      <el-form-item label="備註" prop="noteContent">
        <el-input
            v-model="activityEditRemark.noteContent"
            :rows="8"
            type="textarea"
            placeholder="請輸入活動備註"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="activityRemarkDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="editActivityRemarkSubmit">提 交</el-button>
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