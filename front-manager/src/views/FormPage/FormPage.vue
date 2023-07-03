<template>
  <div class="common-layout">
    <el-container class="layout-container">
      <el-header class="layout-header">用户储蓄管理系统</el-header>
      <el-container class="layout-inner">
        <el-aside class="layout-aside">
          <el-tree
              :data="data"
              :props="defaultProps"
              accordion
              @node-click="handleNodeClick"
              :default-expanded-keys="[5]"
          />
        </el-aside>

        <el-main class="layout-main">
          hhhhhh
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
interface Tree {
  id: number
  label: string
  children?: Tree[]
}

const handleNodeClick = (data: Tree) => {
  console.log(data)

}

const data: Tree[] = [
  {
    id: 1,
    label: '目录',
    children: [
      {
        id: 2,
        label: '储户信息',
        children: [
          {
            id: 4,
            label: '储户列表',
          },
        ],
      },
      {
        id: 3,
        label: '单据信息',
        children: [
          {
            id: 5,
            label: '单据列表',
          },
        ],
      }
    ]
  },
]
const defaultProps = {
  children: 'children',
  label: 'label',
}
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
  }

  .el-tree {
    background: transparent;
  }
}

</style>