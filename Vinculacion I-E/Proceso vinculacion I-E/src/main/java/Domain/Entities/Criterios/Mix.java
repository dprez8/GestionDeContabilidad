package Domain.Entities.Criterios;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.Comparator;
import java.util.List;

public class Mix extends Criterio {

    private List<CriterioUnico> criterios;
    private int posicionCriterio;

    public Mix() {
        this.posicionCriterio = 0;
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

        if(criterioActual.termino()) {
            posicionCriterio++;
            this.ordenar();
        } else {
            criterioActual.siguiente();
        }
    }

    @Override
    public boolean termino() {
        CriterioUnico criterioActual = this.criterios.get(posicionCriterio);

        return (this.posicionCriterio == this.criterios.size() - 1) && criterioActual.termino();
    }

    @Override
    public Egreso getEgreso() {
        return criterios.get(posicionCriterio).getEgreso();
    }

    @Override
    public Ingreso getIngreso() {
        return criterios.get(posicionCriterio).getIngreso();
    }
}
