package Domain.Entities;

import java.util.Collections;
import java.util.List;

public abstract class CriterioUnico implements Criterio {
    @Override
    public abstract void aplicate();

    protected void ordenarPorValor(List<Integer> numeros){
       // Collections.sort();
    }
}
