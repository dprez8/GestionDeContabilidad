package Domain.Entities.Operaciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Entities.DatosDeOperaciones.DocumentoComercial;
import Domain.Entities.DatosDeOperaciones.ItemPresupuesto;
import Domain.Entities.DatosDeOperaciones.Proveedor;
import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.Egreso.Egreso;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name="presupuesto")
public class Presupuesto extends EntidadPersistente {

    @Column(name="operacion_numero")
    private int operacionNumero;

    @Expose
    @OneToMany(mappedBy = "presupuesto",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ItemPresupuesto> items;

    @ManyToOne
    @JoinColumn(name="egreso_asociado", referencedColumnName = "id")
    private Egreso egresoAsociado;

    @Expose
    @Column(name="fecha_vigente", columnDefinition = "DATE")
    private LocalDate fechaVigente;

    @ManyToMany
    private List<CategoriaOperacion> categorias;

    @Expose
    @ManyToOne
    @JoinColumn(name="proveedor_id", referencedColumnName = "proveedor_id")
    private Proveedor proveedor;

    @Expose
    @Column(name="valor_total")
    private Double valorTotal;


    public Presupuesto(){}
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

    public void setFechaVigente(LocalDate fechaVigente) {
        this.fechaVigente = fechaVigente;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setOperacionNumero(int operacionNumero) {
        this.operacionNumero = operacionNumero;
    }

    public void setItems(List<ItemPresupuesto> items) {
        this.items = items;
    }

    public void setEgresoAsociado(Egreso egresoAsociado) {
        this.egresoAsociado = egresoAsociado;
    }

    public void setCategorias(List<CategoriaOperacion> categorias) {
        this.categorias = categorias;
    }

    /********************************************/
    private Double calcularValorTotal(){
        return items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
    }
}
