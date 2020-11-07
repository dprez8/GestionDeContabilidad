package Domain.Entities.Operaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="categoria_operacion")
public class CategoriaOperacion extends EntidadPersistente {

    @Column
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "criterio_id")
    private CriterioOperacion criterio;

    public CategoriaOperacion() {

    }

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


