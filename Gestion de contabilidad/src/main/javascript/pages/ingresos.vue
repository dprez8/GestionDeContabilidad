<template>
    <div class="border border-top-0">
        <b-overlay variant="primary" spinner-variant="light" :show="ingresosLoading" class="border-bottom">
            <b-table show-empty :tbody-tr-class="rowStyle" class="mb-0 border-0 border-bottom table-row-pointer" head-variant="dark" sort-by="name" :fields="campos_ingresos"
                :items="ingresos"
                @row-clicked="toggleIngreso"
            >
                <template #cell(descripcion)="data">
                    <div class="text-nowrap text-truncate">{{data.item.descripcion}}</div>
                </template>
                <template #cell(montoTotal)="data">
                    <span>{{ "$" + data.item.montoTotal }}</span>
                </template>

                <template #row-details="data">
                    <b-collapse :visible="data.item.showIngreso">
                        <div class="px-2 pb-2 egreso_container">
                            <b-card no-body>
                                <b-card-body class="p-2">
                                    <div v-if="data.item.egresos.length">
                                        Este ingreso esta asociado a 
                                        <template v-for="(egreso, key) in data.item.egresos">
                                            <b-badge class="mr-2 py-1 px-2" :key="key" :to="`/operaciones/egreso/${egreso}`">
                                                Egreso {{egreso}}
                                            </b-badge>
                                        </template>
                                    </div>
                                    <div>
                                        Fecha límite de egresos <b-badge class="mr-2 py-1 px-2">{{data.item.fechaAceptacion}}</b-badge>
                                    </div>
                                </b-card-body>
                            </b-card>
                        </div>
                    </b-collapse>
                </template>

                <template #empty>
                    <h4>No hay ingresos para mostrar</h4>
                </template>
            </b-table>
            <b-button class="m-2" variant="outline-primary" to="/operaciones/ingreso/agregar">
                <b-icon-plus></b-icon-plus>
                Agregar Ingreso
            </b-button>
        </b-overlay>
    </div>
</template>

<script>
import axios from 'axios'
import {convertDate, RequestHelper} from '../util/utils'

export default {
    data() {
        return {
            ingresos: [],
            ingresosLoading: false,
            campos_ingresos: [
                { key: 'name', label: 'Ingreso', thClass:['w-25'], tdClass:[], sortable: true },
                { key: 'descripcion', label: 'Descripcion', thClass:['w-50'], tdClass:['mw-0'] },
                { key: 'montoTotal', label: 'Total', thClass:['text-center'], tdClass:['text-center'], sortable: true },
                { key: 'fechaOperacion', label: 'Fecha Operación', thClass: ['text-center', 'w-25'], tdClass: ['text-center'], sortable: true },
            ]
        }
    },
    inject: ['showLoginModal', 'errorHandling'],
    methods: {
        convertDate: convertDate,
        cargarIngresosAPI(){
            this.ingresosLoading = true;

            RequestHelper.get('/api/operaciones/ingresos', {
                success: (data) => {
                    console.log(data);
                    this.ingresos = data.ingresos.map(this.ingresosAPIConverter);
                    console.log(this.ingresos);
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
                    this.ingresosLoading = false;
                }
            });
        },
        ingresosAPIConverter(ingresoAPI) {
            return {
                id: ingresoAPI.id,
                name: 'Ingreso ' + ingresoAPI.id,
                descripcion: ingresoAPI.descripcion,
                montoTotal: ingresoAPI.montoTotal,
                fechaOperacion: `${ingresoAPI.fechaOperacion.day}/${ingresoAPI.fechaOperacion.month}/${ingresoAPI.fechaOperacion.year}`,
                fechaAceptacion: `${ingresoAPI.fechaAceptacion.day}/${ingresoAPI.fechaAceptacion.month}/${ingresoAPI.fechaAceptacion.year}`,
                egresos: ingresoAPI.egresos,
                _showDetails: true,
                showIngreso: false
            }
        },
        toggleIngreso(row) {
            this.seleccionarIngreso(row.id);
        },
        seleccionarIngreso(id) {
            this.ingresos.forEach(ingreso => {
                if(ingreso.id != id)
                    ingreso.showIngreso = false;
                else
                    ingreso.showIngreso = !ingreso.showIngreso;
            });
        },
        rowStyle(item, type) {
            if(type == 'row') {
                if(item.showIngreso) {
                    return ['bg-secondary', 'text-light'];
                }
            }
            if(type == 'row-details') {
                return ['bg-secondary']
            }
        }
    },
    mounted() {
        this.cargarIngresosAPI();
    }
}
</script>

<style>
    .b-table-details > td {
        padding: 0 !important;
    }
    .mw-0{
        max-width: 0;
    }
    table.table-row-pointer > tbody > tr{
        cursor: pointer;
    }
    table.table-row-pointer > tbody > tr.b-table-details{
        cursor: initial;
    }
</style>