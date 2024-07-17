<script>
import {doDelete, doGet, doPost, doPut} from "../http/httpRequest.js";
import {messageConfirm, messageTip} from "../utils/util.js";

export default {
  name: "UserView",

  inject:['reload'],

  data(){
    return{
      userList:[{}],
      pageSize: 0,
      total:0,

      userDialogVisible: false,
      userQuery:{},

      userRules:{
        loginAct : [
          { required: true, message: '請輸入登入帳號', trigger: 'blur' },
        ],
        loginPwd : [
          { required: true, message: '請輸入密碼', trigger: 'blur' },
          { min: 6, max: 16, message: '密碼長度應為6-16位', trigger: 'blur' },
        ],
        name:[
          {required: true, message:'請輸入姓名',trigger:'blur'},
          { min: 2, max: 6, message: '姓名長度應為2-6位', trigger: 'blur' },
        ],
        phone:[
          {required: true, message:'手機號不能為空',trigger:'blur'},
          {pattern: /\d{8}/, message:'手機號碼格式有誤',trigger:'blur'},
        ],
        email:[
          { required: true, message: '郵箱不能為空', trigger: 'blur' },
          { pattern : /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: '郵箱格式有誤', trigger: 'blur'}
        ],
        accountNoExpired : [
          { required: true, message: '請選擇', trigger: 'blur' },
        ],
        credentialsNoExpired : [
          { required: true, message: '請選擇', trigger: 'blur' },
        ],
        accountNoLocked : [
          { required: true, message: '請選擇', trigger: 'blur' },
        ],
        accountEnabled : [
          { required: true, message: '請選擇', trigger: 'blur' },
        ]
      },

      options:[
        {
          label: '是',
          value:1
        },
        {
          label: '否',
          value: 0
        }
      ],

      userIdArray:[],

    }
  },

  mounted() {
    this.getData(1);
  },

  methods :{
    //勾選或者取消勾選時，觸發該方法
    handleSelectionChange(selectionDataArray){
      this.userIdArray = [];
      selectionDataArray.forEach(data=>{
        let userId = data.id;
        this.userIdArray.push(userId);
      })
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
    },

    add(){
      this.userQuery = {};
      this.userDialogVisible = true;
    },

    edit(id){
      this.loadUser(id);
      this.userDialogVisible=true;
    },

    del(id){
      messageConfirm("你確定要刪除該用戶嗎？").then(()=>{
        doDelete("/api/user/"+id,{}).then(resp=>{
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

    loadUser(id){
      doGet("/api/user/"+id,{}).then((resp)=>{
        if(resp.data.code === 200){
          this.userQuery = resp.data.data;
          this.userQuery.loginPwd="";
        }
      })
    },

    userSubmit(){
      let formData = new FormData();
      for(let field in this.userQuery){
        formData.append(field,this.userQuery[field]);
      }

      this.$refs.userRefForm.validate((isValid)=>{
        if(isValid){
          //如果userquery的id大於0，說明是編輯，否則是新增
          if(this.userQuery.id > 0){
            doPut("/api/user",formData).then((resp)=>{
              if(resp.data.code === 200){
                messageTip("編輯成功","success");
                this.reload();
              } else {
                messageTip("編輯失敗", "error");
              }
            })
          } else {
            doPost("/api/user",formData).then((resp)=>{
              if(resp.data.code === 200){
                messageTip("提交成功","success");
                this.reload();
              } else {
                messageTip("提交失敗", "error");
              }
            })
          }
        }
      })
    },

    batchDel(){
      if(this.userIdArray.length <= 0){
        messageTip("請選擇你要刪除的用戶","warning");
      } else {
        messageConfirm("你確定要刪除該用戶嗎？").then(()=>{
          let ids = this.userIdArray.join(",");
          doDelete("/api/user",{ids:ids}).then(resp=>{
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
  <el-button type="primary" @click="add()">添加用戶</el-button>
  <el-button type="danger" @click="batchDel()">批量刪除</el-button>
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

  <!--如果userQuery的id大於0，說明是編輯用戶，否則為新增用戶-->
  <el-dialog v-model="userDialogVisible" :title="userQuery.id > 0 ? '編輯用戶' : '新增用戶'" width="55%" center draggable>
    <el-form ref="userRefForm" :model="userQuery" label-width="110px" :rules="userRules">
      <el-form-item label="帳號" prop="loginAct">
        <el-input v-model="userQuery.loginAct" />
      </el-form-item>

      <el-form-item label="密碼" v-if="userQuery.id > 0">
        <el-input type="password" v-model="userQuery.loginPwd" />
      </el-form-item>

      <el-form-item label="密碼" prop="loginPwd" v-else>
        <el-input type="password" v-model="userQuery.loginPwd" />
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="userQuery.name" />
      </el-form-item>

      <el-form-item label="電話" prop="phone">
        <el-input  v-model="userQuery.phone" />
      </el-form-item>

      <el-form-item label="郵箱" prop="email">
        <el-input v-model="userQuery.email" />
      </el-form-item>


      <el-form-item label="帳號未過期" prop="accountNoExpired">
        <el-select v-model="userQuery.accountNoExpired" placeholder="請選擇">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>

        </el-select>
      </el-form-item>

      <el-form-item label="密碼未過期" prop="credentialsNoExpired">
        <el-select v-model="userQuery.credentialsNoExpired" placeholder="請選擇">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="帳號未鎖定" prop="accountNoLocked">
        <el-select v-model="userQuery.accountNoLocked" placeholder="請選擇">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="帳號是否啟用" prop="accountEnabled">
        <el-select v-model="userQuery.accountEnabled" placeholder="請選擇">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>

    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="userDialogVisible = false">關 閉</el-button>
        <el-button type="primary" @click="userSubmit()">提 交</el-button>
      </div>
    </template>
  </el-dialog>

</template>

<style scoped>
.el-pagination{
  margin-top: 12px;
}
.el-table{
  margin-top: 12px;
}
.el-select{
  width: 100%;
}
</style>