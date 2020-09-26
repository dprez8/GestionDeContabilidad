package Domain.Entities;

import Domain.Entities.Condiciones.Condicion;
import Domain.Entities.Criterios.Criterio;
import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vinculador {
    private List<Ingreso> ingresos;
    private List<Egreso> egresos;
    private List<Condicion> condiciones;
    private Criterio criterio;
    private static Vinculador vinculador;

    public static Vinculador instancia (){
        if(vinculador == null){
            vinculador = new Vinculador();
        }
        return vinculador;
    }

    private Vinculador(){
        this.ingresos = new ArrayList<>();
        this.egresos = new ArrayList<>();
        this.condiciones = new ArrayList<>();
    }
    public void vincular() {
        this.criterio.setEgresos(egresos);
        this.criterio.setIngresos(ingresos);
        this.criterio.ordenar();

        while(!criterio.termino()) {
            Egreso egreso = this.criterio.getEgreso();
            Ingreso ingreso = this.criterio.getIngreso();

            if(this.condiciones.stream().allMatch(unaCondicion->unaCondicion.cumpleCondicion(egreso, ingreso))) {
                // Se puede vincular el egreso al ingreso
                ingreso.addEgresoAsociado(egreso);
                egreso.setIngresoAsociado(ingreso);
            }
            this.criterio.siguiente();
        }
    }

    /*******Getters & Setters******************/
    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    public void addIngreso(Ingreso ... ingresos){
        Collections.addAll(this.ingresos, ingresos);
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
    }

    public void addEgreso(Egreso ... _egresos){
        Collections.addAll(this.egresos, _egresos);
    }

    public List<Condicion> getCondiciones() {
        return condiciones;
    }

    public void addCondiciones(Condicion ... condiciones) {
        Collections.addAll(this.condiciones, condiciones);
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }
}
