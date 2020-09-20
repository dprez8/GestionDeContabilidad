package Domain.Usuarios;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="tipo_de_usuario")
public abstract class Usuario {
	// Esta informacion debe venir de una base de datos
	@Id
	@Column(name="usuario_id")
	private int usuarioId;
	@Column
	protected String nombre;
	@Column
	protected String contrasenia;
	@Column
	protected String mail;

	/**Estos metodos se implementaran cuando veamos el tema de persistencia*/
	public static void iniciarSesion() {
		//
	}

	public void singUp(){
		//
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

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}


}
