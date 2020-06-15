package DatosDeOperaciones;

import Operaciones.Egreso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Presupuesto {
    private List<ItemPresupuesto> items = new ArrayList<>();
    private Egreso egresoAsociado;
    private DocumentoComercial documento;
    private String fechaVigente;

    public Presupuesto(Egreso unEgreso,DocumentoComercial unDocumento,String fechaVigente,ItemPresupuesto ... items){
        this.egresoAsociado = unEgreso;
        this.documento = unDocumento;
        this.fechaVigente = fechaVigente;
        this.agregarItems(items);
    }

    private void agregarItems (ItemPresupuesto ... unosItems) {
        Collections.addAll(this.items, unosItems);
    }
    public double total(){
        return items.stream().collect(Collectors.summingDouble(unItem->unItem.valorTotal()));
    }
}
