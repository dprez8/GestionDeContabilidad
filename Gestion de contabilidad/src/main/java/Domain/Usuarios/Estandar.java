package Domain.Usuarios;

import Domain.BandejaDeMensajes.BandejaDeMensajes;
import Domain.Organizacion.*;

public class Estandar extends Usuario {
	
	private EntidadJuridica miOrganizacion; // Conoce su organizacion
	private BandejaDeMensajes bandejaDeMensajes;

	public Estandar(EntidadJuridica unaOrganizacion,String nombre,String contrasenia, String mail){
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.mail = mail;
		this.miOrganizacion = unaOrganizacion;
		this.bandejaDeMensajes = new BandejaDeMensajes();
	}

	public BandejaDeMensajes getBandejaDeMensajes() {
		return bandejaDeMensajes;
	}
}

