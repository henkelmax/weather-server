import Vue from 'vue'
import VueRouter from 'vue-router'
import Widget from '../views/Widget.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Weather',
    component: Widget
  }
]

const router = new VueRouter({
  routes
})

export default router
