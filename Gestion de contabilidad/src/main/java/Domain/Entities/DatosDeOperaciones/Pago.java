package Domain.Entities.DatosDeOperaciones;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table
public class Pago {

	@Id
	@Column(name="pago_id")
	private int pagoId;
	@Column(columnDefinition = "DATE")
	private LocalDate fechaPago;
	@Column(name="numero_asociado")
	private int numeroAsociado;
	@ManyToOne
	private MedioDePago medioDePago;


	protected Pago() {
	}

	public Pago(int pagoId, LocalDate fechaPago, int numeroAsociado, MedioDePago medioDePago) {
		this.pagoId = pagoId;
		this.fechaPago = fechaPago;
		this.numeroAsociado = numeroAsociado;
		this.medioDePago = medioDePago;
	}

	public int getPagoId() {
		return pagoId;
	}

	public void setPagoId(int pagoId) {
		this.pagoId = pagoId;
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
