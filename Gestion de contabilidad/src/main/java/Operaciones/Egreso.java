package Operaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import DatosDeOperaciones.Item;
import DatosDeOperaciones.MedioDePago;
import DatosDeOperaciones.Proveedor;
import DatosDeOperaciones.DocumentoComercial;
import Usuarios.Estandar;


public class Egreso extends Operacion {
	
	private DocumentoComercial documento;
	private ArrayList<Item> items = new ArrayList<>();
	private MedioDePago medioDePago;
	private Proveedor proveedor;

	
	public Egreso(Estandar generadorDeEgreso, Date fechaOperacion, DocumentoComercial documento,
			MedioDePago medioDePago, Proveedor proveedor, Item ...items) {
		this.generadorOperacion = generadorDeEgreso;
		this.fechaOperacion=fechaOperacion;
		this.documento = documento;
		this.medioDePago = medioDePago;
		this.proveedor = proveedor;
		this.agregarItems(items);
	}
	
	public Egreso() {
	    items = new ArrayList<>();
	}
				
	public void AddItem(Item item){
	items.add(item);
}	


	public void generarEgreso() {
	
	generadorOperacion.darAltaEgreso(this); //paso un Egreso ya formado par que el Usuario Estandar responsable lo de de alta
}

public void agregarItems(Item ... unosItems){
    Collections.addAll(this.items,unosItems);
}
public double valorTotal(){
    return  items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
    //Retorna la sumatoria del precio de cada item del egreso
}
}