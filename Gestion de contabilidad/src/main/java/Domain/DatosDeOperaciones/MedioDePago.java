package Domain.DatosDeOperaciones;

import javax.persistence.*;

@Entity
@Table
public class MedioDePago {
    @Id
	@Column(name="medio_id")
	private String medioId;
    @Column (name="payment_type")
    private String tipoPago;
    @Column (name="nommbre_medio")
    private String nombreMedioDePago;

	public MedioDePago(String medio_id,String tipoPago, String nombreMedioDePago) {
		this.medioId = medio_id;
		this.tipoPago= tipoPago;
		this.nombreMedioDePago = nombreMedioDePago;
	}

}
