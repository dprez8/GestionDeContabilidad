<template>
    <b-overlay class="w-100 h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="p-4 d-flex justify-content-center" style="background: #eee; min-height: 100%">
            <div class="w-100" style="max-width: 768px;">
                <template v-for="(entidad, key) in entidades">
                    <b-card :key="key" no-body class="overflow-hidden mb-4">
                        <b-row no-gutters>
                            <b-col md="2" class="d-flex justify-content-center align-items-center p-2 entity-card-skew">
                                <b-avatar size="5rem" variant="secondary" icon="bar-chart-fill"></b-avatar>
                            </b-col>
                            <b-col>
                                <b-card-body v-if="entidad.tipo == 'Jurídica'" :title="`${entidad.nombreFicticio}`" :sub-title="`Entidad ${entidad.tipo}`">
                                    <b-row no-gutters class="mb-2">
                                        <b-col md="4">
                                            <b-card-text class="mr-2">
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
                                            <b-card-text class="mr-2">
                                                <div class="text-truncate">
                                                    <span v-b-tooltip.html :title="`Dirección Postal<br><em>${organizacionDireccionPostal(entidad)}`">
                                                        <b-icon-globe class="mr-2"/>{{organizacionDireccionPostal(entidad)}}
                                                    </span>
                                                </div>
                                                <div class="text-truncate">
                                                    <span v-b-tooltip.html :title="`Actividad<br><em>${entidad.tipoEntidadJuridica.actividad}`">
                                                        <b-icon-tools class="mr-2"/>{{entidad.tipoEntidadJuridica.actividad}}
                                                    </span>
                                                </div>
                                            </b-card-text>
                                        </b-col>
                                        <b-col md="4">
                                            <b-card-text>
                                                <div class="text-truncate">
                                                    <span v-b-tooltip.html :title="`Cantidad de personal<br><em>${entidad.tipoEntidadJuridica.cantidadDePersonal}`">
                                                        <b-icon-person-lines-fill class="mr-2"/>{{entidad.tipoEntidadJuridica.cantidadDePersonal}}
                                                    </span>
                                                </div>
                                                <div class="text-truncate">
                                                    <span v-b-tooltip.html :title="`Promedio de ventas anuales<br><em>${ventasAnualesFormat(entidad.tipoEntidadJuridica.ventasAnuales)}`">
                                                        <b-icon-cash-stack class="mr-2"/>{{ventasAnualesFormat(entidad.tipoEntidadJuridica.ventasAnuales)}}
                                                    </span>
                                                </div>
                                            </b-card-text>
                                        </b-col>
                                    </b-row>
                                </b-card-body>
                                <b-card-body v-else :title="`${entidad.nombreFicticio}`" :sub-title="`Entidad ${entidad.tipo}`">
                                </b-card-body>
                            </b-col>
                        </b-row>
                    </b-card>
                </template>
            </div>
        </div>
    </b-overlay>
</template>

<script>
import {RequestHelper} from '../util/utils'

export default {
    data() {
        return {
            entidades: [],
            loading: false
        }
    },
    inject: ["errorHandling", "showLoginModal"],
    methods: {
        cargarEntidadesAPI() {
            this.loading = true;

            RequestHelper.get('/api/admin/entidades', {
                success: (data) => {
                    // Cargo las entidades jurídicas y despues las base
                    this.entidades = data.entidadesJuridicas.map((juridica) => {
                        juridica.tipo = "Jurídica";
                        return juridica;
                    });

                    data.entidadesJuridicas.forEach((juridica) => {
                        juridica.entidadesBase.forEach((base) => {
                            base.tipo = "Base";
                            this.entidades.push(base);
                        })
                    })

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
        organizacionDireccionPostal(entidad) {
            return `
            ${entidad.calle} ${entidad.altura}
            ${entidad.ciudad ? ',' + entidad.ciudad : ''} 
            ${entidad.provincia ? ',' + entidad.provincia : ''} 
            ${entidad.pais ?  ',' + entidad.pais : ''}`;
        }
    },
    mounted() {
        this.cargarEntidadesAPI();
    }
}
</script>

<style>
.entity-card-skew::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: var(--success);
    clip-path: polygon(0% 0%, 75% 0%, 25% 100%, 0% 100%);
    transition: all .2s;
    animation: .5s intro-entity;
}
@keyframes intro-entity {
    from {
        clip-path: polygon(0% 0%, 100% 0%, 100% 100%, 0% 100%);
    }
    to {
        clip-path: polygon(0% 0%, 75% 0%, 25% 100%, 0% 100%);
    }
}
</style>