package Domain.Entities.DatosDeOperaciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

@Entity
@Table(name = "tipo_item")
public class TipoItem extends EntidadPersistente {
	
	@Column
	public String nombre;

	public TipoItem() {
		super();
	}

	public TipoItem(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
