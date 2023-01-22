import request from '@/utils/request'

const API_PATH = "admin/role"

export default {
    // 分页查询角色信息
    pageQueryRole(index, limit, queryCondition) {
        return request({
            url: `${API_PATH}/pageQueryRole/${index}/${limit}`,
            method: 'GET',
            params: queryCondition
        })
    },
    // 获取用户的角色
    getUserRole(userId) {
        return request({
            url: `${API_PATH}/getUserRole/${userId}`,
            method: 'GET'
        })
    },
    // 添加角色
    addRole(role) {
        return request({
            url: `${API_PATH}/addRole`,
            method: 'POST',
            data: role
        })
    },
    // 更新角色
    updateRole(role) {
        return request({
            url: `${API_PATH}/updateRole`,
            method: 'PUT',
            data: role
        })
    },
    // 分配角色
    assignUserRole(userId, roleIdList) {
        return request({
            url: `${API_PATH}/assignUserRole/${userId}`,
            method: 'PUT',
            params: {
                "roleIdList": roleIdList
            }
        })
    },
    // 启用或禁用角色
    enableOrDisableRole(roleId, status) {
        return request({
            url: `${API_PATH}/enableOrDisableRole/${roleId}`,
            method: 'PUT',
            params: { "status": status }
        })
    },
    // 删除角色
    deleteRole(roleId) {
        return request({
            url: `${API_PATH}/deleteRole/${roleId}`,
            method: 'DELETE'
        })
    },
    // 批量删除角色
    batchDeleteRole(roleIdList) {
        return request({
            url: `${API_PATH}/batchDeleteRole`,
            method: 'DELETE',
            data: roleIdList
        })
    }
}