import store from '@/store'

export default {
    inserted(el, binding, vnode) {
        const { value } = binding
        const super_admin = "super_admin";
        const roleList = store.getters && store.getters.roleList

        if (value && value instanceof Array && value.length > 0) {
            // 如果传入数据为数组
            const roleFlag = value

            const hasRole = roleList.some(role => {
                return super_admin === role || roleFlag.includes(role)
            })

            if (!hasRole) {
                el.parentNode && el.parentNode.removeChild(el)
            }
        } else if (value && typeof(value) == 'string') {
            // 如果传入的是单个字符串
            const hasRole = roleList.includes(value)

            if (!hasRole) {
                el.parentNode && el.parentNode.removeChild(el)
            }
        } else {
            throw new Error(`请设置角色权限标签值"`)
        }
    }
}