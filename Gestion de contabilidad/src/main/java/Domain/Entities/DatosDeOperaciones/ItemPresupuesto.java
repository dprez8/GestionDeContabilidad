package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.Operaciones.Presupuesto;
import javax.persistence.*;

@Entity
@Table(name="item_presupuesto")
public class ItemPresupuesto {
	
	@Id
	@Column(name="item_presupuesto_id")
	private int idItemPresupuesto;
	@ManyToOne
    @JoinColumn(name="producto_id", referencedColumnName = "producto_id")
	private Producto producto;
	@Column
	private int cantidad;
	@Column
	private double precio;
	@ManyToOne
    @JoinColumn(name="item_egreso_id", referencedColumnName = "item_egreso_id")
    private ItemEgreso itemEgresoAsociado;
	@ManyToOne
    @JoinColumn(name = "presupuesto_id", referencedColumnName = "presupuesto_id")
    private Presupuesto presupuesto;

    protected ItemPresupuesto(){
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
    /**********************/
}
