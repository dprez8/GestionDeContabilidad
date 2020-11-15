<template>
    <li class="list-group-item p-0" v-if="!edit">
        <b-button class="text-left p-2 criterio-toggle" block variant="outline" v-b-toggle="(criterio.id)">
            <b-icon-caret-right-fill scale="0.5"></b-icon-caret-right-fill>
            {{ criterio.name }}
        </b-button>
        <b-collapse v-bind:id="criterio.id">
            <template v-for="(categoria, index) in criterio.categorias">
                <b-form-checkbox :key="index" class="py-2 ml-2"
                    @input="seleccionoCategoria({id: categoria.id, name: categoria.name}, $event)"
                >{{ categoria.name }}</b-form-checkbox>
            </template>
            <ul class="list-group p-2" v-if="criterio.criterios.length">
                <criterio
                    v-for="(criterio, index) in criterio.criterios"
                    :key="index"
                    :criterio="criterio"
                    :selecciono-categoria="seleccionoCategoria"
                ></criterio>
            </ul>
        </b-collapse>
    </li>
    <li class="list-group-item p-0" v-else>
        <b-input-group>
            <template #prepend>
                <b-button class="criterio-toggle" block variant="outline" v-b-toggle="(criterio.id)">
                    <b-icon-caret-right-fill scale="0.5"></b-icon-caret-right-fill>
                </b-button>
            </template>
            <b-form-input class="border-0" placeholder="Criterio" v-model="criterio.name" @change="updateCriterioAPI(criterio.id, criterio.name)"></b-form-input>
            <template #append>
                <b-button block variant="outline" v-b-toggle="(criterio.id)">
                    <b-icon-x></b-icon-x>
                </b-button>
            </template>
        </b-input-group>
        <b-collapse v-bind:id="criterio.id">
            <template v-for="(categoria, index) in criterio.categorias">
                <b-input-group :key="index">
                    <b-form-input class="border-0" placeholder="Categoría" v-model="categoria.name" @change="updateCategoriaAPI(criterio.id, categoria.name)"></b-form-input>
                    <template #append>
                        <b-button block variant="outline" v-b-toggle="(criterio.id)">
                            <b-icon-x></b-icon-x>
                        </b-button>
                    </template>
                </b-input-group>
            </template>
            <ul class="list-group p-2" v-if="criterio.criterios.length">
                <criterio
                    v-for="(criterio, index) in criterio.criterios"
                    :key="index"
                    :criterio="criterio"
                    :edit="true"
                    :selecciono-categoria="seleccionoCategoria"
                ></criterio>
            </ul>
        </b-collapse>
    </li>
</template>

<script>
export default {
    name: 'criterio',
    props: {
        edit: Boolean,
        criterio: Object,
        seleccionoCategoria: Function
    },
    data() {
        return {
            isOpen: false
        }
    },
    methods: {
        // Criterio methods
        createCriterio() {
            
        },
        deleteCriterio(id) {
            
        },
        // Criterio API
        updateCriterioAPI(id, value) {
            console.log(`Update: {id: ${id}, value: ${value}}`);
        },


        // Categoria methods
        // Categoria API
        updateCategoriaAPI(id, value) {
            console.log(`Update: {id: ${id}, value: ${value}}`);
        }
    }
}
</script>

<style>
.criterio-toggle > svg {
    transition: .2s transform;
}
.criterio-toggle.not-collapsed > svg {
    transform: rotate(90deg);
}
</style>