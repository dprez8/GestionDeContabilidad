<template>
    <div class="pt-3">
        <div class="row mb-4 mx-2">
            <div class="col py-2 text-center">
                <h2>Cargar nuevo ingreso</h2>
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
import {convertDate, getCookie} from '../util/utils.js'

export default {
    data() {
        return {
            ingreso: {
                fechaOperacion: null,
                descripcion: null,
                montoTotal: null
            },
            falloInput: false,
            falloCargarIngreso: false,
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
                this.ingreso.fechaOperacion                               &&
                this.ingreso.montoTotal                          

            this.falloInput = !todosLosCamposRellenos;
            if(this.falloInput) {
                return;
            }
            
            var ingresoToSend = JSON.parse(JSON.stringify(this.ingreso));

            console.log("POST '/api/operaciones/ingreso'");
            console.log(JSON.stringify(ingresoToSend, null, 4));

            /*
            $.ajax({
                url: "http://{{ ip }}/api/operaciones/ingreso",
                type: "POST",
                dataType: "json",
                context: this,
                data: JSON.stringify(ingresoToSend),
                success(response) {
                    if(response.code == 403) {
                        app.showSessionEndedAlert(false);
                    } else if(response.code == 400) {
                        this.falloCargarIngreso = true;
                    } else if(response.code == 200) {
                        this.$router.push('/operaciones/ingreso');
                    } else {
                        app.createCommonErrors(response);
                    }
                },
                error(data) {
                    app.createCommonErrors(data);
                },
                complete() {
                    console.log("termino");
                }
            });
            */

            // Obtengo la id del ingreso que se creó
        },
    }
}
</script>

<style>

</style>