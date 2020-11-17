<template>
    <b-overlay class="w-100 h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="p-4 d-flex justify-content-center" style="background: #eee; min-height: 100%">
            <div class="w-100" style="max-width: 768px;">
                <b-card title="Vinculación automática" sub-title="Criterios para la vinculación de Egresos e Ingresos">
                    <b-select :options="entidadesOptions" size="sm" v-model="entidad">
                        <template #first>
                            <b-select-option disabled :value="null">--Seleccione Entidad--</b-select-option>
                        </template>
                    </b-select>
                    <b-form-checkbox-group
                        class="pt-2"
                        v-model="criteriosSeleccionados"
                        :options="criterios"
                        stacked
                    ></b-form-checkbox-group>
                    <transition name="fade">
                        <b-button variant="primary" class="mt-2" v-if="entidad" size="sm" @click="guardarConfiguracionAPI">Guardar Configuración</b-button>
                    </transition>
                </b-card>
            </div>
        </div>
    </b-overlay>
</template>

<script>
import { RequestHelper } from '../util/utils';

export default {
    data() {
        return {
            criterios: [
                {
                    text: "Orden por fecha",
                    value: "Fecha",
                },
                {
                    text: "Orden por valor (Primero Egreso)",
                    value: "OrdenValorPrimeroEgreso",
                },
                {
                    text: "Orden por valor (Primero Ingreso)",
                    value: "OrdenValorPrimeroIngreso",
                }
            ],
            entidad: null,
            entidadesOptions: [],
            criteriosSeleccionados: [],
            loading: false
        }
    },
    inject: ['showLoginModal', 'errorHandling', 'createToast'],
    methods: {
        cargarEntidadesAPI() {
            this.loading = true;

            RequestHelper.get('/api/admin/entidades', {
                success: (data) => {
                    // Cargo las entidades jurídicas y despues las base
                    this.entidadesOptions = data.entidadesJuridicas.map((juridica) => {
                        return {
                            text: `${juridica.nombreFicticio} - Entidad Jurídica`,
                            value: juridica.id
                        }
                    });

                    data.entidadesJuridicas.forEach((juridica) => {
                        juridica.entidadesBase.forEach((base) => {
                            this.entidadesOptions.push({
                                text: `${base.nombreFicticio} - Entidad Base`,
                                value: base.id
                            });
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
                    this.loading = false;
                }
            });
        },
        guardarConfiguracionAPI() {

            this.loading = true;

            var request = {
                organizacionId: this.entidad,
                criterios: this.criteriosSeleccionados
            }

            console.log(JSON.stringify(request, null, 4));

            RequestHelper.post('/api/admin/configurarVinculador', request, {
                success: (data) => {
                    this.createToast('Guardado exitoso', 'Se guardo la configuración correctamente', 'success');
                    console.log(data);
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                failed: (error) => {
                    console.log(error);
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                    this.loading = false;
                },
                default: (data) => {
                    console.log(data);
                }
            });
        }
    },
    mounted() {
        this.cargarEntidadesAPI();
    }
}
</script>

<style>
</style>