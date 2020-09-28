package Domain.Operaciones.Egreso;

import Domain.Exceptions.noAlcanzaIngreso;
import Domain.Operaciones.CategoriaOperacion;
import Domain.Operaciones.Ingreso;
import Domain.Operaciones.Operacion;
import Domain.Operaciones.Presupuesto;
import Domain.Usuarios.Estandar;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import Domain.DatosDeOperaciones.*;
import Domain.Organizacion.*;

import javax.persistence.*;

@Entity
@Table(name="egreso")
public class Egreso extends Operacion {

	@OneToOne
	@JoinColumn(name="documento_comercial_id", referencedColumnName = "documento_comercial_id")
	private DocumentoComercial documento;
	@OneToMany(mappedBy = "egresoAsociado")
	private List<ItemEgreso> items;
	@OneToOne
	@JoinColumn(name= "pago_id", referencedColumnName = "pago_id")
	private Pago pago;
	@ManyToOne
	@JoinColumn(name = "proveedor_id", referencedColumnName = "proveedor_id")
	private Proveedor proveedor;
	@OneToMany(mappedBy = "egresoAsociado")
	private List<Presupuesto> presupuestos;
	@Transient
	private List<Estandar> revisores;
	@ManyToOne
	@JoinColumn(name = "ingreso_asociado", referencedColumnName = "id")
	private Ingreso ingresoAsociado;
	@Transient
	private List<CategoriaOperacion> categorias;
	@Column
	private boolean validado;
	@Column
	private double valorTotal;

	public Egreso() {
		this.fechaCarga= new Date();
		this.items = new ArrayList<>();
		this.presupuestos = new ArrayList<>();
		this.revisores = new ArrayList<>();
		this.categorias = new ArrayList<>();
		this.validado = false;
	}

	/**Getters*/
	public List<Presupuesto> getPresupuestos() {
		return presupuestos;
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

	public int getOperacionNumero() {
		return this.operacionNumero;
	}
	public Organizacion getOrganizacion() {
		return organizacion;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public List<Estandar> getRevisores(){ return this.revisores;}

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
	public void setIngresoAsociado(Ingreso ingreso) throws noAlcanzaIngreso {
		if(ingreso.montoSobrante() >= this.valorTotal) {
			this.ingresoAsociado.desasociarEgreso(this);
			this.ingresoAsociado = ingreso;
			ingreso.asociarEgreso(this);
		}
		else throw new noAlcanzaIngreso("El monto del ingreso asociado es menor al monto del egreso");
	}

	public void addItems (ItemEgreso ... unosItems) {
		Collections.addAll(this.items, unosItems);
		this.valorTotal = calcularValorTotal();
	}

	public void addPresupuestos (Presupuesto ... presupuesto) {
		Collections.addAll(this.presupuestos, presupuesto);
	}

	public void addRevisores(Estandar ... revisor) {
		Collections.addAll(this.revisores, revisor);
	}

	/************************************************/
	private double calcularValorTotal () {
		return  this.items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
		// Retorna la sumatoria del precio de cada item del egreso
	}

	public boolean isValidado() {
		return this.validado;
	}

}