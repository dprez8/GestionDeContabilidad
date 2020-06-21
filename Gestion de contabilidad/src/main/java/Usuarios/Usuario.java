package Usuarios;
import java.io.IOException;
import java.util.*;

import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;
import ValidadorDeContrasenia.*;

public abstract class Usuario {
	protected String usuario;
	protected String contrasenia;
	protected String mail;
	private static List<ControladorUsuario> controlUsuario = new ArrayList<>();
	static Scanner scanner= new Scanner(System.in);
	
	public void validarContraseña() throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
	    for (int i = 0; i < controlUsuario.size(); i++){ //no usar foreach porque sino el try y el catch tienen que estar adentro de la funcion lambda
	    	controlUsuario.get(i).validarConstrasenia(this.contrasenia, this.mail, this.usuario);
		}
	}

	public static void setNuevoControlUsuario(ControladorUsuario ... nuevoControlUsuario) {
		Usuario.controlUsuario = new ArrayList<>();
		Collections.addAll(Usuario.controlUsuario, nuevoControlUsuario);
	}

	/**Estos metodos se implementaran cuando veamos el tema de persistencia*/
	public static void iniciarSesion() {
		//
	}

	public void singUp(){
		//
	}
}
