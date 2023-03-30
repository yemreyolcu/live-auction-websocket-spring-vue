import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    products: [],
    productOneState: false,
    productTwoState: false,
    productThreeState: false,
    storeToken: null,
    wrongToken: false,
    wrongTokenText: 'Session has expired. Please login again.',
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
