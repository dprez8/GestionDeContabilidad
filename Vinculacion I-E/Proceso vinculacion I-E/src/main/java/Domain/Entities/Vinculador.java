package Domain.Entities;

import Domain.Entities.Condiciones.Condicion;
import Domain.Entities.Criterios.Criterio;
import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.Collections;
import java.util.List;

public class Vinculador {
    private List<Ingreso> ingresos;
    private List<Egreso> egresos;
    private List<Condicion> condiciones;
    private Criterio criterio;

    public void vincular() {
        criterio.setEgresos(egresos);
        criterio.setIngresos(ingresos);
        criterio.ordenar();

        while(!criterio.termino()) {
            Egreso egreso = criterio.getEgreso();
            Ingreso ingreso = criterio.getIngreso();

            if(condiciones.stream().allMatch(unaCondicion->unaCondicion.cumpleCondicion(egreso, ingreso))) {
                // Se puede vincular el egreso al ingreso
                ingreso.addEgresoAsociado(egreso);
            }
        }
    }

    /*******Getters & Setters******************/
    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
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
