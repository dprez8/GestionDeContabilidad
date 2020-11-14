<template>
    <!-- template para presupuesto -->
    <div>
        <div class="row mb-4 mx-2" v-if="buscarEgresos">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Egreso</strong></span>
            </div>
            <div class="col px-2" >
                <div class="w-100">
                    <b-collapse :visible="(falloInput)">
                        <b-badge variant="danger">Seleccione un egreso</b-badge>
                    </b-collapse>
                    <b-overlay spinner-variant="primary" :show="egresosLoading" rounded="sm">
                        <b-form-select 
                            :options="egresosSelect"
                            :state="(falloInput) ? false : null"
                            v-model="presupuesto.egreso">
                            <template #first>
                                <b-form-select-option :value="null" disabled>-- Seleccionar un Egreso --</b-form-select-option>
                            </template>
                        </b-form-select>
                    </b-overlay>
                </div>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Fecha Vigente</strong></span>
            </div>
            <div class="col px-2">
                <b-form-datepicker placeholder="Seleccione fecha de vigencia" v-model="presupuesto.fechaVigente" class="mb-2"></b-form-datepicker>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Número operación</strong></span>
            </div>
            <div class="col px-2">
                <b-form-input placeholder="Ingrese número de operacion" v-model="presupuesto.numeroOperacion"></b-form-input>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Items</strong></span>
            </div>
            <div class="col p-2">
                <tabla-items v-bind:actualizarItems="actualizarItems"></tabla-items>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-4">

            </div>
            <div class="col px-2">
                <b-button-group>
                    <b-button variant="primary" @click="confirmar">Confirmar</b-button>
                    <b-button variant="outline-dark" @click="cancelarAccion">Cancelar</b-button>
                </b-button-group>
            </div>
        </div>
    </div>
</template>

<script>
import tablaItems from '../components/tablaItems'
import egresos from '../pages/egresos'
import {RequestHelper} from '../util/utils'

export default {
    props: {
        buscarEgresos: Boolean,
        confirmarAccion: Function,
        cancelarAccion: Function
    },
    data() {
        return {
            presupuesto: {
                egreso: null,
                fechaVigente: null,
                numeroOperacion: null,
                items: []
            },
            egresosLoading: false,
            egresosSelect: [],
            falloInput: false
        }
    },
    inject: ['showLoginModal', 'errorHandling'],
    methods: {
        confirmar() {
            if(this.confirmarAccion != undefined && this.cancelarAccion != undefined) {
                this.presupuesto.items.pop();
                this.confirmarAccion(this.presupuesto);
                return;
            }

            // Hacer algo si no se recibe la funcion confirmarAccion hacer algo default
        },
        actualizarItems(items) {
            this.presupuesto.items = items;
        },
        cargarEgresosAPI(){
            RequestHelper.get('/api/operaciones/egresos', {
                success: (data) => {
                    this.egresosSelect = data.egresos.map(this.egresosAPIConverter);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                    this.egresosLoading = false;
                }
            });
        },
        egresosAPIConverter(egreso) {
            return {
                value: egreso.id,
                text: 'Egreso ' + egreso.id,
                type: egreso
            }
        },
    },
    components: {
        'tabla-items': tablaItems
    },
    watch: {
        'egreso.medioDePago.id': function (medioDePagoId) {

        }
    },
    mounted() {
        //if (!this.egreso)
           this.cargarEgresosAPI();
    }
}
</script>

<style>

</style>