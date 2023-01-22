import request from '@/utils/request'

const API_PATH = "admin"

export default {
    // 登录
    login(account, password) {
        return request({
            url: `${API_PATH}/login`,
            method: 'POST',
            data: {
                "account": account,
                "password": password
            }
        })
    },
    // 获取用户信息
    getInfo() {
        return request({
            url: `${API_PATH}/getInfo`,
            method: 'GET',
        })
    },
    // 获取路由
    getRouters() {
        return request({
            url: `${API_PATH}/getRouters`,
            method: 'GET'
        })
    },
    // 登出
    logout() {
        return request({
            url: `${API_PATH}/logout`,
            method: 'POST'
        })
    }
}