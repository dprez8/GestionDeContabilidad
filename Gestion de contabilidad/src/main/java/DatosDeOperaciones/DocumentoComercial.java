package DatosDeOperaciones;

import java.util.Date;

public class DocumentoComercial {
    private TipoDocumento tipo;
    private int numDocumento;
    private Date fechaDePedido;
    private Date fechaDeEntrega;
    public String descripcion;
	
    public DocumentoComercial(TipoDocumento tipo, int numDocumento, Date fechaDePedido, Date fechaDeEntrega,
			String descripcion) {
		super();
		this.tipo = tipo;
		this.numDocumento = numDocumento;
		this.fechaDePedido = fechaDePedido;
		this.fechaDeEntrega = fechaDeEntrega;
		this.descripcion = descripcion;
	}
    
    
}
