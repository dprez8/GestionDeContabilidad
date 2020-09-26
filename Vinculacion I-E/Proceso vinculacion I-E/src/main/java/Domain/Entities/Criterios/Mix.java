package Domain.Entities.Criterios;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.ArrayList;
import java.util.List;

public class Mix extends Criterio {

    private List<CriterioUnico> criterios;
    private int posicionCriterio;

    public Mix() {
        this.posicionCriterio = 0;
        this.criterios = new ArrayList<>();
    }

    @Override
    public void ordenar() {
        CriterioUnico criterioActual = this.criterios.get(posicionCriterio);

        criterioActual.setEgresos(egresos);
        criterioActual.setIngresos(ingresos);
        criterioActual.ordenar();
    }

    @Override
    public void siguiente() {
        CriterioUnico criterioActual = this.criterios.get(posicionCriterio);
        criterioActual.siguiente();
        if(criterioActual.termino()) {
            posicionCriterio++;
            this.ordenar();
        }
    }

    @Override
    public boolean termino() {
        if(this.posicionCriterio == this.criterios.size() - 1){
            this.posicionCriterio = 0;
            return true;
        }
        return false;
    }

    @Override
    public Egreso getEgreso() {
        return criterios.get(posicionCriterio).getEgreso();
    }

    @Override
    public Ingreso getIngreso() {
        return criterios.get(posicionCriterio).getIngreso();
    }

    /************Setters & Getters**********************/
    public void setCriteriosUnicos(List<CriterioUnico> criterios) {
        this.criterios = criterios;
    }
}
