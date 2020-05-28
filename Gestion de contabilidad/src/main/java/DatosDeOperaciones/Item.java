package DatosDeOperaciones;


public class Item {
	public Producto producto;
	public int cantidad;
	
	public Item(Producto producto, int cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	public Double valorTotal(){
		return cantidad*producto.precio();
	}
}