<template>
    <b-overlay class="container-fluid h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="row h-100">
            <div class="col-5 col-lg-4 col-xl-3 h-100 border-right p-0">
                <div class="w-100 h-100 d-flex flex-column">
                    <div class="flex-shrink-0 border-bottom">
                        <div class="d-flex align-items-center p-1">
                            <div class="w-100 pl-3 text-truncate">{{mensajesFiltradosOrdenados.length}} Mensajes Filtrados</div>
                            <b-button-group>
                                <b-button squared variant="outline" v-b-tooltip.hover.top title="Filtrar y Ordenar mensajes" v-b-toggle.filter-sort><b-icon-sort-down></b-icon-sort-down></b-button>
                            </b-button-group>
                        </div>
                        <b-collapse id="filter-sort">
                            <div class="border-top p-3" style="background-color: #eee;">
                                <b-form-group label="Ordenar por">
                                    <b-form-select v-model="ordenar">
                                        <b-form-select-option :value="null" selected>Sin ordenar</b-form-select-option>
                                        <b-form-select-option value="fecha">Fecha</b-form-select-option>
                                    </b-form-select>
                                </b-form-group>
                                <b-form-group label="Filtrar por" class="m-0">
                                    <b-form-radio v-model="filtrar" :value="null">Ambos</b-form-radio>
                                    <b-form-radio v-model="filtrar" value="leidos">Leidos</b-form-radio>
                                    <b-form-radio v-model="filtrar" value="no leidos">No Leidos</b-form-radio>
                                </b-form-group>
                            </div>
                        </b-collapse>
                    </div>
                    <div class="d-flex h-100 justify-content-center align-items-center" v-if="!mensajesFiltradosOrdenados.length">
                        <div class="text-secondary text-center">
                            <h1><b-icon-emoji-sunglasses></b-icon-emoji-sunglasses></h1>
                            <p>La bandeja de mensajes esta vacía</p>
                        </div>
                    </div>
                    <b-list-group flush v-else class="overflow-auto h-100 p-0">
                        <b-list-group-item class="d-flex justify-content-between align-items-center"
                            v-for="mensaje in mensajesFiltradosOrdenados"
                            v-bind:key="mensaje.id"
                            v-bind:to="('/bandeja/' + mensaje.id)"
                            v-bind:active="(mensaje.id == selected)"
                        >
                            <span class="d-inline-block text-truncate">{{ mensaje.cuerpo }}</span>
                            <b-badge pill variant="success" class="ml-1 mr-1" v-if="!mensaje.leido">nuevo</b-badge>
                            <b-badge pill variant="light">{{ convertDate(mensaje.fechaCreacion) }}</b-badge>
                        </b-list-group-item>
                    </b-list-group>
                </div>
            </div>
            <div class="col overflow-auto h-100 p-0">
                <transition name="fade" mode="out-in">
                    <div class="d-flex h-100 justify-content-center align-items-center" v-if="!getMessageSelected()">
                        <div class="text-secondary text-center">
                            <p>Seleccione algún mensaje para verlo aquí</p>
                        </div>
                    </div>
                    <div class="container-fluid" v-bind:key="selected" v-else>
                        <div class="row">
                            <div class="d-flex bg-primary justify-content-between align-items-center col-12 p-3">
                                <h4 class="text-light m-0">Mensaje {{getMessageSelected().id}}</h4>
                                <b-badge pill variant="primary">{{ convertDate(getMessageSelected().fechaCreacion) }}</b-badge>
                            </div>
                            <div class="col p-3 text-justify">
                                {{ getMessageSelected().cuerpo }}
                            </div>
                        </div>
                    </div>
                </transition>
            </div>
        </div>
        <template #overlay>
            <div class="d-flex align-items-center">
                <b-spinner variant="light" class="flex-shrink-0"></b-spinner>
                <span class="text-light ml-4">Cargando mensajes...</span>
            </div>
        </template>
    </b-overlay>
</template>

<script>
import axios from 'axios'
import {convertDate, RequestHelper} from '../util/utils.js'
export default {
    data() {
        return {
            mensajes: [],
            mensajesFiltradosOrdenados: [],
            selected: null,
            loading: true,
            ordenar: null,
            filtrar: null
        }
    },
    inject: ['errorHandling', 'showLoginModal'],
    methods: {
        convertDate: convertDate,
        seleccionarMensaje() {
            var id = this.$route.params.id;
            this.selected = id;
            this.marcarVistoAPI();
        },
        marcarVistoAPI() {
            if(this.getMessageSelected() == null) {
                return;
            }

            this.getMessageSelected().leido = true;

            var mensajeToSend = {
                id: this.getMessageSelected().id
            }

            RequestHelper.post('/api/bandeja/visto', mensajeToSend, {
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.errorHandling(error);
                }
            })
        },
        cargarMensajesAPI() {
            this.loading = true;

            RequestHelper.get('/api/bandeja', {
                success: (data) => {
                    this.mensajes = data.mensajes;
                    this.ordenarFiltrarMensajes();
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                empty: () => {
                    this.mensajes = [];
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                    this.loading = false;
                }
            })
        },
        getMessageSelected() {
            var mensajeSeleccionado = null;
            for(var i = 0; i < this.mensajes.length; i++) {
                if(this.mensajes[i].id == this.selected) {
                    mensajeSeleccionado = this.mensajes[i];
                }
            }
            return mensajeSeleccionado;
        },
        ordenarPorFecha(mensajeA, mensajeB) {
            if (mensajeA.fechaCreacion > mensajeB.fechaCreacion)
                return -1;
            if (mensajeA.fechaCreacion < mensajeB.fechaCreacion)
                return 1;
            return 0;
        },
        ordenarPorNoLeidos(mensajeA, mensajeB) {
            if (mensajeA.leido < mensajeB.leido)
                return -1;
            if (mensajeA.leido > mensajeB.leido)
                return 1;
            return 0;
        },
        ordenarPorId(mensajeA, mensajeB) {
            if (mensajeA.id < mensajeB.id)
                return -1;
            if (mensajeA.id > mensajeB.id)
                return 1;
            return 0;
        },
        filtrarSoloLeidos(mensaje) {
            return mensaje.leido;
        },
        filtrarSoloNoLeidos(mensaje) {
            return !mensaje.leido;
        },
        ordenarFiltrarMensajes() {
            var filtro = function(){return 1};
            var ordenador = this.ordenarPorId;

            switch(this.filtrar) {
                case "leidos":
                    filtro = this.filtrarSoloLeidos;
                    break;
                case "no leidos":
                    filtro = this.filtrarSoloNoLeidos;
                    break;
            }
            switch(this.ordenar) {
                case "fecha":
                    ordenador = this.ordenarPorFecha;
                    break;
                case "no leidos":
                    ordenador = this.ordenarPorNoLeidos;
                    break;
            }

            var bandejaFiltrada = this.mensajes.filter(filtro);
            var bandejaOrdenada = bandejaFiltrada.sort(ordenador);

            this.mensajesFiltradosOrdenados = bandejaOrdenada;
        }
    },
    watch: {
        $route(to, from) {
            this.seleccionarMensaje()
        },
        ordenar() {
            this.ordenarFiltrarMensajes()
        },
        filtrar() { 
            this.ordenarFiltrarMensajes()
        }
    },
    mounted() {
        this.cargarMensajesAPI();
        this.seleccionarMensaje();
    }
}
</script>

<style>

</style>