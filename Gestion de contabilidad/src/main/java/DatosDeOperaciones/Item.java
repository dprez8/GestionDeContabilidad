package DatosDeOperaciones;



public class Item {
	private Producto producto;
	private int cantidad;
<<<<<<< HEAD
	
	public Item(Producto producto, int cantidad) {
		this.producto = producto;
		this.cantidad = cantidad;
	}
	
	public double valorTotal(){
		return cantidad*producto.precio();
	}
}
=======

    public Item(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }
    public double valorTotal(){
        return cantidad * producto.getPrecio();
    }
}

>>>>>>> operaciones
