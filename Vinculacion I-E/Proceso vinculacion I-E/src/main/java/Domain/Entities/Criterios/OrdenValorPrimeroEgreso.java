package Domain.Entities.Criterios;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.Comparator;

public class OrdenValorPrimeroEgreso extends CriterioUnico {

    public OrdenValorPrimeroEgreso() {
        super();
        this.posicionEgreso = 0;
        this.posicionIngreso = 0;
    }

    @Override
    public void ordenar() {
        this.egresos.sort(Comparator.comparing(Egreso::getMonto));
        this.ingresos.sort(Comparator.comparing(Ingreso::getMonto));
    }

    @Override
    public void siguiente() {
        if(this.posicionEgreso == this.egresos.size() - 1) {
            posicionEgreso = 0;
            posicionIngreso++;
        } else {
            posicionEgreso++;
        }
    }

    @Override
    public boolean termino() {
        return this.posicionIngreso == this.ingresos.size();
    }
}
