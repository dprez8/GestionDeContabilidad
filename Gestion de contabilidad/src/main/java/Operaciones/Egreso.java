package Operaciones;

import Exceptions.noAlcanzaIngreso;
import Usuarios.Estandar;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import DatosDeOperaciones.*;
import Organizacion.*;

public class Egreso extends Operacion {

	private DocumentoComercial documento;
	private List<ItemEgreso> items;
	private MedioDePago medioDePago;
	private Proveedor proveedor;
	private List<Presupuesto> presupuestos;
	private List<Estandar> revisores;
	private Ingreso ingresoAsociado;
	private CategoriaOperacion categoria;
	private Organizacion organizacion;
	private boolean validado;
	private double valorTotal;

	public Egreso(int operacionNumero) {
		super(operacionNumero);
		this.items = new ArrayList<>();
		this.presupuestos = new ArrayList<>();
		this.revisores = new ArrayList<>();
		this.validado = false;
	}

	/**Getters*/
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

	public void setMedioDePago(MedioDePago medioDePago) {
		this.medioDePago = medioDePago;
	}

	public void setValidado(boolean validado) {
		this.validado = validado;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public void setCategoria(CategoriaOperacion categoria) {
		this.categoria = categoria;
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