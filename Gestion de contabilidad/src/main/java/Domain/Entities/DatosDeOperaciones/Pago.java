package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "pago")
public class Pago extends EntidadPersistente {

	@Column(name="codigo_asociado")
	private int codigoAsociado;
	@ManyToOne
	@JoinColumn(name = "medio_id", referencedColumnName = "id")
	private MedioDePago medioDePago;


	public Pago() {
	}

	
	public Pago(int numeroAsociado, MedioDePago medioDePago) {
		this.codigoAsociado = numeroAsociado;
		this.medioDePago = medioDePago;
	}

	public int getCodigoAsociado() {
		return codigoAsociado;
	}

	public void setNumeroAsociado(int numeroAsociado) {
		this.codigoAsociado = numeroAsociado;
	}

	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(MedioDePago medioDePago) {
		this.medioDePago = medioDePago;
	}
}
