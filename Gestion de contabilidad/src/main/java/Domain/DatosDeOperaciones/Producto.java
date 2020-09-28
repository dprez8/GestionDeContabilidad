package Domain.DatosDeOperaciones;

import javax.persistence.*;

@Entity
@Table
public class Producto {
	
	@Id
    @GeneratedValue
	@Column(name="producto_id")
	private int idProducto;
	@Column(name="nombre_producto")
    private String nombreProducto;
	@Column
	private double precio;

    protected Producto(){
    }

    public Producto(String nombreProducto){
        this.nombreProducto = nombreProducto;
    }
    public String getNombreProducto() {
        return nombreProducto;
    }
}

