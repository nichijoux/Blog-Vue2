import Cookies from 'js-cookie'

const TokenKey = 'blog_user_token'

const UserInfoKey = 'blog_user_info'

export function getToken() {
    return Cookies.get(TokenKey)
}

export function setToken(token) {
    return Cookies.set(TokenKey, token)
}

export function removeToken() {
    return Cookies.remove(TokenKey)
}

export function getUserInfo() {
    const userInfo = Cookies.get(UserInfoKey)
    return userInfo ? JSON.parse(userInfo) : null
}

export function setUserInfo(userInfo) {
    return Cookies.set(UserInfoKey, JSON.stringify(userInfo))
}

export function removeUserInfo() {
    return Cookies.remove(UserInfoKey)
}
