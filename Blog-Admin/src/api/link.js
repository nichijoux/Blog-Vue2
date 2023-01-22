import request from '@/utils/request'

const API_PATH = "admin/link"

export default {
    // 分页查询友链信息
    pageQueryLink(index, limit, queryCondition) {
        return request({
            url: `${API_PATH}/pageQueryLink/${index}/${limit}`,
            method: 'GET',
            params: queryCondition
        })
    },
    // 添加友链
    addLink(link) {
        return request({
            url: `${API_PATH}/addLink`,
            method: 'POST',
            data: link
        })
    },
    // 更新友链
    updateLink(link) {
        return request({
            url: `${API_PATH}/updateLink`,
            method: 'PUT',
            data: link
        })
    },
    // 删除友链
    deleteLink(linkId) {
        return request({
            url: `${API_PATH}/deleteLink/${linkId}`,
            method: 'DELETE'
        })
    }
}