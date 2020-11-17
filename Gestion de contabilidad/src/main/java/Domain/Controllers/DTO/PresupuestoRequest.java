package Domain.Controllers.DTO;

import Domain.Entities.Operaciones.Egreso.Egreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PresupuestoRequest {
    public LocalDate fechaVigente;
    public int proveedor;
    public int egreso;
    public List<ItemPresupuestoRequest> items;

    public PresupuestoRequest() {
        this.items = new ArrayList<>();
    }
}
