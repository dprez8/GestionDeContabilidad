package Domain.Controllers.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class IngresoResponse {
    public int id;
    public LocalDate fechaOperacion;
    public LocalDate fechaAceptacion;
    public LocalDateTime fechaCarga;
    public String descripcion;
    public Double montoTotal;
    public Double montoRestante;
    public List<Integer> egresos;
}
