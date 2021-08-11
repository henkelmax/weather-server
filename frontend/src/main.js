import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import 'roboto-fontface/css/roboto/roboto-fontface.css'
import '@mdi/font/css/materialdesignicons.css'
import './registerServiceWorker'

import VueGoogleCharts from 'vue-google-charts'
import router from './router'
import i18n from './i18n'

import VueMoment from 'vue-moment'
import moment from 'moment/min/moment-with-locales'

Vue.use(VueMoment, {
  moment,
})

Vue.use(VueGoogleCharts)

Vue.config.productionTip = false

Vue.prototype.$eventBus = new Vue()

new Vue({
  vuetify,
  router,
  i18n,
  render: h => h(App)
}).$mount('#app')
