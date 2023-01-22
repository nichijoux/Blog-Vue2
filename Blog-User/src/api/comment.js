import request from '@/utils/request';

const API_PATH = 'user/comment'

export default {
    // 分页查询博客评论
    pageQueryArticleComment(index, limit, articleId) {
        return request({
            url: `${API_PATH}/pageQueryArticleComment/${index}/${limit}`,
            method: 'GET',
            params: {
                "articleId": articleId
            }
        })
    },
    // 添加评论
    addComment(comment) {
        return request({
            url: `${API_PATH}/addComment`,
            method: 'POST',
            data: comment
        })
    }
}