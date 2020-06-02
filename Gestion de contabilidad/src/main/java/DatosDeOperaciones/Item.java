package DatosDeOperaciones;

public abstract class Item {
	private Producto producto;
	private int cantidad;

    public Item(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
    }
    public double valorTotal(){
        return cantidad * producto.getPrecio();
    }
}


