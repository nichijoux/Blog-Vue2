const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  // user模块
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.nickname,
  account: state => state.user.account,
  roleList: state => state.user.roleList,
  permissionList: state => state.user.permissionList,
  // tagsView
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  // 路由
  routes: state => state.permission.routes,
}
export default getters
