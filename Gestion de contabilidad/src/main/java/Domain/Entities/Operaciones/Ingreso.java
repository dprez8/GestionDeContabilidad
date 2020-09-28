package Domain.Entities.Operaciones;


import Domain.Entities.Operaciones.Egreso.Egreso;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="ingreso")
public class Ingreso extends Operacion {
    @Column
    private String descripcion;
    @Column
    private double montoTotal;
    @OneToMany(mappedBy = "ingresoAsociado")
    private List<Egreso> egresos;

    protected Ingreso(){
    }

    public Ingreso(int operacionNumero, String descripcion, double montoTotal){
    	this.fechaCarga= new Date();
    	this.descripcion = descripcion;
        this.montoTotal = montoTotal;
        this.egresos = new ArrayList<>();
    }

    public double montoSobrante() {
        return montoTotal-montoEnUso();
    }

    public double montoEnUso(){
        return egresos.stream().collect(Collectors.summingDouble(unEgreso -> unEgreso.getValorTotal()));
    }

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
}
