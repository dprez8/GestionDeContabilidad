<template>
    <!-- template para presupuesto -->
    <div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Fecha Vigente</strong></span>
            </div>
            <div class="col px-2">
                <b-form-datepicker placeholder="Seleccione fecha de vigencia" v-model="presupuesto.fechaVigente" class="mb-2"></b-form-datepicker>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Número operación</strong></span>
            </div>
            <div class="col px-2">
                <b-form-input placeholder="Ingrese número de operacion" v-model="presupuesto.numeroOperacion"></b-form-input>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-sm-4 text-sm-right py-2">
                <span class="mr-2"><strong>Items</strong></span>
            </div>
            <div class="col p-2">
                <tabla-items v-bind:actualizarItems="actualizarItems"></tabla-items>
            </div>
        </div>
        <div class="row mb-4 mx-2">
            <div class="col-4">

            </div>
            <div class="col d-flex justify-content-between px-2">
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

export default {
    props: {
        confirmarAccion: Function,
        cancelarAccion: Function
    },
    data() {
        return {
            presupuesto: {
                fechaVigente: null,
                numeroOperacion: null,
                items: []
            },
        }
    },
    methods: {
        confirmar() {
            if(this.confirmarAccion != null && this.confirmarAccion != undefined) {
                this.presupuesto.items.pop();
                this.confirmarAccion(this.presupuesto);
                return;
            }

            // Hacer algo si no se recibe la funcion confirmarAccion hacer algo default
        },
        actualizarItems(items) {
            this.presupuesto.items = items;
        }
    },
    components: {
        'tabla-items': tablaItems
    },
    watch: {
        'egreso.medioDePago.id': function (medioDePagoId) {

        }
    }
}
</script>

<style>

</style>