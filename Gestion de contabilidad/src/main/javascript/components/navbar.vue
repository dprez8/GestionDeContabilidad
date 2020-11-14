<template>
    <nav class="navbar navbar-expand-md navbar-dark d-flex bg-dark flex-nowrap">
        <button class="navbar-toggler border-0 p-1 mr-2" data-toggle="collapse" @click="toggleSidebar">
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
        <b-modal hide-footer hide-header hide-backdrop
            body-class="p-0 rounded overflow-hidden"
            dialog-class="user-panel"
            content-class="shadow"
            v-model="userPanelShow"
        >
            <b-list-group flush>
                <b-list-group-item to="/cuenta" @click="userPanelShow = false">
                        <b-icon-person-fill class="mr-2"></b-icon-person-fill> {{userData.nombre}}
                </b-list-group-item>
                <b-list-group-item to="#" @click="logout">
                    <b-icon-box-arrow-left class="mr-2"></b-icon-box-arrow-left> Cerrar sesión
                </b-list-group-item>
            </b-list-group>
        </b-modal>
    </nav>
</template>

<script>
import {capitalizeFirstLetter, getCookie} from '../util/utils'

export default {
    data() {
        return {
            userData: {
                organizacion: "",
                nombre: ""
            },
            userPanelShow: false
        }
    },
    methods: {
        toggleSidebar() {
            this.$emit('toggleSidebar');
        },
        logout() {
            document.cookie = "username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            document.cookie = "organizacion=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
            document.cookie = "JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";

            this.$router.push("/login");
        }
    },
    mounted() {
        this.userData.nombre = capitalizeFirstLetter(getCookie("username"));
        this.userData.organizacion = getCookie("organizacion");
    },
}
</script>

<style>

</style>