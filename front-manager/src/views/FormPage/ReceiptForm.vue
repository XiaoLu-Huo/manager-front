<template>
  <section>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="formInline.name" placeholder="姓名" clearable/>
      </el-form-item>
      <el-form-item label="身份证号">
        <el-input v-model="formInline.idCard" placeholder="身份证号" clearable/>
      </el-form-item>
      <el-form-item label="存款到期日开始时间">
        <el-date-picker
            v-model="formInline.endTimeFrom"
            type="date"
            placeholder="存款到期日开始时间"
            clearable
            format="YYYY-MM-DD"
            value-format="YYYY/MM/DD 00:00:00"
        />
      </el-form-item>
      <el-form-item label="存款到期日结束时间">
        <el-date-picker
            v-model="formInline.endTimeTo"
            type="date"
            placeholder="存款到期日结束时间"
            clearable
            format="YYYY-MM-DD"
            value-format="YYYY/MM/DD 23:59:59"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">Query</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="tableData" style="width: 1400px; height: 600px;">
      <el-table-column fixed prop="name" label="姓名" width="180"/>
      <el-table-column prop="code" label="单号" width="180"/>
      <el-table-column prop="idCard" label="身份证号码" width="300"/>
      <el-table-column prop="amount" label="存款金额(CNY)" width="200"/>
      <el-table-column prop="startTime" label="存款开始时间" width="200"/>
      <el-table-column prop="endTime" label="存款到期时间" width="200"/>
      <el-table-column prop="term" label="存期(月)" width="150"/>
    </el-table>

    <div style = "margin: 10px 0">
      <el-pagination
          background
          v-model:current-page="pageNumber"
          v-model:page-size="pageSize"
          :page-sizes="[2, 5, 10, 20]"
          layout="total, prev, pager, next, sizes"
          :total="totalNumber"
          @size-change="onSubmit"
          @current-change="onSubmit"
      />
    </div>

  </section>
</template>
<script lang="ts" setup>
import {reactive, ref} from 'vue'
import axios from "axios";
import Pagination from '@/components/Pagination'
import {isNumber} from "element-plus/es/utils";

const formInline = reactive({
  name: '',
  idCard: '',
  endTimeFrom: '',
  endTimeTo: '',
})

const totalNumber = ref(3)
const pageNumber = ref(1)
const pageSize = ref(5)

const tableData =ref([]);

const getReceiptList = (value, pageNumber: number, pageSize: number) => {
  axios.post('http://localhost:8080/receipt/list', {
    "name": value.name,
    "idCard": value.idCard,
    "endTimeFrom": value.startTime,
    "endTimeTo": value.endTime,
    "pageNumber": pageNumber,
    "pageSize": pageSize
  }).then(res => {
    console.log('res', res.data);
    tableData.value = res.data.list;
    totalNumber.value = res.data.total;
  })
}
getReceiptList({
  "name": '',
  "idCard": '',
  "endTimeFrom": '',
  "endTimeTo": '',
},pageNumber.value, pageSize.value);

const onSubmit = () => {
  console.log('submit!', formInline)
  getReceiptList(formInline, pageNumber.value, pageSize.value);
}

</script>

<style lang="scss">
  .el-form-item__label {
    display: inline-flex;
    justify-content: space-between;
    align-items: flex-start;
    flex: 0 0 auto;
    font-size: 25px;
    color: rgba(14,92,255,1);
    height: 45px;
    width: 250px;
    padding: 20px 10px 8px 0;
    box-sizing: border-box;
    flex-direction: column-reverse;
    flex-wrap: wrap;
    align-content: flex-end;
  }

  .cell {
    box-sizing: border-box;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: normal;
    word-break: break-all;
    line-height: 23px;
    height: 35px;
    font-family: "Times New Roman", cursive;
    font-weight: normal;
    font-size: 18px;
    padding: 10px 10px;
    text-align: center;
  }

  .demo-pagination-block + .demo-pagination-block {
    margin-top: 10px;
  }
  .demo-pagination-block .demonstration {
    margin-bottom: 16px;
  }
</style>