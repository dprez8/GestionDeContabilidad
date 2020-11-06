<template>
    <div>
        <p>Seleccione el Ingreso para asociar a este Egreso</p>
        <b-input-group class="my-2" style="flex-wrap: nowrap">
            <b-input-group-prepend>
                <b-button variant="outline" class="border-secondary" 
                ><b-icon-search></b-icon-search></b-button>
            </b-input-group-prepend>
            <b-form-input class="border-secondary" placeholder="Buscar Ingreso"></b-form-input>
        </b-input-group>
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

            axios
                .get('/api/operaciones/ingresos')
                .then(response => {
                    var data = response.data;

                    if(data.code == 200) {
                        this.ingresosSelect = data.ingresos.map(this.ingresosAPIConverter);
                    } else if (data.code == 403) {
                        this.showLoginModal(true);
                    }
                })
                .catch(error => {
                    this.errorHandling(error);
                })
                .then(() => {
                    // allways
                    this.ingresosLoading = false;
                })
        },
        ingresosAPIConverter(ingresoAPI) {
            return {
                value: {
                    id: ingresoAPI.id, 
                    montoTotal: ingresoAPI.montoTotal, 
                    descripcion: ingresoAPI.descripcion
                },
                text: ingresoAPI.descripcion + ' - $' + ingresoAPI.montoTotal
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