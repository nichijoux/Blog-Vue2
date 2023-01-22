import store from '@/store'

export default {
    inserted(el, binding, vnode) {
        const { value } = binding
        // 获取拥有的权限值
        const permissionList = store.getters && store.getters.permissionList;
        const avatar = store.getters && store.getters.avatar;
        if (value && value instanceof Array && value.length > 0) {
            // 如果所绑定的是数组
            const permissionFlag = value
            // 判断是否拥有权限
            const hasPermissions = permissionList.some(permission => {
                return permissionFlag.includes(permission)
            })

            if (!hasPermissions) {
                el.parentNode && el.parentNode.removeChild(el)
            }
        } else if (value && typeof (value) == 'string') {
            // 如果绑定的是字符串
            const hasPermissions = permissionList.includes(value)

            if (!hasPermissions) {
                el.parentNode && el.parentNode.removeChild(el)
            }
        } else {
            throw new Error(`请设置操作权限值`)
        }
    }
}