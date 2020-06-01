package DatosDeOperaciones;

public class Producto {
	private String descripcion;
	private double precio;
	
    public Producto(String descripcion, double precio){
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }
}

