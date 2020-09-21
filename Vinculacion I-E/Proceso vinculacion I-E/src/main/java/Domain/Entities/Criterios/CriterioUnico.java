package Domain.Entities.Criterios;

import Domain.Entities.ReglaCondiciones.Regla;

import java.util.List;

public interface CriterioUnico extends Criterio {
    @Override
    public abstract void aplicate(Regla regla);

    //void ordenarPorValor(List<Integer> numeros);
}
