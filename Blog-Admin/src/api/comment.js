import request from '@/utils/request'

const API_PATH = "admin/comment"

export default {
    // 分页查询评论
    pageQueryComment(index, limit, queryCondition) {
        return request({
            url: `${API_PATH}/pageQueryComment/${index}/${limit}`,
            method: 'GET',
            params: queryCondition
        })
    }
}