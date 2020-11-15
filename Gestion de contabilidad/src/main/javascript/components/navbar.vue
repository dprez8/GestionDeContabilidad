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
                <b-list-group-item button @click="logout">
                    <b-icon-box-arrow-left class="mr-2"></b-icon-box-arrow-left> Cerrar sesión
                </b-list-group-item>
            </b-list-group>
        </b-modal>
    </nav>
</template>

<script>
import {capitalizeFirstLetter, getCookie, RequestHelper} from '../util/utils'

export default {
    props: {
        userData: Object
    },
    data() {
        return {
            userPanelShow: false
        }
    },
    inject: ['errorHandling'],
    methods: {
        toggleSidebar() {
            this.$emit('toggleSidebar');
        },
        logout() {
            RequestHelper.post('/api/auth/logout', null, {
                always: () => {
                    sessionStorage.clear();
                    this.$router.push("/login");
                }
            });
        }
    }
}
</script>

<style>

</style>