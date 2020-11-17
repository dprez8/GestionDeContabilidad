<template>
    <div class="px-1 px-md-3 px-lg-5 py-4 d-flex justify-content-center">
        <div class="operaciones-wrapper">
            <b-button variant="outline-primary" class="mb-4" @click="vincularAutomaticamenteAPI">Vinculación de Egresos e Ingresos <b-icon-vr/></b-button>
            <b-nav tabs>
                <b-nav-item :active="egresoOpened()" to="/operaciones/egreso">Egresos</b-nav-item>
                <b-nav-item :active="ingresoOpened()" to="/operaciones/ingreso">Ingresos</b-nav-item>
            </b-nav>
            <transition name="fade" mode="out-in">
                <div v-if="!egresoOpened() && !ingresoOpened()" class="border border-top-0 d-flex justify-content-center align-items-center p-2" key="vacio" style="height: 300px;">
                    <div class="text-center text-secondary">
                        <p class="pb-4">Seleccione algún tipo de operación</p>
                    </div>
                </div>
                <router-view></router-view>
            </transition>
        </div>
    </div>
</template>

<script>
import { RequestHelper } from '../util/utils';
export default {
    data() {
        return {
            publicPath: process.env.BASE_URL
        }
    },
    inject: ["showLoginModal", "createToast", "errorHandling"],
    methods: {
        egresoOpened() {
            return (this.$route.name == 'egresos' || this.$route.name == 'egreso');
        },
        ingresoOpened() {
            return (this.$route.name == 'ingresos');
        },
        vincularAutomaticamenteAPI() {
            RequestHelper.get("/api/operaciones/asociarAutomaticamente", {
                success: (data) => {
                    this.createToast('Vinculación exitosa', 'Se vinculador los Egresos con los Ingresos exitosamente', 'success');
                    console.log(data);
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                failed: (error) => {
                    this.createToast('Error', 'No se pudo vincular los Egresos e Ingresos, pruebe nuevamente mas tarde', "danger")
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
}
</script>

<style>
.operaciones-wrapper{
    width: 100%;
    max-width: 768px;
}
</style>