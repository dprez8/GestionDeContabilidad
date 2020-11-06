<template>
    <!-- template para nuevo egreso -->
    <div class="pt-3">
        <b-overlay spinner-variant="light" variant="primary" :show="egresoLoading && !falloCarga" no-wrap></b-overlay>
        <div class="row mb-4 mx-2">
            <div class="col py-2 text-center">
                <h2>Cargar nuevo egreso</h2>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Organización</strong></span>
            </div>
            <div class="col bg-light p-2">
                <span class="ml-2">{{getCookie("organizacion")}}</span>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
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
                    <!--
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Imagen</strong></span>
                        </div>
                        <div class="col p-2">
                            <span class="ml-2">We'll see...</span>
                        </div>
                    </div>
                    -->
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
                <b-button variant="outline-primary" v-b-modal.modal-agregar-presupuesto>
                    <b-icon-plus></b-icon-plus>
                    Cargar Presupuestos
                </b-button>
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
                    <b-badge variant="danger">Hubo un problema al cargar el egreso</b-badge>
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
                v-bind:confirmarAccion="confirmarNuevoPresupuesto"
                v-bind:cancelarAccion="cancelarNuevoPresupuesto"
            ></agregar-presupuesto>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios';
import {convertDate, getCookie} from '../util/utils.js'
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
                    imagenDelDocumento: null
                },
                items: []
            },
            idEgreso: null,
            falloInput: false,
            falloCarga: false,
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
            ]
        }
    },
    computed: {
        state(data) {
            console.log(data);
            return false;
        }
    },
    inject: ['errorHandling', 'createToast', 'showLoginModal'],
    methods: {
        getCookie: getCookie,
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
            
            // Comienzo cargando el proveedor
            this.crearProveedorAPI();

        },
        crearProveedorAPI() {
            if (this.proveedorAAgregar != null) {
                // Hay que agregar un proveedor antes de agregar el egreso
                console.log("POST '/api/proveedor'");
                console.log(JSON.stringify(this.proveedorAAgregar, null, 4));

                axios
                    .post('/api/proveedor', this.proveedorAAgregar)
                    .then(response => {
                        var data = response.data;

                        if(data.code == 200) {
                            this.egreso.proveedor = data.id;
                            this.crearEgresoAPI()
                        } else if (data.code == 403) {
                            this.showLoginModal(true);
                        } else if (data.code == 400) {
                            this.falloCarga = true;
                        } else {
                            this.falloCarga = true;
                        }
                    })
                    .catch(error => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    })
                    .then(() => {
                        // allways
                    })
                /*
                $.ajax({
                    url: "http://{{ ip }}/api/proveedor",
                    type: "POST",
                    dataType: "json",
                    context: this,
                    data: JSON.stringify(this.proveedorAAgregar),
                    success(response) {
                        if(response.code == 403) {
                            app.showSessionEndedAlert(false);
                        } else if(response.code == 400) {
                            // Fallo la carga de proveedor
                            this.falloCarga = true;
                        } else if(response.code == 200) {
                            // El proveedor se creo exitosamente
                            this.egreso.proveedor = response.id;
                            // Creo el egreso
                            this.crearEgresoAPI();
                        } else {
                            // Fallo la carga de proveedor (default)
                            this.falloCarga = true;
                            app.createCommonErrors(response);
                        }
                    },
                    error(data) {
                        this.falloCarga = true;
                        app.createCommonErrors(data);
                    }
                });
                */
            } else {
                this.crearEgresoAPI()
            }
        },
        crearEgresoAPI() {
            var egresoToSend = JSON.parse(JSON.stringify(this.egreso));
            egresoToSend.items.pop();

            console.log("POST '/api/operaciones/egreso'");
            console.log(JSON.stringify(egresoToSend, null, 4));

            axios
                .post('/api/operaciones/egreso', egresoToSend)
                .then(response => {
                    var data = response.data;

                    if(data.code == 200) {
                        this.idEgreso = data.id;
                        this.crearPresupuestosAPI();
                    } else if (data.code == 403) {
                        this.showLoginModal(true);
                    } else if (data.code == 400) {
                        this.falloCarga = true;
                    } else {
                        this.falloCarga = true;
                    }
                })
                .catch(error => {
                    this.errorHandling(error);
                    this.falloCarga = true;
                })
                .then(() => {
                    // allways
                })
            /*
            $.ajax({
                url: "http://{{ ip }}/api/operaciones/egreso",
                type: "POST",
                dataType: "json",
                context: this,
                data: JSON.stringify(egresoToSend),
                success(response) {
                    if(response.code == 403) {
                        app.showSessionEndedAlert(false);
                    } else if(response.code == 400) {
                        // Fallo la carga de egreso
                        this.falloCarga = true;
                    } else if(response.code == 200) {
                        // El egreso se creo exitosamente
                        this.idEgreso = response.id;
                        // Creo los presupuestos
                        this.crearPresupuestosAPI();
                    } else {
                        // Fallo la carga de egreso (default)
                        this.falloCarga = true;
                        app.createCommonErrors(response);
                    }
                },
                error(data) {
                    this.falloCarga = true;
                    app.createCommonErrors(data);
                }
            });
            */
        },
        crearPresupuestosAPI() {
            // Proximamente
            this.asociarCategoriasAPI();
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

                axios
                    .post('/api/categorias/asociar', request)
                    .then(response => {
                        var data = response.data;

                        if(data.code == 200) {
                            this.asociarIngresoAPI();
                        } else if (data.code == 403) {
                            this.showLoginModal(true);
                        } else if (data.code == 400) {
                            this.falloCarga = true;
                        } else {
                            this.falloCarga = true;
                        }
                    })
                    .catch(error => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    })
                    .then(() => {
                        // allways
                    })

                /*
                $.ajax({
                    url: "http://{{ ip }}/api/categorias/asociar",
                    type: "POST",
                    dataType: "json",
                    context: this,
                    data: JSON.stringify(request),
                    success(response) {
                        if(response.code == 403) {
                            app.showSessionEndedAlert(false);
                        } else if(response.code == 400) {
                            // Fallo la asociacion de categorias
                            this.falloCarga = true;
                        } else if(response.code == 200) {
                            // Las categorias se asociaron correctamente
                            console.log(response);
                            this.asociarIngresoAPI();
                        } else {
                            // Fallo la asociacion de categorias (default)
                            this.falloCarga = true;
                            app.createCommonErrors(response);
                        }
                    },
                    error(data) {
                        this.falloCarga = true;
                        app.createCommonErrors(data);
                    }
                });
                */
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

                axios
                    .post('/api/operaciones/asociarManualmente', request)
                    .then(response => {
                        var data = response.data;

                        if(data.code == 200) {
                            this.createToast('Guardado exitoso', 'Se dio de alta el egreso correctamente', 'success');
                            this.$router.push('/operaciones/egreso');
                        } else if (data.code == 403) {
                            this.showLoginModal(true);
                        } else if (data.code == 400) {
                            this.falloCarga = true;
                        } else {
                            this.falloCarga = true;
                        }
                    })
                    .catch(error => {
                        this.errorHandling(error);
                        this.falloCarga = true;
                    })
                    .then(() => {
                        // allways
                    })
                /*
                $.ajax({
                    url: "http://{{ ip }}/api/operaciones/asociarManualmente",
                    type: "POST",
                    dataType: "json",
                    context: this,
                    data: JSON.stringify(request),
                    success(response) {
                        if(response.code == 403) {
                            app.showSessionEndedAlert(false);
                        } else if(response.code == 400) {
                            // Fallo la asociacion de ingreso
                            this.falloCarga = true;
                        } else if(response.code == 200) {
                            // El ingreso se asoció correctamente
                            console.log(response);
                            app.createToast('Guardado exitoso', 'Se dio de alta el egreso correctamente', 'success');
                            this.$router.push('/operaciones/egreso');
                        } else {
                            // Fallo la asociacion de ingreso (default)
                            this.falloCarga = true;
                            app.createCommonErrors(response);
                        }
                    },
                    error(data) {
                        this.falloCarga = true;
                        app.createCommonErrors(data);
                    }
                });
                */
            } else {
                this.$router.push('/operaciones/egreso');
            }
        },
        cargarProveedoresAPI() {
            this.proveedoresLoading = true;

            axios
                .get('/api/proveedores')
                .then(response => {
                    var data = response.data;

                    if(data.code == 200) {
                        this.proveedoresSelect = data.data.map(this.proveedoresAPIConverter);
                    } else if (data.code == 403) {
                        this.showLoginModal(true);
                    }
                })
                .catch(error => {
                    this.errorHandling(error);
                })
                .then(() => {
                    // allways
                    this.proveedoresLoading = false;
                })
            /*
            var request = {
                url: "http://{{ ip }}/api/proveedores",
                type: "GET",
                dataType: "json",
                context: this,
                cache: false,
                success(response) {
                    if(response.code == 403) {
                        app.showSessionEndedAlert(true);
                    } else if(response.code == 200) {
                        this.proveedoresSelect = response.data.map(this.proveedoresAPIConverter);
                    }
                    else {
                        app.createCommonErrors(response);
                    }
                },
                error(data) {
                    app.createCommonErrors(data);
                },
                complete() {
                    this.proveedoresLoading = false;
                }
            };
            $.ajax(request);
            */
        },
        proveedoresAPIConverter(proveedor) {
            return {
                value: proveedor.id,
                text: proveedor.nombre
            }
        },
        cargarMediosDePagoAPI() {
            this.mediosDePagoLoading = true;

            axios
                .get('/api/medios')
                .then(response => {
                    var data = response.data;

                    if(data.code == 200) {
                        this.mediosDePagoSelect = data.data.map(this.mediosDePagoAPIConverter);
                    } else if (data.code == 403) {
                        this.showLoginModal(true);
                    }
                })
                .catch(error => {
                    this.errorHandling(error);
                })
                .then(() => {
                    // allways
                    this.mediosDePagoLoading = false;
                })
            /*
            var request = {
                url: "http://{{ ip }}/api/medios",
                type: "GET",
                dataType: "json",
                context: this,
                cache: false,
                success(response) {
                    if(response.code == 403) {
                        app.showSessionEndedAlert(true);
                    } else if(response.code == 200) {
                        this.mediosDePagoSelect = response.data.map(this.mediosDePagoAPIConverter);
                    }
                    else {
                        app.createCommonErrors(response);
                    }
                },
                error(data) {
                    app.createCommonErrors(data);
                },
                complete() {
                    this.mediosDePagoLoading = false;
                }
            };
            $.ajax(request);
            */
        },
        mediosDePagoAPIConverter(medioDePago) {
            return {
                value: medioDePago.id,
                text: medioDePago.medioDePago.charAt(0).toUpperCase() + medioDePago.medioDePago.slice(1), // Le pongo mayuscula la primer letra
                type: medioDePago.medioDePago
            }
        },
        actualizarItems(items) {
            this.egreso.items = items;
        },
        // Proveedores
        confirmarNuevoProveedor(data) {
            this.$bvModal.hide('modal-agregar-proveedor');
            console.log(data);

            this.proveedorAAgregar = data;
            this.egreso.proveedor = "nuevoProveedor";
        },
        cancelarNuevoProveedor() {
            this.$bvModal.hide('modal-agregar-proveedor');
        },
        // Presupuestos
        confirmarNuevoPresupuesto(data) {
            this.$bvModal.hide('modal-agregar-presupuesto');
            console.log(data);

            this.presupuestosAAgregar.push(data);
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
            console.log(data);

            this.categoriasAAsociar = data;
        },
        cancelarAsociarCategorias() {
            this.$bvModal.hide('modal-asociar-categoria');
        },
        // Ingreso
        confirmarAsociarIngreso(data) {
            this.$bvModal.hide('modal-asociar-ingreso');
            console.log(data);

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
    },
    watch: {
        'egreso.medioDePago.id': function (medioDePagoId) {

        }
    }
}
</script>

<style>

</style>