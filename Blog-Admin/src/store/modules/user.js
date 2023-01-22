import loginAPI from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    // 用户昵称
    nickname: '',
    // 用户账号
    account: '',
    // 用户头像
    avatar: '',
    // 用户角色
    roleList: [],
    // 权限值列表
    permissionList: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NICKNAME: (state, nickname) => {
    state.nickname = nickname
  },
  SET_ACCOUNT: (state, account) => {
    state.account = account
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_ROLE_LIST: (state, roleList) => {
    state.roleList = roleList
  },
  SET_PERMISSION_LIST: (state, permissionList) => {
    state.permissionList = permissionList
  }
}

const actions = {
  // 用户登录
  login({ commit }, userInfo) {
    const { account, password } = userInfo;
    return new Promise((resolve, reject) => {
      loginAPI.login(account.trim(), password).then((response) => {
        const token = response.data;
        commit("SET_TOKEN", token)
        // 设置cookie中的token信息
        setToken(token)
        resolve()
      }).catch((error) => {
        reject(error)
      })
    })
  },

  // 获取用户信息
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      // 调用api获取信息
      loginAPI.getInfo().then((response) => {
        if (!response.data) {
          return reject('校验失败,请重新登录');
        }
        // 获取用户信息
        const userInfoVO = response.data.userInfoVO;
        // 设置用户信息
        commit('SET_NICKNAME', userInfoVO.nickname);
        commit('SET_ACCOUNT', userInfoVO.account)
        commit('SET_AVATAR', userInfoVO.avatar);
        // 获取角色列表
        const roleList = response.data.roleList;
        if (roleList && roleList.length > 0) {
          commit('SET_ROLE_LIST', roleList)
          // 获取权限值列表
          const permissionList = response.data.permissionList;
          commit('SET_PERMISSION_LIST', permissionList)
          // 放行
          resolve(response.data)
        } else {
          reject("用户不存在角色!!!");
        }
      }).catch((error) => {
        reject(error)
      })
    })
  },

  // 用户登出
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      // 调用API进行登出
      loginAPI.logout().then((response) => {
        // 先移除token
        removeToken();
        // 在重置router
        resetRouter();
        // 重置状态
        commit('RESET_STATE');
        resolve();
      }).catch((error) => {
        reject(error);
      })
    })
  },

  // 清除token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
