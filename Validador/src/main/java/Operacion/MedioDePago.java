package Operacion;

public class MedioDePago {
	private MetodoDePago metodoPago;
	private int numAsociado;
	private int paymentId;
	
	public MedioDePago(MetodoDePago metodoPago, int numAsociado, int paymentId) {
		super();
		this.metodoPago = metodoPago;
		this.numAsociado = numAsociado;
		this.paymentId = paymentId;
	}
}
