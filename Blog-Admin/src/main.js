import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'
import directive from './directive'

import '@/icons' // icon
import '@/permission' // permission control

// 滑动验证码
import SlideVerify from 'vue-monoplasty-slide-verify'
// markdown组件
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

// 滑动验证框
Vue.use(SlideVerify)

Vue.use(ElementUI, { locale })
Vue.use(directive)
Vue.use(mavonEditor)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
