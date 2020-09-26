package Domain.DTO;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionDTO {
    private Integer diasDesde;
    private Integer diasHasta;
    private String criterio;
    private List<String> criterios;

    public ConfiguracionDTO() {
        this.criterios = new ArrayList<>();
    }

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

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public List<String> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<String> criterios) {
        this.criterios = criterios;
    }
}
