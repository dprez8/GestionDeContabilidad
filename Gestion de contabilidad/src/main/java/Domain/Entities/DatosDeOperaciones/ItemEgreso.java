package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.Egreso.Egreso;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name="item_egreso")
public class ItemEgreso extends EntidadPersistente {

	@Expose
	@ManyToOne
	@JoinColumn(name = "item_id", referencedColumnName="id")
	private Item item;

	@Expose
	@Column
	private int cantidad;

	@Expose
	@Column
	private double precio;

	@ManyToOne
	@JoinColumn(name= "egreso_id", referencedColumnName = "id")
	private Egreso egresoAsociado;

	public ItemEgreso() {
	}

    public ItemEgreso(Item item, int cantidad, double precio) {
            this.item = item;
            this.cantidad = cantidad;
            this.precio = precio;
    }
    public double valorTotal(){
        return this.cantidad * this.precio;
    }

	public void setItem(Item item) {
		this.item =item;
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
	public Item getItem() {
		return item;
	}
    /********************************/
}


