import request from '@/utils/request'

const API_PATH = "admin/article"

export default {
    // 分页查询文章列表
    pageQueryArticle(index, limit, queryCondition) {
        return request({
            url: `${API_PATH}/pageQueryArticle/${index}/${limit}`,
            method: "GET",
            params: queryCondition
        })
    },
    // 获取文章信息
    getArticle(id) {
        return request({
            url: `${API_PATH}/getArticle/${id}`,
            method: "GET"
        })
    },
    // 添加文章
    addArticle(article) {
        return request({
            url: `${API_PATH}/addArticle`,
            method: "POST",
            data: article
        })
    },
    // 更新文章
    updateArticle(article) {
        return request({
            url: `${API_PATH}/updateArticle`,
            method: "PUT",
            data: article
        })
    },
    // 删除文章
    deleteArticle(articleId) {
        return request({
            url: `${API_PATH}/deleteArticle/${articleId}`,
            method: "DELETE"
        })
    }
}