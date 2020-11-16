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
                <span class="mr-2"><strong>Proveedor</strong></span>
            </div>
            <div class="col px-2 d-flex align-items-end">
                <div class="w-100">
                    <b-collapse :visible="(!presupuesto.proveedor && falloInput)">
                        <b-badge variant="danger">Seleccione un proveedor</b-badge>
                    </b-collapse>
                    <b-overlay spinner-variant="primary" :show="proveedoresLoading" rounded="sm">
                        <b-form-select
                            :options="proveedoresSelect" 
                            :state="(!presupuesto.proveedor && falloInput) ? false : null"
                            v-model="presupuesto.proveedor">
                            <template #first>
                                <b-form-select-option :value="null" disabled>-- Selecciona un Proveedor --</b-form-select-option>
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
                <b-collapse :visible="(!presupuesto.fechaVigente && falloInput)">
                    <b-badge variant="danger">Seleccione una fecha</b-badge>
                </b-collapse>
                <b-form-datepicker 
                    :state="(!presupuesto.fechaVigente && falloInput) ? false : null"
                    placeholder="Seleccione fecha de vigencia" v-model="presupuesto.fechaVigente" class="mb-2"
                ></b-form-datepicker>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Número operación</strong></span>
            </div>
            <div class="col px-2">
                <b-collapse :visible="(!presupuesto.numeroOperacion && falloInput)">
                    <b-badge variant="danger">Ingrese el número de operación</b-badge>
                </b-collapse>
                <b-form-input 
                    :state="(!presupuesto.fechaVigente && falloInput) ? false : null"
                    placeholder="Ingrese número de operacion" v-model="presupuesto.numeroOperacion"
                ></b-form-input>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Items</strong></span>
            </div>
            <div class="col p-2">
                <tabla-items :itemsReadOnly="itemsReadOnly" :actualizarItems="actualizarItems"></tabla-items>
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
        itemsReadOnly: Array,
        buscarEgresos: Boolean,
        confirmarAccion: Function,
        cancelarAccion: Function
    },
    data() {
        return {
            presupuesto: {
                proveedor: null,
                egreso: null,
                fechaVigente: null,
                numeroOperacion: null,
                items: []
            },
            proveedoresSelect: [],
            proveedoresLoading: false,
            egresosLoading: false,
            egresosSelect: [],
            falloInput: false
        }
    },
    inject: ['showLoginModal', 'errorHandling'],
    methods: {
        confirmar() {

            var todosLosCamposRellenos = 
                this.presupuesto.proveedor          &&
                this.presupuesto.fechaVigente       &&
                this.presupuesto.numeroOperacion    &&
                this.presupuesto.items.length > 1

            this.falloInput = !todosLosCamposRellenos;
            if(this.falloInput) {
                return;
            }

            if(this.confirmarAccion != undefined && this.cancelarAccion != undefined) {
                this.confirmarAccion(this.presupuesto);
                return;
            }
        },
        actualizarItems(items) {
            this.presupuesto.items = items.map((unItem) => { 
                return {
                    itemEgreso: unItem.id,
                    precio: unItem.precio
                } 
            });
        },
        cargarProveedoresAPI() {
            this.proveedoresLoading = true;

            RequestHelper.get('/api/proveedores', {
                success: (data) => {
                    this.proveedoresSelect = data.data.map(this.proveedoresAPIConverter);
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
                    this.proveedoresLoading = false;
                }
            });
        },
        proveedoresAPIConverter(proveedor) {
            return {
                value: proveedor.id,
                text: proveedor.nombre
            }
        },
        cargarEgresosAPI(){
            RequestHelper.get('/api/operaciones/egresos', {
                success: (data) => {
                    this.egresosSelect = data.egresos.map(this.egresosAPIConverter);
                },
                notLoggedIn: () => {
                    this.showLoginModal(false);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                forbidden: (error) => {
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
        this.cargarProveedoresAPI();
    }
}
</script>

<style>

</style>