package Domain.Entities.Operaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Organizacion.Organizacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="criterio_operacion")
public class CriterioOperacion extends EntidadPersistente{

    @Transient //@OneToMany(mappedBy = "criterio_id")
    private List<CategoriaOperacion> categorias;
    @Transient
    private List<CriterioOperacion> criteriosHijo;
    @ManyToOne
    @OneToMany(mappedBy = "criterioPadre")
    @JoinColumn(name="criterio_padre_id", referencedColumnName = "id")
    private CriterioOperacion criterioPadre;
    @Column
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
    protected Organizacion organizacion;

    public CriterioOperacion(){
    }

    public CriterioOperacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public CriterioOperacion(String descrip){
        descripcion = descrip;
        categorias = new ArrayList<>();
        criteriosHijo = new ArrayList<>();
    }

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public CriterioOperacion getCriterioPadre() {
		return criterioPadre;
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

    public Organizacion getOrganizacion() {	return organizacion; }

    public void setOrganizacion(Organizacion organizacion) { this.organizacion = organizacion;	}
}
