package Domain.Entities.Operaciones.Egreso;

import Domain.Entities.Operaciones.CategoriaOperacion;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Operaciones.Operacion;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Usuarios.Estandar;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Organizacion.*;
import com.google.gson.annotations.Expose;


import javax.persistence.*;

@Entity
@Table(name="egreso")
public class Egreso extends Operacion {

	@Expose
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="documento_comercial_id", referencedColumnName = "id")
	private DocumentoComercial documento;

	@Expose
	@OneToMany(mappedBy = "egresoAsociado",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<ItemEgreso> items;

	@Expose
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name= "pago_id",referencedColumnName = "id")
	private Pago pago;

	@Expose
	@ManyToOne
	@JoinColumn(name = "proveedor_id", referencedColumnName = "proveedor_id")
	private Proveedor proveedor;

	@Expose
	@OneToMany(mappedBy = "egresoAsociado",cascade = CascadeType.ALL)
	private List<Presupuesto> presupuestos;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "egreso_x_revisor")
	private List<Estandar> revisores;

	@Expose
	@ManyToOne
	@JoinColumn(name = "ingreso_asociado", referencedColumnName = "id")
	private Ingreso ingresoAsociado;

	@Expose
	@ManyToMany
	private List<CategoriaOperacion> categorias;

	@Expose
	@Column
	private boolean validado;

	@Expose
	@Column
	private double valorTotal;
	
	@Expose
	@Column
	private int cantidadPresupuestos;

	public Egreso() {
		this.items = new ArrayList<>();
		this.presupuestos = new ArrayList<>();
		this.revisores = new ArrayList<>();
		this.categorias = new ArrayList<>();
		this.validado = false;
		this.cantidadPresupuestos=0;
	}

	/**Getters*/
	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
	}

	public int getCantidadPresupuestos() {
		return cantidadPresupuestos;
	}

	public Pago getPago(){
		return pago;
	}

	public DocumentoComercial getDocumento() {
		return documento;
	}
	public List<ItemEgreso> getItems() {
		return items;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public Organizacion getOrganizacion() {
		return organizacion;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public List<Estandar> getRevisores(){ return this.revisores;}
	public Ingreso getIngresoAsociado() { return this.ingresoAsociado; }

	/**Setters*/
	public void setDocumento(DocumentoComercial documento) {
		this.documento = documento;
	}
	public void setPago(Pago pago) {
		this.pago = pago;
	}
	public void setValidado(boolean validado) {
		this.validado = validado;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public void addCategorias(CategoriaOperacion ... categorias) {
		Collections.addAll(this.categorias, categorias);
	}
	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
	public void setIngresoAsociado(Ingreso ingreso) { this.ingresoAsociado = ingreso; }
	public void setItems(List<ItemEgreso> items) {
		this.items = items;
	}

	public void setPresupuestos(List<Presupuesto> presupuestos) {
		this.presupuestos = presupuestos;
	}
	public void setCategorias(List<CategoriaOperacion> categorias) {
		this.categorias = categorias;
	}
	public void addItems (ItemEgreso ... unosItems) {
		Collections.addAll(this.items, unosItems);
	}
	
	public void agregarItems(List<ItemEgreso> items){
		
		for(ItemEgreso item:items) {
			this.addItems(item);
		}
	}
	public void addPresupuestos (Presupuesto ... presupuesto) {
		Collections.addAll(this.presupuestos, presupuesto);
	}
	public void addRevisores(Estandar ... revisor) {
		Collections.addAll(this.revisores, revisor);
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public void setCantidadPresupuestos(int cantidadPresupuestos) {
		this.cantidadPresupuestos = cantidadPresupuestos;
	}


	/************************************************/
	public double calcularValorTotal () {
		return  this.items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
		// Retorna la sumatoria del precio de cada item del egreso
	}

	public boolean isValidado() {
		return this.validado;
	}

}