import Vue from 'vue'
import Vuex from 'vuex'
import VuexPersist from 'vuex-persist'

Vue.use(Vuex)

const vuexPersist = new VuexPersist({
  key: 'weather',
  storage: window.localStorage
})

export default new Vuex.Store({
  plugins: [vuexPersist.plugin],
  state: {
    smoothGraphs: true
  },
  mutations: {
    setSmoothGraphs(state, smooth) {
      state.smoothGraphs = !!smooth
    }
  },
  actions: {
  },
  modules: {
  }
})
