<template>
    <div>
        <p>Seleccione categorias para asociar al egreso</p>
        <ul class="list-group">
            <criterio
                v-for="(criterio, index) in criterios"
                :key="index"
                :criterio="criterio"
                :selecciono-categoria="seleccionoCategoria"
            ></criterio>
        </ul>
        <div class="pt-2" v-if="categoriasSeleccionadas.length">
            <span>Categorias seleccionadas: </span>
            <template
                v-for="(categoria, index) in categoriasSeleccionadas"
            >
                <b-badge :key="index" class="mr-1">{{ categoria.name }}</b-badge>
            </template>
        </div>
        <div class="row pt-4">
            <div class="col d-flex justify-content-between">
                <b-button-group>
                    <b-button variant="primary" @click="confirmarAccion(categoriasSeleccionadas)">Confirmar</b-button>
                    <b-button variant="outline-dark" @click="cancelarAccion">Cancelar</b-button>
                </b-button-group>
            </div>
        </div>
    </div>
</template>

<script>
import criterio from './criterio.vue'

export default {
    props: {
        confirmarAccion: Function,
        cancelarAccion: Function
    },
    data() {
        return {
            criterios: [],
            criteriosAPI: [],
            categoriasSeleccionadas: [],
            categoriasLoading: false
        }
    },
    methods: {
        cargarCriteriosAPI() {
            this.categoriasLoading = false;

            /*
            $.ajax({
                url: "http://{{ ip }}/api/categorias",
                type: "GET",
                dataType: "json",
                context: this,
                cache: false,
                success(response) {
                    if(response.code == 403) {
                        app.showSessionEndedAlert(true);
                    } else if(response.code == 200) {
                        this.criteriosAPI = response.criterios;
                        this.procesarCriterios();
                    } else {
                        app.createCommonErrors(data);
                    }
                },
                error(data) {
                    app.createCommonErrors(data);
                },
                complete() {
                    this.categoriasLoading = false;
                }
            });
            */
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