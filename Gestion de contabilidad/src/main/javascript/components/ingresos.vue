<template>
    <div class="border border-top-0">
        <b-overlay variant="primary" spinner-variant="light" :show="ingresosLoading" class="border-bottom">
            <b-table show-empty class="mb-0 border-0 border-bottom" head-variant="dark" :fields="campos_ingresos"
                :items="ingresos">
                <template #cell(descripcion)="data">
                    <b-collapse :id="'ingreso-descripcion'+data.index" :visible="true">
                        <div class="text-nowrap text-truncate" v-b-toggle="'ingreso-descripcion'+data.index">\{{data.item.descripcion}}</div>
                    </b-collapse>
                    <b-collapse :id="'ingreso-descripcion'+data.index">
                        <div style="overflow-wrap: break-word" v-b-toggle="'ingreso-descripcion'+data.index">\{{data.item.descripcion}}</div>
                    </b-collapse>
                </template>
                <template #cell(montoTotal)="data">
                    <span>\{{ "$" + data.item.montoTotal }}</span>
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
import {convertDate} from '../util/utils'

export default {
    data() {
        return {
            ingresos: [],
            ingresosLoading: false,
            campos_ingresos: [
                { key: 'name', label: 'Ingreso', thClass:['w-25'], tdClass:[] },
                { key: 'descripcion', label: 'Descripcion', thClass:['w-50'], tdClass:['mw-0'] },
                { key: 'montoTotal', label: 'Total', thClass:['text-center'], tdClass:['text-center'] },
                { key: 'fechaOperacion', label: 'Fecha Operación', thClass: ['text-center', 'w-25'], tdClass: ['text-center'] },
            ]
        }
    },
    methods: {
        cargarIngresosAPI(){
            this.ingresosLoading = true;
            /*
            $.ajax({
                url: "http://{{ ip }}/api/operaciones/ingresos",
                type: "GET",
                dataType: "json",
                context: this,
                cache: false,
                success(response) {
                    if(response.code == 403) {
                        app.showSessionEndedAlert(true);
                    } else if(response.code == 200) {
                        console.log(response);
                        this.ingresos = response.ingresos.map(this.ingresosAPIConverter);
                    } else {
                        app.createCommonErrors(response);
                    }
                },
                error(data) {
                    app.createCommonErrors(data);
                },
                complete() {
                    this.ingresosLoading = false;
                }
            });
            */
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