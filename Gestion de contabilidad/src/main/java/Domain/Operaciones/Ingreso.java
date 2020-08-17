package Domain.Operaciones;


import Domain.Operaciones.Egreso.Egreso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ingreso extends Operacion {
    private String descripcion;
    private double montoTotal;
    private List<Egreso> egresos;

    public Ingreso(int operacionNumero, String descripcion, double montoTotal){
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
