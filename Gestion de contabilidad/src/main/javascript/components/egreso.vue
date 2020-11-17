<template>
    <div class="px-2 pb-2 egreso_container">
        <b-card v-if="egreso">
            <b-overlay spinner-variant="light" variant="primary" :show="egresoLoading" no-wrap>
                <template #overlay>
                    <div>
                        <div class="d-flex justify-content-center">
                            <b-spinner variant="light" class="flex-shrink-0"></b-spinner>
                        </div>
                        <div class="mt-4" v-if="archivo">
                            <p class="text-light">Subiendo archivo</p>
                            <b-progress class="progress-min-width bg-transparent" variant="light"  max="100" height="2rem">
                                <b-progress-bar :value="archivoProgress">
                                    <strong class="text-secondary">{{ archivoProgress }}%</strong>
                                </b-progress-bar>
                            </b-progress>
                        </div>
                    </div>
                </template>
            </b-overlay>
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
                                    <b-list-group-item class="p-2"><strong>Número Documento: </strong>{{ egreso.documento.numDocumento }}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Fecha Pedido: </strong>{{ convertDate(egreso.documento.fechaDePedido) }}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Fecha Entrega: </strong>{{ convertDate(egreso.documento.fechaDeEntrega) }}</b-list-group-item>
                                    <b-list-group-item class="p-2"><strong>Descripcion: </strong>{{ egreso.documento.descripcion }}</b-list-group-item>
                                    <!--tiene un archivo-->
                                    <b-list-group-item class="p-2" 
                                    :href="`/files/${egreso.documento.pathAdjunto}`" target="_blank" 
                                    v-if="egreso.documento.pathAdjunto">
                                        <span class="text-primary">
                                            Archivo adjunto 
                                            <b-icon-box-arrow-in-up-right/>
                                        </span>
                                    </b-list-group-item>
                                    <b-list-group-item class="p-2" v-if="egreso.documento.pathAdjunto">
                                        <b-input-group >
                                            <b-form-file
                                                v-model="archivo"
                                                placeholder="Seleccione un archivo o sueltelo aqui"
                                                drop-placeholder="Suelta el archivo aqui..."
                                                browse-text="Buscar"
                                            ></b-form-file>
                                            <template #append>
                                                <b-button variant="outline-secondary" @click="archivo = null">
                                                    <b-icon-x />
                                                </b-button>
                                                <transition name="fade">
                                                    <b-button variant="outline-primary" v-if="archivo" @click="updateFileAPI">
                                                        <b-icon-arrow-bar-up />
                                                    </b-button>
                                                </transition>
                                            </template>
                                        </b-input-group>
                                    </b-list-group-item>
                                    <b-list-group-item class="p-2" v-else>
                                        <b-input-group >
                                            <b-form-file
                                                v-model="archivo"
                                                placeholder="Seleccione un archivo o sueltelo aqui"
                                                drop-placeholder="Suelta el archivo aqui..."
                                                browse-text="Buscar"
                                            ></b-form-file>
                                            <template #append>
                                                <b-button variant="outline-secondary" @click="archivo = null">
                                                    <b-icon-x />
                                                </b-button>
                                                <transition name="fade">
                                                    <b-button variant="outline-primary" v-if="archivo" @click="uploadFileAPI">
                                                        <b-icon-upload />
                                                    </b-button>
                                                </transition>
                                            </template>
                                        </b-input-group>
                                    </b-list-group-item>
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
                    <b-table borderless foot-clone class="m-0 border-0"
                        :fields="campos_items" 
                        :items="egreso.items"
                    >
                        <template #cell(precio)="data">
                            <span>{{ "$" + data.item.precio }}</span>
                        </template>

                        <template #foot(tipoItem)>
                            <span></span>
                        </template>
                        <template #foot(descripcion)>
                            <span></span>
                        </template>
                        <template #foot(cantidad)>
                            <span>Total</span>
                        </template>
                        <template #foot(precio)>
                            <span>{{ '$' + egreso.valorTotal }}</span>
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
            <div class="row m-0 pt-2" v-if="egreso.presupuestos.length">
                <div class="col p-0">
                    <b-list-group>
                        <b-list-group-item class="p-2 text-center" :class="{'bg-secondary': showPresupuestos, 'border-bottom-0': showPresupuestos, 'text-light': showPresupuestos}" @click="showPresupuestos = !showPresupuestos" button>
                            <strong>Presupuestos</strong><b-badge class="ml-2" variant="light"><strong>{{egreso.presupuestos.length}}</strong></b-badge>
                        </b-list-group-item>
                        <b-list-group-item class="p-0 border-0 overflow-hidden">
                            <b-collapse :visible="showPresupuestos">
                                <div class="px-2 pb-2 bg-secondary" :key="key" v-for="(presupuesto, key) in egreso.presupuestos">
                                    <b-list-group>
                                        <b-list-group-item class="p-2 d-flex justify-content-between">
                                            <span><strong>Proveedor</strong> {{presupuesto.proveedor.nombre}} </span>
                                            <span><strong>Fecha Vigente</strong> {{convertDate(presupuesto.fechaVigente)}} </span>
                                        </b-list-group-item>
                                        <div class="p-0 bg-light overflow-hidden">
                                            <b-table borderless foot-clone class="m-0 border-0"
                                                :fields="campos_items" 
                                                :items="presupuesto.items.map(itemEgresoAPIConverter)"
                                            >
                                                <template #cell(precio)="data">
                                                    <span>{{ "$" + data.item.precio }}</span>
                                                </template>

                                                <template #foot(tipoItem)>
                                                    <span></span>
                                                </template>
                                                <template #foot(descripcion)>
                                                    <span></span>
                                                </template>
                                                <template #foot(cantidad)>
                                                    <span>Total</span>
                                                </template>
                                                <template #foot(precio)>
                                                    <span>{{ '$' + presupuesto.valorTotal }}</span>
                                                </template>
                                            </b-table>
                                        </div>
                                    </b-list-group>
                                </div>
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
                            <b-button v-b-modal.modal-asociar-categoria variant="outline-secondary">Asociar a categorias</b-button>
                            <b-button v-b-modal.modal-agregar-presupuesto variant="outline-secondary">Agregar Presupuesto</b-button>
                            <template v-if="!egreso.estaSuscrito">
                                <b-button variant="outline-danger" @click="suscribirseAPI">Suscribirse</b-button>
                            </template>
                            <template v-else>
                                <b-button v-b-modal.modal-asociar-ingreso variant="danger" disabled><b-icon-check/> Suscrito</b-button>
                            </template>
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

        <b-modal id="modal-agregar-presupuesto" size="xl" v-if="egreso" hide-footer scrollable centered title="Crear nuevo Presupuesto">
            <agregar-presupuesto
                :itemsReadOnly="JSON.parse(JSON.stringify(egreso.items))"
                :confirmarAccion="confirmarNuevoPresupuesto"
                :cancelarAccion="cancelarNuevoPresupuesto"
            ></agregar-presupuesto>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
import {convertDate, RequestHelper} from '../util/utils'
import asociarCategoria from './asociarCategoria'
import asociarIngreso from './asociarIngreso'
import agregarPresupuesto from './agregarPresupuesto'

export default {
    props: {
        egresoLoaded: Function
    },
    template: `{{> egreso/egresoHTML.hbs}}`,
    data() {
        return {
            egreso: null,
            archivo: null,
            archivoImagenUrl: "",
            archivoProgress: 0,
            showDatosProveedor: false,
            showDatosFactura: false,
            showIngreso: false,
            showPresupuestos: false,
            campos_items: [
                {
                    key: 'tipoItem',
                    label: 'Tipo',
                    tdClass: [],
                    thClass: []
                },
                {
                    key: 'descripcion',
                    label: 'Descripcion',
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
    inject: ['showLoginModal', 'errorHandling', 'createToast'],
    methods: {
        convertDate: convertDate,
        cargarEgresoAPI() {
            var id = this.$route.params.id;
            if(id == undefined)
                return;

            RequestHelper.get(`/api/operaciones/egreso/${id}`, {
                success: (data) => {
                    console.log(data);
                    this.egreso = data.egreso;
                    this.egreso.items = data.egreso.items.map(this.itemEgresoAPIConverter);
                    this.egreso.estaSuscrito = data.estaSuscrito;
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
                    this.egresoLoaded();
                }
            });
        },
        itemEgresoAPIConverter(item) {
            // Arreglar cuando se cambien los items en backend
            return {
                id: item.id,
                tipoItem: item.item.tipoItem.nombre,
                descripcion: item.item.descripcion,
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

                RequestHelper.post(`/api/operaciones/asociarManualmente`, request, {
                    success: (data) => {
                        this.cargarEgresoAPI();
                    },
                    notLoggedIn: () => {
                        this.showLoginModal(true);
                    },
                    failed: (data) => {
                        this.falloCarga = true;
                        this.falloCargaDetalles = data.message;
                    },
                    forbidden: (error) => {
                        this.falloCarga = true;
                        this.errorHandling(error);
                    },
                    error: (error) => {
                        this.falloCarga = true;
                        this.errorHandling(error);
                    },
                    always: () => {
                        this.egresoLoading = false;
                    }
                });
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

                RequestHelper.post(`/api/categorias/asociar`, request, {
                    success: (data) => {
                        this.cargarEgresoAPI();
                    },
                    notLoggedIn: () => {
                        this.showLoginModal(true);
                    },
                    failed: (data) => {
                        this.falloCarga = true;
                        this.falloAsociacionDetalles = data.message;
                    },
                    forbidden: (error) => {
                        this.falloCarga = true;
                        this.errorHandling(error);
                    },
                    error: (error) => {
                        this.falloCarga = true;
                        this.errorHandling(error);
                    },
                    always: () => {
                        this.egresoLoading = false;
                    }
                });
            }
        },
        cancelarAsociarCategorias() {
            this.$bvModal.hide('modal-asociar-categoria');
        },
        confirmarNuevoPresupuesto(data) {
            this.$bvModal.hide('modal-agregar-presupuesto');
            var presupuesto = data;
            presupuesto.egreso = this.egreso.id;
            console.log(presupuesto);

            this.falloCarga = false;

            RequestHelper.post(`/api/operaciones/presupuesto`, presupuesto, {
                success: (data) => {
                    this.createToast('Guardado exitoso', 'Se creó el presupuesto exitosamente', 'success');
                    this.cargarEgresoAPI();
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                failed: (data) => {
                    this.falloCarga = true;
                    this.falloCargaDetalles = data.message;
                },
                forbidden: (error) => {
                    this.falloCarga = true;
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.falloCarga = true;
                    this.errorHandling(error);
                }
            });
        },
        cancelarNuevoPresupuesto() {
            this.$bvModal.hide('modal-agregar-presupuesto');
        },
        suscribirseAPI() {
            this.egresoLoading = true;

            RequestHelper.post(`/api/operaciones/egreso/suscribirse`, {egresoId: this.egreso.id}, {
                success: (data) => {
                    console.log(data);
                    this.cargarEgresoAPI();
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                failed: (data) => {
                    this.falloCarga = true;
                    this.falloCargaDetalles = data.message;
                },
                forbidden: (error) => {
                    this.falloCarga = true;
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.falloCarga = true;
                    this.errorHandling(error);
                },
                always: () => {
                    this.egresoLoading = false;
                }
            });
        },
        updateFileAPI() {
            if(this.archivo) {
                this.egresoLoading = true;

                var request = new FormData();
                request.append(this.egreso.documento.pathAdjunto, this.archivo);

                console.log(this.egreso.documento.pathAdjunto);

                const config = {
                    onUploadProgress: (progressEvent) => {
                    var percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total)
                        this.archivoProgress = percentCompleted;
                    }
                }

                RequestHelper.patch('/api/operaciones/documentoComercial/archivo', request, {
                    success: (data) => {
                        console.log(data.paths.archivo);
                    },
                    notLoggedIn: (data) => {
                        this.showLoginModal(false);
                    },
                    failed: (data) => {
                        console.log(data);
                    },
                    forbidden: (error) => {
                        this.errorHandling(error);
                    },
                    error: (error) => {
                        this.errorHandling(error);
                    },
                    always: () => {
                        this.archivoProgress = 0;
                        this.egresoLoading = false;
                    },
                    default: (data) => {
                        console.log("NO RESPONSE CODE");
                        console.log(data);
                    }
                }, config);
            } else {
                this.createToast("No hay archivo", "Elija un archivo para actualizar el Egreso", "warning");
            }
        },
        uploadFileAPI(archivo) {
            
            if(this.archivo) {

                this.egresoLoading = true;

                var request = new FormData();
                request.append('archivo', this.archivo);

                const config = {
                    onUploadProgress: (progressEvent) => {
                    var percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total)
                        this.archivoProgress = percentCompleted;
                    }
                }

                RequestHelper.post('/api/operaciones/documentoComercial/archivo', request, {
                    success: (data) => {
                        console.log(data);
                        this.updateArchivoEgresoAPI(data.paths.archivo);
                    },
                    notLoggedIn: (data) => {
                        this.showLoginModal(false);
                    },
                    failed: (data) => {
                        console.log(data);
                    },
                    forbidden: (error) => {
                        this.errorHandling(error);
                    },
                    error: (error) => {
                        this.errorHandling(error);
                    },
                    always: () => {
                        this.egresoLoading = false;
                    },
                    default: (data) => {
                        console.log("NO RESPONSE CODE");
                        console.log(data);
                    }
                }, config);
            } else {
                this.createToast("No hay archivo", "Elija un archivo para agregar al Egreso", "warning");
            }
        },
        updateArchivoEgresoAPI(archivo) {
            var request = {
                egreso: this.egreso.id,
                pathAdjuntoDocumentoComercial: archivo
            }
            RequestHelper.patch('/api/operaciones/egreso/modificarPathAdjunto', request, {
                success: (data) => {
                    this.cargarEgresoAPI();
                },
                notLoggedIn: (data) => {
                    this.showLoginModal(false);
                },
                failed: (data) => {
                    console.log(data);
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                    this.egresoLoading = false;
                },
                default: (data) => {
                    console.log("NO RESPONSE CODE");
                    console.log(data);
                }
            });
        }
    },
    mounted() {
        this.cargarEgresoAPI();
    },
    components: {
        'asociar-ingreso': asociarIngreso,
        'asociar-categoria': asociarCategoria,
        'agregar-presupuesto': agregarPresupuesto
    }
}
</script>

<style>
.egreso_container{
    background: var(--secondary);
}
</style>