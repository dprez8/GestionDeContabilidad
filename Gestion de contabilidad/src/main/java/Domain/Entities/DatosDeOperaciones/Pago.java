package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "pago")
public class Pago extends EntidadPersistente {

	@Column(name="codigo_asociado")
	private String codigoAsociado;
	@ManyToOne
	@JoinColumn(name = "medio_id", referencedColumnName = "id")
	private MedioDePago medioDePago;


	public Pago() {
	}

	
	public Pago(String codigoAsociado, MedioDePago medioDePago) {
		this.codigoAsociado = codigoAsociado;
		this.medioDePago = medioDePago;
	}

	public String getCodigoAsociado() {
		return codigoAsociado;
	}

	public void setCodigoAsociado(String string) {
		this.codigoAsociado = string;
	}

	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(MedioDePago medioDePago) {
		this.medioDePago = medioDePago;
	}
}
