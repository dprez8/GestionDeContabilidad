package Domain.DatosDeOperaciones;

import Domain.Operaciones.Presupuesto;
import javax.persistence.*;

public class ItemPresupuesto {
	
	@Id
	@Column(name="item_presupuesto_id")
	private int idItemPresupuesto;
	@ManyToOne
	private Producto producto;
	@Column
	private int cantidad;
	@Column
	private double precio;
	@ManyToOne
    private ItemEgreso itemEgresoAsociado;
	@ManyToOne
    private Presupuesto presupuesto;
   
    

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
    /**********************/
}
