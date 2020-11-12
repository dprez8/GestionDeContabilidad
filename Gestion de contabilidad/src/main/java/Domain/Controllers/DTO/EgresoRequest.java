package Domain.Controllers.DTO;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EgresoRequest {
    public LocalDate fechaOperacion;
    public int proveedor;
    public MedioDePagoRequest medioDePago;
    public DocumentoRequest documentoComercial;
    public int cantidadPresupuestos;
    public int organizacion_id;
    public int tipo;
    public List<ItemRequest> items;

    public EgresoRequest(){
        this.items = new ArrayList<>();
    }
}
