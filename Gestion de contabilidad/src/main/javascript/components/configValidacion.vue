<template>
    <b-overlay class="w-100 h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="text-center p-2">
            <div>
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
                    <b-overlay rounded variant="light" :show="cambiarHora || cambiarMinuto" @shown="focusInput">
                        <template #overlay>
                            <b-form @submit.prevent="cambiarHora = false" v-if="cambiarHora">
                                <b-form-input ref="inputHora" class="bg-secondary text-light text-center border-secondary" v-model="scheduler.horaInicio" style="font-size: 2rem;"></b-form-input>
                            </b-form>
                            <b-form @submit.prevent="cambiarMinuto = false" v-else>
                                <b-form-input ref="inputMinuto" class="bg-secondary text-light text-center border-secondary" v-model="scheduler.minutoInicio" style="font-size: 2rem;"></b-form-input>
                            </b-form>
                        </template>
                        <div class="d-flex p-2 align-items-center">
                            <b-button-group vertical>
                                <b-button @click="scheduler.horaInicio++">
                                    <b-icon-chevron-compact-up></b-icon-chevron-compact-up>
                                </b-button>
                                <b-button style="font-size: 2rem;" @click="cambiarHora = true">
                                    {{scheduler.horaInicio}}
                                </b-button>
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
                                <b-button style="font-size: 2rem;" @click="cambiarMinuto = true">
                                    {{scheduler.minutoInicio}}
                                </b-button>
                                <b-button @click="scheduler.minutoInicio--">
                                    <b-icon-chevron-compact-down></b-icon-chevron-compact-down>
                                </b-button>
                            </b-button-group>
                        </div>
                    </b-overlay>
                </div>
            </div>
            <div class="d-flex justify-content-center">
                <b-button variant="primary" @click="guardarConfiguracionAPI">Guardar Configuración</b-button>
            </div>
        </div>
    </b-overlay>
</template>

<script>
import axios from 'axios'

export default {
    data() {
        return {
            scheduler: {
                horaInicio: 0,
                minutoInicio: 0,
                dias: []
            },
            diasOptions: [
                {value: 1, text: "Lu"},
                {value: 2, text: "Ma"},
                {value: 3, text: "Mi"},
                {value: 4, text: "Ju"},
                {value: 5, text: "Vi"},
                {value: 6, text: "Sa"},
                {value: 7, text: "Do"}
            ],
            loading: false,
            cambiarHora: false,
            cambiarMinuto: false
        }
    },
    inject: ['showLoginModal', 'errorHandling', 'createToast'],
    methods: {
        cargarConfiguracionAPI() {
            this.loading = true;

            axios
                .get('/api/bandeja/configuracion')
                .then(response => {
                    var data = response.data;
                    if(data.code == 200) {
                        this.scheduler = data.scheduler;
                        this.updateHoraYMinuto();
                    } else if (data.code == 403) {
                        this.showLoginModal(true);
                    }
                })
                .catch(error => {
                    this.errorHandling(error);
                })
                .then(() => {
                    // allways
                    this.loading = false;
                });
        },
        guardarConfiguracionAPI() {
            this.loading = true;

            axios
                .post('/api/bandeja/configurar', this.scheduler)
                .then(response => {
                    var data = response.data;
                    if(data.code == 200) {
                        this.createToast('Guardado exitoso', 'Se guardo la configuración correctamente', 'success');
                        this.updateHoraYMinuto();
                    } else if (data.code == 403) {
                        this.showLoginModal(false);
                    }
                })
                .catch(error => {
                    this.errorHandling(error);
                })
                .then(() => {
                    // allways
                    this.loading = false;
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
            if(hora > 23) {
                this.scheduler.horaInicio = 0
            } else if(hora < 0) {
                this.scheduler.horaInicio = 23
            }
        },
        formatMinuto(minuto) {
            if(minuto > 59) {
                this.scheduler.minutoInicio = 0;
                this.scheduler.horaInicio++;
            } else if(minuto < 0) {
                this.scheduler.minutoInicio = 59;
                this.scheduler.horaInicio--;
            }
        },
        focusInput() {
            if(this.cambiarHora) {
                this.$refs.inputHora.focus();
                this.$refs.inputHora.select();
            } else if(this.cambiarMinuto) {
                this.$refs.inputMinuto.focus();
                this.$refs.inputMinuto.select();
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