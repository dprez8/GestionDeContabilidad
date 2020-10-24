package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import java.time.LocalDate;

import javax.persistence.*;
@Entity
@Table(name="documento_comercial")
public class DocumentoComercial extends EntidadPersistente {

	@Column(name="numero_documento")
	private int numDocumento;

	@ManyToOne
	@JoinColumn(name = "tipo_documento_id", referencedColumnName = "id")
	private TipoDocumento tipo;

	@Column(name = "fecha_pedido", columnDefinition = "DATE")
    private LocalDate fechaDePedido;

    @Column(name = "fecha_entrega", columnDefinition = "DATE")
	private LocalDate fechaDeEntrega;

    @Column
    private String descripcion;

    @Column(name="path_adjunto")
    private String pathAdjunto;

	public DocumentoComercial() {
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

	public TipoDocumento getTipo() {
		return tipo;
	}

	public void setTipo(TipoDocumento tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPathAdjunto() {
		return pathAdjunto;
	}

	public void setPathAdjunto(String pathAdjunto) {
		this.pathAdjunto = pathAdjunto;
	}
}
