package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table(name = "pago")
public class Pago extends EntidadPersistente {

	@Column(name = "fecha_pago",columnDefinition = "DATE")
	private LocalDate fechaPago;
	@Column(name="numero_asociado")
	private int numeroAsociado;
	@ManyToOne
	@JoinColumn(name = "medio_id", referencedColumnName = "id")
	private MedioDePago medioDePago;


	public Pago() {
	}

	public Pago(LocalDate fechaPago, int numeroAsociado, MedioDePago medioDePago) {
		this.fechaPago = fechaPago;
		this.numeroAsociado = numeroAsociado;
		this.medioDePago = medioDePago;
	}

	public LocalDate getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}

	public int getNumeroAsociado() {
		return numeroAsociado;
	}

	public void setNumeroAsociado(int numeroAsociado) {
		this.numeroAsociado = numeroAsociado;
	}

	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(MedioDePago medioDePago) {
		this.medioDePago = medioDePago;
	}
}
