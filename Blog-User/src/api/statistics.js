import request from '@/utils/request'

const API_PATH = '/user/statistics'

export default {
    // 获取统计数据
    getStatistics() {
        return request({
            url: `${API_PATH}/getBlogUserStatistics`,
            method: 'GET'
        })
    }
}