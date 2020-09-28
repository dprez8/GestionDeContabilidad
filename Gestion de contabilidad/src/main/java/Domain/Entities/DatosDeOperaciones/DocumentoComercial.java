package Domain.Entities.DatosDeOperaciones;

import java.time.LocalDate;

import javax.persistence.*;
@Entity
@Table
public class DocumentoComercial{
	@Id
	@GeneratedValue
	@Column(name="documento_comercial_id")
	private int numDocumento;
	@ManyToOne
	@JoinColumn(name = "tipo_documento_id", referencedColumnName = "tipo_documento_id")
	private TipoDocumento tipo;
	@Column(columnDefinition = "DATE")
    private LocalDate fechaDePedido;
    @Column(columnDefinition = "DATE")
	private LocalDate fechaDeEntrega;
    @Column
    private String descripcion;
    @Column(name="path_adjunto")
    private String pathAdjunto;

	protected DocumentoComercial() {
	}

    public DocumentoComercial(TipoDocumento tipo, int numDocumento) {
		this.tipo = tipo;
		this.numDocumento = numDocumento;
	}
    
    public DocumentoComercial(TipoDocumento tipo, int numDocumento, String descripcion,String pathAdjunto) {
		this.tipo = tipo;
		this.numDocumento = numDocumento;
		this.descripcion= descripcion;
		this.pathAdjunto = pathAdjunto;
	}

	/**Setter & Getters*/

	public int getNumDocumento() {
		return numDocumento;
	}

	public LocalDate getFechaDePedido(){
		return fechaDePedido;
	}

	public LocalDate getFechaDeEntrega(){
		return fechaDeEntrega;
	}

	public void setNumDocumento(int numDocumento) {
		this.numDocumento = numDocumento;
	}

	public void setFechaDePedido(LocalDate fechaDePedido){
		this.fechaDePedido = fechaDePedido;
	}

	public void setFechaDeEntrega(LocalDate fechaDeEntrega){
		this.fechaDeEntrega = fechaDeEntrega;
	}
}
