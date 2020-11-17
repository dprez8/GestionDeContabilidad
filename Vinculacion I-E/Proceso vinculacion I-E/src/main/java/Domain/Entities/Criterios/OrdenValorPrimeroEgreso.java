package Domain.Entities.Criterios;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.Comparator;

public class OrdenValorPrimeroEgreso extends CriterioUnico {

    public OrdenValorPrimeroEgreso() {
        super();
    }

    @Override
    public void ordenar() {
        super.ordenar();
        this.egresos.sort(Comparator.comparing(Egreso::getMonto));
        this.ingresos.sort(Comparator.comparing(Ingreso::getMonto));
    }

    @Override
    public void siguiente() {
        if(this.posicionEgreso == this.egresos.size() - 1) {
            this.posicionEgreso = 0;
            this.posicionIngreso++;
        } else {
            this.posicionEgreso++;
        }
    }

    @Override
    public boolean termino() {
        return this.posicionIngreso == this.ingresos.size();
    }
}
