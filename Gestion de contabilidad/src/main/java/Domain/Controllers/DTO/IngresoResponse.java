package Domain.Controllers.DTO;

import java.time.LocalDate;
import java.util.List;

public class IngresoResponse {
    public int id;
    public LocalDate fechaOperacion;
    public LocalDate fechaCarga;
    public String descripcion;
    public Double montoTotal;
    public List<Integer> egresos;
}
