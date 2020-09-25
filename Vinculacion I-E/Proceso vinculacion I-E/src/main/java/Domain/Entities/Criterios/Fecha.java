package Domain.Entities.Criterios;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.Comparator;

public class Fecha extends CriterioUnico {

    public Fecha() {
        super();
        this.posicionEgreso = 0;
        this.posicionIngreso = 0;
    }

    @Override
    public void ordenar() {
        this.egresos.sort(Comparator.comparing(Egreso::getFecha));
        this.ingresos.sort(Comparator.comparing(Ingreso::getFecha));
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
