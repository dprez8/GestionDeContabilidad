<template>
    <div class="px-2 pb-2 egreso_container">
        <b-card v-if="egreso">
            <b-overlay spinner-variant="light" variant="primary" rounded="sm" :show="egresoLoading" no-wrap></b-overlay>
            <div class="row m-0">
                <div class="col p-0 pr-1">
                    <b-list-group>
                        <b-list-group-item class="p-2 text-center" :class="{'bg-secondary': showDatosProveedor, 'text-light': showDatosProveedor}" @click="showDatosProveedor = !showDatosProveedor" button><strong>Proveedor</strong> {{ egreso.proveedor.nombre }}</b-list-group-item>
                        <b-list-group-item class="p-0 overflow-hidden">
                            <b-collapse :visible="showDatosProveedor">
                                <b-list-group flush>
                                    <b-list-group-item class="p-2"><strong>Calle: </strong>{{ egreso.proveedor.calle }}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Altura: </strong>{{ egreso.proveedor.altura }}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Piso: </strong>{{ egreso.proveedor.piso }}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Codigo Postal: </strong>{{ egreso.proveedor.zipcode }}</b-list-group-item>
                                </b-list-group>
                            </b-collapse>
                        </b-list-group-item>
                    </b-list-group>
                </div>
                <div class="col p-0 pl-1">
                    <b-list-group>
                        <b-list-group-item class="p-2 text-center" :class="{'bg-secondary': showDatosFactura, 'text-light': showDatosFactura}" @click="showDatosFactura = !showDatosFactura" button><strong>Documento</strong> {{ egreso.documento.tipo.nombreTipoDeDocumento }}</b-list-group-item>
                        <b-list-group-item class="p-0 overflow-hidden">
                            <b-collapse :visible="showDatosFactura">
                                <b-list-group flush>
                                    <b-list-group-item class="p-2"><strong>Fecha Pedido: </strong>{{ convertDate(egreso.documento.fechaDePedido) }}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Fecha Entrega: </strong>{{ convertDate(egreso.documento.fechaDeEntrega) }}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Descripcion: </strong>{{ egreso.documento.descripcion }}</b-list-group-item>
                                </b-list-group>
                            </b-collapse>
                        </b-list-group-item>
                    </b-list-group>
                </div>
            </div>
            <div class="row m-0">
                <div class="col p-0 pt-2">
                    <b-list-group>
                        <b-list-group-item class="p-2"><strong>Medio de pago: </strong>{{ egreso.pago.medioDePago.medioDePago }} {{ egreso.pago.codigoAsociado }}</b-list-group-item>
                    </b-list-group>
                </div>
            </div>
            <div class="row m-0 pt-2">
                <div class="col p-0 rounded overflow-hidden border">
                    <b-table borderless foot class="m-0 border-0"
                        :fields="campos_items" 
                        :items="egreso.items"
                    >
                        <template #cell(precio)="data">
                            <span>{{ "$" + data.item.precio }}</span>
                        </template>
                    </b-table>
                </div>
            </div>
            <div class="row m-0 pt-2" v-if="egreso.ingresoAsociado">
                <div class="col p-0">
                    <b-list-group>
                        <b-list-group-item class="p-2 text-center" :class="{'bg-secondary': showIngreso, 'text-light': showIngreso}" @click="showIngreso = !showIngreso" button><strong>Ingreso</strong></b-list-group-item>
                        <b-list-group-item class="p-0 overflow-hidden">
                            <b-collapse :visible="showIngreso">
                                <b-list-group flush>
                                    <b-list-group-item class="p-2"><strong>Fecha Operacion</strong> {{convertDate(egreso.ingresoAsociado.fechaOperacion)}}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Descripcion</strong> {{egreso.ingresoAsociado.descripcion}}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Total:</strong> {{'$'+egreso.ingresoAsociado.montoTotal}}</b-list-group-item>
                                </b-list-group>
                            </b-collapse>
                        </b-list-group-item>
                    </b-list-group>
                </div>
            </div>
            <div class="row m-0">
                <div class="col p-0 pt-2">
                    <b-collapse :visible="falloCarga">
                        <b-badge variant="danger">{{falloCargaDetalles}}</b-badge>
                    </b-collapse>
                    <b-button-toolbar>
                        <b-button-group size="sm">
                            <template v-if="!egreso.ingresoAsociado">
                                <b-button v-b-modal.modal-asociar-ingreso variant="outline-secondary">Asociar a Ingreso</b-button>
                            </template>
                            <template v-if="!egreso.categorias.length">
                                <b-button v-b-modal.modal-asociar-categoria variant="outline-secondary">Asociar a categorias</b-button>
                            </template>
                            <!--
                            <b-button>Ver Presupuestos asociados</b-button>
                            <b-button variant="danger">Suscribirse</b-button>
                            -->
                        </b-button-group>
                    </b-button-toolbar>
                </div>
            </div>
            <template v-if="egreso.categorias">
                <template v-for="(categoria, index) in egreso.categorias">
                    <b-badge :key="index" class="mr-1">{{categoria.descripcion}}</b-badge>
                </template>
            </template>
        </b-card>
        <div v-else>
            
        </div>

        <b-modal id="modal-asociar-ingreso" hide-footer scrollable centered title="Asociar a Ingreso">
            <asociar-ingreso
                v-bind:confirmarAccion="confirmarAsociarIngreso"
                v-bind:cancelarAccion="cancelarAsociarIngreso"
            ></asociar-ingreso>
        </b-modal>

        <b-modal id="modal-asociar-categoria" hide-footer scrollable centered title="Asociar a Categoría">
            <asociar-categoria
                v-bind:confirmarAccion="confirmarAsociarCategorias"
                v-bind:cancelarAccion="cancelarAsociarCategorias"
            ></asociar-categoria>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
import {convertDate} from '../util/utils'
import asociarCategoria from './asociarCategoria'
import asociarIngreso from './asociarIngreso'

export default {
    props: {
        egresoLoaded: Function
    },
    template: `{{> egreso/egresoHTML.hbs}}`,
    data() {
        return {
            egreso: null,
            ingresos: [],
            showDatosProveedor: false,
            showDatosFactura: false,
            showIngreso: false,
            showPresupuesto: false,
            campos_items: [
                {
                    key: 'producto',
                    label: 'Producto',
                    tdClass: ['w-100'],
                    thClass: []
                },
                {
                    key: 'cantidad',
                    label: 'Cantidad',
                    tdClass: ['text-center'],
                    thClass: ['text-center']
                },
                {
                    key: 'precio',
                    label: 'Precio',
                    tdClass: [],
                    thClass: []
                }
            ],
            egresoLoading: false,
            falloCarga: false,
            falloCargaDetalles: ""
        }
    },
    inject: ['showLoginModal', 'errorHandling'],
    methods: {
        convertDate: convertDate,
        cargarEgresoAPI() {
            var id = this.$route.params.id;
            if(id == undefined)
                return;

            axios
                .get(`/api/operaciones/egreso/${id}`)
                .then(response => {
                    var data = response.data;
                    console.log(data);
                    if(data.code == 200) {
                        this.egreso = data.egreso;
                        this.egreso.items = data.egreso.items.map(this.itemEgresoAPIConverter);
                    } else if (data.code == 403) {
                        this.showLoginModal(true);
                    }
                })
                .catch(error => {
                    this.errorHandling(error);
                })
                .then(() => {
                    // allways
                    this.egresoLoaded();
                })
        },
        itemEgresoAPIConverter(item) {
            return {
                producto: item.producto.nombreProducto,
                cantidad: item.cantidad,
                precio: item.precio
            }
        },
        // Ingreso
        confirmarAsociarIngreso(data) {

            this.falloCarga = false;

            this.$bvModal.hide('modal-asociar-ingreso');
            console.log(data);

            var ingreso = data;

            if(ingreso) {
                this.egresoLoading = true;

                var request = {
                    egresoId: this.egreso.id,
                    ingresoId: ingreso.id
                }

                console.log("POST '/api/operaciones/asociarManualmente'");
                console.log(JSON.stringify(request, null, 4));

                axios
                    .post(`/api/operaciones/asociarManualmente`, request)
                    .then(response => {
                        var data = response.data;
                        console.log(data);
                        if(data.code == 200) {
                            this.cargarEgresoAPI();
                        } else if (data.code == 403) {
                            this.showLoginModal(true);
                        } else if (data.code == 400) {
                            this.falloCarga = true;
                            this.falloCargaDetalles = data.message;
                        }
                    })
                    .catch(error => {
                        this.falloCarga = true;
                        this.errorHandling(error);
                    })
                    .then(() => {
                        // allways
                        this.egresoLoading = false;
                    })
            } 

        },
        cancelarAsociarIngreso() {
            this.$bvModal.hide('modal-asociar-ingreso');
        },
        // Categorias
        confirmarAsociarCategorias(data) {
            this.falloCarga = false;

            this.$bvModal.hide('modal-asociar-categoria');
            console.log(data);

            var categorias = data;

            if(categorias) {
                
                this.egresoLoading = true;

                var request = {
                    id: this.egreso.id,
                    categorias: categorias.map(function(categoria){
                        return categoria.id;
                    }),
                }

                console.log("POST '/api/categorias/asociar'");
                console.log(JSON.stringify(request, null, 4));

                axios
                    .post(`/api/categorias/asociar`, request)
                    .then(response => {
                        var data = response.data;
                        console.log(data);
                        if(data.code == 200) {
                            this.cargarEgresoAPI();
                        } else if (data.code == 403) {
                            this.showLoginModal(true);
                        } else if (data.code == 400) {
                            this.falloCarga = true;
                            this.falloAsociacionDetalles = data.message;
                        }
                    })
                    .catch(error => {
                        this.falloCarga = true;
                        this.errorHandling(error);
                    })
                    .then(() => {
                        // allways
                        this.egresoLoading = false;
                    })
            }
        },
        cancelarAsociarCategorias() {
            this.$bvModal.hide('modal-asociar-categoria');
        },
    },
    mounted() {
        this.cargarEgresoAPI();
    },
    components: {
        'asociar-ingreso': asociarIngreso,
        'asociar-categoria': asociarCategoria
    }
}
</script>

<style>
.egreso_container{
    background: var(--secondary);
}
</style>