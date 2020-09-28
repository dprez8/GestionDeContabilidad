package Domain.Entities.DatosDeOperaciones;

import javax.persistence.*;

public class ItemEgreso {
	@Id
	@Column(name="item_egreso_id")
	private int idItemEgreso;
	@ManyToOne
	private Producto producto;
	@Column
	private int cantidad;
	@Column
	private double precio;

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


