package Domain.Entities.Usuarios;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.common.hash.Hashing;

import Domain.Entities.BandejaDeMensajes.BandejaDeMensajes;
import Domain.Entities.Organizacion.*;
import Domain.Entities.ValidadorDeContrasenia.ValidadorDeContrasenia;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;

@Entity
@DiscriminatorValue("estandar")
public class Estandar extends Usuario {

	@ManyToOne
	@JoinColumn(name = "organizacion_id", referencedColumnName = "id")
	@Cascade(CascadeType.ALL)
	private Organizacion miOrganizacion; // Conoce su organizacion
	@Transient
	private BandejaDeMensajes bandejaDeMensajes;

	public Estandar(){
		this.bandejaDeMensajes = new BandejaDeMensajes(this);
	}

	public Estandar(Organizacion unaOrganizacion,String username, String nombre,String apellido,String contrasenia, String mail) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta{
		super(username,nombre,apellido,contrasenia,mail);
		this.miOrganizacion = unaOrganizacion;
		this.bandejaDeMensajes = new BandejaDeMensajes(this);
	}

	public BandejaDeMensajes getBandejaDeMensajes() {
		return bandejaDeMensajes;
	}

	public Organizacion getMiOrganizacion() {
		return miOrganizacion;
	}

	public void setMiOrganizacion(Organizacion miOrganizacion) {
		this.miOrganizacion = miOrganizacion;
	}

	public void setBandejaDeMensajes(BandejaDeMensajes bandejaDeMensajes) {
		this.bandejaDeMensajes = bandejaDeMensajes;
	}
}

