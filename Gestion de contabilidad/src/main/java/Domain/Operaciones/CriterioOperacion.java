package Domain.Operaciones;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="criterio_operacion")
public class CriterioOperacion {
    @Id
    @Column(name="criterio_id")
    private int criterioId;
    @Transient //@OneToMany(mappedBy = "criterio_id")
    private List<CategoriaOperacion> categorias;
    @Transient
    private List<CriterioOperacion> criteriosHijo;
    @ManyToOne
    @OneToMany(mappedBy = "criterioPadre")
    @JoinColumn(name="criterio_padre_id", referencedColumnName = "criterio_id")
    private CriterioOperacion criterioPadre;
    @Column
    private String descripcion;

    protected CriterioOperacion(){
    }

    public CriterioOperacion(String descrip){
        descripcion = descrip;
        categorias = new ArrayList<>();
        criteriosHijo = new ArrayList<>();
    }

    public void setCriterioHijo(CriterioOperacion critOperacion){
        criteriosHijo.add(critOperacion);
    }

    public void setCriterioPadre(CriterioOperacion critOperacion){
        criterioPadre = critOperacion;
    }
    
    public void addCategorias(CategoriaOperacion ... categorias){
        Collections.addAll(this.categorias, categorias);
    }
}
