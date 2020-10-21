package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name="medio_de_pago")
public class MedioDePago extends EntidadPersistente {

    @Column (name="payment_type")
    private String tipoPago;
    @Column (name="nombre_medio")
    private String nombreMedioDePago;

	protected MedioDePago() {
	}

	public MedioDePago(String tipoPago, String nombreMedioDePago) {
		this.tipoPago= tipoPago;
		this.nombreMedioDePago = nombreMedioDePago;
	}

	public String getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public String getNombreMedioDePago() {
		return nombreMedioDePago;
	}

	public void setNombreMedioDePago(String nombreMedioDePago) {
		this.nombreMedioDePago = nombreMedioDePago;
	}
}
