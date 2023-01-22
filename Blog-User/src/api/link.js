import request from '@/utils/request'

const API_PATH = '/user/link'

export default {
    // 获取所有的友链
    getAllLink() {
        return request({
            url: `${API_PATH}/getAllLink`,
            method: 'GET'
        })
    },
    // 添加友链
    addLink(link) {
        return request({
            url: `${API_PATH}/addLink`,
            method: 'POST',
            data: link
        })
    }
}