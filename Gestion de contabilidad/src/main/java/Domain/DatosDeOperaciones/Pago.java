package Domain.DatosDeOperaciones;

import java.time.LocalDate;

public class Pago {
	private int pagoId;
	private LocalDate fecha_pago;
	private int numero_asociado;
	private MedioDePago medio_id;
	
	public Pago(int pagoId, LocalDate fecha_pago, int numero_asociado, MedioDePago medio_id) {
		this.pagoId = pagoId;
		this.fecha_pago = fecha_pago;
		this.numero_asociado = numero_asociado;
		this.medio_id = medio_id;
	} 
	
	
}
