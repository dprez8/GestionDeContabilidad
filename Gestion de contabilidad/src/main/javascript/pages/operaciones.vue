<template>
    <div class="px-1 px-md-3 px-lg-5 py-4 d-flex justify-content-center">
        <div class="operaciones-wrapper">
            <b-nav tabs>
                <b-nav-item :active="egresoOpened()" to="/operaciones/egreso">Egresos</b-nav-item>
                <b-nav-item :active="ingresoOpened()" to="/operaciones/ingreso">Ingresos</b-nav-item>
            </b-nav>
            <transition name="fade" mode="out-in">
                <div v-if="!egresoOpened() && !ingresoOpened()" class="border border-top-0 d-flex justify-content-center align-items-center p-2" key="vacio" style="height: 300px;">
                    <div class="text-center text-secondary">
                        <b-img class="thinking_emoji" :src="require('../assets/thinking.svg')"/>
                        <p class="pb-4">Seleccione algún tipo de operación</p>
                    </div>
                </div>
                <router-view></router-view>
            </transition>
        </div>
    </div>
</template>

<script>
export default {
    methods: {
        egresoOpened() {
            return (this.$route.name == 'egresos' || this.$route.name == 'egreso');
        },
        ingresoOpened() {
            return (this.$route.name == 'ingresos');
        },
    },
}
</script>

<style>
.thinking_emoji {
    width: 15%; opacity: 0.7;
}
.operaciones-wrapper{
    width: 100%;
    max-width: 768px;
}
</style>