package Domain.Operaciones;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CategoriaOperacion {
    @Column
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "criterio_id")
    private CriterioOperacion criterio;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CriterioOperacion getCriterio() {
        return criterio;
    }

    public void setCriterio(CriterioOperacion criterio) {
        this.criterio = criterio;
    }
}
