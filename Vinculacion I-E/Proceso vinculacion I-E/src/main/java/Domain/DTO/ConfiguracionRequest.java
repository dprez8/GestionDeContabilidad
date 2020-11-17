package Domain.DTO;

import Domain.Entities.Criterios.Criterio;

public class ConfiguracionRequest {
    private Criterio criterio;

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }
}
