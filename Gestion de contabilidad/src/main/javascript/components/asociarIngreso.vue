<template>
    <div>
        <p>Seleccione el Ingreso para asociar a este Egreso</p>
        <b-overlay spinner-variant="primary" :show="ingresosLoading" rounded="sm">
            <b-form-select class="border-secondary" :options="ingresosSelect" v-model="ingresoSelected">
                <template #first>
                    <b-form-select-option :value="null" disabled>-- Selecciona un Ingreso --</b-form-select-option>
                </template>
            </b-form-select>
        </b-overlay>
        <b-button-group class="mt-5">
            <b-button variant="primary" @click="confirmarAccion(ingresoSelected)">Confirmar</b-button>
            <b-button variant="outline-dark" @click="cancelarAccion">Cancelar</b-button>
        </b-button-group>
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
            ingresosSelect: [],
            ingresoSelected: null,
            ingresosLoading: false
        }
    },
    inject: ['showLoginModal', 'errorHandling'],
    methods: {
        cargarIngresosAPI(){            
            this.ingresosLoading = true;

            RequestHelper.get('/api/operaciones/ingresos', {
                success: (data) => {
                    this.ingresosSelect = data.ingresos.map(this.ingresosAPIConverter);
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
                    this.ingresosLoading = false;
                }
            });
        },
        ingresosAPIConverter(ingresoAPI) {
            return {
                value: {
                    id: ingresoAPI.id, 
                    montoTotal: ingresoAPI.montoTotal, 
                    montoRestante: ingresoAPI.montoRestante, 
                    descripcion: ingresoAPI.descripcion
                },
                text: ingresoAPI.descripcion + ' - $' + ingresoAPI.montoRestante + '/$' + ingresoAPI.montoTotal
            }
        }
    },
    mounted() {
        this.cargarIngresosAPI();
    }
}
</script>

<style>

</style>