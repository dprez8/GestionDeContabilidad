package Domain.Entities.DatosDeOperaciones;

import javax.persistence.*;

@Entity
@Table(name="tipo_de_documento")
public class TipoDocumento {
	@Id
	@GeneratedValue
	@Column(name="tipo_documento_id")
	private int tipoId;
	@Column (name="nombre_tipo")
	private String nombreTipoDeDocumento;

	protected TipoDocumento() {

	}

	public TipoDocumento(String nombreTipoDeDocumento) {
		this.nombreTipoDeDocumento = nombreTipoDeDocumento;
	}
	
}
