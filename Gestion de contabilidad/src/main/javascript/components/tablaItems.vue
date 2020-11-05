<template>
    <b-table small responsive hover bordered class="mb-0 rounded" :fields="campos_items" :items="items"
        head-variant="dark" foot-variant="light" foot-clone>
        <template #cell(nombreProducto)="row">
            <b-form-input class="border-0 px-2 bg-transparent" v-model="row.item.nombreProducto" @input="addItem"></b-form-input>
        </template>
        <template #cell(precio)="row">
            <b-form-input class="border-0 px-2 bg-transparent text-center" v-model="row.item.precio" @input="addItem"></b-form-input>
        </template>
        <template #cell(cantidad)="row">
            <b-form-input class="border-0 px-2 bg-transparent text-center" v-model="row.item.cantidad" @input="addItem"></b-form-input>
        </template>
        <template #cell(delete)="row">
            <b-button variant="outline" class="px-2 m-0 text-danger" @click="deleteItem(row.index)">
                <b-icon-x></b-icon-x>
            </b-button>
        </template>

        
        <template #foot(nombreProducto)>
            <span class="text-danger"></span>
        </template>
        <template #foot(cantidad)>
            <span class="text-danger">Total</span>
        </template>
        <template #foot(precio)>
            <span class="text-danger">{{ '$' + precioTotal() }}</span>
        </template>
    </b-table>
</template>

<script>
export default {
    props: {
        actualizarItems: Function
    },
    data() {
        return {
            items: [
                {
                    nombreProducto: "",
                    cantidad: "",
                    precio: ""
                }
            ],
            campos_items: [{
                    key: 'nombreProducto',
                    label: 'Producto',
                    tdClass: ['w-75']
                },
                {
                    key: 'cantidad',
                    label: 'Cantidad',
                    tdClass: [],
                    thClass: ['text-center']
                },
                {
                    key: 'precio',
                    label: 'Precio',
                    tdClass: ['w-25'],
                    thClass: ['text-center']
                },
                {
                    key: 'delete',
                    label: '',
                    tdClass: [],
                    thClass: ['text-center']
                }
            ]
        }
    },
    methods: {
        checkIfAddItem() {
            var items = this.items;
            var lastItemIndex = items.length - 1;
            var lastItem = items[lastItemIndex];

            if (lastItem == undefined ||
                lastItem.nombreProducto != "" ||
                lastItem.cantidad != "" ||
                lastItem.precio != ""
            ) {
                return true;
            }
            return false;
        },
        addItem() {
            if (this.checkIfAddItem()) {
                this.items.push({
                    nombreProducto: "",
                    cantidad: "",
                    precio: ""
                });
            }
            this.actualizarItems(this.items);
        },
        deleteItem(index) {
            this.items.splice(index, 1);
            this.addItem();
        },
        precioTotal() {
            var precio = 0;
            this.items.forEach(item => {
                var itemPrecio = parseFloat(item.precio);
                var itemCantidad = parseInt(item.cantidad);

                if(!isNaN(itemPrecio) && !isNaN(itemCantidad))
                    precio += itemPrecio * itemCantidad;
            });
            return precio;
        }
    }
}
</script>

<style>

</style>