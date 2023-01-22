// 导入路由脚本文件中的固定路由和动态路由
import { asyncRoutes, constantRoutes } from '@/router'
import loginAPI from '@/api/login'
// layout布局
import Layout from '@/layout/index'

/**
 * Use meta.role to determine if the current user has permission
 * 判断当前用户是否拥有该角色下的菜单信息
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
    if (route.meta && route.meta.roles) {
        return roles.some(role => route.meta.roles.includes(role))
    } else {
        return true
    }
}

/**
 * Filter asynchronous routing tables by recursion
 * 过滤出所拥有的的菜单信息
 * @param routes asyncRoutes
 * @param roles
 */
function filterAsyncRouter(asyncRouterMap) {
    // 遍历后台传来的路由字符串，转换为组件对象
    try {
        const accessedRouters = asyncRouterMap.filter(route => {
            if (route.component) {
                if (route.component === 'Layout') {
                    // Layout组件特殊处理
                    route.component = Layout
                } else {
                    // 否则则进行路由拼接
                    const component = route.component
                    route.component = (resolve) => require([`@/views/${component}.vue`], resolve)
                }
            }
            // 如果有子菜单
            if (route.children && route.children.length) {
                route.children = filterAsyncRouter(route.children)
            }
            return true
        })
        return accessedRouters
    } catch (e) {
        console.log(e)
    }
}
const state = {
    routes: [],
    addRoutes: []
}

// 将路由信息保存到store中
const mutations = {
    SET_ROUTES: (state, routes) => {
        state.addRoutes = routes
        state.routes = constantRoutes.concat(routes)
    }
}

// 动态生成路由
const actions = {
    async generateRoutes({ commit }) {
        // generateRoutes
        const response = await loginAPI.getRouters()
        // 菜单获取
        return new Promise(resolve => {
            let routerTree = buildRouterTree(response.data);
            // 最后添加404
            routerTree.push({ path: '*', redirect: '/404', hidden: true });
            const accessedRouters = filterAsyncRouter(routerTree);
            // 设置到vuex中
            commit("SET_ROUTES", accessedRouters)
            resolve(accessedRouters)
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

/**
 * 构建出路由菜单
 * @param {*} menuTree 后端返回的menuTree
 */
function buildRouterTree(menuTree) {
    let routerList = [];
    // 遍历menuTree
    for (let i = 0; i < menuTree.length; i++) {
        let menu = menuTree[i];
        // 一个router实例
        let router = {};
        // 1.获取访问路径
        router.path = getRouterPath(menu);
        // 2.获取组件
        router.component = getRouterComponent(menu);
        if (menu.redirect) {
            // 3.获取重定向地址
            router.redirect = menu.redirect;
        }
        // 4.获取router名称
        router.name = menu.name;
        // 5.获取router的meta信息
        router.meta = getRouterMeta(menu);
        // 6.获取路由是否显示
        router.hidden = menu.hidden;

        // 判断是否存在子菜单
        const children = menu.children;
        if (children && children.length > 0) {
            router.children = buildRouterTree(children)
        }

        if (menu.pid == 0 && menu.type == 1) {
            // 如果menu是菜单并且其应该为顶层菜单则添加一个父目录去容纳
            const parent = {};
            let index = menu.path.lastIndexOf('/')
            if (index == -1) {
                index = 0;
            }
            parent.path = '/' + menu.path.slice(0, index);
            parent.component = getRouterComponent(parent);
            parent.redirect = getRouterPath(menu);
            parent.meta = getRouterMeta(menu);
            parent.children = [];
            parent.children.push(router)
            routerList.push(parent);
        } else {
            routerList.push(router);
        }
    }
    return routerList;
}

/**
 * 获取路由地址
 * @param {*} menu 
 */
function getRouterPath(menu) {
    if (menu.path != '/')
        return '/' + menu.path;
    else
        return '/'
}

/**
 * 获取路由组件
 * @param {*} menu 
 * @returns 
 */
function getRouterComponent(menu) {
    if (menu.component) {
        return menu.component;
    }
    return "Layout"
}

/**
 * 获取路由的meta信息
 * @param {*} menu 
 * @returns 
 */
function getRouterMeta(menu) {
    return {
        "title": menu.name,
        "icon": menu.icon
    }
}