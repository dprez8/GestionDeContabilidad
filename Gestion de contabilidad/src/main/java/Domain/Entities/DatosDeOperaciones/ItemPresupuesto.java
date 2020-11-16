package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.Presupuesto;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name="item_presupuesto")
public class ItemPresupuesto extends EntidadPersistente {

    @Expose
	@ManyToOne
    @JoinColumn(name="item_id", referencedColumnName = "id")
	private Item item;

	@Expose
	@Column
	private int cantidad;

	@Expose
	@Column
	private double precio;

	@ManyToOne
    @JoinColumn(name="item_egreso_id", referencedColumnName = "id")
    private ItemEgreso itemEgresoAsociado;

	@ManyToOne
    @JoinColumn(name = "presupuesto_id", referencedColumnName = "id")
    private Presupuesto presupuesto;

    public ItemPresupuesto(){
    }

    public ItemPresupuesto(Item item,ItemEgreso unItem, int cantidad, double precio, Presupuesto presupuesto){
        this.item = item;
        this.itemEgresoAsociado = unItem;
        this.cantidad = cantidad;
        this.precio = precio;
        this.presupuesto= presupuesto;
    }
    
    public ItemPresupuesto(Item item,ItemEgreso unItem, int cantidad, double precio){
        this.item = item;
        this.itemEgresoAsociado = unItem;
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
	public Item getItem() {
		return item;
	}
    public ItemEgreso getItemEgresoAsociado() {
        return itemEgresoAsociado;
    }

    public void setProducto(Item item) {
        this.item = item;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setItemEgresoAsociado(ItemEgreso itemEgresoAsociado) {
        this.itemEgresoAsociado = itemEgresoAsociado;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

	public void setItem(Item item) {
		this.item = item;
	}
    
    
    /**********************/
}
