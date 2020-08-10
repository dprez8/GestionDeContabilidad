package Domain.Operaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Domain.DatosDeOperaciones.DocumentoComercial;
import Domain.DatosDeOperaciones.ItemPresupuesto;
import Domain.DatosDeOperaciones.Proveedor;
import Domain.Operaciones.Egreso.Egreso;

public class Presupuesto {
    private int operacionNumero;
    private List<ItemPresupuesto> items;
    private Egreso egresoAsociado;
    private DocumentoComercial documento;
    private String fechaVigente;
    private CategoriaOperacion categoria;
    private Proveedor proveedor;
    private Double valorTotal;
    

    public Presupuesto(int operacionNumero, Egreso unEgreso){
        this.operacionNumero = operacionNumero;
        this.egresoAsociado = unEgreso;
        this.items = new ArrayList<>();
    }
    /**Getters*/
    public List<ItemPresupuesto> getItems() {
		return items;
	}
	public DocumentoComercial getDocumento() {
		return documento;
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
	public Egreso getEgresoAsociado() {
		return egresoAsociado;
	}
    public String getFechaVigente() {
        return fechaVigente;
    }
    public CategoriaOperacion getCategoria() {
        return categoria;
    }
    /**Setters*/
    public void setCategoria(CategoriaOperacion categoria) {
        this.categoria = categoria;
    }

	private void addItems (ItemPresupuesto ... unosItems) {
        Collections.addAll(this.items, unosItems);
        this.valorTotal = calcularValorTotal();
    }

    public void setDocumento(DocumentoComercial documento) {
        this.documento = documento;
    }

    public void setFechaVigente(String fechaVigente) {
        this.fechaVigente = fechaVigente;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /********************************************/
    private Double calcularValorTotal(){
        return items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
    }
}
