<template>
  <section class="receipt-form-section">
    <div>
      <br/>
      <p class = "receipt-text" >
          此页面为保存了用户的存单信息，可以根据用户名字、身份证号、存款到期日范围进行查询
      </p>
      <br/>
    </div>
    <el-form ref="formRef" :inline="true" :model="formInline" class="demo-form-inline">
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
        <el-button type="primary" round :icon="Search" @click="onSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" color="#626aef" round :icon="Refresh" @click="reset">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" color="green" round :icon="Edit" @click="handleCreateReceipt">
          创建存单
        </el-button>
        <el-dialog v-model="dialogFormVisible" title="创建存单" class="create-receipt-form-dialog">
          <el-form :model="createReceipt" ref="ruleFormRef" :rules="rules">
            <el-form-item label="姓名" prop="name"
                          :inline-message = "true"
                          :rules="[{ required: true, message: '姓名不能为空', trigger: ['blur'] },]"
            >
              <el-input v-model="createReceipt.name" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="身份证号码" prop="idCard"
                          :inline-message = "true"
                          :rules="[
                              // {
                              //   required: true,
                              //   pattern: /^[1-9]\d{5}(19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                              //   message: '身份证号码格式不正确',
                              //   trigger: 'blur',
                              // },
                              { min: 18, max: 18, message: '身份证号码长度不正确', trigger: ['blur'] },
                              ]"
            >
              <el-input v-model="createReceipt.idCard" autocomplete="off" maxlength="18"/>
            </el-form-item>
            <el-form-item label="金额(CNY)" prop="amount"
                          :inline-message = "true"
                          :rules="[
                              {required: true, type: 'number', message: '金额为数字，且不为空', trigger: ['blur']},
                          ]"
            >
              <el-input v-model.number="createReceipt.amount" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="存期(月)" prop="term"
                          :inline-message = "true"
                          :rules="[
                              { required: true, type: 'number', message: '期限为数字且不能为空', trigger: ['blur'] },
                          ]"
            >
              <el-input v-model.number="createReceipt.term" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="存期开始时间" prop="startTime"
                          :inline-message = "true"
                          :rules="[
                              { required: true, message: '请选择存款日期', trigger: ['blur']},
                          ]"
            >
              <el-date-picker
                  v-model="createReceipt.startTime"
                  type="date"
                  placeholder="存期开始时间"
                  clearable
                  format="YYYY-MM-DD"
                  value-format="YYYY/MM/DD 00:00:00"
              />
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取消</el-button>
              <el-button type="primary" @click="submitCreateReceipt">提交</el-button>
            </span>
          </template>
        </el-dialog>
      </el-form-item>
    </el-form>

    <el-table :data="tableData" style="width: 100%; height: 600px;">
      <el-table-column fixed prop="name" label="姓名" width="150"/>
      <el-table-column prop="code" label="单号" width="150"/>
      <el-table-column prop="idCard" label="身份证号码" width="240"/>
      <el-table-column prop="amount" label="存款金额(CNY)" width="150"/>
      <el-table-column prop="startTime" label="存款开始时间" width="200"/>
      <el-table-column prop="endTime" label="存款到期时间" width="200"/>
      <el-table-column prop="term" label="存期(月)" width="100"/>
      <el-table-column prop="id" label="id" width="100" v-if="showAgeColumn = false"/>
      <el-table-column fixed="right" label="操作" width="180">
        <template #default="scope">
          <el-button link type="primary" @click="handleUpdateReceipt(scope.row, scope.$index)">更新</el-button>
<!--          <el-button link type="primary" @click="handleQueryReceipt(scope.$index)">查看详情</el-button>-->
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0" class="page-right">
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
import type {FormInstance} from "element-plus";
import {ElMessage} from 'element-plus'
import request from "@/utils/request";
import {formatDate} from "@/utils";

const ruleFormRef = ref<FormInstance>()

const formInline = reactive({
  name: '',
  idCard: '',
  endTimeFrom: '',
  endTimeTo: '',
})

interface CreateReceipt {
  id: undefined,
  name: undefined,
  startTime: undefined,
  term: undefined,
  amount: undefined,
  idCard: undefined,
}

let createReceipt = reactive<CreateReceipt>(
    {
      id: undefined,
      name: undefined,
      startTime: undefined,
      term: undefined,
      amount: undefined,
      idCard: undefined,
    }
);

const totalNumber = ref(3)
const pageNumber = ref(1)
const pageSize = ref(10)
const tableData = ref([]);
const dialogFormVisible = ref(false)
const showAgeColumn = ref(false)
const formRef = ref<FormInstance>()

const getReceiptList = (value, pageNumber: number, pageSize: number) => {
  request.post('/receipt/list', {
    "name": value.name,
    "idCard": value.idCard,
    "endTimeFrom": value.endTimeFrom,
    "endTimeTo": value.endTimeTo,
    "pageNumber": pageNumber,
    "pageSize": pageSize
  }).then(res => {
    const list =  res.data.list
    tableData.value = list.map((item) => ({
      ...item,
      endTime: formatDate(item.endTime),
      startTime: formatDate(item.startTime),
    }));
    totalNumber.value = res.data.total;
  })
}

// 一进到receiptForm页面就调用该函数，查询存单列表数据
getReceiptList({
  "name": '',
  "idCard": '',
  "endTimeFrom": '',
  "endTimeTo": '',
}, pageNumber.value, pageSize.value);

// 点击查询时调用该函数，查询满足条件的列表数据
const onSubmit = () => {
  console.log('submit!', formInline)
  getReceiptList(formInline, pageNumber.value, pageSize.value);
}

// 重置查询框数据，并展示列表数据全集
const reset = () => {
  formInline.name = ''
  formInline.idCard = ''
  formInline.endTimeFrom = ''
  formInline.endTimeTo = ''
  getReceiptList(formInline, pageNumber.value, pageSize.value);
}

// 创建存单数据
const handleCreateReceipt = () => {
  // 将弹窗历史数据置空
  createReceipt = reactive<CreateReceipt>({
    id: undefined,
    name: undefined,
    startTime: undefined,
    term: undefined,
    amount: undefined,
    idCard: undefined,
  })
  // 打开弹窗
  dialogFormVisible.value = true

}

// 保存并提交存单数据
const submitCreateReceipt = () => {
// // TODO 需要发请求将数据提交到db 提交时需要判断globalIndex是否大于0，是则走更新接口，否则走创建接口
  if (createReceipt && createReceipt.id !== undefined) {
    console.log("修改数据 ~~~", createReceipt)
    // 更新操作，走更新接口
    request.post("/receipt/update", createReceipt)
        .then(res => {
          console.log("更新成功", res.data)
          dialogFormVisible.value = false
          getReceiptList(formInline, pageNumber.value, pageSize.value);
          ElMessage.success("更新成功")
        })
  } else {
    // 创建操作，走创建接口
    console.log("新增数据 !!!", createReceipt)
    request.post("/receipt/create", createReceipt)
        .then(res => {
          dialogFormVisible.value = false
          getReceiptList(formInline, pageNumber.value, pageSize.value);
          ElMessage.success('创建成功.')
        })
  }
}


// 更新存单数据，在点击时需要将当前行的数据填充到表单中
const handleUpdateReceipt = (row, index) => {
  console.log("=====", row, index);
  createReceipt.amount = row.amount;
  createReceipt.term = row.term;
  createReceipt.id = row.id;
  createReceipt.idCard = row.idCard;
  createReceipt.name = row.name;
  createReceipt.startTime = row.startTime;
  dialogFormVisible.value = true
}

</script>

<style lang="scss">

.receipt-text {
  color: #5151e8;
  font-size: 25px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  //padding: 10px 0;
}

.receipt-form-section {
  width: 100%;
  height: 100%;
  min-height: 400px;
  display: flex;
  flex-direction: column;
}

.create-receipt-form-dialog {
  width: 35%;
  min-width: 500px;

  .el-form-item__label {
    color: black;
    width: 200px;
  }
}

.el-form-item__label {
  display: inline-flex;
  justify-content: space-between;
  align-items: flex-start;
  flex: 0 0 auto;
  font-size: 25px;
  color: rgba(14, 92, 255, 1);
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
  padding: 10px 10px;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
}

.demo-pagination-block + .demo-pagination-block {
  margin-top: 10px;
}

.demo-pagination-block .demonstration {
  margin-bottom: 16px;
}

.page-right {
  display: flex;
  justify-content: right;
  align-items: normal;
  padding: 10px 0;
}
</style>