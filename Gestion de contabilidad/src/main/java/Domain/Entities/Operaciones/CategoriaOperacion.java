package Domain.Entities.Operaciones;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="categoria_operacion")
public class CategoriaOperacion {

    @Id
    @GeneratedValue
    @Column(name="categoria_id")
    private int categoriaId;
    @Column
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "criterio_id")
    private CriterioOperacion criterio;

    public CategoriaOperacion() {

    }

    public int getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(int categoriaId) {
		this.categoriaId = categoriaId;
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


