package Domain.DatosDeOperaciones;

import javax.persistence.*;

@Entity
@Table
public class TipoDocumento {
	@Id
	@GeneratedValue
	@Column(name="tipo_documento_id")
	private int tipoId;
	@Column (name="nombre_tipo")
	private String nombreTipoDeDocumento;

	public TipoDocumento(String nombreTipoDeDocumento) {
		this.nombreTipoDeDocumento = nombreTipoDeDocumento;
	}
	
}
