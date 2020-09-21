package Domain.Entities.ReglaCondiciones;

import Domain.Entities.Criterios.Criterio;
import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;

import java.time.LocalDate;
import java.util.List;

public class Regla {
    private Condicion condicion;
    private Criterio criterio;
    private LocalDate fechaInicio;
    private LocalDate fechaLimite;
    private List<Egreso> egresos;
    private List<Ingreso> ingresos;

    public boolean cumplisCondicion(LocalDate fechaEgreso){
        return this.condicion.cumplisCon(this.fechaInicio,this.fechaLimite,fechaEgreso);
    }
    public void ejecutarCriterio(){
        this.criterio.aplicate(this);
    }

    public Condicion getCondicion() {
        return condicion;
    }

    public void setCondicion(Condicion condicion) {
        this.condicion = condicion;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public List<Egreso> getEgresos() {
        return egresos;
    }

    public void setEgresos(List<Egreso> egresos) {
        this.egresos = egresos;
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }
}
