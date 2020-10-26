package Domain.Controllers.DTO;

import java.time.LocalDate;

public class EgresoResponse {
    public int id;
    public boolean validado;
    public Double valorTotal;
    public LocalDate fechaOperacion;
}