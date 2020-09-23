package Domain.Entities.Criterios;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
            posicionEgreso = 0;
            posicionIngreso++;
        } else {
            posicionEgreso++;
        }
    }

    @Override
    public boolean termino() {
        return this.posicionIngreso == this.ingresos.size() - 1;
    }
}
