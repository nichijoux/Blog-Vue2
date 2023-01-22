import Vue from 'vue'
import Vuex from 'vuex'
import getters from '@/store/getters'
import user from '@/store/modules/user'

Vue.use(Vuex)

// 分模块的vuex
const store = new Vuex.Store({
    modules: {
        user
    },
    getters
})

export default store;