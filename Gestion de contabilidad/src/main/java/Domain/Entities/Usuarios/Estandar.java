package Domain.Entities.Usuarios;

import java.nio.charset.StandardCharsets;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.common.hash.Hashing;

import Domain.Entities.BandejaDeMensajes.BandejaDeMensajes;
import Domain.Entities.Organizacion.*;

@Entity
@DiscriminatorValue("estandar")
public class Estandar extends Usuario {

	@ManyToOne
	@JoinColumn(name = "organizacion_id", referencedColumnName = "id")
	@Cascade(CascadeType.ALL)
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
		this.HashearPassword(contrasenia);
	}

	public BandejaDeMensajes getBandejaDeMensajes() {
		return bandejaDeMensajes;
	}
	
	public void HashearPassword(String contrasenia){
		this.hashedPassword = Hashing.sha256()
				  .hashString(contrasenia, StandardCharsets.UTF_8)
				  .toString();
	}
}

