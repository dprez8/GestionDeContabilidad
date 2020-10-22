package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.Presupuesto;
import javax.persistence.*;

@Entity
@Table(name="item_presupuesto")
public class ItemPresupuesto extends EntidadPersistente {

	@ManyToOne
    @JoinColumn(name="producto_id", referencedColumnName = "id")
	private Producto producto;

	@Column
	private int cantidad;
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

    public ItemPresupuesto(Producto unProducto,ItemEgreso unItem, int cantidad, double precio, Presupuesto presupuesto){
        this.producto = unProducto;
        this.itemEgresoAsociado = unItem;
        this.cantidad = cantidad;
        this.precio = precio;
        this.presupuesto= presupuesto;
    }
    
    public ItemPresupuesto(Producto unProducto,ItemEgreso unItem, int cantidad, double precio){
        this.producto = unProducto;
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
	public Producto getProducto() {
		return producto;
	}
    public ItemEgreso getItemEgresoAsociado() {
        return itemEgresoAsociado;
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

    public void setItemEgresoAsociado(ItemEgreso itemEgresoAsociado) {
        this.itemEgresoAsociado = itemEgresoAsociado;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }
    /**********************/
}
