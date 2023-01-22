// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'

// 自己的消息提示组件
import Message from '@/components/Message/Message.js'; // 注意引入的路径
Vue.use(Message); // 会去执行Message.js中的install方法

// animate.css
import 'animate.css'
// axios
import axios from 'axios'
import VueScrollTo from 'vue-scrollto'
// semantic ui
import semantic from '../node_modules/semantic-ui-css/semantic.min.js'
import '../node_modules/semantic-ui-css/semantic.min.css'
// layui
import layui from '../static/js/layui.js'
import '../static/css/layui.css'
// tocbot
import '../node_modules/tocbot/dist/tocbot.css'
// 自己的css
import '../static/css/blog.css'
import '../static/css/typo.css'

Vue.config.productionTip = false
Vue.prototype.$axios = axios;
Vue.use(VueScrollTo, {
  container: 'body',   // 滚动元素
  duration: 500,  // 动画时长
  easing: 'ease',  // 动画曲线
  offset: 0,  // 滚动终点距离父元素顶部距离
  force: true,  // 强制立即执行，即便元素是可见的
  cancelable: true,
  onStart: false,
  onDone: false,
  onCancel: false,
  x: false, // x 轴禁止滚动
  y: true
})
Vue.use(semantic)
Vue.use(layui)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
