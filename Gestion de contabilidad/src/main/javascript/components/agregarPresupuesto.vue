<template>
    <!-- template para presupuesto -->
    <div style="overflow-x: hidden">
        <transition :name="cargandoProveedor ? 'slide' : 'slide-back'" mode="out-in">
            <div key="0" v-if="!cargandoProveedor">
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
                                    <template>
                                        <b-form-select-option class="text-primary" v-if="(proveedorAAgregar != null)" value="nuevoProveedor">{{ proveedorAAgregar.nombre }} (nuevo)</b-form-select-option>
                                    </template>
                                </b-form-select>
                            </b-overlay>
                        </div>
                    </div>
                    <div class="col px-2 d-flex align-items-end">
                        <b-button block variant="outline-primary" @click="cargandoProveedor = true">
                            <b-icon-plus></b-icon-plus>
                            Cargar Proveedor
                        </b-button>
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
            <div key="1" v-else>
                <h5 class="text-center mb-4">Cargar Proveedor</h5>
                <agregar-proveedor 
                    v-bind:confirmarAccion="confirmarProveedor"
                    v-bind:cancelarAccion="cancelarProveedor"
                ></agregar-proveedor>
            </div>
        </transition>
    </div>
</template>

<script>
import tablaItems from '../components/tablaItems'
import egresos from '../pages/egresos'
import agregarProveedor from '../components/agregarProveedor'
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
                items: []
            },
            cargandoProveedor: false,
            proveedoresSelect: [],
            proveedoresLoading: false,
            proveedorAAgregar: null,
            egresosLoading: false,
            egresosSelect: [],
            falloInput: false
        }
    },
    inject: ['showLoginModal', 'errorHandling', 'createToast'],
    methods: {
        confirmar() {

            var todosLosCamposRellenos = 
                this.presupuesto.proveedor          &&
                this.presupuesto.fechaVigente       &&
                this.presupuesto.items.length > 0

            this.falloInput = !todosLosCamposRellenos;
            if(this.falloInput) {
                return;
            }

            this.crearProveedorAPI();
        },
        crearProveedorAPI() {
            if (this.proveedorAAgregar != null) {
                // Hay que agregar un proveedor antes de agregar el egreso
                console.log("POST '/api/proveedor'");
                console.log(JSON.stringify(this.proveedorAAgregar, null, 4));

                RequestHelper.post('/api/proveedor', this.proveedorAAgregar, {
                    success: (data) => {
                        this.createToast('Guardado exitoso', 'Se creó el proveedor exitosamente', 'success');
                        this.presupuesto.proveedor = data.id;

                        if(this.confirmarAccion != undefined && this.cancelarAccion != undefined) {
                            this.confirmarAccion(this.presupuesto);
                            return;
                        }
                    },
                    notLoggedIn: () => {
                        this.showLoginModal(true);
                    },
                    failed: (data) => {
                        this.createToast('Error', 'No se pudo crear el proveedor', 'danger');
                    },
                    forbidden: (error) => {
                        this.errorHandling(error);
                    },
                    error: (error) => {
                        this.errorHandling(error);
                    },
                });
            } else {
                if(this.confirmarAccion != undefined && this.cancelarAccion != undefined) {
                    this.confirmarAccion(this.presupuesto);
                    return;
                }
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
        confirmarProveedor(data) {
            this.proveedorAAgregar = data;
            console.log(this.proveedorAAgregar.nombre);
            this.presupuesto.proveedor = "nuevoProveedor";
            this.cargandoProveedor = false;
        },
        cancelarProveedor() {
            this.cargandoProveedor = false;
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
        'tabla-items': tablaItems,
        'agregar-proveedor': agregarProveedor
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