package Domain.Usuarios;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import Domain.BandejaDeMensajes.BandejaDeMensajes;
import Domain.Organizacion.*;

@Entity
@DiscriminatorValue("estandar")
public class Estandar extends Usuario {
	@Transient
	private EntidadJuridica miOrganizacion; // Conoce su organizacion
	@Transient
	private BandejaDeMensajes bandejaDeMensajes;

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

