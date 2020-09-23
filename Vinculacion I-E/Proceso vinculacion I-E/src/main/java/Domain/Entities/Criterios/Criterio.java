package Domain.Entities.Criterios;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.ArrayList;
import java.util.List;

public abstract class Criterio {
    protected List<Egreso> egresos;
    protected List<Ingreso> ingresos;
    protected int posicionEgreso;
    protected int posicionIngreso;

    public Criterio() {
        this.egresos = new ArrayList<Egreso>();
        this.ingresos = new ArrayList<Ingreso>();
    }

    public abstract void ordenar();
    public abstract void siguiente();
    public abstract boolean termino();

    public Egreso getEgreso() {
        return egresos.get(posicionEgreso);
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
    }

    public Ingreso getIngreso() {
        return ingresos.get(posicionIngreso);
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }
}
