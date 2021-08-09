import Vue from 'vue'
import VueRouter from 'vue-router'
import Weather from '../views/Weather.vue'
import Privacy from '../views/Privacy.vue'
import Legal from '../views/Legal.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Weather',
    component: Weather
  },
  {
    path: '/privacy',
    name: 'Privacy',
    component: Privacy
  },
  {
    path: '/legal',
    name: 'Legal',
    component: Legal
  }
]

const router = new VueRouter({
  routes
})

export default router
