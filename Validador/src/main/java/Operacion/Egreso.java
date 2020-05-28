package Operacion;

import java.util.ArrayList;
import java.util.Date;

import Usuarios.Estandar; //Problema: Estandar importa de Egreso y Egreso de Usuario (solventar)

public class Egreso extends Operacion {
	
	private DocumentoComercial documento;
	private ArrayList<Item> items = new ArrayList<>();
	private MedioDePago medioDePago;
	private Proveedor proveedor;
	private double precioTotal;
	
	public Egreso(Estandar generadorDeEgreso, Date fechaOperacion, DocumentoComercial documento,
			MedioDePago medioDePago, Proveedor proveedor, Item ...items) {
		super();
		this.generadorOperacion = generadorDeEgreso;
		this.fechaOperacion=fechaOperacion;
		this.documento = documento;
		this.medioDePago = medioDePago;
		this.proveedor = proveedor;
		this.AddItem(items);
	}

public void AddItem(Item item[]){ //Quiero poder añadir los items directo desde el constructor...
	for(int i=0;i<item.length;i++) {
			//items.AddItem(item[i]);
	}
}			
public void AddItem(Item item){
	items.add(item);
}	


public void precioTotal(){
	//precioTotal= this.valorTotal();
}

public void generarEgreso() {
	
	generadorOperacion.darAltaEgreso(this); //paso un Egreso ya formado par que el Usuario Estandar responsable lo de de alta
}
	/*private Double valorTotal(){
	    return items.forEach(item->item.valorTotal()); hay q sumar los precios de cada item,pero no se como sumarlos luego del forEach
	}*/
}
