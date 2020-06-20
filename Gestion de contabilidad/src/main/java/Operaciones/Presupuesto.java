package Operaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import DatosDeOperaciones.DocumentoComercial;
import DatosDeOperaciones.ItemPresupuesto;
import DatosDeOperaciones.Proveedor;

public class Presupuesto {
    private List<ItemPresupuesto> items = new ArrayList<>();
    private Egreso egresoAsociado;
    private DocumentoComercial documento;
    private String fechaVigente;
    private CategoriaOperacion categoria;
    //private Proveedor proveedor;
    

    public Presupuesto(Egreso unEgreso,DocumentoComercial unDocumento,String fechaVigente,ItemPresupuesto ... items){
        this.egresoAsociado = unEgreso;
        this.documento = unDocumento;
        this.fechaVigente = fechaVigente;
        this.agregarItems(items);
    }
    
    

    public List<ItemPresupuesto> getItems() {
		return items;
	}


	public DocumentoComercial getDocumento() {
		return documento;
	}

	

	public Egreso getEgresoAsociado() {
		return egresoAsociado;
	}


	private void agregarItems (ItemPresupuesto ... unosItems) {
        Collections.addAll(this.items, unosItems);
    }
    public double total(){
        return items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
    }

    public void setCategoria(CategoriaOperacion categoria) {
        this.categoria = categoria;
    }
}
