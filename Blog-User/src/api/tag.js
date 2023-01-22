import request from '@/utils/request'

const API_PATH = '/user/tag'

export default {
    // 获取所有启用的标签
    getAllEnableTag() {
        return request({
            url: `${API_PATH}/getAllEnableTag`,
            method: 'GET'
        })
    }
}