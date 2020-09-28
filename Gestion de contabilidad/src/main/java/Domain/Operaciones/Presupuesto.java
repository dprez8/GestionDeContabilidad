package Domain.Operaciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Domain.DatosDeOperaciones.DocumentoComercial;
import Domain.DatosDeOperaciones.ItemPresupuesto;
import Domain.DatosDeOperaciones.Proveedor;
import Domain.Operaciones.Egreso.Egreso;

import javax.persistence.*;

@Entity
@Table
public class Presupuesto {

    @Id
    @GeneratedValue
    @Column(name="presupuesto_id")
    private int presupuestoId;
    @Column
    private int operacionNumero;
    @Transient
    private List<ItemPresupuesto> items;
    @ManyToOne
    private Egreso egresoAsociado;
    @OneToOne
    @JoinColumn(referencedColumnName = "documento_comercial_id")
    private DocumentoComercial documento;
    @Column(columnDefinition = "DATE")
    private LocalDate fechaVigente;
    @Transient
    private List<CategoriaOperacion> categorias;
    @ManyToOne
    private Proveedor proveedor;
    @Column
    private Double valorTotal;
    

    public Presupuesto(int operacionNumero, Egreso unEgreso){
        this.operacionNumero = operacionNumero;
        this.egresoAsociado = unEgreso;
        this.categorias = new ArrayList<>();
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
    public LocalDate getFechaVigente() {
        return fechaVigente;
    }
    public List<CategoriaOperacion> getCategorias() {
        return categorias;
    }
    /**Setters*/
    public void addCategorias(CategoriaOperacion ... categorias) {
        Collections.addAll(this.categorias,categorias);
    }

	public void addItems (ItemPresupuesto ... unosItems) {
        Collections.addAll(this.items, unosItems);
        this.valorTotal = calcularValorTotal();
    }

    public void setDocumento(DocumentoComercial documento) {
        this.documento = documento;
    }

    public void setFechaVigente(LocalDate fechaVigente) {
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
