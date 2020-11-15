<template>
    <b-overlay class="w-100 h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="p-4 d-flex justify-content-center" style="background: #eee; min-height: 100%">
            <div class="w-100" style="max-width: 768px;">
                <b-card title="Vinculación automática" sub-title="Criterios para la vinculación de Egresos e Ingresos">
                    <b-form-checkbox-group
                        class="pt-2"
                        v-model="criteriosSeleccionados"
                        :options="criterios"
                        stacked
                    ></b-form-checkbox-group>
                </b-card>
            </div>
        </div>
    </b-overlay>
</template>

<script>
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
            criteriosSeleccionados: [],
            loading: false
        }
    },
    methods: {
        guardarCambios() {
            console.log(this.criteriosSeleccionados);
        }
    },
    watch: {
        criteriosSeleccionados() {
            this.guardarCambios();
        }
    }
}
</script>

<style>
.user-card-skew::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: var(--primary);
    clip-path: polygon(0% 0%, 100% 0%, 0% 100%, 0% 100%);
    transition: all .2s;
}
</style>