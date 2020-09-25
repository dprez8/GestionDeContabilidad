package Domain.DTO;

import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.util.ArrayList;
import java.util.List;

public class OperacionesDTO {
    public List<Ingreso> ingresos;
    public List<Egreso> egresos;

    public OperacionesDTO() {
        this.ingresos = new ArrayList<>();
        this.egresos = new ArrayList<>();
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
    }

}
