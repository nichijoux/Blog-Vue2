
import { getToken, setToken, removeToken } from '@/utils/cookie'

// 初始化state
const getDefaultState = () => {
    return {
        // 用户token
        token: getToken(),
        // 用户昵称

        // 用户头像
    }
}

const state = getDefaultState()

const mutations = {
    // 重置状态
    RESET_STATE: (state) => {
        Object.assign(state, getDefaultState());
    },
    // 设置token
    SET_TOKEN: (state, token) => {
        state.token = token;
    }
}

const actions = {
    // 用户登录
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}