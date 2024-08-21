<script>
import {doGet, doPost, doPut} from "../http/httpRequest.js";
import {goBack, messageTip} from "../utils/util.js";

export default {
  name: "ClueRecordView",
  data(){
    return{
      clueQuery : {},
      ownerOptions : [{}],
      activityOptions : [{}],
      productOptions : [{}],
      appellationOptions : [{}],
      needLoanOptions : [{}],
      intentionStateOptions : [{}],
      clueStateOptions : [{}],
      sourceOptions : [{}],
      clueRules : {
        phone : [
          {required: true, message:'手機號不能為空',trigger:'blur'},
          {pattern: /\d{8}/, message:'手機號碼格式有誤',trigger:'blur'},
          { validator : this.checkPhone, trigger: 'blur'},
        ],
        name:[
          { min: 2, max: 6, message: '姓名長度應為2-6位', trigger: 'blur' },
        ],
        yearIncome:[
          {pattern: /^[0-9]+(\.[0-9]{2})?$/, message:'必須是數字且只能有兩位小數',trigger:'blur'},
        ],
        description:[
          { min: 5, message: '線索描述至少5個字符', trigger: 'blur'},
        ],
      }
    }
  },

  mounted() {
    this.loadDicValue('appellation');
    this.loadDicValue('needLoan');
    this.loadDicValue('intentionState');
    this.loadDicValue('clueState');
    this.loadDicValue('source');
    this.loadDicValue('activity');
    this.loadDicValue('product');
    this.loadOwner();
    this.loadLoginUser();
    this.loadClue();
  },

  methods:{
    goBack,
    loadDicValue(typeCode) {
      doGet("/api/dicvalue/" + typeCode, {}).then( resp => {
        if (resp.data.code === 200) {
          console.log(resp.data.data);
          if (typeCode === 'appellation') {
            this.appellationOptions = resp.data.data;
          } else if (typeCode === 'needLoan') {
            this.needLoanOptions = resp.data.data;
          } else if (typeCode === 'intentionState') {
            this.intentionStateOptions = resp.data.data;
          } else if (typeCode === 'clueState') {
            this.clueStateOptions = resp.data.data;
          } else if (typeCode === 'source') {
            this.sourceOptions = resp.data.data;
          }else if (typeCode === 'activity') {
            this.activityOptions = resp.data.data;
          }else if (typeCode === 'product') {
            this.productOptions = resp.data.data;
          }
        }
      })
    },


    loadLoginUser(){
      doGet("/api/login/info",{}).then((resp)=>{
        this.clueQuery.ownerId = resp.data.data.id;
      })
    },

    loadOwner(){
      doGet("/api/owner",{}).then(resp=>{
        if(resp.data.code === 200){
          this.ownerOptions = resp.data.data;
        }
      })
    },

    checkPhone(rule, value, callback) {
      let phone = value;
      if (phone) {
        doGet("/api/clue/" + phone, {}).then(resp => {
          if (resp.data.code === 500) {
            return callback(new Error('該手機已登記，不能再登記.'))
          } else {
            return callback();
          }
        })
      }
    },

    addClueSubmit() {
      this.$refs.clueRefForm.validate( (isValid) => {
        if (isValid) {
          let formData = new FormData();
          for (let field in this.clueQuery) {
            if(this.clueQuery[field]){
              formData.append(field, this.clueQuery[field]);
            }
          }
          if (this.clueQuery.id > 0) {
            doPut("/api/clue", formData).then( (resp) => {
              if (resp.data.code === 200) {
                messageTip("編輯成功", "success");
                this.$router.push("/dashboard/clue");
              } else {
                messageTip("編輯失敗", "error");
              }
            });
          } else {
            doPost("/api/clue", formData).then( (resp) => {
              if (resp.data.code === 200) {
                messageTip("添加成功", "success");
                this.$router.push("/dashboard/clue");
              } else {
                messageTip("添加失敗", "error");
              }
            });
          }
        }
      })
    },

    loadClue(){
      let id = this.$route.params.id;
      if(id){
        doGet("/api/clue/detail/"+id).then(resp=>{
          if(resp.data.code === 200){
            console.log(resp);
            this.clueQuery = resp.data.data;
          }
        })
      }
    },
  }

}
</script>

<template>
  <el-form
      ref="clueRefForm"
      :model="clueQuery"
      :rules="clueRules"
      label-width="100px"
      style="max-width: 95%;">

    <el-form-item label="負責人" prop="ownerId">
      <el-select
          v-model="clueQuery.ownerId"
          placeholder="請選擇負責人"
          style="width: 100%"
          clearable
          disabled>
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="所屬活動">
      <el-select
          v-model="clueQuery.activityId"
          placeholder="請選擇所屬活動"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in activityOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="姓名" prop="fullName">
      <el-input v-model="clueQuery.fullName"/>
    </el-form-item>

    <el-form-item label="稱呼">
      <el-select
          v-model="clueQuery.appellation"
          placeholder="請選擇稱呼"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in appellationOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="手機" v-if="clueQuery.id > 0">
      <el-input v-model="clueQuery.phone" disabled/>
    </el-form-item>

    <el-form-item label="手機" prop="phone" v-else>
      <el-input v-model="clueQuery.phone"/>
    </el-form-item>

    <el-form-item label="郵箱" prop="email">
      <el-input v-model="clueQuery.email"/>
    </el-form-item>

    <el-form-item label="年齡" prop="age">
      <el-input v-model="clueQuery.age"/>
    </el-form-item>

    <el-form-item label="職業">
      <el-input v-model="clueQuery.job"/>
    </el-form-item>

    <el-form-item label="年收入" prop="yearIncome">
      <el-input v-model="clueQuery.yearIncome"/>
    </el-form-item>

    <el-form-item label="住址">
      <el-input v-model="clueQuery.address"/>
    </el-form-item>

    <el-form-item label="貸款">
      <el-select
          v-model="clueQuery.needLoan"
          placeholder="請選擇是否貸款"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in needLoanOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向狀態">
      <el-select
          v-model="clueQuery.intentionState"
          placeholder="請選擇意向狀態"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in intentionStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向產品">
      <el-select
          v-model="clueQuery.intentionProduct"
          placeholder="請選擇意向產品"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in productOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="線索狀態">
      <el-select
          v-model="clueQuery.state"
          placeholder="請選擇線索狀態"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in clueStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="線索來源">
      <el-select
          v-model="clueQuery.source"
          placeholder="請選擇線索來源"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in sourceOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="線索描述" prop="description">
      <el-input
          v-model="clueQuery.description"
          :rows="5"
          type="textarea"
          placeholder="請輸入線索描述"/>
    </el-form-item>

    <el-form-item label="下次聯絡時間">
      <el-date-picker
          v-model="clueQuery.nextContactTime"
          type="datetime"
          style="width: 100%;"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="請選擇下次聯絡時間"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="addClueSubmit">提 交</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>