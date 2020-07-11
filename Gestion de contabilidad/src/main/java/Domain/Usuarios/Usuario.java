package Domain.Usuarios;

public abstract class Usuario {
	// Esta informacion debe venir de una base de datos
	protected String nombre;
	protected String contrasenia;
	protected String mail;

	/**Estos metodos se implementaran cuando veamos el tema de persistencia*/
	public static void iniciarSesion() {
		//
	}

	public void singUp(){
		//
	}
}
