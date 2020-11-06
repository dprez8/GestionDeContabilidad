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
	@JoinColumn(name = "tipo_id", referencedColumnName="id")
	private TipoItem tipo;

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

    public ItemEgreso(TipoItem tipo, int cantidad, double precio) {
            this.tipo = tipo;
            this.cantidad = cantidad;
            this.precio = precio;
    }
    public double valorTotal(){
        return this.cantidad * this.precio;
    }

	public void setTipo(TipoItem tipo) {
		this.tipo = tipo;
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
	public TipoItem getTipo() {
		return tipo;
	}
    /********************************/
}


