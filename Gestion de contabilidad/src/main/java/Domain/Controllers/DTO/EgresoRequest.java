package Domain.Controllers.DTO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EgresoRequest {
    public LocalDate fecha;
    public int proveedor;
    public MedioDePagoRequest medioDePago;
    public DocumentoRequest documentoComercial;
    public List<ItemRequest> items;

    public EgresoRequest(){
        this.items = new ArrayList<>();
    }
}
