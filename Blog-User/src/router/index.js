import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/Login'
import Index from '@/views/Index'
import Blog from '@/views/Blog'
import Tags from '@/views/Tags'
import Archive from '@/views/Archive'
import Link from '@/views/Link'
import NotFound from '@/views/NotFound'
import Center from '@/views/Center'

Vue.use(Router)

const constantRoutes = [
  {
    path: '/',
    redirect: '/index'
  },
  // 首页
  {
    path: '/index',
    name: 'Index',
    component: Index
  },
  // 登录
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  // 文章详情
  {
    path: '/blog',
    name: 'Blog',
    component: Blog
  },
  // 标签页
  {
    path: '/tags',
    name: 'Tags',
    component: Tags
  },
  // 归档页
  {
    path: '/archive',
    name: 'Archive',
    component: Archive
  },
  // 友链页
  {
    path: '/link',
    name: 'Link',
    component: Link
  },
  // 用户中心页
  {
    path: '/center',
    name: 'Center',
    component: Center
  },
  // 页面未找到
  {
    path: '/404',
    name: 'NotFound',
    component: NotFound,
    hidden: true
  },
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

export default router