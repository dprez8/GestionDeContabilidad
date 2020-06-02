package DatosDeOperaciones;

import java.util.Date;

public class DocumentoComercial {
    private TipoDocumento tipo;
    private int numDocumento;
    private Date fechaDePedido;
    private Date fechaDeEntrega;
    private String descripcion;
    private String pathAdjunto;
	
    public DocumentoComercial(TipoDocumento tipo, int numDocumento, Date fechaDePedido, Date fechaDeEntrega,
			String descripcion) {
		this.tipo = tipo;
		this.numDocumento = numDocumento;
		this.fechaDePedido = fechaDePedido;
		this.fechaDeEntrega = fechaDeEntrega;
		this.descripcion = descripcion;
	}
	public DocumentoComercial(TipoDocumento tipo, int numDocumento, Date fechaDePedido, Date fechaDeEntrega,
							  String descripcion, String pathAdjunto) {
		this.tipo = tipo;
		this.numDocumento = numDocumento;
		this.fechaDePedido = fechaDePedido;
		this.fechaDeEntrega = fechaDeEntrega;
		this.descripcion = descripcion;
		this.pathAdjunto = pathAdjunto;
	}
}
