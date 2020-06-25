package Operaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import DatosDeOperaciones.DocumentoComercial;
import DatosDeOperaciones.ItemPresupuesto;
import DatosDeOperaciones.Proveedor;

public class Presupuesto {
    private int operacionNumero;
    private List<ItemPresupuesto> items = new ArrayList<>();
    private Egreso egresoAsociado;
    private DocumentoComercial documento;
    private String fechaVigente;
    private CategoriaOperacion categoria;
    private Proveedor proveedor;
    private Double valorTotal;
    

    public Presupuesto(int operacionNumero, Egreso unEgreso,DocumentoComercial unDocumento,String fechaVigente,Proveedor unProveedor,ItemPresupuesto ... items){
        this.operacionNumero = operacionNumero;
        this.egresoAsociado = unEgreso;
        this.documento = unDocumento;
        this.fechaVigente = fechaVigente;
        this.proveedor = unProveedor;
        this.agregarItems(items);
        this.valorTotal = this.calcularValorTotal();
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
    private Double calcularValorTotal(){
        return items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
    }

    public void setCategoria(CategoriaOperacion categoria) {
        this.categoria = categoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public int getOperacionNumero() {
        return operacionNumero;
    }

    public Double getValorTotal() {
        return valorTotal;
    }
}
