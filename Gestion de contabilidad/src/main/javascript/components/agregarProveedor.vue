<template>
    <!-- template para proveedor -->
    <div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Nombre</strong></span>
            </div>
            <div class="col px-2">
                <b-collapse :visible="(!proveedor.nombre && falloInput)">
                    <b-badge variant="danger">Ingrese el nombre del proveedor</b-badge>
                </b-collapse>
                <b-form-input placeholder="Ingrese nombre del proveedor" 
                    v-model="proveedor.nombre"
                    :state="(!proveedor.nombre && falloInput) ? false : null"
                ></b-form-input>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Documento</strong></span>
            </div>
            <div class="col px-2">
                <b-collapse :visible="(!proveedor.documento && falloInput)">
                    <b-badge variant="danger">Ingrese documento del proveedor</b-badge>
                </b-collapse>
                <b-form-input placeholder="Ingrese documento del proveedor" 
                    v-model="proveedor.documento"
                    :state="(!proveedor.documento && falloInput) ? false : null"
                ></b-form-input>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Dirección Postal</strong></span>
            </div>
            <div class="col p-2">
                <div class="p-2 container-fluid border border rounded">
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Pais</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!proveedor.pais && falloInput)">
                                <b-badge variant="danger">Seleccione un país</b-badge>
                            </b-collapse>
                            <b-overlay spinner-variant="primary" :show="paisesLoading" rounded="sm">
                                <b-form-select 
                                    :state="(!proveedor.pais && falloInput) ? false : null"
                                    v-model="proveedor.pais" :options="paises" @change="cargarProvinciasAPI">
                                    <template #first>
                                        <b-form-select-option :value="null" disabled>-- Selecciona un país --</b-form-select-option>
                                    </template>
                                </b-form-select>
                            </b-overlay>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Provincia</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!proveedor.provincia && falloInput)">
                                <b-badge variant="danger">Seleccione una provincia</b-badge>
                            </b-collapse>
                            <b-overlay spinner-variant="primary" :show="provinciasLoading" rounded="sm">
                                <b-form-select 
                                    :state="(!proveedor.provincia && falloInput) ? false : null"
                                    v-model="proveedor.provincia" :options="provincias" @change="cargarCiudadesAPI">
                                    <template #first>
                                        <b-form-select-option :value="null" disabled>-- Selecciona una provincia --</b-form-select-option>
                                    </template>
                                </b-form-select>
                            </b-overlay>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Ciudad</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!proveedor.ciudad && falloInput)">
                                <b-badge variant="danger">Seleccione una ciudad</b-badge>
                            </b-collapse>
                            <b-overlay spinner-variant="primary" :show="ciudadesLoading" rounded="sm">
                                <b-form-select 
                                    :state="(!proveedor.ciudad && falloInput) ? false : null"
                                    v-model="proveedor.ciudad" :options="ciudades">
                                    <template #first>
                                        <b-form-select-option :value="null" disabled>-- Selecciona una ciudad --</b-form-select-option>
                                    </template>
                                </b-form-select>
                            </b-overlay>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Calle</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!proveedor.calle && falloInput)">
                                <b-badge variant="danger">Ingrese calle</b-badge>
                            </b-collapse>
                            <b-form-input placeholder="Ingrese calle" 
                                :state="(!proveedor.calle && falloInput) ? false : null"
                                v-model="proveedor.calle"></b-form-input>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Altura</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!proveedor.altura && falloInput)">
                                <b-badge variant="danger">Ingrese altura</b-badge>
                            </b-collapse>
                            <b-form-input placeholder="Ingrese altura" 
                                type="number"
                                :state="(!proveedor.altura && falloInput) ? false : null"
                                v-model="proveedor.altura"></b-form-input>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Piso</strong></span>
                        </div>
                        <div class="col">
                            <b-form-input placeholder="Ingrese piso" 
                                type="number"
                                v-model="proveedor.piso"></b-form-input>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 text-sm-right py-2">
                            <span class="mr-2"><strong>Codigo Postal</strong></span>
                        </div>
                        <div class="col">
                            <b-collapse :visible="(!proveedor.codigoPostal && falloInput)">
                                <b-badge variant="danger">Ingrese codigo postal</b-badge>
                            </b-collapse>
                            <b-form-input placeholder="Ingrese codigo postal" 
                                type="number"
                                :state="(!proveedor.codigoPostal && falloInput) ? false : null"
                                v-model="proveedor.codigoPostal"></b-form-input>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-4">

            </div>
            <div class="col px-2">
                <b-collapse :visible="(falloInput)">
                    <b-badge variant="danger">Hay problemas en los campos</b-badge>
                </b-collapse>
                <b-button-group>
                    <b-button variant="primary" @click="confirmar()">Confirmar</b-button>
                    <b-button variant="outline-dark" @click="cancelarAccion">Cancelar</b-button>
                </b-button-group>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import {RequestHelper} from '../util/utils.js'

export default {
    props: {
        confirmarAccion: Function,
        cancelarAccion: Function
    },
    data() {
        return {
            proveedor: {
                nombre: null,
                documento: null,
                pais: null,
                provincia: null,
                ciudad: null,
                calle: "",
                altura: "",
                piso: "",
                codigoPostal: ""
            },
            paises: [],
            provincias: [],
            ciudades: [],
            paisesLoading: false,
            provinciasLoading: false,
            ciudadesLoading: false,
            falloInput: false
        }
    },
    inject: ['showLoginModal', 'errorHandling'],
    methods: {
        confirmar() {
            var todosLosCamposRellenos = 
                this.proveedor.nombre           &&
                this.proveedor.documento        &&
                this.proveedor.pais             &&
                this.proveedor.provincia        &&
                this.proveedor.ciudad           &&
                this.proveedor.calle            &&
                this.proveedor.altura           &&
                this.proveedor.codigoPostal;

            this.falloInput = !todosLosCamposRellenos;
            if(this.falloInput) {
                return;
            }

            if(this.confirmarAccion) {
                this.confirmarAccion(this.proveedor);
            }
        },
        cargarPaisesAPI() {
            this.loadingPaises = true;
            this.proveedor.pais = null;
            this.proveedor.provincia = null;
            this.proveedor.ciudad = null;

            RequestHelper.get('/api/pais', {
                success: (data) => {
                    this.paises = data.data.map(function(unPais){
                        return {
                            value: unPais.clave,
                            text: unPais.name
                        }
                    });
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
                    this.loadingPaises = false;
                }
            });
        },
        cargarProvinciasAPI() {
            this.provinciasLoading = true;
            this.proveedor.provincia = null;
            this.proveedor.ciudad = null;

            var paisID = this.proveedor.pais;
            if(paisID == null)
                return;

            RequestHelper.get(`/api/pais/${paisID}/provincia`, {
                success: (data) => {
                    this.provincias = data.data.map(function(unaProvincia){
                        return {
                            value: unaProvincia.clave,
                            text: unaProvincia.name
                        }
                    });
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
                    this.provinciasLoading = false;
                }
            });
        },
        cargarCiudadesAPI() {
            this.proveedor.ciudad = null;
            this.ciudadesLoading = true;

            var paisID = this.proveedor.pais;
            var provinciaID = this.proveedor.provincia;

            if(paisID == null || provinciaID == null)
                return;

            RequestHelper.get(`/api/pais/${paisID}/provincia/${provinciaID}/ciudad`, {
                success: (data) => {
                    this.ciudades = data.data.map(function(unaCiudad){
                        return {
                            value: unaCiudad.clave,
                            text: unaCiudad.name
                        }
                    });
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
                    this.ciudadesLoading = false;
                }
            });
        }
    },
    watch: {
        proveedor() {
            console.log("changed");
        }
    },
    mounted() {
        this.cargarPaisesAPI();
    }
}
</script>

<style>

</style>