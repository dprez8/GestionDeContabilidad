package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

@Entity
@Table(name="medio_de_pago")
public class MedioDePago extends EntidadPersistente {

	
	@Column(name="medio_de_pago")
	private String medioDePago;
    @ManyToOne
    @JoinColumn(name = "tipo_pago_id", referencedColumnName = "id")
    private TipoPago tipoPago;


	public MedioDePago() {

	}

	public MedioDePago(String medio,TipoPago tipoPago) {
		this.medioDePago= medio;
		this.tipoPago= tipoPago;
	}

	public String getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(String medioDePago) {
		this.medioDePago = medioDePago;
	}

	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}
}
