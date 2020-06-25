package Operaciones;

import Exceptions.noAlcanzaIngreso;
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
	private double valorTotal;

	public Egreso(int operacionNumero, DocumentoComercial documento,
				  MedioDePago medioDePago, Proveedor proveedor, Organizacion organizacion,
				  ItemEgreso ...items) {
		this.operacionNumero = operacionNumero;
		this.fechaOperacion = new Date();
		this.documento = documento;
		this.medioDePago = medioDePago;
		this.proveedor = proveedor;
		this.organizacion = organizacion;
		this.validado = false;
		this.agregarItems(items);
		this.valorTotal = calculaValorTotal();
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
		revisores.add(revisor);
	}
    public void agregarRevisor(Estandar ... revisor) {
		Collections.addAll(this.revisores, revisor);
    }

    public List<Estandar> obtenerRevisores(){ return this.revisores;}

    private double calculaValorTotal () {
        return  items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
        // Retorna la sumatoria del precio de cada item del egreso
    }

	public double getValorTotal() {
		return valorTotal;
	}

	public void setCategoria(CategoriaOperacion categoria) {
		this.categoria = categoria;
	}

	public void setIngresoAsociado(Ingreso ingreso) throws noAlcanzaIngreso {
		if(ingreso.montoSobrante() >= this.valorTotal) {
			this.ingresoAsociado.desasociarEgreso(this);
			this.ingresoAsociado = ingreso;
			ingreso.asociarEgreso(this);
		}
		else throw new noAlcanzaIngreso("El monto del ingreso asociado es menor al monto del egreso");
	}

	public boolean isValidado() {
		return validado;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public int getOperacionNumero() {
		return operacionNumero;
	}
}