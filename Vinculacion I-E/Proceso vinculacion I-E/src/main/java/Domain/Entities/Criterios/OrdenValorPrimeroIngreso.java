package Domain.Entities.Criterios;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.Comparator;

public class OrdenValorPrimeroIngreso extends CriterioUnico {

    public OrdenValorPrimeroIngreso() {
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
        if(this.posicionIngreso == this.ingresos.size() - 1) {
            posicionEgreso++;
            posicionIngreso = 0;
        } else {
            posicionIngreso++;
        }
    }

    @Override
    public boolean termino() {
        return this.posicionEgreso == this.egresos.size();
    }
}
