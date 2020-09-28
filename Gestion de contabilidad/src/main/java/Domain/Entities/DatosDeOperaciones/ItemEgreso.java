package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.Operaciones.Egreso.Egreso;

import javax.persistence.*;

@Entity
@Table(name="item_egreso")
public class ItemEgreso {
	@Id
	@GeneratedValue
	@Column(name="item_egreso_id")
	private int idItemEgreso;
	@ManyToOne
	@JoinColumn(referencedColumnName = "producto_id", name="producto_id")
	private Producto producto;
	@Column
	private int cantidad;
	@Column
	private double precio;
	@ManyToOne
	@JoinColumn(name= "egreso_id", referencedColumnName = "id")
	private Egreso egresoAsociado;

	protected ItemEgreso() {
	}

    public ItemEgreso(Producto producto, int cantidad, double precio) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precio = precio;
    }
    public double valorTotal(){
        return this.cantidad * this.precio;
    }

	/**Getters*/
	public int getCantidad() {
		return cantidad;
	}

	public double getPrecio() {
		return precio;
	}
	public Producto getProducto() {
		return producto;
	}
    /********************************/
}


