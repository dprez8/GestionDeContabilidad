package Domain.Entities.Usuarios;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_usuario")
public abstract class Usuario extends EntidadPersistente {

	@Column
	protected String nombre;

	@Transient
	protected String contrasenia;
	
	@Column(name="contrasenia")
	protected String hashedPassword;

	@Column
	protected String mail;

	/**Setters & getters*/
	public String getNombre(){
		return nombre;
	}

	public String getContrasenia(){
		return contrasenia;
	}

	public String getMail(){
		return mail;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
