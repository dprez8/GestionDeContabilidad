<template>
    <div>
        <!-- Navbar -->
        <nav class="navbar navbar-expand-md navbar-dark d-flex bg-dark flex-nowrap">
            <button class="navbar-toggler border-0 p-1 mr-2" data-toggle="collapse" v-on:click="sidebarShow = !sidebarShow">
                <span class="navbar-toggler-icon"></span>
            </button>
            <!-- Nombre de organizacion -->
            <div id="navbar-organizacion" class="navbar-brand mr-2">
                <img src="../assets/utn_logo.png" width="30" height="30" class="d-inline-block align-top" style="filter: invert(1)">
                <span class="p-2">{{userData.organizacion}}</span>
            </div>
            <!-- Avatar -->
            <div class="ml-auto" v-b-toggle.user-content>
                <b-avatar variant="primary" :text="userData.nombre.substring(0,1)"></b-avatar>
            </div>
            <!-- Panel de opciones de usuario -->
            <b-collapse class="shadow-lg" id="user-content">
                <b-list-group>
                    <b-list-group-item>{{userData.nombre}}</b-list-group-item>
                    <b-list-group-item to="/configuracion" v-b-toggle.user-content>
                            <b-icon-gear-fill class="mr-2"></b-icon-gear-fill> Configuración
                    </b-list-group-item>
                    <b-list-group-item to="#" @click="logout">
                        <b-icon-box-arrow-left class="mr-2"></b-icon-box-arrow-left> Cerrar sesión
                    </b-list-group-item>
                </b-list-group>
            </b-collapse>
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
                <div class="overflow-auto" style="height: calc(100vh - 106px)">
                    <transition name="fade" mode="out-in">
                        <router-view :key="routerViewKey"></router-view>
                    </transition>
                </div>
                <!-- /#page-content-wrapper -->
            </div>
            <!-- /#rightside-wrapper -->

        </div>
        <!-- /#wrapper -->

        <b-modal id="login-modal" centered hide-footer hide-header :no-close-on-backdrop="true">
            <p>Se ha cerrado la sesión, ingrese su usuario y contraseña para poder continuar</p>
            <loginForm @loginSuccess="loginSuccess"></loginForm>
        </b-modal>

    </div>
    <!-- /#panel-wrapper -->
</template>

<style>

</style>

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
            sidebarShow: true,
            breadcrumb: [],
            reloadPage: false,
            routerViewKey: 0
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
                    var key = crumb.label;
                    var dynamicLabel = this.$route.params[key];

                    if(dynamicLabel != undefined) {
                        crumb.label = dynamicLabel;
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
        checkIfLoggedIn() {
            axios
                .get('http://gesoc.ddns.net/api/login')
                .then(response => {
                    var data = response.data;

                    if(data.code == 200) {
                        console.log(data);
                        this.userData.nombre = capitalizeFirstLetter(data.nombre);
                        this.userData.organizacion = data.organizacion.razonSocial;
                    } else if (data.code == 403) {
                        this.showLoginModal(true);
                    } else {
                        //app.createCommonErrors(data);
                    }
                });
        },
        showLoginModal(reload) {
            this.reloadPage = reload;
            this.$bvModal.show('login-modal');
        },
        softReloadPage() {
            this.routerViewKey ? this.routerViewKey++ : this.routerViewKey--;
        }
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

        this.checkIfLoggedIn();
    },
    provide() {
        return {
            showLoginModal: this.showLoginModal
        }
    },
    components: {
        "loginForm": loginForm
    }
}
</script>