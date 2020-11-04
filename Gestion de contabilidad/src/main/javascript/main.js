import Vue from 'vue'
import VueRouter from 'vue-router'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)
// Install Router
Vue.use(VueRouter)

Vue.config.productionTip = false

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// Importamos el componente base
import router from './router.js'
import App from './App.vue'

new Vue({
	el: "#app",
	methods: {
		createToast(title, body, variant) {
			this.$bvToast.toast(body, {
				title: title,
				variant: variant,
				toaster: 'b-toaster-bottom-right',
				solid: true,
				appendToToast: true
			});
		},
		errorHandling(response) {
			if(response.status) {
				switch(response.status) {
					case 500 :
						this.createToast('Error 500', 'Ha ocurrido un error en el servidor, vuelva a intentarlo mas tarde', 'danger');
						break;
					default :
						this.createToast('Error', 'Ha ocurrido un error, vuelva a intentarlo mas tarde', 'danger');
				}
			} else {
				this.createToast('Error', 'Ha ocurrido un error, vuelva a intentarlo mas tarde', 'danger');
			}
		}
	},
	router: router,
	render: h => h(App)
})