import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import { getToken } from '@/utils/cookie'

// 创建axios实例
const service = axios.create({
    // url公用部分
    baseURL: 'http://localhost:8500',
    // 超时时间
    timeout: 10000
})

// 请求前拦截
service.interceptors.request.use(
    config => {
        if (store.getters.token) {
            // 设置token
            config.headers['token'] = getToken()
        }
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

// 响应拦截器
service.interceptors.response.use(
    response => {
        const res = response.data

        // 如果返回的code不是20000则说明出错了,进行提示
        if (res.code !== 20000) {
            Vue.prototype.$message.show({
                title: "系统提示",
                message: res.message,
                type: "error",
                duration: 2000
            });

            if (res.code === 20003 || res.code === 20004) {
                // 重新登录
                Vue.prototype.$message.show({
                    title: "系统提示",
                    message: "检测到token异常,请重新登录",
                    type: "error",
                    duration: 2000
                });
                store.dispatch('user/resetToken').then(() => {
                    location.reload()
                })
            }

            return Promise.reject(new Error(res.message || 'Error'))
        } else {
            return res
        }
    },
    error => {
        Vue.prototype.$message.show({
            title: "系统提示",
            message: error.message,
            type: "error",
            duration: 2000
        });
        return Promise.reject(error)
    }
)

export default service