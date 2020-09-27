package Domain.DTO;

import Domain.Entities.Criterios.Criterio;

public class ConfiguracionRequest {
    private Integer diasDesde;
    private Integer diasHasta;
    private Criterio criterio;

    public Integer getDiasDesde() {
        return diasDesde;
    }

    public void setDiasDesde(Integer diasDesde) {
        this.diasDesde = diasDesde;
    }

    public Integer getDiasHasta() {
        return diasHasta;
    }

    public void setDiasHasta(Integer diasHasta) {
        this.diasHasta = diasHasta;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }
}
