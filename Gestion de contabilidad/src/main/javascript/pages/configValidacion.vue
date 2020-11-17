<template>
    <b-overlay class="w-100 h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="p-4 d-flex justify-content-center" style="background: #eee; min-height: 100%">
            <div class="w-100" style="max-width: 425px;">
                <b-select :options="entidadesOptions" v-model="scheduler.entidad">
                    <template #first>
                        <b-select-option disabled :value="null">--Seleccione Entidad--</b-select-option>
                    </template>
                </b-select>
                <transition name="fade">
                    <div v-if="scheduler.entidad" class="text-center">
                        <p class="mt-4 mb-2">Días en los que se ejecutará la validación de egresos</p>
                        <div class="d-flex justify-content-center">
                            <template v-for="(dia, index) in diasOptions">
                                <b-button class="mr-1 p-0 rounded-circle text-center" 
                                    :key="index"
                                    style="width: 40px; height: 40px;"
                                    :variant="diaIsSelected(dia.value) ? 'primary' : 'outline-secondary'"
                                    @click="toggleDia(dia.value)"
                                >{{dia.text}}</b-button>
                            </template>
                        </div>
                        <p class="mt-4 mb-2">Horario de inicio</p>
                        <div class="d-flex justify-content-center mb-5">
                            <div class="d-flex p-2 align-items-center">
                                <b-button-group vertical>
                                    <b-button @click="scheduler.horaInicio++">
                                        <b-icon-chevron-compact-up></b-icon-chevron-compact-up>
                                    </b-button>
                                    <b-form-input class="bg-secondary text-light border-secondary rounded-0 text-center"
                                    style="font-size: 2rem; width: 70px" 
                                    v-model="scheduler.horaInicio" 
                                    lazy></b-form-input>
                                    <b-button @click="scheduler.horaInicio--">
                                        <b-icon-chevron-compact-down></b-icon-chevron-compact-down>
                                    </b-button>
                                </b-button-group>
                                <div class="p-2">
                                    <b-icon-dot class="d-block"></b-icon-dot>
                                    <b-icon-dot class="d-block"></b-icon-dot>
                                </div>
                                <b-button-group vertical>
                                    <b-button @click="scheduler.minutoInicio++">
                                        <b-icon-chevron-compact-up></b-icon-chevron-compact-up>
                                    </b-button>
                                    <b-form-input class="bg-secondary text-light border-secondary rounded-0 text-center"
                                    style="font-size: 2rem; width: 70px" 
                                    v-model="scheduler.minutoInicio"
                                    lazy></b-form-input>
                                    <b-button @click="scheduler.minutoInicio--">
                                        <b-icon-chevron-compact-down></b-icon-chevron-compact-down>
                                    </b-button>
                                </b-button-group>
                            </div>
                        </div>
                        <div class="d-flex justify-content-center">
                            <b-button variant="primary" @click="guardarConfiguracionAPI">Guardar Configuración</b-button>
                        </div>
                    </div>
                </transition>
            </div>
        </div>
    </b-overlay>
</template>

<script>
import axios from 'axios'
import { RequestHelper } from '../util/utils';

export default {
    data() {
        return {
            scheduler: {
                entidad: null,
                horaInicio: 0,
                minutoInicio: 0,
                dias: []
            },
            entidadesOptions: [
                {text: "EEEEEE - Entidad Jurídica", value: 1}
            ],
            diasOptions: [
                {value: 1, text: "Lu"},
                {value: 2, text: "Ma"},
                {value: 3, text: "Mi"},
                {value: 4, text: "Ju"},
                {value: 5, text: "Vi"},
                {value: 6, text: "Sa"},
                {value: 7, text: "Do"}
            ],
            loading: false
        }
    },
    inject: ['showLoginModal', 'errorHandling', 'createToast'],
    methods: {
        cargarConfiguracionAPI() {
            this.loading = true;

            RequestHelper.get('/api/bandeja/configuracion', {
                success: (data) => {
                    this.scheduler = data.schedulerInit;
                    this.updateHoraYMinuto();
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
                    this.loading = false;
                }
            });
        },
        guardarConfiguracionAPI() {
            this.loading = true;

            RequestHelper.post('/api/admin/bandeja/configurar', this.scheduler, {
                success: (data) => {
                    this.createToast('Guardado exitoso', 'Se guardo la configuración correctamente', 'success');
                    this.updateHoraYMinuto();
                },
                notLoggedIn: () => {
                    this.showLoginModal(false);
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                    this.loading = false;
                }
            });
        },
        toggleDia(dia) {
            if(this.diaIsSelected(dia)) {
                this.scheduler.dias.splice(this.getDiaIndex(dia), 1);
            } else {
                this.scheduler.dias.push(dia);
            }
        },
        diaIsSelected(dia) {
            return this.getDiaIndex(dia) != - 1;
        },
        getDiaIndex(dia) {
            return this.scheduler.dias.indexOf(dia);
        },
        updateHoraYMinuto() {
            this.scheduler.horaInicio = ('0' + this.scheduler.horaInicio).slice(-2);
            this.scheduler.minutoInicio = ('0' + this.scheduler.minutoInicio).slice(-2);
        },
        formatHora(hora) {
            if(isNaN(hora))
                this.scheduler.horaInicio = 0;

            if(hora > 23) {
                this.scheduler.horaInicio = 0
            } else if(hora < 0) {
                this.scheduler.horaInicio = 23
            }
        },
        formatMinuto(minuto) {
            if(isNaN(minuto))
                this.scheduler.minutoInicio = 0;

            if(minuto > 59) {
                this.scheduler.minutoInicio = 0;
                this.scheduler.horaInicio++;
            } else if(minuto < 0) {
                this.scheduler.minutoInicio = 59;
                this.scheduler.horaInicio--;
            }
        }
    },
    mounted() {
        this.cargarConfiguracionAPI()
    },
    watch: {
        'scheduler.horaInicio'(hora) {
            this.formatHora(hora);
            this.updateHoraYMinuto();
        },
        'scheduler.minutoInicio'(minuto) {
            this.formatMinuto(minuto);
            this.updateHoraYMinuto();
        }
    }
}
</script>

<style>

</style>