package Domain.Controllers.DTO;

import java.time.LocalDate;

public class DocumentoRequest {
    public int tipo;
    public int numeroDocumento;
    public LocalDate fechaDePedido;
    public LocalDate fechaDeEntrega;
    public String descripcion;
    public String nombreFicheroDocumento;
}
