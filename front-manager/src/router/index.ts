import {createRouter, createWebHistory,RouterOptions} from 'vue-router'
import LoginPage from '../views/LoginPage/LoginPage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes:[
    {
      path: '/',
      name: 'home',
      component: LoginPage,
    },
    {
      path: '/form',
      name: 'form',
      component: ()=>import('../views/FormPage/FormPage.vue'),
      children:[
        {
          // 当 /user/:id/profile 匹配成功
          // UserProfile 将被渲染到 User 的 <router-view> 内部
          path: 'receipt',
          component: ()=>import('../views/FormPage/ReceiptForm.vue'),
        },
        {
          // 当 /user/:id/profile 匹配成功
          // UserProfile 将被渲染到 User 的 <router-view> 内部
          path: 'user',
          component: ()=>import('../views/FormPage/UserForm.vue'),
        },
      ]
    },

  ]
} as RouterOptions);


export default router
