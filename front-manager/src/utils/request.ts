import axios from "axios";
import {getToken} from "@/utils/token";
import router from "@/router";

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
    //  res.code
    return res
}, error => {
    console.log('response error: ' + error) // for error debug
    // 跳转到登陆页面
    router.push("/")
    return Promise.reject(error)
})

export default request
