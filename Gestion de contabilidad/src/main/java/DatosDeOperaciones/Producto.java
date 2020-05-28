package DatosDeOperaciones;

public class Producto {
	public String descripcion;
	public Double precio;
	
	
	public Producto(String descripcion, Double precio) {
		super();
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public Double precio() {
		return precio;
	}
}