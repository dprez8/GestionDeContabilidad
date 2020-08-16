package Domain.DatosDeOperaciones;

import java.util.Date;

public class DocumentoComercial{
    private TipoDocumento tipo;
    private int numDocumento;
    private Date fechaDePedido;
    private Date fechaDeEntrega;
    private String pathAdjunto;
	
    public DocumentoComercial(TipoDocumento tipo, int numDocumento) {
		this.tipo = tipo;
		this.numDocumento = numDocumento;
	}
	public DocumentoComercial(TipoDocumento tipo, int numDocumento, String pathAdjunto) {
		this.tipo = tipo;
		this.numDocumento = numDocumento;
		this.pathAdjunto = pathAdjunto;
	}

	/**Setter & Getters*/

	public int getNumDocumento() {
		return numDocumento;
	}

	public Date getFechaDePedido(){
		return fechaDePedido;
	}

	public Date getFechaDeEntrega(){
		return fechaDeEntrega;
	}

	public void setNumDocumento(int numDocumento) {
		this.numDocumento = numDocumento;
	}

	public void setFechaDePedido(Date fechaDePedido){
		this.fechaDePedido = fechaDePedido;
	}

	public void setFechaDeEntrega(Date fechaDeEntrega){
		this.fechaDeEntrega = fechaDeEntrega;
	}
}
