package Domain.DatosDeOperaciones;

import javax.persistence.*;

@Entity
@Table
public class TipoDocumento {
	@Id
	@GeneratedValue
	private int tipoId;
	@Column
	private String nombreTipoDeDocumento;

	public TipoDocumento(String nombreTipoDeDocumento) {
		this.nombreTipoDeDocumento = nombreTipoDeDocumento;
	}
	
}
