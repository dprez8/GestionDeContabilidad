package DatosDeOperaciones;

public class ItemPresupuesto {
    private ItemEgreso itemEgresoAsociado;
    private Producto producto;
    private int cantidad;
    private double precio;

    public ItemPresupuesto(Producto unProducto,ItemEgreso unItem, int cantidad, double precio){
        this.producto = unProducto;
        this.itemEgresoAsociado = unItem;
        this.cantidad = cantidad;
        this.precio = precio;
    }
    public double valorTotal(){
        return this.cantidad * this.precio;
    }
    
    public int getCantidad() {
		return cantidad;
	}

	public double getPrecio() {
		return precio;
	}
	public Producto getProducto() {
		return producto;
	}
    public ItemEgreso getItemEgresoAsociado() {
        return itemEgresoAsociado;
    }
}
