import request from '@/utils/request';

const API_PATH = 'user'

export default {
    // 登录
    login(loginDTO) {
        return request({
            url: `${API_PATH}/login`,
            method: 'POST',
            data: loginDTO
        })
    },
    // 注册
    register(registerDTO) {
        return request({
            url: `${API_PATH}/register`,
            method: 'POST',
            data: registerDTO
        })
    },
    // 退出登录
    logout() {
        return request({
            url: `${API_PATH}/logout`,
            method: 'POST'
        })
    }
}