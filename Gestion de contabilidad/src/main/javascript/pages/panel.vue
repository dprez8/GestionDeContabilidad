<template>
    <div>
        <!-- Navbar -->
        <nav class="navbar navbar-expand-md navbar-dark d-flex bg-dark flex-nowrap">
            <button class="navbar-toggler border-0 p-1 mr-2" data-toggle="collapse" v-on:click="sidebarShow = !sidebarShow">
                <span class="navbar-toggler-icon"></span>
            </button>
            <!-- Nombre de organizacion -->
            <router-link id="navbar-organizacion" class="navbar-brand mr-2" to="/">
                <img src="../assets/utn_logo.png" width="30" height="30" class="d-inline-block align-top" style="filter: invert(1)">
                <span class="p-2">{{userData.organizacion}}</span>
            </router-link>
            <!-- Avatar -->
            <div class="ml-auto user-panel-avatar" @click="userPanelShow = true">
                <b-avatar variant="primary" :text="userData.nombre.substring(0,1)"></b-avatar>
            </div>
            <!-- Panel de opciones de usuario -->
        </nav>
        <!-- /#navbar-wrapper -->

        <div class="d-flex" v-bind:class="{toggled: !sidebarShow}" id="wrapper">
            <!-- Sidebar -->
            <div id="sidebar-wrapper" class="border-right">
                <b-list-group flush>
                    <template v-for="(page, index) in sidebar">
                        <b-list-group-item 
                            :key=index
                            :to="page.path" 
                            @click.native="sidebarShow = true"
                        >{{ page.label }}</b-list-group-item>
                    </template>
                </b-list-group>
            </div>
            <!-- /#sidebar-wrapper -->

            <!-- Rightside -->
            <div class="w-100 overflow-auto">
                <!-- Breadcrum -->
                <b-breadcrumb class="m-0 border-bottom bg-light" style="border-radius: 0;">
                    <b-breadcrumb-item to="/">
                        <b-icon-house></b-icon-house>
                    </b-breadcrumb-item>
                    <template v-for="(page, index) in breadcrumb">
                        <b-breadcrumb-item :key=index v-bind:to="page.path" v-bind:active="page.active">
                            {{ page.label }}
                        </b-breadcrumb-item>
                    </template>
                </b-breadcrumb>
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

        <!-- Panel de opciones de usuario -->
        <b-modal hide-footer hide-header hide-backdrop
            body-class="p-0 rounded overflow-hidden"
            dialog-class="user-panel"
            content-class="shadow"
            v-model="userPanelShow"
        >
            <b-list-group flush>
                <b-list-group-item>{{userData.nombre}}</b-list-group-item>
                <b-list-group-item to="/configuracion" @click="userPanelShow = false">
                        <b-icon-gear-fill class="mr-2"></b-icon-gear-fill> Configuración
                </b-list-group-item>
                <b-list-group-item to="#" @click="logout">
                    <b-icon-box-arrow-left class="mr-2"></b-icon-box-arrow-left> Cerrar sesión
                </b-list-group-item>
            </b-list-group>
        </b-modal>

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

    </div>
    <!-- /#panel-wrapper -->
</template>

<script>
import {capitalizeFirstLetter, getCookie} from '../util/utils'
import axios from 'axios'
import loginForm from '../components/loginForm'

export default {
    data() {
        return {
            sidebar: [
                {
                    label: "Operaciones",
                    path: '/operaciones',
                },
                {
                    label: "Presupuestos",
                    path: '/presupuestos'
                },
                {
                    label: "Bandeja de mensajes",
                    path: '/bandeja'
                }
            ],
            userData: {
                organizacion: "",
                nombre: ""
            },
            userPanelShow: false,
            sidebarShow: true,
            breadcrumb: [],
            reloadPage: false,
            routerViewKey: 0,
            loading: true,

            debugSparkModalHTML: ""
        }
    },
    methods: {
        updateBreadcrumb() {
            
            if(this.$route.meta.breadcrumb == undefined){
                this.breadcrumb = [];
                return;
            }

            var breadcrumb = JSON.parse(JSON.stringify(this.$route.meta.breadcrumb));

            breadcrumb.forEach(crumb => {
                if(crumb.type == "dynamic") {
                    var key = crumb.dynamicText;
                    var dynamicLabel = this.$route.params[key];

                    if(dynamicLabel != undefined) {
                        crumb.label = crumb.label.replace(key, dynamicLabel);
                        crumb.path = crumb.path.replace(key, dynamicLabel);
                    }
                }
            });
            this.breadcrumb = breadcrumb;
        },
        logout() {
            document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            document.cookie = "organizacion=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

            this.$router.push("/login");
        },
        loginSuccess() {
            if(this.reloadPage)
                this.softReloadPage();

            this.$bvModal.hide('login-modal');
        },
        showLoginModal(reload) {
            this.reloadPage = reload;
            this.$bvModal.show('login-modal');
        },
        softReloadPage() {
            this.routerViewKey ? this.routerViewKey++ : this.routerViewKey--;
        },
        createToast(title, body, variant) {
			this.$bvToast.toast(body, {
				title: title,
				variant: variant,
				toaster: 'b-toaster-bottom-right',
				solid: true,
				appendToToast: true
			});
		},
		errorHandling(error) {
            console.log(error);

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
			} else {
				this.createToast('Error', 'Ha ocurrido un error, vuelva a intentarlo mas tarde', 'danger');
			}
        },
    },
    watch: {
        $route(to, from) {
            this.updateBreadcrumb();
        }
    },
    mounted() {
        this.updateBreadcrumb();
        this.userData.nombre = capitalizeFirstLetter(getCookie("username"));
        this.userData.organizacion = getCookie("organizacion");
    },
    provide() {
        return {
            showLoginModal: this.showLoginModal,
            errorHandling: this.errorHandling,
            createToast: this.createToast
        }
    },
    components: {
        "loginForm": loginForm
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
#wrapper {
    overflow-x: hidden;
}

#sidebar-wrapper {
    min-height: calc(100vh - 57px);
    margin-left: -15rem;
    -webkit-transition: margin .25s ease-out;
    -moz-transition: margin .25s ease-out;
    -o-transition: margin .25s ease-out;
    transition: margin .25s ease-out;
}

#sidebar-wrapper .sidebar-heading {
    padding: 0.875rem 1.25rem;
    font-size: 1.2rem;
}

#sidebar-wrapper .list-group {
    width: 15rem;
}

#page-content-wrapper {
    min-width: 100vw;
}

#wrapper.toggled #sidebar-wrapper {
    margin-left: 0;
}

@media (min-width: 768px) {
    #sidebar-wrapper {
        margin-left: 0;
    }

    #page-content-wrapper {
        min-width: 0;
        width: 100%;
    }

    #wrapper.toggled #sidebar-wrapper {
        margin-left: -15rem;
    }
    #navbar-organizacion{
        width: 14rem;
    }
}

</style>