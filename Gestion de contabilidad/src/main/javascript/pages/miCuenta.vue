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
                <b-card no-body class="mb-2">
                    <b-card-body class="py-2">Entidades</b-card-body>
                </b-card>
                <b-card no-body class="overflow-hidden mb-4">
                    <b-row no-gutters>
                        <b-col md="2" class="d-flex justify-content-center align-items-center p-2 entity-card-skew">
                            <b-avatar size="5rem" variant="secondary" icon="bar-chart-fill"></b-avatar>
                        </b-col>
                        <b-col>
                            <b-card-body :title="`${entidad.nombreFicticio}`" :sub-title="`Entidad ${entidad.tipo}`">
                                <b-row no-gutters class="mb-2">
                                    <b-col md="4">
                                        <b-card-text>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Razon Social<br><em>${entidad.razonSocial}`">
                                                    <b-icon-card-text class="mr-2"/>{{entidad.razonSocial}}
                                                </span>
                                            </div>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`CUIT<br><em>${entidad.cuit}`">
                                                    <b-icon-credit-card2-front class="mr-2"/>{{entidad.cuit}}
                                                </span>
                                            </div>
                                        </b-card-text>
                                    </b-col>
                                    <b-col md="4">
                                        <b-card-text>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Dirección Postal<br><em>${entidad.direccionPostal}`">
                                                    <b-icon-globe class="mr-2"/>{{entidad.direccionPostal}}
                                                </span>
                                            </div>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Actividad<br><em>${entidad.actividad}`">
                                                    <b-icon-tools class="mr-2"/>{{entidad.actividad}}
                                                </span>
                                            </div>
                                        </b-card-text>
                                    </b-col>
                                    <b-col md="4">
                                        <b-card-text>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Cantidad de personal<br><em>${entidad.cantPersonal}`">
                                                    <b-icon-person-lines-fill class="mr-2"/>{{entidad.cantPersonal}}
                                                </span>
                                            </div>
                                            <div class="text-truncate">
                                                <span v-b-tooltip.html :title="`Promedio de ventas anuales<br><em>${ventasAnualesFormat(entidad.promVentasAnuales)}`">
                                                    <b-icon-cash-stack class="mr-2"/>{{ventasAnualesFormat(entidad.promVentasAnuales)}}
                                                </span>
                                            </div>
                                        </b-card-text>
                                    </b-col>
                                </b-row>
                            </b-card-body>
                        </b-col>
                    </b-row>
                </b-card>
                <b-card title="Cambiar contraseña">
                    <div class="mt-2">Contraseña actual</div>
                    <b-form-input type="password" placeholder="Ingrese su contraseña"></b-form-input>
                    <div class="mt-2">Nueva contraseña</div>
                    <b-form-input type="password" placeholder="Ingrese nueva contraseña"></b-form-input>
                    <div class="mt-2">Repita la nueva contraseña</div>
                    <b-form-input type="password" placeholder="Repita contraseña"></b-form-input>
                    <div class="mt-2">
                        <b-button variant="primary">Confirmar</b-button>
                    </div>
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
            entidad: {
                id: 1,
                nombreFicticio: "Oficina Central Buenos Aires",
                razonSocial: "EAAF BA",
                cuit: "30-15269857-2",
                direccionPostal: "Av. Medrano 951, Almagro, CABA, BA, Argentina",
                tipo: "Jurídica",
                cantPersonal: 150,
                actividad: "Construcción",
                promVentasAnuales: 600000000.0
            },
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
                    this.usuario.email = data.gmail; // ACTUALIZAR
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

            RequestHelper.get('/api/usuario/organizaciones', {
                success: (data) => {
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
    mounted() {
        this.cargarDatosAPI();
    }
}
</script>

<style>

</style>