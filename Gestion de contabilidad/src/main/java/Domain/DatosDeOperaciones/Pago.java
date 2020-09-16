package Domain.DatosDeOperaciones;

import java.time.LocalDate;

import javax.persistence.*;

@Entity
@Table
public class Pago {
	
	@Id
	@Column(name="pago_id")
	private int pagoId;
	@Column(columnDefinition = "DATE")
	private LocalDate fecha_pago;
	@Column(name="numero_asociado")
	private int numero_asociado;
	@ManyToOne
	private MedioDePago medio_id;
	
	public Pago(int pagoId, LocalDate fecha_pago, int numero_asociado, MedioDePago medio_id) {
		this.pagoId = pagoId;
		this.fecha_pago = fecha_pago;
		this.numero_asociado = numero_asociado;
		this.medio_id = medio_id;
	} 
	
	
}
