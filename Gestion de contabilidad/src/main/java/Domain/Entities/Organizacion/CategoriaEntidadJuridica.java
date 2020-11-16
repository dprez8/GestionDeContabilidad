package Domain.Entities.Organizacion;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "categoria_juridica")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_juridica")
public abstract class CategoriaEntidadJuridica extends EntidadPersistente {
	
	@JoinColumn(name = "entidad_juridica_id", referencedColumnName = "id")
    private EntidadJuridica entidadJuridica;

	public EntidadJuridica getEntidadJuridica() {
		return entidadJuridica;
	}

	public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
		this.entidadJuridica = entidadJuridica;
	}
	
	
}
