import axios from "axios";
import {getToken, removeToken} from "@/utils/token";
import router from "@/router";
import {ElMessage} from "element-plus";

const request = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 30000
})

request.interceptors.request.use(config => {
    config.headers['content-type'] = 'application/json;charset=utf-8'
    // config.headers['authority'] = 'Bearer '+ getToken()
    config.headers['token'] = getToken()
    return config
}, error => {
    console.log('request error: ' + error) // for error debug
    return Promise.reject(error)
})

request.interceptors.response.use(response => {
    return response;
}, error => {
    console.log("error",error.response.status)
    if (error.response.status === 401) {
        // 跳转到登陆页面
        ElMessage.error("token不存在或已失效，请重新登陆")
        removeToken()
        // router.push({name:'home'})
        window.location.href='/';
    }
    console.log('response error: ' + error) // for error debug
    return Promise.reject(error)
})

export default request
