package Domain.Entities.Operaciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Ingreso {
    private Double monto;
    private List<Egreso> egresosAsociados;
    private LocalDate fecha;

    public Ingreso(){
        this.egresosAsociados = new ArrayList<>();
    }
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

    public void addEgresoAsociado(Egreso egresosAsociado) {
        this.egresosAsociados.add(egresosAsociado);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getMontoEgresosAsociados() {
        return this.egresosAsociados.stream().collect(Collectors.summingDouble(unEgreso->unEgreso.getMonto()));
    }

}
