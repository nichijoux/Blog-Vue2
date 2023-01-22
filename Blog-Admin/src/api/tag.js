import request from '@/utils/request'

const API_PATH = "admin/tag"

export default {
    // 分页查询标签
    pageQueryTag(index, limit, queryCondition) {
        return request({
            url: `${API_PATH}/pageQueryTag/${index}/${limit}`,
            method: "GET",
            params: queryCondition
        })
    },
    // 获取所有启用的标签
    getAllEnableTag() {
        return request({
            url: `${API_PATH}/getAllEnableTag`,
            method: "GET"
        })
    },
    // 添加标签
    addTag(tag) {
        return request({
            url: `${API_PATH}/addTag`,
            method: "POST",
            data: tag
        })
    },
    // 更新标签
    updateTag(tag) {
        return request({
            url: `${API_PATH}/updateTag`,
            method: "PUT",
            data: tag
        })
    },
    // 启用或者禁用标签
    enableOrDisableTag(tagId, status) {
        return request({
            url: `${API_PATH}/enableOrDisableTag/${tagId}`,
            method: "PUT",
            params: { "status": status }
        })
    },
    // 删除标签
    deleteTag(tagId) {
        return request({
            url: `${API_PATH}/deleteTag/${tagId}`,
            method: "DELETE"
        })
    }
}