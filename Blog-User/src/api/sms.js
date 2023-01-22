import request from '@/utils/request';

const API_PATH = 'user'

export default {
    // 获取验证码
    getValidateCode(email) {
        return request({
            url: `${API_PATH}/getValidateCode`,
            method: 'GET',
            params: { "email": email }
        })
    }
}