<template>
  <div class="common-layout">
    <el-container class="layout-container">
      <el-header class="layout-header">用户储蓄管理系统</el-header>
      <el-container class="layout-inner">
        <el-aside class="layout-aside">
          <el-carousel :interval="4000" type="card" height="600px">
            <el-carousel-item v-for="item in carouselData" :key="item" class="layout-aside-carousel">
              <img :src=item.imgUrl class="layout-aside-carousel-img">
            </el-carousel-item>
          </el-carousel>
          <br>
          <br>
          <br>
          <h3 class="header-text">欢迎来到我们的网页 ~~</h3>
          <br>
          <p class="text">该网站是一个用户存储系统，主要包含了用户的基本信息以及单据信息等内容</p>
        </el-aside>

        <el-main class="layout-main">
          <el-form
              ref="ruleFormRef"
              :model="ruleForm"
              :rules="rules"
              label-width="120px"
              class="demo-ruleForm"
              :size="formSize"
              status-icon
          >
            <el-form-item label="用户名" prop="userName">
              <el-input class="name-password" v-model="ruleForm.userName"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input class="name-password" v-model="ruleForm.password"/>
            </el-form-item>
            <el-form-item>
              <el-button class="name-password" type="primary" @click="submitForm(ruleFormRef)">
                Login
              </el-button>
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'
import {useRouter} from "vue-router";
import back from "../../photo/background.png";
import back2 from "../../photo/background1.jpeg";
import back3 from "../../photo/background2.jpeg";
import axios from "axios";
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {setToken} from "@/utils/token";
const router = useRouter();

interface RuleForm {
  userName: string
  password: string
}

const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  userName: '',
  password: '',
})

const rules = reactive<FormRules<RuleForm>>({
  userName: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, max: 8, message: '用户名长度为3-8位', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 10, message: '密码长度为6-10位', trigger: 'blur'},
  ]
})
const carouselData = ref([{
  imgUrl: back
}, {
  imgUrl: back2
}, {
  imgUrl: back3
}
]);

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      request.post("/login", ruleForm)
          .then(res => {
            if (res.data !== '') {
              console.log("token!", res.data)
              router.push({
                name: 'receipt-list',
              })
              setToken(res.data)
              ElMessage.success("登陆成功")
              console.log('submit!', ruleForm)
            } else {
              ElMessage.error("用户名或者密码错误，请重新输入")
              console.log('请登录!', fields)
            }
          })
    } else {
      console.log('error submit!', fields)
    }
  })
}

const options = Array.from({length: 10000}).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
</script>

<style lang="scss">
.common-layout {
  width: 100%;
  height: 100vh;
  background: #1c92d2;  /* fallback for old browsers */
  background: -webkit-linear-gradient(to right, #f2fcfe, #1c92d2);  /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(to right, #f2fcfe, #1c92d2); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */


  .layout-container {
    width: 100%;
    height: 100vh;
  }

  .layout-inner {
    height: 80vh;
  }

  .layout-header {
    box-shadow: 4px 4px 15px -3px #666;
    height: 10vh;
    min-height: 100px;
    color: black; /* fallback for old browsers */
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    font-size: 40px;
    font-weight: bold;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .layout-aside {
    width: 70%;

    &-carousel {
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 50px;

      &-img {
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }
  }

  .layout-main {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}

.el-form-item__label {
  font-size: 25px;
  color: #181818;
  font-weight: bolder;
  font-family: "Times New Roman",cursive;
}

.name-password {
  width: 200px;
  height: 40px;
}

.el-carousel__item h3 {
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.text{
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Times New Roman", cursive;
  font-size: 20px;
}

.header-text {
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Times New Roman", cursive;
  font-size: 30px;
}

</style>