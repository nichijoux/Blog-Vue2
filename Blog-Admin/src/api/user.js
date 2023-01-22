import request from '@/utils/request'

const API_PATH = "admin/user"

export default {
  // 分页查询用户信息
  pageQueryUser(index, limit, queryCondition) {
    return request({
      url: `${API_PATH}/pageQueryUser/${index}/${limit}`,
      method: 'GET',
      params: queryCondition
    })
  },
  // 添加管理员用户
  addUser(userInfo) {
    return request({
      url: `${API_PATH}/addUser`,
      method: 'POST',
      data: userInfo
    })
  },
  // 更新管理员用户
  updateUser(userInfo) {
    return request({
      url: `${API_PATH}/updateUser`,
      method: 'PUT',
      data: userInfo
    })
  },
  // 启用或禁用用户
  enableOrDisableUser(userId, status) {
    return request({
      url: `${API_PATH}/enableOrDisableUser/${userId}`,
      method: 'PUT',
      params: { status: status }
    })
  },
  // 删除用户
  deleteUser(userId) {
    return request({
      url: `${API_PATH}/deleteUser/${userId}`,
      method: 'DELETE'
    })
  },
  // 批量删除用户
  batchDeleteUser(userIdList) {
    return request({
      url: `${API_PATH}/batchDeleteUser`,
      method: 'DELETE',
      data: userIdList
    })
  }
}