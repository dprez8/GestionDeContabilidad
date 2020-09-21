package Domain.Entities;

import java.time.LocalDate;

public class Regla {
    private Condicion condicion;
    private Criterio criterio;
    private LocalDate fechaInicio;
    private LocalDate fechaLimite;

    public boolean cumplisCondicion(LocalDate fechaEgreso){
        return this.condicion.cumplisCon(this.fechaInicio,this.fechaLimite,fechaEgreso);
    }
    public void ejecutarCriterio(){
        this.criterio.aplicate();
    }
}
