<template>
    <div class="border border-top-0">
        <b-overlay variant="primary" spinner-variant="light" :show="egresosLoading" class="border-bottom">
            <b-table show-empty :tbody-tr-class="rowStyle" class="mb-0 border-0 border-bottom table-row-pointer" head-variant="dark"
                :fields="campos_egresos" 
                :items="egresos"
                :tbody-transition-props="transProps"
                @row-clicked="toggleEgreso"
            >
                <template #cell(validado)="data">
                    <b-badge v-if="data.item.validado" variant="success">Validado</b-badge>
                    <b-badge v-else variant="warning">Sin Validar</b-badge>
                </template>

                <template #cell(valorTotal)="data">
                    <span>\{{ "$" + data.item.valorTotal }}</span>
                </template>
                <template #row-details="data">
                    <b-collapse :visible="data.item.showEgreso && egresoLoading" class="bg-light">
                        <div class="text-center p-4">
                            <b-spinner variant="primary" label="Spinning"></b-spinner>
                        </div>
                    </b-collapse>
                    <b-collapse :visible="data.item.showEgreso && !egresoLoading" class="bg-light">
                        <transition name="fade">
                            <egreso v-if="data.item.showEgreso" :egresoLoaded="egresoLoaded" :ingresos="ingresos"></egreso>
                        </transition>
                    </b-collapse>
                </template>

                <template #empty>
                    <h4>No hay egresos para mostrar</h4>
                </template>
            </b-table>
        </b-overlay>
        <b-button class="m-2" variant="outline-primary" to="/operaciones/egreso/agregar">
            <b-icon-plus></b-icon-plus>
            Agregar Egreso
        </b-button>
    </div>
</template>

<script>
import axios from 'axios'
import egreso from '../components/egreso'
import {convertDate} from '../util/utils'

export default {
    data() {
        return {
            egresos: [],
            egresoLoading: true,
            egresosLoading: false,
            egresoSelected: null,

            ingresosLoading: false,
            campos_egresos: [
                { key: 'name', label: 'Egreso', thClass:['w-100'], tdClass:[] },
                { key: 'validado', label: 'Estado', thClass:['text-center'], tdClass:['text-center'] },
                { key: 'valorTotal', label: 'Total', thClass:['text-center'], tdClass:['text-center'] },
                { key: 'fechaOperacion', label: 'Emitido', thClass:['text-center'], tdClass:['text-center'] }
            ],
            transProps: {
              name: "fade"
            }
        }
    },
    inject: ['showLoginModal'],
    methods: {
        cargarEgresosAPI(){
            this.egresosLoading = true;
            
            axios
                .get('http://gesoc.ddns.net/api/operaciones/egresos')
                .then(response => {
                    var data = response.data;
                    console.log(data);

                    if(data.code == 200) {
                        // aaa
                    } else if (data.code == 403) {
                        this.showLoginModal(true);
                    } else {
                        //app.createCommonErrors(data);
                    }
                });
            /*
            $.ajax({
                url: "http://{{ ip }}/api/operaciones/egresos",
                type: "GET",
                dataType: "json",
                context: this,
                cache: false,
                success(response) {
                    if(response.code == 403) {
                        app.showSessionEndedAlert(true);
                    } else if(response.code == 200) {
                        console.log(response);
                        this.egresos = response.egresos.map(this.egresosAPIConverter);
                    } else {
                        app.createCommonErrors(response);
                    }
                },
                error(data) {
                    app.createCommonErrors(data);
                },
                complete() {
                    this.egresosLoading = false;
                }
            });
            */
        },
        egresosAPIConverter(egresoAPI) {
            return {
                id: egresoAPI.id,
                name: 'Egreso ' + egresoAPI.id,
                validado: egresoAPI.validado,
                valorTotal: egresoAPI.montoTotal,
                fechaOperacion: convertDate(egresoAPI.fechaOperacion),
                _showDetails: true,
                showEgreso: false
            }
        },
        toggleEgreso(row) {
            if(!row.showEgreso) {
                this.$router.push('/operaciones/egreso/'+ row.id);
            } else {
                this.$router.push('/operaciones/egreso');
            }
        },
        seleccionarEgreso() {
            var id = this.$route.params.id;
            this.egresoLoading = true;

            this.egresos.forEach(egreso => {
                if(egreso.id != id)
                    egreso.showEgreso = false;
                else
                    egreso.showEgreso = true;
            });
        },
        egresoLoaded(){
            this.egresoLoading = false;
        },
        egresoOpened() {
            return (this.$route.name == 'egresos' || this.$route.name == 'egreso');
        },
        rowStyle(item, type) {
            if(type == 'row') {
                if(item.showEgreso) {
                    return ['bg-secondary', 'text-light'];
                }
            }
        }
    },
    components: {
        'egreso': egreso
    },
    watch: {
        $route(to, from) {
            if(this.$route.name == 'egresos')
                this.cargarEgresosAPI();

            this.seleccionarEgreso();
        },
        egresosLoading(to, from) {
            if(to == false) {
                this.seleccionarEgreso();
            }
        }
    },
    mounted() {
        this.cargarEgresosAPI();
        this.seleccionarEgreso();
    }
}
</script>

<style>

</style>