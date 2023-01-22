import request from '@/utils/request'

const API_PATH = '/user/article'

export default {
    // 分页查询获取发布文章
    pageQueryPublishedArticle(index, limit, tagId) {
        return request({
            url: `${API_PATH}/pageQueryPublishedArticle/${index}/${limit}`,
            method: 'GET',
            params: { "tagId": tagId }
        })
    },
    // 获取已经发布文章的详情
    getPublishedArticleDetail(articleId) {
        return request({
            url: `${API_PATH}/getPublishedArticleDetail/${articleId}`,
            method: 'GET'
        })
    },
    // 获取热门推荐文章
    getHotArticleList() {
        return request({
            url: `${API_PATH}/getHotArticleList`,
            method: 'GET'
        })
    },
    // 获取归档
    getArchiveList() {
        return request({
            url: `${API_PATH}/getArchiveList`,
            method: 'GET'
        })
    }
}