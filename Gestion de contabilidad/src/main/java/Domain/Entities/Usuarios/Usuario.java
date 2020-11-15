package Domain.Entities.Usuarios;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.ValidadorDeContrasenia.ValidadorDeContrasenia;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import com.google.common.hash.Hashing;

import javax.persistence.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_usuario")
public abstract class Usuario extends EntidadPersistente {

	@Column(name = "username")
	protected String username;

	@Column
	protected String nombre;
	
	@Column
	protected String apellido;
	
	@Column(name="contrasenia")
	protected String contrasenia;

	@Column
	protected String mail;

	public Usuario(String username, String nombre, String apellido,String contrasenia, String mail) throws contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, IOException {
		this.username = username;
		this.nombre   = nombre;
		this.apellido = apellido;
		this.mail  	  = mail;
		verificarContrasenia(contrasenia);
	}

	public Usuario(){

	}
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

	public void setContrasenia(String contrasenia) throws contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, IOException {
		verificarContrasenia(contrasenia);
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	private void verificarContrasenia(String contrasenia) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {

		if(ValidadorDeContrasenia.validarContrasenia(this)){
			this.HashearPassword(contrasenia);}
	}

	private void HashearPassword(String contrasenia){
		this.contrasenia = Hashing.sha256()
				.hashString(contrasenia, StandardCharsets.UTF_8)
				.toString();
	}
}
