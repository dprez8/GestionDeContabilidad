<template>
    <b-overlay class="w-100 h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="p-4 d-flex justify-content-center" style="background: #eee; min-height: 100%">
            <div class="w-100" style="max-width: 768px;">
                <b-card no-body class="mb-2">
                    <b-card-body class="py-2">Datos personales</b-card-body>
                </b-card>
                <b-card no-body class="overflow-hidden mb-4">
                    <b-row no-gutters>
                        <b-col md="2" class="d-flex justify-content-center align-items-center p-2 user-card-skew">
                            <b-avatar size="5rem" variant="secondary"></b-avatar>
                        </b-col>
                        <b-col>
                            <b-card-body :title="`${usuario.nombre} ${usuario.apellido}`">
                                <b-card-text>
                                    <div><b-icon-person class="mr-2"/>{{usuario.username}}</div>
                                    <div><b-icon-envelope class="mr-2"/>{{usuario.email}}</div>
                                </b-card-text>
                            </b-card-body>
                        </b-col>
                    </b-row>
                </b-card>
                <b-card v-if="organizacion" no-body class="mb-2">
                    <b-card-body class="py-2">Organización</b-card-body>
                </b-card>
                <b-card v-if="organizacion" no-body class="overflow-hidden mb-4">
                    <b-row no-gutters>
                        <b-col md="2" class="d-flex justify-content-center align-items-center p-2 entity-card-skew">
                            <b-avatar size="5rem" variant="secondary" icon="bar-chart-fill"></b-avatar>
                        </b-col>
                        <b-col>
                            <b-card-body v-if="organizacion.tipo == 'Jurídica'" :title="`${organizacion.nombreFicticio}`" :sub-title="`Entidad ${organizacion.tipo}`">
                                <b-row no-gutters class="mb-2">
                                    <b-col md="4">
                                        <b-card-text>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Razon Social<br><em>${organizacion.razonSocial}`">
                                                    <b-icon-card-text class="mr-2"/>{{organizacion.razonSocial}}
                                                </span>
                                            </div>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`CUIT<br><em>${organizacion.cuit}`">
                                                    <b-icon-credit-card2-front class="mr-2"/>{{organizacion.cuit}}
                                                </span>
                                            </div>
                                        </b-card-text>
                                    </b-col>
                                    <b-col md="4">
                                        <b-card-text>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Dirección Postal<br><em>${organizacionDireccionPostal}`">
                                                    <b-icon-globe class="mr-2"/>{{organizacionDireccionPostal}}
                                                </span>
                                            </div>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Actividad<br><em>${organizacion.tipoEntidadJuridica.actividad}`">
                                                    <b-icon-tools class="mr-2"/>{{organizacion.tipoEntidadJuridica.actividad}}
                                                </span>
                                            </div>
                                        </b-card-text>
                                    </b-col>
                                    <b-col md="4">
                                        <b-card-text>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Cantidad de personal<br><em>${organizacion.tipoEntidadJuridica.cantidadDePersonal}`">
                                                    <b-icon-person-lines-fill class="mr-2"/>{{organizacion.tipoEntidadJuridica.cantidadDePersonal}}
                                                </span>
                                            </div>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Promedio de ventas anuales<br><em>${ventasAnualesFormat(organizacion.tipoEntidadJuridica.ventasAnuales)}`">
                                                    <b-icon-cash-stack class="mr-2"/>{{ventasAnualesFormat(organizacion.tipoEntidadJuridica.ventasAnuales)}}
                                                </span>
                                            </div>
                                        </b-card-text>
                                    </b-col>
                                </b-row>
                            </b-card-body>
                            <b-card-body v-else :title="`${organizacion.nombreFicticio}`" :sub-title="`Entidad ${organizacion.tipo}`">
                            </b-card-body>
                        </b-col>
                    </b-row>
                </b-card>
            </div>
        </div>
    </b-overlay>
</template>

<script>
import {RequestHelper} from '../util/utils'
export default {
    data() {
        return {
            usuario: {
                nombre: "",
                apellido: "",
                username: "",
                email: "",
            },
            organizacion: null,
            loading: false
        }
    },
    inject: ['showLoginModal', 'errorHandling', 'createToast'],
    methods: {
        cargarDatosAPI() {
            this.loading = true;

            RequestHelper.get('/api/auth/me', {
                success: (data) => {
                    this.usuario.username = data.username;
                    this.usuario.nombre = data.nombre;
                    this.usuario.apellido = data.apellido;
                    this.usuario.email = data.email;
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
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
            });

            RequestHelper.get('/api/entidades', {
                success: (data) => {
                    
                    if(data.organizacion.tipoEntidadJuridica) {
                        // ES una entidad Jurídica
                        this.organizacion = data.organizacion;
                        this.organizacion.tipo = "Jurídica";
                    } else {
                        // ES una entidad Base
                        this.organizacion = data.organizacion;
                        this.organizacion.tipo = "Base";
                    }
                    // DATOS INCOMPLETOS

                    //this.usuario.username = data.nombre;
                    //this.entidad = data.organizacion;
                    console.log(data);
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
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
            });
        },
        ventasAnualesFormat(prom) {
            return `$ ${prom.toLocaleString()}`;
        },
    },
    computed: {
        organizacionDireccionPostal() {
            return `
            ${this.organizacion.calle} ${this.organizacion.altura}
            ${this.organizacion.ciudad ? ',' + this.organizacion.ciudad : ''} 
            ${this.organizacion.provincia ? ',' + this.organizacion.provincia : ''} 
            ${this.organizacion.pais ?  ',' + this.organizacion.pais : ''}`;
        }
    },
    mounted() {
        this.cargarDatosAPI();
    }
}
</script>

<style>

</style>