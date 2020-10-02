package Domain.Entities.Usuarios;

import javax.persistence.*;

import Domain.Entities.BandejaDeMensajes.BandejaDeMensajes;
import Domain.Entities.Organizacion.*;

@Entity
@DiscriminatorValue("estandar")
public class Estandar extends Usuario {

	@ManyToOne
	@JoinColumn(name = "organizacion_id", referencedColumnName = "id")
	private Organizacion miOrganizacion; // Conoce su organizacion
	@Transient
	private BandejaDeMensajes bandejaDeMensajes;

	protected Estandar(){
		this.bandejaDeMensajes = new BandejaDeMensajes(this);
	}

	public Estandar(EntidadJuridica unaOrganizacion,String nombre,String contrasenia, String mail){
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.mail = mail;
		this.miOrganizacion = unaOrganizacion;
		this.bandejaDeMensajes = new BandejaDeMensajes(this);
	}

	public BandejaDeMensajes getBandejaDeMensajes() {
		return bandejaDeMensajes;
	}
}

