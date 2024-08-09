<script>
import {doDelete, doGet, doPost} from "../http/httpRequest.js";
import {messageConfirm, messageTip} from "../utils/util.js";

export default {
  name: "ClueView",

  inject:['reload'],
  data(){
    return{
      clueList:[{
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
      importExcelDialogVisible:false,
      clueIdArray:[],

    }
  },

  mounted() {
    this.getData(1);
  },

  methods:{
    handleSelectionChange(selectionDataArray){
      this.clueIdArray = [];
      selectionDataArray.forEach(data=>{
        let clueId = data.id;
        this.clueIdArray.push(clueId);
      })
    },

    toPage(current){
      this.getData(current);
    },

    getData(current){
      doGet("/api/clues",{
        current: current //當前是第幾頁
      }).then(resp=> {
        if(resp.data.code === 200){
          console.log(resp)
          this.clueList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    importExcel(){
      this.importExcelDialogVisible = true;
    },

    submitImportExcel(){
      this.$refs.uploadRef.submit();
    },

    uploadFile(param){
      let fileObj = param.file;
      let formData = new FormData();
      formData.append('file',fileObj);
      doPost("/api/importExcel",formData).then(resp=>{
        if(resp.data.code === 200){
          messageTip("導入成功","success");
          this.$refs.uploadRef.clearFiles();
          this.reload();
        }else {
          messageTip("導入失敗","error");
        }
      })
    },


    addClue(){
      this.$router.push("/dashboard/clue/add");
    },

    edit(id){
      this.$router.push("/dashboard/clue/edit/"+id);
    },

    view(id){
      this.$router.push("/dashboard/clue/detail/"+id)
    },

    del(id){
      messageConfirm("你確定要刪除該線索嗎？").then(()=>{
        doDelete("/api/clue/"+id,{}).then(resp=>{
          if(resp.data.code === 200){
            messageTip("刪除成功","success");
            this.reload();
          } else {
            messageTip("刪除失敗，原因： "+ resp.data.msg,"warning");
          }
        })
      }).catch(()=>{
        messageTip("已取消","warning");
      })
    },

    batchDelClue(){
      if(this.clueIdArray.length <= 0){
        messageTip("請選擇你要刪除的線索","warning");
      } else {
        messageConfirm("你確定要刪除該線索嗎？").then(()=>{
          let ids = this.clueIdArray.join(",");
          doDelete("/api/clue",{ids:ids}).then(resp=>{
            if(resp.data.code === 200){
              messageTip("批量刪除成功","success");
              this.reload();
            } else {
              messageTip("批量刪除失敗，原因： " + resp.data.msg, "error");
            }
          })
        }).catch(()=>{
          messageTip("已取消","warning");
        })
      }
    },

  }
}
</script>

<template>
  <el-button type="primary" class="btn" @click="addClue()" v-hasPermission="'clue:add'">添加線索</el-button>
  <el-button type="success" class="btn" @click="importExcel()" v-hasPermission="'clue:import'">導入線索(Excel)</el-button>
  <el-button type="danger" class="btn" @click="batchDelClue()" v-hasPermission="'clue:delete'">批量刪除</el-button>

  <el-table
      :data="clueList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="40"/>
    <el-table-column type="index" label="序號" width="55"/>
    <el-table-column property="ownerDO.name" label="負責人" width="90" />
    <el-table-column property="activityDO.name" label="所屬活動" width="100"/>
    <el-table-column label="姓名">
      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.fullName }}</a>
      </template>
    </el-table-column>
    <el-table-column property="appellationDO.typeValue" label="稱呼"/>
    <el-table-column property="phone" label="手機" width="120"/>
    <el-table-column property="needLoanDO.typeValue" label="是否貸款"/>
    <el-table-column property="intentionStateDO.typeValue" label="意向狀態"/>
    <el-table-column property="intentionProductDO.name" label="意向產品"/>

    <el-table-column label="線索狀態">
      <template #default="scope">
        <span style="background: chartreuse" v-if="scope.row.state === -1">{{scope.row.stateDO.typeValue}}</span>
        <span v-else>{{scope.row.stateDO.typeValue}}</span>
      </template>
    </el-table-column>

    <el-table-column property="sourceDO.typeValue" label="線索來源"/>
    <el-table-column property="nextContactTime" label="下次聯絡時間" width="165"/>
    <el-table-column label="操作" width="230">
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)" v-hasPermission="'clue:view'">詳情</el-button>
        <el-button type="success" @click="edit(scope.row.id)" v-hasPermission="'clue:edit'">編輯</el-button>
        <el-button type="danger" @click="del(scope.row.id)" v-hasPermission="'clue:delete'">刪除</el-button>
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


  <el-dialog v-model="importExcelDialogVisible" title="導入線索Excel" width="500" center>
        <el-upload
            ref="uploadRef"
            :http-request="uploadFile"
            :auto-upload="false"
        >
          <template #trigger>
            <el-button type="primary">選擇Excel文件</el-button>
          </template>
          僅支持.xls或.xlsx的文件
          <template #tip>
            <div class="el-upload__tip">
              重要提示：
              <ul>
                <li>上傳文件僅支持.xls或.xlsx的文件；</li>
                <li>Excel文件第一行將視為字段名；</li>
                <li>請確認您的文件大小不超過50MB；</li>
                <li>日期值以文本形式保存，必須為yyyy-MM-dd格式；</li>
                <li>時間以文本形式保存，格式必須為yyyy-MM-dd HH:mm:ss的格式；</li>
              </ul>
            </div>
          </template>

        </el-upload>


    <template #footer>
      <span class="dialog-footer">
        <el-button @click="importExcelDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="submitImportExcel()">導 入</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<style scoped>
.el-table{
  margin-top: 15px;
}
.el-pagination{
  margin-top: 15px;
}
</style>