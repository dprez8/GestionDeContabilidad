package Domain.Controllers.DTO;

import Domain.Entities.Operaciones.Egreso.Egreso;

import java.time.LocalDate;

public class PresupuestoResponse {
    public int id;
    public int egresoAsociadoId;
    public Double montoTotal;
    public LocalDate fechaVigente;
}
