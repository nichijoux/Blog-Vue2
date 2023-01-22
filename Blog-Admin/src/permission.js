import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

// 路由切换前守卫
router.beforeEach(async (to, from, next) => {
  // 加载条动画开始
  NProgress.start()
  // 设置名称
  document.title = getPageTitle(to.meta.title)

  // 查询token是否存在,如果存在
  const hasToken = getToken()
  if (hasToken) {
    if (to.path === '/login') {
      // 进入登录界面
      next({ path: '/' })
      NProgress.done()
    } else {
      // 如果不是登录界面
      if (!store.getters.name) {
        // 如果没有获取用户信息
        // 尝试获取用户信息
        store.dispatch('user/getInfo').then((response) => {
          store.dispatch('permission/generateRoutes').then((accessRoutes) => {
            // 加入动态路由
            router.options.routes = accessRoutes;
            router.addRoutes(accessRoutes)
            // 确保addRoutes已经完成
            next({ ...to, replace: true })
          })
        }).catch((error) => {
          // 清除token然后重新登录界面
          store.dispatch('user/resetToken').then((error) => {
            Message.error(error || '发生错误')
            next(`/login?redirect=${to.path}`)
            NProgress.done()
          })
        })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 路径为白名单路径
      next()
    } else {
      // 重定向到登录页面
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
