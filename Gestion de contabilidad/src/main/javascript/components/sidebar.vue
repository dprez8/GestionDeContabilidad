<template>
    <div id="sidebar-wrapper" class="border-right">
        <b-list-group flush>
            <template v-for="(item, key) in sidebar">
                <b-list-group-item 
                    :key="key"
                    class="d-flex align-items-center" 
                    :to="item.to" 
                    :active="active(item)"
                >
                    <b-icon class="mr-2" :icon="item.icon"></b-icon>
                    <span>{{item.text}}</span>
                    <b-badge class="ml-auto" variant="success" pill v-if="item.bandeja && mensajesCount">{{mensajesCount}}</b-badge>
                </b-list-group-item>
            </template>
        </b-list-group>
    </div>
</template>

<script>
import {RequestHelper} from '../util/utils'

export default {
    props: {
        sidebar: Array
    },
    data() {
        return {
            update: true,
            updating: false,
            updateSpeed: 20000, // 20s
            mensajesCount: 0
        }
    },
    inject: ["showLoginModal", "errorHandling", "createToast"],
    methods: {
        closeSidebar() {
            this.$emit('closeSidebar');
        },
        active(item) {
            if(this.$route.meta.breadcrumb && this.$route.meta.breadcrumb[0]) {
                return this.$route.meta.breadcrumb[0].label == item.text;
            } else {
                return this.$route.name == item.routeName;
            } 
        },
        cargarMensajesAPI() {
            if(sessionStorage.getItem('authRol') != "Administrador")
            {
                RequestHelper.get('/api/bandeja', {
                    success: (data) => {
                        this.mensajesCount = data.mensajes.map((unMensaje) => unMensaje.leido ? 0 : 1);
                        this.mensajesCount = this.mensajesCount.reduce((valorAnterior, valorActual) => valorAnterior + valorActual);
                    },
                    notLoggedIn: () => {
                        this.showLoginModal(true);
                    },
                    forbidden: (error) => {
                        this.errorHandling(error);
                    },
                    empty: () => {
                        this.mensajes = [];
                    },
                    error: (error) => {
                        this.errorHandling(error);
                    }
                });
                if(this.update && !this.updating) {
                    this.updating = true;
                    setTimeout(() => {this.updating = false; this.cargarMensajesAPI()}, this.updateSpeed);
                }
            }
        },
    },
    mounted() {
        this.cargarMensajesAPI();
    }
}
</script>

<style>
#wrapper {
    overflow-x: hidden;
}

#sidebar-wrapper {
    min-height: calc(100vh - 57px);
    margin-left: -16rem;
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
    width: 16rem;
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
        margin-left: -16rem;
    }
    #navbar-organizacion{
        width: 14rem;
    }
}
</style>