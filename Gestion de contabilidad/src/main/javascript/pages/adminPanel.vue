<template>
    <div class="row h-100 m-0">
        <b-list-group flush class="col-4 overflow-auto h-100 p-0 border-right" style="max-width: 300px;">
            <template v-for="(item, key) in items">
                <b-list-group-item 
                    :key="key"
                    class="d-flex justify-content-between align-items-center" 
                    :to="item.to" 
                    :active="$route.name == item.routeName"
                >{{item.text}}</b-list-group-item>
            </template>
        </b-list-group>
        <div class="col overflow-auto h-100 p-0" style="background-color: #eee;">
            <transition name="fade" mode="out-in">
                <router-view></router-view>
            </transition>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            items: [
                {text: 'Usuarios', routeName: 'configUsuarios', to: '/panel/usuarios'},
                {text: 'Criterios', routeName: 'configCriterios', to: '/panel/criterios'},
                {text: 'Vinculacion de Operaciones', routeName: 'configVinculacion', to: '/panel/vinculacion'},
                {text: 'Validacion de Egresos', routeName: 'configValidacion', to: '/panel/validaciones'}
            ]
        }
    }
    
}
</script>

<style>

</style>