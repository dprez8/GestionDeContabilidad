package DatosDeOperaciones;

public class Producto {
	private String descripcion;
	private double precio;
	
	
	public Producto(String descripcion, Double precio) {
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public double precio() {
		return precio;
	}
}