<template>
    <!-- template para nuevo egreso -->
    <div class="pt-3">
        <b-overlay spinner-variant="light" variant="primary" :show="egresoLoading && !falloCarga" no-wrap>
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
        <div class="row mb-4 mx-2">
            <div class="col py-2 text-center">
                <h2>Cargar nuevo egreso</h2>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Fecha Operación</strong></span>
            </div>
            <div class="col">
                <b-collapse :visible="(!egreso.fechaOperacion && falloInput)">
                    <b-badge variant="danger">Ingrese la fecha de la operacion</b-badge>
                </b-collapse>
                <b-form-datepicker placeholder="Seleccione fecha de la Operación" class="mb-2"
                    :state="(!egreso.fechaOperacion && falloInput) ? false : null"
                    v-model="egreso.fechaOperacion"
                ></b-form-datepicker>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2 justify-content-between">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Proveedor</strong></span>
            </div>
            <div class="col d-flex align-items-end">
                <div class="w-100">
                    <b-collapse :visible="(!egreso.proveedor && falloInput)">
                        <b-badge variant="danger">Seleccione un proveedor</b-badge>
                    </b-collapse>
                    <b-overlay spinner-variant="primary" :show="proveedoresLoading" rounded="sm">
                        <b-form-select
                            :options="proveedoresSelect" 
                            :state="(!egreso.proveedor && falloInput) ? false : null"
                            v-model="egreso.proveedor">
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
            <div class="col d-flex align-items-end">
                <b-button block variant="outline-primary" v-b-modal.modal-agregar-proveedor>
                    <b-icon-plus></b-icon-plus>
                    Cargar Proveedor
                </b-button>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2 justify-content-between">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Medio de Pago</strong></span>
            </div>
            <div class="col d-flex align-items-end">
                <div class="w-100">
                    <b-collapse :visible="(!egreso.medioDePago.id && falloInput)">
                        <b-badge variant="danger">Seleccione un medio de pago</b-badge>
                    </b-collapse>
                    <b-overlay spinner-variant="primary" :show="mediosDePagoLoading" rounded="sm">
                        <b-form-select 
                            :options="mediosDePagoSelect"
                            :state="(!egreso.medioDePago.id && falloInput) ? false : null"
                            v-model="egreso.medioDePago.id">
                            <template #first>
                                <b-form-select-option :value="null" disabled>-- Seleccionar un Medio de Pago --</b-form-select-option>
                            </template>
                        </b-form-select>
                    </b-overlay>
                </div>
            </div>
            <div class="col d-flex align-items-end">
                <div class="w-100">
                    <b-collapse :visible="(!egreso.medioDePago.dato && falloInput)">
                        <b-badge variant="danger">Ingrese el número de pago</b-badge>
                    </b-collapse>
                    <b-form-input placeholder="Ingrese número de pago"
                        :state="(!egreso.medioDePago.dato && falloInput) ? false : null"
                        v-model="egreso.medioDePago.dato"
                    ></b-form-input>
                </div>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Documento comercial</strong></span>
            </div>
            <div class="col">
                <div class="p-2 container-fluid border border rounded">
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Tipo</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!egreso.documentoComercial.tipo && falloInput)">
                                <b-badge variant="danger">Seleccione un tipo de documento</b-badge>
                            </b-collapse>
                            <b-form-select 
                                :options="tipoDocumentoSelect"
                                :state="(!egreso.documentoComercial.tipo && falloInput) ? false : null"
                                v-model="egreso.documentoComercial.tipo">
                                <template #first>
                                    <b-form-select-option :value="null" disabled>-- Seleccionar un Tipo de Documento --</b-form-select-option>
                                </template>
                            </b-form-select>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Número documento</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!egreso.documentoComercial.numeroDocumento && falloInput)">
                                <b-badge variant="danger">Ingrese el número de documento</b-badge>
                            </b-collapse>
                            <b-form-input placeholder="Ingrese número de documento" 
                                type="number"
                                :state="(!egreso.documentoComercial.numeroDocumento && falloInput) ? false : null"
                                v-model="egreso.documentoComercial.numeroDocumento"
                            ></b-form-input>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Fecha de Pedido</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!egreso.documentoComercial.fechaDePedido && falloInput)">
                                <b-badge variant="danger">Ingrese la fecha de pedido</b-badge>
                            </b-collapse>
                            <b-form-datepicker placeholder="Seleccione fecha de Pedido" class="mb-2"
                                :state="(!egreso.documentoComercial.fechaDePedido && falloInput) ? false : null"
                                v-model="egreso.documentoComercial.fechaDePedido"></b-form-datepicker>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Fecha de Entrega</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!egreso.documentoComercial.fechaDeEntrega && falloInput)">
                                <b-badge variant="danger">Ingrese la fecha de entrega</b-badge>
                            </b-collapse>
                            <b-form-datepicker placeholder="Seleccione fecha de Entrega" class="mb-2" 
                            :state="(!egreso.documentoComercial.fechaDeEntrega && falloInput) ? false : null"
                            v-model="egreso.documentoComercial.fechaDeEntrega"></b-form-datepicker>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Descripción</strong></span>
                        </div>
                        <div class="col">
                            <b-col class="px-0 mx-0">
                                <b-form-textarea rows="3" max-rows="6"
                                    placeholder="Breve descripción" v-model="egreso.documentoComercial.descripcion"
                                ></b-form-textarea>
                            </b-col>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Archivo</strong></span>
                        </div>
                        <div class="col">
                            <b-col class="px-0 mx-0">
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
                                    </template>
                                </b-input-group>
                                <b-collapse :visible="archivoImagenUrl != ''">
                                    <b-img :src="archivoImagenUrl" fluid />
                                </b-collapse>
                            </b-col>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Items</strong></span>
            </div>
            <div class="col">
                <b-collapse :visible="(!egreso.items.length && falloInput)">
                    <b-badge variant="danger">Ingrese por lo menos un item</b-badge>
                </b-collapse>
                <tabla-items
                    v-bind:actualizarItems="actualizarItems"
                ></tabla-items>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Presupuestos</strong></span>
            </div>
            <div class="col">
                <div class="d-flex w-100 align-items-center">
                    <div class="p-2 w-100">
                        <b-form-checkbox v-model="requierePresupuestos" @change="!requierePresupuestos ? egreso.presupuestosMinimos = '' : true" switch>
                            Requiere presupuestos
                        </b-form-checkbox>
                    </div>
                    <transition name="fade">
                        <b-form-input type="number" class="w-50" v-if="requierePresupuestos" v-model="egreso.presupuestosMinimos" placeholder="Presupuestos mínimos"></b-form-input>
                    </transition>
                </div>
                <div class="pt-2" v-if="presupuestosAAgregar.length">
                    <p>Al egreso se le asociaran los siguientes presupuestos: </p>
                    <b-table small class="mb-0 rounded" :fields="presupuestosAAgregarCampos" :items="presupuestosAAgregar">
                        <template #cell(fechaVigente)="data">
                            {{ convertDate(data.item.fechaVigente) }}
                        </template>
                        <template #cell(items)="data">
                            {{ data.item.items.length }}
                        </template>
                        <template #cell(delete)="data">
                            <b-button variant="outline" class="p-0 m-0 text-danger" @click="removerPresupuesto(data.index)">
                                <b-icon-x></b-icon-x>
                            </b-button>
                        </template>
                    </b-table>
                </div>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Acciones</strong></span>
            </div>
            <div class="col">
                <b-button-group class="w-100">
                    <b-button v-b-modal.modal-asociar-ingreso>Asociar a Ingreso</b-button>
                    <b-button v-b-modal.modal-asociar-categoria>Asociar a Categorías</b-button>
                </b-button-group>
                <div class="pt-2" v-if="ingresoAAsociar">
                    <b-collapse v-if="ingresoAAsociar" :visible="precioTotal() > ingresoAAsociar.montoTotal">
                        <b-badge variant="danger">El egreso supera el monto del ingreso</b-badge>
                    </b-collapse>
                    <span>El egreso se asociará al siguiente ingreso: </span>
                    <b-badge>{{ ingresoAAsociar.descripcion + ' - $' + ingresoAAsociar.montoTotal}}</b-badge>
                </div>
                <div class="pt-2" v-if="categoriasAAsociar.length">
                    <span>El egreso se asociará a las siguientes categorías: </span>
                    <template
                        v-for="(categoria, index) in categoriasAAsociar"
                    >
                        <b-badge :key="index" class="mr-1">{{ categoria.name }}</b-badge>
                    </template>
                </div>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
    
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Suscribirse</strong></span>
            </div>
            <div class="col px-2">
                <b-form-checkbox class="py-2 ml-2" v-model="suscribirse">Suscribirse a este egreso</b-form-checkbox>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>

        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">

            </div>
            <div class="col">
                <b-collapse :visible="(falloInput)">
                    <b-badge variant="danger">Hay problemas en los campos</b-badge>
                </b-collapse>
                <b-collapse :visible="(falloCarga)">
                    <b-badge variant="danger">{{falloCargaDetalles}}</b-badge>
                </b-collapse>
                <b-button-group>
                    <b-button variant="primary" @click="confirmar">Confirmar</b-button>
                    <b-button variant="outline-dark" to="/operaciones/egreso">Cancelar</b-button>
                </b-button-group>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        
        <b-modal id="modal-asociar-categoria" hide-footer scrollable centered title="Asociar a Categoría">
            <asociar-categoria
                v-bind:confirmarAccion="confirmarAsociarCategorias"
                v-bind:cancelarAccion="cancelarAsociarCategorias"
            ></asociar-categoria>
        </b-modal>
        
        <b-modal id="modal-asociar-ingreso" hide-footer scrollable centered title="Asociar a Ingreso">
            <asociar-ingreso
                v-bind:confirmarAccion="confirmarAsociarIngreso"
                v-bind:cancelarAccion="cancelarAsociarIngreso"
            ></asociar-ingreso>
        </b-modal>

        <b-modal id="modal-agregar-proveedor" size="xl" hide-footer scrollable centered title="Crear nuevo Proveedor">
            <agregar-proveedor 
                v-bind:confirmarAccion="confirmarNuevoProveedor"
                v-bind:cancelarAccion="cancelarNuevoProveedor"
            ></agregar-proveedor>
        </b-modal>

        <b-modal id="modal-agregar-presupuesto" size="xl" hide-footer scrollable centered title="Crear nuevo Presupuesto">
            <agregar-presupuesto
                :itemsReadOnly="itemsReadOnlyMap(egreso.items)"
                :confirmarAccion="confirmarNuevoPresupuesto"
                :cancelarAccion="cancelarNuevoPresupuesto"
            ></agregar-presupuesto>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
import {convertDate, getCookie, RequestHelper} from '../util/utils.js'
import agregarProveedor from '../components/agregarProveedor'
import agregarPresupuesto from '../components/agregarPresupuesto'
import asociarCategoria from '../components/asociarCategoria'
import asociarIngreso from '../components/asociarIngreso'
import tablaItems from '../components/tablaItems'

export default {
    data() {
        return {
            egreso: {
                fechaOperacion: null,
                proveedor: null,
                medioDePago: {
                    id: null,
                    dato: null
                },
                documentoComercial: {
                    tipo: null,
                    numeroDocumento: null,
                    fechaDePedido: null,
                    fechaDeEntrega: null,
                    descripcion: null,
                    nombreFicheroDocumento: null
                },
                items: [],
                presupuestosMinimos: null
            },
            archivo: null,
            requierePresupuestos: false,
            idEgreso: null,
            falloInput: false,
            falloCarga: false,
            falloCargaDetalles: "Hubo un problema al cargar el egreso",
            proveedoresSelect: [],
            mediosDePagoSelect: [],
            mediosDePagoType: {
                credit_card: []
            },
            tipoDocumentoSelect: [{
                    value: 1,
                    text: "Factura A"
                },
                {
                    value: 2,
                    text: "Factura B"
                },
                {
                    value: 3,
                    text: "Factura C"
                }
            ],
            proveedoresLoading: false,
            mediosDePagoLoading: false,
            egresoLoading: false,
            archivoProgress: 0,

            suscribirse: false,
            proveedorAAgregar: null,
            presupuestosAAgregar: [],
            categoriasAAsociar: [],
            ingresoAAsociar: null,
            presupuestosAAgregarCampos: [
                {
                    key: 'fechaVigente',
                    tdClass: ['w-50'],
                    thClass: ['w-50']
                },
                {
                    key: 'numeroOperacion',
                    tdClass: ['w-50'],
                    thClass: ['w-50']
                },
                {
                    key: 'items',
                    tdClass: ['text-center'],
                    thClass: ['text-center']
                },
                {
                    key: 'delete',
                    label: '',
                    tdClass: [],
                    thClass: ['text-center']
                }
            ],
            archivoImagenUrl: ''
        }
    },
    computed: {
        state(data) {
            console.log(data);
            return false;
        }
    },
    inject: ['errorHandling', 'createToast', 'showLoginModal'],
    watch: {
        'archivo'() {
            if(this.archivo) {
                // Si es imagen mostrar la imagen
                if( this.archivo.type == "image/png"   ||
                    this.archivo.type == "image/jpeg"  ||
                    this.archivo.type == "image/gif") {
                        var reader = new FileReader();
                        reader.onload = () => {
                            var dataURL = reader.result;
                            this.archivoImagenUrl = dataURL;
                        };
                        reader.readAsDataURL(this.archivo);
                    }
            } else {
                this.archivoImagenUrl = "";
            }
        }
    },
    methods: {
        getCookie: getCookie,
        convertDate: convertDate,
        itemsReadOnlyMap(items) {
            var nuevosItems = JSON.parse(JSON.stringify(items));
            nuevosItems.map((unItem) => {
                if(unItem.tipoItem)
                    unItem.tipoItem = unItem.tipoItem.nombre;
                return unItem;
            })
            nuevosItems.pop();

            return nuevosItems;
        },
        confirmar() {

            this.egresoLoading = true;
            
            // Verifico que los input sean correctos
            var todosLosCamposRellenos = 
                this.egreso.fechaOperacion                      &&
                this.egreso.proveedor                           &&
                this.egreso.medioDePago.id                      &&
                this.egreso.medioDePago.dato                    &&
                this.egreso.documentoComercial.tipo             &&
                this.egreso.documentoComercial.numeroDocumento  &&
                this.egreso.documentoComercial.fechaDePedido    &&
                this.egreso.documentoComercial.fechaDeEntrega   &&
                this.egreso.items.length > 1

            if(this.ingresoAAsociar != null) {
                todosLosCamposRellenos = this.precioTotal() <= this.ingresoAAsociar.montoTotal
            }
            this.falloInput = !todosLosCamposRellenos;
            if(this.falloInput) {
                this.egresoLoading = false;
                return;
            }
            
            // Comienzo cargando el archivo
            this.uploadFileAPI();

        },
        uploadFileAPI() {
            
            if(this.archivo) {
                var request = new FormData();
                request.append('archivo', this.archivo);

                const config = {
                    onUploadProgress: (progressEvent) => {
                    var percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total)
                        this.archivoProgress = percentCompleted;
                    }
                }

                RequestHelper.post('/api/operaciones/egreso/cargarArchivos', request, {
                    success: (data) => {
                        console.log(data.paths.archivo);
                        this.egreso.documentoComercial.nombreFicheroDocumento = data.paths.archivo;
                        this.crearProveedorAPI();
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
                        this.archivoProgress = 0;
                    },
                    default: (data) => {
                        console.log("NO RESPONSE CODE");
                        console.log(data);
                    }
                }, config);
            } else {
                this.crearProveedorAPI();
            }

        },
        crearProveedorAPI() {
            if (this.proveedorAAgregar != null) {
                // Hay que agregar un proveedor antes de agregar el egreso
                console.log("POST '/api/proveedor'");
                console.log(JSON.stringify(this.proveedorAAgregar, null, 4));

                RequestHelper.post('/api/proveedor', this.proveedorAAgregar, {
                    success: (data) => {
                        this.createToast('Guardado exitoso', 'Se creó el proveedor exitosamente', 'success');
                        this.egreso.proveedor = data.id;
                        this.crearEgresoAPI();
                    },
                    notLoggedIn: () => {
                        this.showLoginModal(true);
                    },
                    failed: (data) => {
                        this.falloCargaDetalles = data;
                        this.falloCarga = true;
                    },
                    forbidden: (error) => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    },
                    error: (error) => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    }
                });
            } else {
                this.crearEgresoAPI()
            }
        },
        crearEgresoAPI() {
            var egresoToSend = JSON.parse(JSON.stringify(this.egreso));
            egresoToSend.items.pop();
            egresoToSend.items.map((unItem) => {
                unItem.tipoItem = unItem.tipoItem.id;
                return unItem;
            })

            console.log("POST '/api/operaciones/egreso'");
            console.log(JSON.stringify(egresoToSend, null, 4));

            RequestHelper.post('/api/operaciones/egreso', egresoToSend, {
                success: (data) => {
                    this.createToast('Guardado exitoso', 'Se creó el egreso correctamente', 'success');
                    this.idEgreso = data.id;
                    this.crearPresupuestosAPI();
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                failed: (data) => {
                    this.falloCargaDetalles = data.message;
                    this.falloCarga = true;
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                    this.falloCarga = true;
                },
                error: (error) => {
                    this.errorHandling(error);
                    this.falloCarga = true;
                }
            });
        },
        crearPresupuestosAPI() {
            // Se hace muy complicado crear los presupuestos ni bien se carga un Egreso: 
            // Los presupuestos ahora se tienen que cargar luego de haber cargado un Egreso.

            this.asociarCategoriasAPI();

            // if (this.presupuestosAAgregar != null && this.presupuestosAAgregar.length) {

            //     this.presupuestosAAgregar.forEach((presupuesto) => {

            //         presupuesto.egreso = this.idEgreso;
                    
            //         console.log("POST '/api/operaciones/presupuesto'");
            //         console.log(JSON.stringify(presupuesto, null, 4));

            //         RequestHelper.post(`/api/operaciones/presupuesto`, presupuesto, {
            //             success: (data) => {
            //                 this.createToast('Guardado exitoso', 'Se creó el presupuesto correctamente', 'success');
            //             },
            //             notLoggedIn: () => {
            //                 this.showLoginModal(true);
            //             },
            //             failed: (data) => {
            //                 this.falloCarga = true;
            //                 this.falloCargaDetalles = data.message;
            //             },
            //             forbidden: (error) => {
            //                 this.falloCarga = true;
            //                 this.errorHandling(error);
            //             },
            //             error: (error) => {
            //                 this.errorHandling(error);
            //                 this.falloCarga = true;
            //             }
            //         });
            //     });
                
            //     this.asociarCategoriasAPI();

            // } else {
            //     this.asociarCategoriasAPI();
            // }
        },
        asociarCategoriasAPI() {
            if(this.categoriasAAsociar.length) {
                var request = {
                    id: this.idEgreso,
                    categorias: this.categoriasAAsociar.map(function(categoria){
                        return categoria.id;
                    }),
                }

                console.log("POST '/api/categorias/asociar'");
                console.log(JSON.stringify(request, null, 4));

                RequestHelper.post('/api/categorias/asociar', request, {
                    success: (data) => {
                        this.createToast('Guardado exitoso', 'Se asoció a categorías exitosamente', 'success');
                        this.asociarIngresoAPI();
                    },
                    notLoggedIn: () => {
                        this.showLoginModal(true);
                    },
                    failed: () => {
                        this.falloCarga = true;
                    },
                    forbidden: (error) => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    },
                    error: (error) => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    }
                });
            } else {
                this.asociarIngresoAPI();
            }
        },
        asociarIngresoAPI() {

            if(this.ingresoAAsociar) {
                var request = {
                    ingresoId: this.ingresoAAsociar.id,
                    egresoId: this.idEgreso,
                }

                console.log("POST '/api/operaciones/asociarManualmente'");
                console.log(JSON.stringify(request, null, 4));

                RequestHelper.post('/api/operaciones/asociarManualmente', request, {
                    success: (data) => {
                        this.createToast('Guardado exitoso', 'Se asoció al ingreso exitosamente', 'success');
                        this.$router.push('/operaciones/egreso');
                    },
                    notLoggedIn: () => {
                        this.showLoginModal(true);
                    },
                    failed: () => {
                        this.falloCarga = true;
                    },
                    forbidden: (error) => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    },
                    error: (error) => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    }
                });
            } else {
                this.$router.push('/operaciones/egreso');
            }
        },
        cargarEntidadesAPI() {
            this.entidadesLoading = true;

            RequestHelper.get('/api/entidades', {
                success: (data) => {
                    console.log(data);
                    this.entidadesOptions = [{
                        text: `${data.organizacion.razonSocial} - Entidad Jurídica`,
                        value: data.organizacion.id,
                    }];
                    data.organizacion.entidadesBase.forEach((base) => {
                        this.entidadesOptions.push({
                            text: `${base.nombreFicticio} - Entidad Base`,
                            value: base.id
                        })
                    })
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
                    this.entidadesLoading = false;
                }
            });
        },
        cargarProveedoresAPI() {
            this.proveedoresLoading = true;

            RequestHelper.get('/api/proveedores', {
                success: (data) => {
                    console.log(data);
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
        cargarMediosDePagoAPI() {
            this.mediosDePagoLoading = true;

            RequestHelper.get('/api/medios', {
                success: (data) => {
                    this.mediosDePagoSelect = data.data.map(this.mediosDePagoAPIConverter);
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
                    this.mediosDePagoLoading = false;
                }
            });
        },
        mediosDePagoAPIConverter(medioDePago) {
            return {
                value: medioDePago.id,
                text: medioDePago.medioDePago.charAt(0).toUpperCase() + medioDePago.medioDePago.slice(1), // Le pongo mayuscula la primer letra
                type: medioDePago.medioDePago
            }
        },
        actualizarItems(items) {
            console.log(items);
            this.egreso.items = items;
        },
        // Proveedores
        confirmarNuevoProveedor(data) {
            this.$bvModal.hide('modal-agregar-proveedor');
            this.proveedorAAgregar = data;
            this.egreso.proveedor = "nuevoProveedor";
        },
        cancelarNuevoProveedor() {
            this.$bvModal.hide('modal-agregar-proveedor');
        },
        // Presupuestos
        confirmarNuevoPresupuesto(data) {
            this.$bvModal.hide('modal-agregar-presupuesto');
            this.presupuestosAAgregar.push(data);
            console.log(data);
        },
        cancelarNuevoPresupuesto() {
            this.$bvModal.hide('modal-agregar-presupuesto');
        },
        removerPresupuesto(index) {
            this.presupuestosAAgregar.splice(index, 1);
        },
        // Categorias
        confirmarAsociarCategorias(data) {
            this.$bvModal.hide('modal-asociar-categoria');
            this.categoriasAAsociar = data;
        },
        cancelarAsociarCategorias() {
            this.$bvModal.hide('modal-asociar-categoria');
        },
        // Ingreso
        confirmarAsociarIngreso(data) {
            this.$bvModal.hide('modal-asociar-ingreso');
            this.ingresoAAsociar = data;
        },
        cancelarAsociarIngreso() {
            this.$bvModal.hide('modal-asociar-ingreso');
        },
        
        precioTotal() {
            var precio = 0;
            this.egreso.items.forEach(item => {
                var itemPrecio = parseFloat(item.precio);
                var itemCantidad = parseInt(item.cantidad);

                if(!isNaN(itemPrecio) && !isNaN(itemCantidad))
                    precio += itemPrecio * itemCantidad;
            });
            return precio;
        }
    },
    mounted() {
        this.cargarProveedoresAPI();
        this.cargarMediosDePagoAPI();
    },
    components: {
        'tabla-items': tablaItems,
        'asociar-categoria': asociarCategoria,
        'asociar-ingreso': asociarIngreso,
        'agregar-proveedor': agregarProveedor,
        'agregar-presupuesto': agregarPresupuesto
    }
}
</script>

<style>
    .custom-file-label {
        border-color: var(--secondary);
    }
    .custom-file-label::after {
        display: none;
        content: "" !important;
    }
    .progress-min-width{
        min-width: 50vw;
    }
</style>