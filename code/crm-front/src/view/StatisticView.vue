<script>
import {doGet} from "../http/httpRequest.js";
import * as echarts from 'echarts';

export default {
  name: "StatisticView",
  data(){
    return{
      summaryData:{},
    }
  },

  mounted() {
    this.loadSummary();
    this.loadSaleFunnelChart();
    this.loadSourcePieChart();
  },

  methods:{
    loadSummary(){
      doGet("/api/summary/data",{}).then(resp=>{
        if(resp.data.code === 200){
          console.log(resp)
          this.summaryData = resp.data.data;
        }
      })
    },

    loadSaleFunnelChart(){
      doGet("/api/saleFunnel/data",{}).then(resp=>{
        if(resp.data.code === 200){
          let saleFunelData = resp.data.data;
          let chartDom = document.getElementById('saleFunnelChart');
          let myChart = echarts.init(chartDom);
          let option;

          option = {
            title: {
              text: '銷售漏斗圖',
              left: 'center',
              top: 'bottom',
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c}個'
            },
            toolbox: {
              feature: {
                dataView: { readOnly: false },
                restore: {},
                saveAsImage: {}
              }
            },
            legend: {
              data: ['線索', '客戶', '交易', '成交']
            },
            series: [
              {
                name: '銷售數據統計',
                type: 'funnel',
                left: '10%',
                top: 60,
                bottom: 60,
                width: '80%',
                min: 0,
                max: 100,
                minSize: '0%',
                maxSize: '100%',
                sort: 'descending',
                gap: 2,
                label: {
                  show: true,
                  position: 'inside'
                },
                labelLine: {
                  length: 10,
                  lineStyle: {
                    width: 1,
                    type: 'solid'
                  }
                },
                itemStyle: {
                  borderColor: '#fff',
                  borderWidth: 1
                },
                emphasis: {
                  label: {
                    fontSize: 20
                  }
                },
                data: saleFunelData,
              }
            ]
          };

          option && myChart.setOption(option);
        }
      })



    },

    loadSourcePieChart(){
      doGet("/api/sourcePie/data",{}).then(resp=>{
        if(resp.data.code === 200){
          let sourcePieData = resp.data.data;
          var chartDom = document.getElementById('sourcePieChart');
          var myChart = echarts.init(chartDom);
          var option;

          option = {
            title: {
              text: '線索來源統計',
              left: 'center',
              top:'bottom',
            },
            tooltip: {
              trigger: 'item'
            },
            legend: {
              orient: 'horizontal',
              left: 'center'
            },
            series: [
              {
                name: '線索來源統計',
                type: 'pie',
                radius: '60%',
                data: sourcePieData,
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };

          option && myChart.setOption(option);
        }
      })
    },
  },


}
</script>

<template>
  <el-row>
    <el-col :span="6">
      <el-statistic :value="summaryData.effectiveActivityCount">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            市場活動
          </div>
        </template>
        <template #suffix>/{{ summaryData.totalActivityCount }}</template>
      </el-statistic>
    </el-col>

    <el-col :span="6">
      <el-statistic title="線索總數" :value="summaryData.totalClueCount" />
    </el-col>

    <el-col :span="6">
      <el-statistic title="客戶總數" :value="summaryData.totalCustomerCount" />
    </el-col>

    <el-col :span="6">
      <el-statistic :value="summaryData.successTranAmount">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            交易總額
          </div>
        </template>
        <template #suffix>/{{ summaryData.totalTranAmount }}</template>
      </el-statistic>
    </el-col>

  </el-row>
  <div id="saleFunnelChart" style="width: 48%; height: 350px; margin-top: 30px; float: left;"></div>

  <div id="sourcePieChart" style="width: 48%; height: 350px; margin-top: 30px; float: left;"></div>
</template>

<style scoped>
.el-row{
  text-align: center;
}

</style>