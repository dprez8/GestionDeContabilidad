package Domain.Entities.Criterios;

import Domain.Entities.ReglaCondiciones.Regla;

public interface Criterio {
    void aplicate(Regla regla);
}
