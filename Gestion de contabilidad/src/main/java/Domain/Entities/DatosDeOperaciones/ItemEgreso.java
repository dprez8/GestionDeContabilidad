package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.Egreso.Egreso;

import javax.persistence.*;

@Entity
@Table(name="item_egreso")
public class ItemEgreso extends EntidadPersistente {

	@ManyToOne
	@JoinColumn(name = "producto_id", referencedColumnName="id")
	private Producto producto;

	@Column
	private int cantidad;
	@Column
	private double precio;
	@ManyToOne
	@JoinColumn(name= "egreso_id", referencedColumnName = "id")
	private Egreso egresoAsociado;

	public ItemEgreso() {
	}

    public ItemEgreso(Producto producto, int cantidad, double precio) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precio = precio;
    }
    public double valorTotal(){
        return this.cantidad * this.precio;
    }

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Egreso getEgresoAsociado() {
		return egresoAsociado;
	}

	public void setEgresoAsociado(Egreso egresoAsociado) {
		this.egresoAsociado = egresoAsociado;
	}

	/**Getters & Setters*/

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


