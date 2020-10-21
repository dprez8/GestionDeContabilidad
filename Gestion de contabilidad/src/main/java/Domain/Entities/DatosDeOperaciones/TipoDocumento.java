package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name="tipo_de_documento")
public class TipoDocumento extends EntidadPersistente {

	@Column (name="nombre_tipo")
	private String nombreTipoDeDocumento;

	public TipoDocumento() {

	}

	public TipoDocumento(String nombreTipoDeDocumento) {
		this.nombreTipoDeDocumento = nombreTipoDeDocumento;
	}

	public String getNombreTipoDeDocumento() {
		return nombreTipoDeDocumento;
	}

	public void setNombreTipoDeDocumento(String nombreTipoDeDocumento) {
		this.nombreTipoDeDocumento = nombreTipoDeDocumento;
	}
}
