package Domain.DatosDeOperaciones;

public class MedioDePago {
    private String medio_id;
    private String tipoPago;
    private String nombreMedioDePago;

	public MedioDePago(String medio_id,String tipoPago, String nombreMedioDePago) {
		this.medio_id = medio_id;
		this.tipoPago= tipoPago;
		this.nombreMedioDePago = nombreMedioDePago;
	}

}
