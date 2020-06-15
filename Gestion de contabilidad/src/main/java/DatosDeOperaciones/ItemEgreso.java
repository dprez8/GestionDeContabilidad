package DatosDeOperaciones;

public class ItemEgreso {
	private Producto producto;
	private int cantidad;
	private double precio;

    public ItemEgreso(Producto producto, int cantidad, double precio) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precio = precio;
    }
    public double valorTotal(){
        return this.cantidad * this.precio;
    }
}


