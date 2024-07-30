<script>
import {doGet, doPost, doPut} from "../http/httpRequest.js";
import {goBack, messageTip} from "../utils/util.js";

export default {
  name: "ActivityRecordView",

  data(){
    return{
      activityQuery:{},
      activityRules:{
        ownerId:[
          {required: true, message: '請選擇負責人', trigger:'blur'}
        ],
        name:[
          {required: true, message: '請選擇活動名稱', trigger:'blur'}
        ],
        startTime:[
          {required: true, message: '請選擇開始時間', trigger:'blur'}
        ],
        endTime:[
          {required: true, message: '請選擇結束時間', trigger:'blur'}
        ],
        cost:[
          {required: true, message: '請選擇預算', trigger:'blur'},
          {pattern: /^[0-9]+(\.[0-9]{2})?$/, message:'必須是數字且只能有兩位小數',trigger:'blur'},
        ]
        ,
        description:[
          {required: true, message: '請選擇活動描述', trigger:'blur'},
          {min:5, max:255,message: '活動描述字數長度為5-255位',trigger: 'blur'}
        ],
      },
      ownerOptions:[{}],
    }
  },

  mounted() {
    this.loadOwner();
    this.loadActivity();
  },

  methods:{
    goBack,
    loadOwner(){
      doGet("/api/owner",{}).then(resp=>{
        if(resp.data.code === 200){
          this.ownerOptions = resp.data.data;
        }
      })
    },


    activitySubmit(){
      let formData = new FormData();
      for(let field in this.activityQuery){
        if(this.activityQuery[field]){
          formData.append(field,this.activityQuery[field]);
        }

      }

      this.$refs.activityRefForm.validate((isValid)=>{
        if(isValid){
          if(this.activityQuery.id > 0){
            doPut("/api/activity",formData).then((resp)=>{
              if(resp.data.code === 200){
                messageTip("編輯成功","success");
                this.$router.push("/dashboard/activity");
              } else {
                messageTip("編輯失敗", "error");
              }
            })
          }else{
            doPost("/api/activity",formData).then((resp)=>{
              if(resp.data.code === 200){
                messageTip("提交成功","success");
                this.$router.push("/dashboard/activity");
              } else {
                messageTip("提交失敗", "error");
              }
            })
          }
        }
      })
    },

    loadActivity(){
      let id = this.$route.params.id;
      if(id){
        doGet("/api/activity/"+id).then(resp=>{
          if(resp.data.code === 200){
            this.activityQuery = resp.data.data;
          }
        })
      }
    },

  }
}
</script>

<template>
  <el-form ref="activityRefForm" :model="activityQuery" label-width="110px" :rules="activityRules">
    <el-form-item label="負責人" prop="ownerId">
      <el-select
          v-model="activityQuery.ownerId"
          placeholder="請選擇負責人"
          @click="loadOwner"
          clearable>
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="活動名稱" prop="name">
      <el-input v-model="activityQuery.name" />
    </el-form-item>

    <el-form-item label="開始時間" prop="startTime">
      <el-date-picker
          v-model="activityQuery.startTime"
          type="datetime"
          placeholder="請選擇開始時間"
          value-format="YYYY-MM-DD HH:mm:ss"
          style="width: 50%"
      />
    </el-form-item>

    <el-form-item label="結束時間" prop="endTime">
      <el-date-picker
          v-model="activityQuery.endTime"
          type="datetime"
          placeholder="請選擇結束時間"
          value-format="YYYY-MM-DD HH:mm:ss"
          style="width: 50%"
      />
    </el-form-item>


    <el-form-item label="活動預算" prop="cost">
      <el-input v-model="activityQuery.cost" />
    </el-form-item>

    <el-form-item label="活動描述" prop="description">
      <el-input
        v-model="activityQuery.description"
        :rows="6"
        type="textarea"
        placeholder="請輸入活動描述"
        style="width:50%"
      />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="activitySubmit()">提 交</el-button>
      <el-button type="success" @click="goBack()">返 回</el-button>
    </el-form-item>

  </el-form>
</template>

<style scoped>
.el-select {
  --el-select-width: 150px;
}
.el-input{
  width: 50%;
}
</style>