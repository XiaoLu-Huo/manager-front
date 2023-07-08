<template>
  <section class="receipt-form-section">
    <div>
      <br/>
      <p class = "user-text" >
        此页面为保存了用户的基本信息，包括用户的电话和身份证信息，可以根据用户名字进行模糊查询
      </p>
      <br/>
    </div>
    <el-form ref="formRef" :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="formInline.name" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" round :icon="Search" @click="onSubmit">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" color="#626aef" round :icon="Refresh" @click="reset">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="success" color="green" round :icon="Edit" @click="handleCreateUser">
          新增用户
        </el-button>
        <el-dialog v-model="dialogFormVisible" title="创建用户" class="create-receipt-form-dialog">
          <el-form :model="createUser" ref="ruleFormRef" :rules="rules">
            <el-form-item label="姓名" prop="name"
                          :inline-message = "true"
                          :rules="[{ required: true, message: '姓名不能为空', trigger: ['blur', 'change'] },]"
            >
              <el-input v-model="createUser.name" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="身份证号码" prop="idCard"
                          :inline-message = "true"
                          :rules="[
                              { required: true, message: '身份证号码不能为空', trigger: ['blur', 'change'] },
                              { min: 18, max: 18, message: '身份证号码长度不正确', trigger: ['blur', 'change'] },
                              // {
                              //   required: true,
                              //   pattern: /^[1-9]\d{5}(19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                              //   message: '身份证号码格式不正确',
                              //   trigger: 'blur',
                              // },
                              ]"
            >
              <el-input v-model="createUser.idCard" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="电话" prop="phoneNumber">
              <el-input v-model="createUser.phoneNumber" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-input v-model="createUser.address" autocomplete="off"/>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取消</el-button>
              <el-button type="primary" @click="submitCreateUser">提交</el-button>
            </span>
          </template>
        </el-dialog>
      </el-form-item>
    </el-form>

    <el-table :data="tableData" style="width: 100%; height: 600px;">
      <el-table-column fixed prop="name" label="姓名" />
      <el-table-column prop="code" label="单号" />
      <el-table-column prop="idCard" label="身份证号码" />
      <el-table-column prop="phoneNumber" label="联系电话" />
      <el-table-column prop="id" label="id"  v-if="showAgeColumn = false"/>
      <el-table-column fixed="right" label="操作" >
        <template #default="scope">
          <el-button link type="primary" @click="handleUpdateUser(scope.row, scope.$index)">更新</el-button>
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
import axios from "axios";
import type {FormInstance, FormRules} from "element-plus";
import { Delete, Edit, Search, Share, Upload, Refresh } from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'

const ruleFormRef = ref<FormInstance>()

const formInline = reactive({
  name: '',
})

interface CreateUser {
  id: undefined,
  name: undefined,
  phoneNumber: undefined,
  idCard: undefined,
  address: undefined,
}

let createUser = reactive<CreateUser>(
    {
      id: undefined,
      name: undefined,
      phoneNumber: undefined,
      idCard: undefined,
      address: undefined,
    }
);

const totalNumber = ref(3)
const pageNumber = ref(1)
const pageSize = ref(10)
const tableData = ref([]);
const dialogFormVisible = ref(false)
const showAgeColumn = ref(false)
const formRef = ref<FormInstance>()

const getUserList = (value, pageNumber: number, pageSize: number) => {
  axios.post('http://localhost:8080/user/list', {
    "name": value.name,
    "pageNumber": pageNumber,
    "pageSize": pageSize
  }).then(res => {
    console.log('res', res.data);
    tableData.value = res.data.list;
    totalNumber.value = res.data.total;
  })
}

// 一进到receiptForm页面就调用该函数，查询存单列表数据
getUserList({
  "name": '',
}, pageNumber.value, pageSize.value);

// 点击查询时调用该函数，查询满足条件的列表数据
const onSubmit = () => {
  console.log('submit!', formInline)
  getUserList(formInline, pageNumber.value, pageSize.value);
}

// 重置查询框数据，并展示列表数据全集
const reset = () => {
  formInline.name = ''
  getUserList(formInline, pageNumber.value, pageSize.value);
}

// 创建存单数据
const handleCreateUser = () => {
  // 将弹窗历史数据置空
  createUser = reactive<CreateUser>({
    id: undefined,
    name: undefined,
    phoneNumber: undefined,
    idCard: undefined,
    address: undefined,
  })
  // 打开弹窗
  dialogFormVisible.value = true

}

// 保存并提交存单数据
const submitCreateUser = () => {
// // TODO 需要发请求将数据提交到db 提交时需要判断globalIndex是否大于0，是则走更新接口，否则走创建接口
  if (createUser && createUser.id !== undefined) {
    console.log("修改数据 ~~~", createUser)
    // 更新操作，走更新接口
    axios.post("http://localhost:8080/user/update", createUser)
        .then(res => {
          console.log("更新成功", res.data)
          dialogFormVisible.value = false
          getUserList(formInline, pageNumber.value, pageSize.value);
          ElMessage.success("更新成功")
        })
  } else {
    // 创建操作，走创建接口
    console.log("新增数据 !!!", createUser)
    axios.post("http://localhost:8080/user/create", createUser)
        .then(res => {
          dialogFormVisible.value = false
          getUserList(formInline, pageNumber.value, pageSize.value);
          ElMessage.success('创建成功.')
        })
  }
}


// 更新存单数据，在点击时需要将当前行的数据填充到表单中
const handleUpdateUser = (row, index) => {
  console.log("=====", row, index);
  createUser.id = row.id;
  createUser.idCard = row.idCard;
  createUser.name = row.name;
  createUser.phoneNumber = row.phoneNumber;
  dialogFormVisible.value = true
}

</script>

<style lang="scss">
.user-text {
  color: chocolate;
  font-size: 25px;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  padding: 15px 0;
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