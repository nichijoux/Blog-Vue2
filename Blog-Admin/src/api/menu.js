import request from '@/utils/request'

const API_PATH = "admin/menu"

export default {
    // 获取所有的Menu
    getAllMenu() {
        return request({
            url: `${API_PATH}/getAllMenu`,
            method: "GET"
        })
    },
    // 获取treeSelect组件需要的树形menu
    getTreeSelectMenu() {
        return request({
            url: `${API_PATH}/getTreeSelectMenu`,
            method: "GET"
        })
    },
    // 根据角色id获取菜单
    getAssignedMenu(roleId) {
        return request({
            url: `${API_PATH}/getAssignedMenu/${roleId}`,
            method: 'GET'
        })
    },
    // 添加menu
    addMenu(menu) {
        return request({
            url: `${API_PATH}/addMenu`,
            method: "POST",
            data: menu
        })
    },
    // 更新menu
    updateMenu(menu) {
        return request({
            url: `${API_PATH}/updateMenu`,
            method: "PUT",
            data: menu
        })
    },
    // 给角色分配权限
    assignRoleMenu(roleId, menuIdList) {
        return request({
            url: `${API_PATH}/assignRoleMenu/${roleId}`,
            method: "PUT",
            params: {
                "menuIdList": menuIdList
            }
        })
    },
    // 启用或者禁用菜单
    enableOrDisableMenu(menuId, status) {
        return request({
            url: `${API_PATH}/enableOrDisableMenu/${menuId}`,
            method: "PUT",
            params: { "status": status }
        })
    },
    // 删除menu
    deleteMenu(menuId) {
        return request({
            url: `${API_PATH}/deleteMenu/${menuId}`,
            method: "DELETE"
        })
    }
}