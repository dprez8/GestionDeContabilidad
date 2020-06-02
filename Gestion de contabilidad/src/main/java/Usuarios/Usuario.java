package Usuarios;
import java.io.IOException;
import java.util.*;

import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;
import Validador.*;

public abstract class Usuario {
	private String usuario;
	private String contraseña;
	private String mail;
	private static List<ControladorUsuario> controlUsuario = new ArrayList<>();
	static Scanner scanner= new Scanner(System.in);
	
	public static void iniciarSesion() { //Duda si este metodo deberia estar en usuario
		
		System.out.println("Ingrese el usuario: ");
		//Busqueda en la base de datos el usuario
		System.out.println("Ingrese la contraseña: ");
		//Comprobar contraseña dado el usuario
		
	}

    public void singUp() throws IOException {
		//Duda si este metodo deberia estar en usuario
		boolean rehacer;
		boolean creado;
		
		do{	//Que permita Al usuario volver a ingresar los datos si salta alguna excepcion

			rehacer=false;
			creado=false;

			try {
				System.out.println("Ingrese un nombre de usuario: ");
				String user= scanner.nextLine();
				System.out.println("Ingrese una contraseña: ");
				String password= scanner.nextLine();
				System.out.println("Ingrese su correo electronico: ");
				String mail= scanner.nextLine();
				this.validarContraseña();
				creado=true; //si no salta ninguna excepcion se crea el usuario
				this.generarUsuario(user,password,mail);
			}
			catch(contraseniaCorta cc){
				cc.getMessage();
			}
			catch(contraseniaMuyComun cmc){
				cmc.getMessage();
			}
			catch(repiteContraseniaEnMailOUsuario rcmu){
				rcmu.getMessage();
			}

			if(!creado){
				System.out.println("Ingrese los datos nuevamente: ");
				scanner.next();
				rehacer=true;
			}

		}while(rehacer);
	}
	
	public void validarContraseña() throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
	    for (int i = 0; i < controlUsuario.size(); i++){ //no usar foreach porque sino el try y el catch tienen que estar adentro de la funcion lambda
	    	controlUsuario.get(i).validarConstrasenia(this.contraseña, this.mail, this.usuario);
		}
	}

	public static void setNuevoControlUsuario(ControladorUsuario ... nuevoControlUsuario) {
		Usuario.controlUsuario = new ArrayList<>();
		Collections.addAll(Usuario.controlUsuario, nuevoControlUsuario);
	}
	
	public void generarUsuario(String user,String password,String email) {
		usuario=user;
		contraseña=password;
		mail=email;
	}
}
