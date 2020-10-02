package Domain.Entities.Operaciones;

import java.time.LocalDate;

public class Egreso {
    private Integer numeroEgreso;
    private Double monto;
    private LocalDate fecha;
    private Ingreso ingresoAsociado;

    /************Getters & Setters*************/
    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Ingreso getIngresoAsociado() {
        return ingresoAsociado;
    }

    public void setIngresoAsociado(Ingreso ingresoAsociado) {
        this.ingresoAsociado = ingresoAsociado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getNumeroEgreso() {
        return numeroEgreso;
    }

    public void setNumeroEgreso(Integer numeroEgreso) {
        this.numeroEgreso = numeroEgreso;
    }
}
