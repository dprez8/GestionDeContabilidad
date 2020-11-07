package Domain.Entities.DatosDeOperaciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

@Entity
@Table(name = "tipo_item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TipoItem extends EntidadPersistente{
	 	@Expose
	    @Column(name="nombre")
	    protected String nombre;
	 	
	    public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

}
