package Domain.Controllers.DTO;

import Domain.Entities.Operaciones.Egreso.Egreso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PresupuestoRequest {
    public LocalDate fechaVigente;
    public int numeroOperacion;
    public int egresoId;
    public List<ItemRequest> items;

    public PresupuestoRequest() {
        this.items = new ArrayList<>();
    }
}
