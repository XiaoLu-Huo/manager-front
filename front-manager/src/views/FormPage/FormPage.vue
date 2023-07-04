<template>
  <div class="common-layout">
    <el-container class="layout-container">
      <el-header class="layout-header">用户储蓄管理系统</el-header>
      <el-container class="layout-inner">
        <el-aside class="layout-aside">
          <h3 class="mb-2">目 录</h3>
          <el-menu
              default-active="2-1"
              class="el-menu-vertical-demo"
              @open="handleOpen"
              @close="handleClose"
          >
            <el-sub-menu index="1">
              <template #title>
                <el-icon>
                  <icon-menu/>
                </el-icon>
                <span>储户信息</span>
              </template>
              <el-menu-item index="1-1"><router-link to="/form/user">储户列表</router-link></el-menu-item>
            </el-sub-menu>
            <el-sub-menu index="2">
              <template #title>
                <el-icon>
                  <icon-menu/>
                </el-icon>
                <span>单据信息</span>
              </template>
              <el-menu-item index="2-1"><router-link to="/form/receipt">单据列表</router-link></el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-aside>
        <el-main class="layout-main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<style lang="scss">
.common-layout {
  width: 100%;
  height: 100vh;
  background: #1c92d2; /* fallback for old browsers */
  background: -webkit-linear-gradient(to right, #f2fcfe, #1c92d2); /* Chrome 10-25, Safari 5.1-6 */
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
    width: 20%;
    box-shadow: 4px 4px 15px -3px #666;

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
    //background-color: white;
  }

  .el-tree {
    background: transparent;
  }

  .el-menu-vertical-demo {
    background-color: rgba(255, 255, 255, 0.2);
  }

  .el-menu-item {
    background-color: rgba(255, 255, 255, 0.5);
  }

  .el-menu-item.is-active {
    color: #1c92d2;
    background: rgba(255, 255, 255, 0.5);
  }

}

</style>