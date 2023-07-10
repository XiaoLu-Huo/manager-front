import axios from "axios";
import {getToken} from "@/utils/token";
const request = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 30000
})

request.interceptors.request.use(config => {
    config.headers['content-type'] = 'application/json;charset=utf-8'
    config.headers['authority'] = 'Bearer '+ getToken()
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
    return Promise.reject(error)
})

export default request
