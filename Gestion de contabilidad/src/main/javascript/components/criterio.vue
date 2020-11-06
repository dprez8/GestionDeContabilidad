<template>
    <li class="list-group-item p-0">
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
</template>

<script>
export default {
    name: 'criterio',
    props: {
        criterio: Object,
        seleccionoCategoria: Function
    },
    data() {
        return {
            isOpen: false
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