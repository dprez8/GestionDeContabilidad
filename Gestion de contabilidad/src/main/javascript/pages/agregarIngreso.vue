<template>
    <div class="pt-3">
        <b-overlay spinner-variant="light" variant="primary" :show="ingresoLoading" no-wrap></b-overlay>
        <div class="row mb-4 mx-2">
            <div class="col py-2 text-center">
                <h2>Cargar nuevo ingreso</h2>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Entidad</strong></span>
            </div>
            <div class="col">
                <b-select :options="entidadesOptions" v-model="ingreso.organizacion_id"></b-select>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Descripción</strong></span>
            </div>
            <div class="col">
                <b-collapse :visible="(!ingreso.descripcion && falloInput)">
                    <b-badge variant="danger">Ingrese una descripcion para poder identificar el ingreso</b-badge>
                </b-collapse>
                <b-form-textarea rows="3" max-rows="6" placeholder="Breve descripción"
                    :state="(!ingreso.descripcion && falloInput) ? false : null"
                    v-model="ingreso.descripcion"></b-form-textarea>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Fecha Operación</strong></span>
            </div>
            <div class="col">
                <b-collapse :visible="(!ingreso.fechaOperacion && falloInput)">
                    <b-badge variant="danger">Ingrese la fecha de la operacion</b-badge>
                </b-collapse>
                <b-form-datepicker placeholder="Seleccione fecha de la Operación" class="mb-2"
                    :state="(!ingreso.fechaOperacion && falloInput) ? false : null" v-model="ingreso.fechaOperacion"></b-form-datepicker>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 col-lg-3 text-sm-right py-2">
                <span class="mr-2"><strong>Monto Total</strong></span>
            </div>
            <div class="col">
                <b-collapse :visible="(!ingreso.montoTotal && falloInput)">
                    <b-badge variant="danger">Ingrese el monto total</b-badge>
                </b-collapse>
                <b-form-input placeholder="Ingrese monto total"
                    :state="(!ingreso.montoTotal && falloInput) ? false : null"
                    v-model="ingreso.montoTotal"></b-form-input>
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
                <b-collapse :visible="(falloCargarIngreso)">
                    <b-badge variant="danger">Hubo un problema al cargar el ingreso</b-badge>
                </b-collapse>
                
                <b-button-group>
                    <b-button variant="primary" @click="confirmar">Confirmar</b-button>
                    <b-button variant="outline-dark" to="/operaciones/ingreso">Cancelar</b-button>
                </b-button-group>
            </div>
            <div class="col-lg-1 col-xl-3"></div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import {convertDate, getCookie, RequestHelper} from '../util/utils.js'

export default {
    data() {
        return {
            ingreso: {
                organizacion_id: null,
                fechaOperacion: null,
                fechaAceptacionEgresos: null,
                descripcion: null,
                montoTotal: null
            },
            entidadesOptions: [
                {text: "xd", value: 1}
            ],
            falloInput: false,
            falloCargarIngreso: false,
            ingresoLoading: false
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
            // Creo el ingreso
            var todosLosCamposRellenos = 
                this.ingreso.fechaOperacion &&
                this.ingreso.montoTotal                          

            this.falloInput = !todosLosCamposRellenos;
            if(this.falloInput) {
                return;
            }

            this.ingresoLoading = true;
            this.falloCargarIngreso = false;
            
            var ingresoToSend = JSON.parse(JSON.stringify(this.ingreso));

            console.log("POST '/api/operaciones/ingreso'");
            console.log(JSON.stringify(ingresoToSend, null, 4));

            RequestHelper.post('/api/operaciones/ingreso', ingresoToSend, {
                success: (data) => {
                    this.createToast('Guardado exitoso', 'Se dio de alta el ingreso correctamente', 'success');
                    this.$router.push('/operaciones/ingreso');
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                failed: () => {
                    this.falloCargarIngreso = true;
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                    this.ingresoLoading = false;
                }
            });
        },
    }
}
</script>

<style>

</style>