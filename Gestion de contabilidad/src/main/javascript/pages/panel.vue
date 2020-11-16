<template>
    <div>
        <!-- Navbar -->
        <navbar :userData="userData" @toggleSidebar="sidebarShow = !sidebarShow"/>
        <!-- /#navbar-wrapper -->

        <div class="d-flex" v-bind:class="{toggled: !sidebarShow}" id="wrapper">
            <!-- Sidebar -->
            <sidebar 
                :sidebar="sidebar"
                @closeSidebar="sidebarShow = true"
            />
            <!-- /#sidebar-wrapper -->

            <!-- Rightside -->
            <div class="w-100 overflow-auto">
                <!-- Breadcrum -->
                <breadcrumb/>
                <!-- /$breadcrum-wrapper -->

                <!-- Page Content -->
                <div class="overflow-auto" style="height: calc(100vh - 106px);">
                    <transition name="fade" mode="out-in">
                        <router-view :key="routerViewKey"></router-view>
                    </transition>
                </div>
                <!-- /#page-content-wrapper -->
            </div>
            <!-- /#rightside-wrapper -->
        </div>
        <!-- /#wrapper -->

        <b-modal id="login-modal" centered hide-footer hide-header no-close-on-esc no-close-on-backdrop size="sm">
            <div>
                <h3>Se cerró la sesión</h3>
                <p>Vuelva a iniciar sesión para continuar</p>
                <loginForm @loginSuccess="loginSuccess"></loginForm>
            </div>
        </b-modal>

        <b-modal id="spark-modal" ok-title="Cerrar" ok-only hide-header size="sm" modal-class="modal-fullscreen">
            <iframe :srcdoc="debugSparkModalHTML" style="width: 100%; height: 100%">
                <p>Your browser does not support iframes.</p>
            </iframe>
        </b-modal>

        <b-alert
            v-model="alert.show"
            :variant="alert.variant"
            class="position-fixed fixed-bottom m-0 rounded-0"
            style="z-index: 2000;"
            dismissible
        >{{alert.message}}</b-alert>
    </div>
    <!-- /#panel-wrapper -->
</template>

<script>
import {capitalizeFirstLetter, getCookie, RequestHelper} from '../util/utils'
import axios from 'axios'
import loginForm from '../components/loginForm'
import breadcrumb from '../components/breadcrumb'
import navbar from '../components/navbar'
import sidebar from '../components/sidebar'

export default {
    props: {
        sidebar: Array
    },
    data() {
        return {
            userData: {
                nombre: "",
                organizacion: ""
            },
            sidebarShow: true,
            reloadPage: false,
            routerViewKey: 0,
            loading: true,

            debugSparkModalHTML: "",

            alert: {
                show: false,
                message: "",
                variant: ""
            }
        }
    },
    methods: {
        loginSuccess() {
            if(this.reloadPage)
                this.softReloadPage();

            this.getUserData();
            this.$bvModal.hide('login-modal');
        },
        softReloadPage() {
            this.routerViewKey ? this.routerViewKey++ : this.routerViewKey--;
        },
        showLoginModal(reload) {
            this.reloadPage = reload;
            this.$bvModal.show('login-modal');
        },
        createToast(title, body, variant, appendToToast) {
			this.$bvToast.toast(body, {
				title: title,
				variant: variant,
				toaster: 'b-toaster-bottom-right',
				solid: true,
				appendToToast: (appendToToast != undefined) ? appendToToast : true
			});
		},
		errorHandling(error) {
			if(error.response) {
				switch(error.response.status) {
                    case 500 : 
                        //this.createToast('Error 500', 'Ha ocurrido un error en el servidor, vuelva a intentarlo mas tarde', 'danger');
                        this.$bvModal.show('spark-modal');
                        this.debugSparkModalHTML = error.response.data;
						break;
					default :
						this.createToast('Error', 'Ha ocurrido un error, vuelva a intentarlo mas tarde', 'danger');
				}
            } 
            else if(error.code) {
                switch(error.code) {
                    case 403:
                        this.alert.show = true;
                        this.alert.message = `Error 403, ${error.message}`;
                        this.alert.variant = "warning";
                        break;

                }
            }
        },
        getUserData() {
            RequestHelper.get('/api/auth/me', {
                success: (data) => {
                    this.userData.nombre = data.nombre;
                },
                notLoggedIn: (data) => {
                    this.showLoginModal(true);
                },
                forbidden: (error) => {
                    console.log("asd");
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.errorHandling(error);
                }
            });
        }
    },
    mounted() {
        this.getUserData();
    },
    provide() {
        return {
            showLoginModal: this.showLoginModal,
            errorHandling: this.errorHandling,
            createToast: this.createToast
        }
    },
    components: {
        "loginForm": loginForm,
        "breadcrumb": breadcrumb,
        "navbar": navbar,
        "sidebar": sidebar
    }
}
</script>

<style>
.modal-fullscreen .modal-dialog {
    max-width: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100vh;
    display: flex;
    position: fixed;
    z-index: 100000;
}
.modal-fullscreen .modal-body {
    padding: 0;
}

.user-panel-avatar{
    cursor: pointer;
}
.user-panel{
    position: absolute;
    top: 2rem;
    right: 5px;
}

#navbar-organizacion{
    width: auto;
}


</style>