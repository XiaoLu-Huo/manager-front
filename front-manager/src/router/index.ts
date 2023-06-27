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
    },

  ]
} as RouterOptions);


export default router
