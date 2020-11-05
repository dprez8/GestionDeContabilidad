package Domain.Controllers.DTO;

import java.time.LocalDate;

public class IngresoRequest {
    public String descripcion;
    public Double montoTotal;
    public LocalDate fechaOperacion;
    public LocalDate fechaAceptacionEgresos;
    public int organizacion_id;
    public int moneda_id;
}
