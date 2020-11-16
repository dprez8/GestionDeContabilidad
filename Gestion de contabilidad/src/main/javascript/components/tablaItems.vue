<template>
    <div>
        <b-table small responsive hover bordered class="mb-0 rounded" v-if="!itemsReadOnly" :fields="campos_items" :items="items"
            head-variant="dark" foot-variant="light" foot-clone>
            <template #cell(tipoItem)="row">
                <b-form-select class="border-0 px-2 bg-transparent" v-model="row.item.tipoItem" :options="tipoItemsOptions" @input="addItem">
                    <template #first>
                        <b-form-select-option :value="null" disabled>-- Tipo --</b-form-select-option>
                    </template>
                </b-form-select>
            </template>
            <template #cell(descripcion)="row">
                <b-form-input class="border-0 px-2 bg-transparent" v-model="row.item.descripcion" list="items-list" @input="addItem();"></b-form-input>
            </template>
            <template #cell(precio)="row">
                <b-form-input type="number" class="border-0 px-2 bg-transparent text-center" v-model="row.item.precio" @input="addItem"></b-form-input>
            </template>
            <template #cell(cantidad)="row">
                <b-form-input type="number" class="border-0 px-2 bg-transparent text-center" v-model="row.item.cantidad" @input="addItem"></b-form-input>
            </template>
            <template #cell(delete)="row">
                <b-button variant="outline" class="px-2 m-0 text-danger" @click="deleteItem(row.index)">
                    <b-icon-x></b-icon-x>
                </b-button>
            </template>

            
            <template #foot(tipoItem)>
                <span class="text-danger"></span>
            </template>
            <template #foot(descripcion)>
                <span class="text-danger"></span>
            </template>
            <template #foot(cantidad)>
                <span class="text-danger">Total</span>
            </template>
            <template #foot(precio)>
                <span class="text-danger">{{ '$' + precioTotal() }}</span>
            </template>
        </b-table>        
        <b-table small responsive hover bordered class="mb-0 rounded" v-else :fields="campos_items" :items="itemsReadOnly"
            head-variant="dark" foot-variant="light" foot-clone>
            <template #cell(precio)="row">
                <b-form-input type="number" class="border-0 px-2 bg-transparent text-center" v-model="row.item.precio" @input="addItem"></b-form-input>
            </template>
            <template #cell(delete)="row">
                <b-button variant="outline" class="px-2 m-0 text-danger" @click="deleteItem(row.index)">
                    <b-icon-x></b-icon-x>
                </b-button>
            </template>
            
            <template #foot(tipoItem)>
                <span class="text-danger"></span>
            </template>
            <template #foot(descripcion)>
                <span class="text-danger"></span>
            </template>
            <template #foot(cantidad)>
                <span class="text-danger">Total</span>
            </template>
            <template #foot(precio)>
                <span class="text-danger">{{ '$' + precioTotal() }}</span>
            </template>
        </b-table>
        <b-form-datalist 
            id="items-list" :options="itemsOptions"
        ></b-form-datalist>
    </div>
</template>

<script>
import { RequestHelper } from '../util/utils';
export default {
    props: {
        itemsReadOnly: Array,
        actualizarItems: Function
    },
    data() {
        return {
            items: [
                {
                    tipoItem: null,
                    descripcion: "",
                    cantidad: "",
                    precio: ""
                }
            ],
            tipoItemsOptions: [],
            itemsOptions: [],
            campos_items: [{
                    key: 'tipoItem',
                    label: 'Tipo',
                    tdClass: ['align-middle', 'text-center'],
                    thClass: ['tipo-th']
                },{
                    key: 'descripcion',
                    label: 'Descripcion',
                    tdClass: ['align-middle'],
                    thClass: ['w-75']
                },
                {
                    key: 'cantidad',
                    label: 'Cantidad',
                    tdClass: ['align-middle', 'text-center'],
                    thClass: ['text-center']
                },
                {
                    key: 'precio',
                    label: 'Precio',
                    tdClass: ['align-middle'],
                    thClass: ['text-center', 'precio-th']
                },
                {
                    key: 'delete',
                    label: '',
                    tdClass: ['align-middle'],
                    thClass: ['text-center']
                }
            ]
        }
    },
    inject: ["errorHandling", "showLoginModal"],
    methods: {
        checkIfAddItem() {
            var items = this.items;
            var lastItemIndex = items.length - 1;
            var lastItem = items[lastItemIndex];

            if (lastItem == undefined       ||
                lastItem.descripcion != ""  ||
                lastItem.cantidad != ""     ||
                lastItem.precio != ""       ||
                lastItem.tipoItem != null
            ) {
                return true;
            }
            return false;
        },
        addItem() {
            if (this.checkIfAddItem()) {
                this.items.push({
                    tipoItem: null,
                    descripcion: "",
                    cantidad: "",
                    precio: ""
                });
            }
            if(this.itemsReadOnly) {
                this.actualizarItems(this.itemsReadOnly);
            }
            else
                this.actualizarItems(this.items);
        },
        deleteItem(index) {
            this.items.splice(index, 1);
            this.addItem();
        },
        precioTotal() {
            var precio = 0;
            var items = this.items;
            if(this.itemsReadOnly)
                items = this.itemsReadOnly;
                
            items.forEach(item => {
                var itemPrecio = parseFloat(item.precio);
                var itemCantidad = parseInt(item.cantidad);

                if(!isNaN(itemPrecio) && !isNaN(itemCantidad))
                    precio += itemPrecio * itemCantidad;
            });
            return precio;
        },
        searchItemAPI() {
            RequestHelper.get('/api/items', {
                success: (data) => {
                    console.log(data);
                    this.itemsOptions = data.items.map((item) => {
                        return item.descripcion;
                    })
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                }
            });
        },
        searchTipoItemAPI() {
            RequestHelper.get('/api/tipoItems', {
                success: (data) => {
                    console.log(data);
                    this.tipoItemsOptions = data.tipoItems.map((tipo) => {
                        return {
                            text: tipo.nombre,
                            value: {
                                id: tipo.id,
                                nombre: tipo.nombre
                            }
                        }
                    })
                },
                notLoggedIn: () => {
                    this.showLoginModal(true);
                },
                error: (error) => {
                    this.errorHandling(error);
                },
                forbidden: (error) => {
                    this.errorHandling(error);
                },
                always: () => {
                }
            });
        }
    },
    mounted() {
        if(this.itemsReadOnly)
            this.actualizarItems(this.itemsReadOnly);

        this.searchTipoItemAPI();
        this.searchItemAPI();
    }
}
</script>

<style>
    .tipo-th {
        min-width: 120px;
    }
    .precio-th {
        min-width: 100px;
    }
</style>