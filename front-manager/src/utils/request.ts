import axios from "axios";
import {getToken} from "@/utils/token";
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
    let res = response
    if (res.status === '400') {
        // 跳转到登陆页面
        ElMessage.error("token不存在或已失效，请重新登陆")
        router.push("/")
    }
    return res
}, error => {
    console.log('response error: ' + error) // for error debug
    return Promise.reject(error)
})

export default request
