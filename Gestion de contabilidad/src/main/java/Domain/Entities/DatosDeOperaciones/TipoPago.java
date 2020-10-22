package Domain.Entities.DatosDeOperaciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

@Entity
@Table(name="tipo_de_pago")
public class TipoPago extends EntidadPersistente {
	@Column(name="tipo_pago")
	private String tipoPago;
}
