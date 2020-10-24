package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

@Entity
@Table(name="medio_de_pago")
public class MedioDePago extends EntidadPersistente {

	
	@Column(name="medio_de_pago")
	private String medioDePago;

	public MedioDePago() {}
	public MedioDePago(String medio) {
		this.medioDePago= medio;
	}

	public String getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(String medioDePago) {
		this.medioDePago = medioDePago;
	}
}
