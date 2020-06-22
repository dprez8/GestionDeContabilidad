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
	private boolean pasoValidaciones;
	private int validaciones;
	private boolean fueVerificado;

	public Egreso(DocumentoComercial documento,
				  MedioDePago medioDePago, Proveedor proveedor, Organizacion organizacion,
				  ItemEgreso ...items) {
		this.fechaOperacion = new Date();
		this.documento = documento;
		this.medioDePago = medioDePago;
		this.proveedor = proveedor;
		this.organizacion = organizacion;
		this.agregarItems(items);
		this.pasoValidaciones=false; //si paso todas las validadiones
		this.validaciones=0; //contador de validaciones pasadas
		this.fueVerificado=false; //indica si el validador ya fue sometido a las validaciones
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
	
	public int cantidadValidaciones(){
		return validaciones;
	}

	public void validar(){
		this.pasoValidaciones=true;
	}
	
	public boolean fueVerificado(){
		return fueVerificado;
	}
	
	public void yaVerificado(){
		fueVerificado=true;
	}
	
	public void agregarItems (ItemEgreso ... unosItems) {
        Collections.addAll(this.items, unosItems);
    }
    
    public void agregarPresupuestos (Presupuesto ... presupuesto) {
        Collections.addAll(this.presupuestos, presupuesto);
    }
    
    public void agregarRevisor(Estandar ... revisor) {
        Collections.addAll(this.revisores, revisor);
    }

    public double valorTotal () {
        return  items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
        // Retorna la sumatoria del precio de cada item del egreso
    }

	public void reiniciarValidaciones() {
		this.validaciones=0;
	}
	
	public void pasoValidacion(){
		this.validaciones++;
	}
}