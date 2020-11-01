package Domain.Entities.Operaciones;


import Domain.Entities.Operaciones.Egreso.Egreso;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("ingreso")
public class Ingreso extends Operacion {

    @Expose
    @Column
    private String descripcion;

    @Expose
    @Column
    private double montoTotal;

    @OneToMany(mappedBy = "ingresoAsociado",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Egreso> egresos;

    public Ingreso(){
        this.egresos = new ArrayList<>();
        this.fechaCarga= LocalDateTime.now();
    }

    public Ingreso(String descripcion, double montoTotal){
    	this.fechaCarga= LocalDateTime.now();
    	this.descripcion = descripcion;
        this.montoTotal = montoTotal;
        this.egresos = new ArrayList<>();
    }

    public double montoSobrante() {
        return montoTotal-montoEnUso();
    }

    public double montoEnUso(){
        return egresos.stream().mapToDouble(Egreso::getValorTotal).sum();
    }
    /*********Setters & Getters*************/
    public void desasociarEgreso(Egreso egreso){
        egresos.remove(egreso);
    }

    public void asociarEgreso(Egreso egreso){
        if(montoSobrante()-egreso.getValorTotal()>=0)
        	egresos.add(egreso);
        else{
        	System.out.println("No puede asociarse este egreso porque supera el valor del ingreso");
        }
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
    }

    public void setEgresos(Egreso ... egresos) {
        Collections.addAll(this.egresos,egresos);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }
}
