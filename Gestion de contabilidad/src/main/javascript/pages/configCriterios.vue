<template>
    <b-overlay class="w-100 h-100" spinner-variant="light" variant="primary" :show="loading">
        <div class="p-4 d-flex justify-content-center" style="background: #eee; min-height: 100%">
            <div class="w-100" style="max-width: 768px;">
                <b-card 
                    title="Criterios y Categorías"
                    sub-title="Agregar, editar y eliminar criterios y categorías de Egresos" class="mb-4">
                    <ul class="list-group pt-2">
                        <criterio
                            v-for="(criterio, index) in criterios"
                            :key="index"
                            :criterio="criterio"
                            :edit="true"
                            :selecciono-categoria="seleccionoCategoria"
                        ></criterio>
                    </ul>
                </b-card>
            </div>
        </div>
    </b-overlay>
</template>

<script>
import { RequestHelper } from '../util/utils.js'
import criterio from '../components/criterio'

export default {
    props: {
        confirmarAccion: Function,
        cancelarAccion: Function
    },
    data() {
        return {
            criterios: [],
            criteriosAPI: [],
            loading: false
        }
    },
    inject: ['showLoginModal', 'errorHandling'],
    methods: {
        cargarCriteriosAPI() {
            this.loading = true;

            RequestHelper.get(`/api/categorias`, {
                success: (data) => {
                    this.criteriosAPI = data.criterios;
                    this.procesarCriterios();
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                    this.loading = false;
                }
            });
        },
        procesarCriterios() {
            this.criteriosAPI.forEach(criterio => {
                if(!criterio.categorias)
                    criterio.categorias = [];
                if(!criterio.criterios)
                    criterio.criterios = [];
                
                criterio.id = criterio.id.toString();

                if(criterio.idCriterioPadre) {
                    var criterioPadre = this.criterioPorId(criterio.idCriterioPadre);

                    if(!criterioPadre.criterios)
                        criterioPadre.criterios = [];

                    criterioPadre.criterios.push(criterio);
                }
            });

            var criteriosToDelete = [];
            var i = 0;

            for(i = 0; i < this.criteriosAPI.length; i++) {
                var criterio = this.criteriosAPI[i];

                if(criterio.idCriterioPadre) {
                    criteriosToDelete.push(i);
                } 
            }
            
            for (i = criteriosToDelete.length -1; i >= 0; i--)
                this.criteriosAPI.splice(criteriosToDelete[i], 1);

            this.criterios = this.criteriosAPI;
        },
        criterioPorId(id) {
            var criterioDefinido = null;
            this.criteriosAPI.forEach(criterio => {
                if(criterio.id == id) {
                    criterioDefinido = criterio;
                }
            });
            return criterioDefinido;
        },
        seleccionoCategoria(criterio, selected) {
            if (selected)
                this.categoriasSeleccionadas.push(criterio);
            else {
                var index = this.categoriasSeleccionadas.indexOf(criterio);
                this.categoriasSeleccionadas.splice(index, 1);
            }
        }
    },
    mounted() {
        this.cargarCriteriosAPI();
    },
    components: {
        'criterio': criterio
    }
}
</script>

<style>

</style>