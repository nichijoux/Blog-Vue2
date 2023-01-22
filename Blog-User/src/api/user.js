import request from '@/utils/request';

const API_PATH = 'user'

export default {
    // 获取用户信息
    getUserInfo() {
        return request({
            url: `${API_PATH}/getUserInfo`,
            method: "GET"
        })
    },
    // 更新用户信息
    updateUserInfo(userInfo) {
        return request({
            url: `${API_PATH}/updateUserInfo`,
            method: 'PUT',
            data: userInfo
        })
    }
}