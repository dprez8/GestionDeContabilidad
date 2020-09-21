package Domain.Entities.Operaciones;

import java.time.LocalDate;
import java.util.List;

public class Ingreso {
    private Double monto;
    private List<Egreso> egresosAsociados;
    private LocalDate fecha;

    /**********Getters & Setters*****************/
    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public List<Egreso> getEgresosAsociados() {
        return egresosAsociados;
    }

    public void setEgresosAsociados(List<Egreso> egresosAsociados) {
        this.egresosAsociados = egresosAsociados;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}
