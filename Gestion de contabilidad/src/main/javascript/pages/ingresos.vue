<template>
    <div class="border border-top-0">
        <b-overlay variant="primary" spinner-variant="light" :show="ingresosLoading" class="border-bottom">
            <b-table show-empty class="mb-0 border-0 border-bottom" head-variant="dark" sort-by="name" :fields="campos_ingresos"
                :items="ingresos">
                <template #cell(descripcion)="data">
                    <b-collapse :id="'ingreso-descripcion'+data.index" :visible="true">
                        <div class="text-nowrap text-truncate" v-b-toggle="'ingreso-descripcion'+data.index">{{data.item.descripcion}}</div>
                    </b-collapse>
                    <b-collapse :id="'ingreso-descripcion'+data.index">
                        <div style="overflow-wrap: break-word" v-b-toggle="'ingreso-descripcion'+data.index">{{data.item.descripcion}}</div>
                    </b-collapse>
                </template>
                <template #cell(montoTotal)="data">
                    <span>{{ "$" + data.item.montoTotal }}</span>
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
        cargarIngresosAPI(){
            this.ingresosLoading = true;

            RequestHelper.get('/api/operaciones/ingresos', {
                success: (data) => {
                    this.ingresos = data.ingresos.map(this.ingresosAPIConverter);
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                    this.ingresosLoading = false;
                }
            });

            // axios
            //     .get(`/api/operaciones/ingresos`)
            //     .then(response => {
            //         var data = response.data;
            //         console.log(data);

            //         if(data.code == 200) {
            //             this.ingresos = data.ingresos.map(this.ingresosAPIConverter);
            //         } else if (data.code == 403) {
            //             this.showLoginModal(true);
            //         }
            //     })
            //     .catch(error => {
            //         this.errorHandling(error);
            //     })
            //     .then(() => {
            //         // always
            //         this.ingresosLoading = false;
            //     })
        },
        ingresosAPIConverter(ingresoAPI) {
            return {
                id: ingresoAPI.id,
                name: 'Ingreso ' + ingresoAPI.id,
                descripcion: ingresoAPI.descripcion,
                montoTotal: ingresoAPI.montoTotal,
                fechaOperacion: convertDate(ingresoAPI.fechaOperacion),
                _showDetails: true,
                showEgreso: false
            }
        }
    },
    mounted() {
        this.cargarIngresosAPI();
    }
}
</script>

<style>

</style>