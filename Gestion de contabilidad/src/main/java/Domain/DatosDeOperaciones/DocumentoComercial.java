package Domain.DatosDeOperaciones;

import java.util.Date;

public class DocumentoComercial{
    private TipoDocumento tipo;
    private int numDocumento;
    private Date fechaDePedido;
    private Date fechaDeEntrega;
    private String pathAdjunto;
	
    public DocumentoComercial(TipoDocumento tipo, int numDocumento, Date fechaDePedido, Date fechaDeEntrega) {
		this.tipo = tipo;
		this.numDocumento = numDocumento;
		this.fechaDePedido = fechaDePedido;
		this.fechaDeEntrega = fechaDeEntrega;
	}
	public DocumentoComercial(TipoDocumento tipo, int numDocumento, Date fechaDePedido, Date fechaDeEntrega,
							  String pathAdjunto) {
		this.tipo = tipo;
		this.numDocumento = numDocumento;
		this.fechaDePedido = fechaDePedido;
		this.fechaDeEntrega = fechaDeEntrega;
		this.pathAdjunto = pathAdjunto;
	}
}
