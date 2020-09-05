package Domain.DatosDeOperaciones;

public class MedioDePago {
    private int numeroAsociado;
    private String tipoPago;
    private String nombreMedioDePago;


	public MedioDePago(int numeroAsociado, String nombreMedioDePago) {
		this.numeroAsociado = numeroAsociado;
		this.nombreMedioDePago = nombreMedioDePago;
	}
	public MedioDePago(int numeroAsociado,String tipoPago, String nombreMedioDePago) {
		this.numeroAsociado = numeroAsociado;
		this.tipoPago= tipoPago;
		this.nombreMedioDePago = nombreMedioDePago;
	}

}
