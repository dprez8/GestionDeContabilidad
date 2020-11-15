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
                            <b-col>
                                <b-card-body class="d-flex h-100 justify-content-end align-items-end">
                                    <b-button variant="primary" size="sm" class="mr-2"><b-icon-pencil class="mr-2"/>Editar</b-button>
                                    <b-button variant="danger" size="sm"><b-icon-x class="mr-2"/>Borrar</b-button>
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
export default {
    data() {
        return {
            entidades: [
                {
                    id: 1,
                    nombreFicticio: "Oficina Central Buenos Aires",
                    razonSocial: "EAAF BA",
                    cuit: "30-15269857-2",
                    direccionPostal: "Av. Medrano 951, Almagro, CABA, BA, Argentina",
                    tipo: "Jurídica",
                    cantPersonal: 150,
                    actividad: "Construcción",
                    promVentasAnuales: 600000000.0
                }
            ],
            loading: false
        }
    },
    methods: {
        ventasAnualesFormat(prom) {
            return `$ ${prom.toLocaleString()}`;
        }
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