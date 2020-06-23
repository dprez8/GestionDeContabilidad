package Operaciones;

import Usuarios.Estandar;
import java.util.ArrayList;

import java.util.Date;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import DatosDeOperaciones.*;
import Organizacion.EntidadJuridica;
import Organizacion.Organizacion;

public class Egreso extends Operacion {
	
	private DocumentoComercial documento;
	private List<ItemEgreso> items = new ArrayList<>();
	private MedioDePago medioDePago;
	private Proveedor proveedor;
	private List<Presupuesto> presupuestos = new ArrayList<>();
	private List<Estandar> revisores = new ArrayList<>();
	private Ingreso ingresoAsociado;
	private CategoriaOperacion categoria;
	private boolean validado;

	public Egreso(DocumentoComercial documento,
				  MedioDePago medioDePago, Proveedor proveedor, Organizacion organizacion,
				  ItemEgreso ...items) {
		this.fechaOperacion = new Date();
		this.documento = documento;
		this.medioDePago = medioDePago;
		this.proveedor = proveedor;
		this.organizacion = organizacion;
		this.validado = false;
		this.agregarItems(items);
	}

    public List<Presupuesto> getPresupuestos() {
		return presupuestos;
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

	public void agregarItems (ItemEgreso ... unosItems) {
        Collections.addAll(this.items, unosItems);
    }
    
    public void agregarPresupuestos (Presupuesto ... presupuesto) {
        Collections.addAll(this.presupuestos, presupuesto);
    }

    public void darseDeAltaRevisor(Estandar revisor) {

	}
    public void agregarRevisor(Estandar ... revisor) {
		Collections.addAll(this.revisores, revisor);
    }

    public List<Estandar> obtenerRevisores(){ return this.revisores;}

    public Double valorTotal () {
        return  items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
        // Retorna la sumatoria del precio de cada item del egreso
    }

	public void setCategoria(CategoriaOperacion categoria) {
		this.categoria = categoria;
	}

	public void setIngresoAsociado(Ingreso ingreso) {
		this.ingresoAsociado = ingreso;
	}

	public boolean isValidado() {
		return validado;
	}
}