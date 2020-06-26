package Usuarios;
import java.io.IOException;
import java.util.*;

import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;
import ValidadorDeContrasenia.*;

public abstract class Usuario {
	// Esta informacion debe venir de una base de datos
	protected String usuario;
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
