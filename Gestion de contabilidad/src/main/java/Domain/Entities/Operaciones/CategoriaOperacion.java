package Domain.Entities.Operaciones;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="categoria_operacion")
public class CategoriaOperacion {

    @Id
    @Column(name="categoria_id")
    private int categoriaId;
    @Column
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "criterio_id")
    private CriterioOperacion criterio;

    protected CategoriaOperacion() {

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

// propia del mapeo
@Entity
@Table(name="categoria_x_operacion")
class CategoriaPorOperacion {

    @EmbeddedId
    CategoriaOperacionKey id;

    @ManyToOne
    @MapsId("categoriaId")
    @JoinColumn(name="categoria_id")
    public CategoriaOperacion categoria;

    @ManyToOne
    @MapsId("operacionId")
    @JoinColumn(name = "operacion_id")
    public Operacion operacion;

    @Column
    public String descripcion;

}

@Embeddable
class CategoriaOperacionKey implements Serializable {

    @Column(name = "categoria_id")
    public int categoriaId;

    @Column(name = "operacion_id")
    public int operacionId;

}
