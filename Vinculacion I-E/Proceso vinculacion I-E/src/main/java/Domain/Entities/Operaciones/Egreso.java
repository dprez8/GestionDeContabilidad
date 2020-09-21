package Domain.Entities.Operaciones;

import java.time.LocalDate;

public class Egreso {
    private Double monto;
    private Ingreso ingresoAsociado;
    private LocalDate fecha;

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
}
