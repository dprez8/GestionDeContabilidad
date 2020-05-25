package Usuarios;
import java.io.IOException;
import java.util.Scanner;

import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;
import Validador.*;

public class Usuario {
	private String usuario;
	private String contraseña;
	private String mail;
	//private static ControladorUsuario controlUsuario;
	static Scanner scanner= new Scanner(System.in);
	
	public Usuario(String usuario, String contraseña,String mail) throws IOException, contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario {
		super();
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.mail=mail;
	}
	
	public static void iniciarSesion() { //Duda si este metodo deberia estar en usuario
		
		System.out.println("Ingrese el usuario: ");
		//Busqueda en la base de datos el usuario
		System.out.println("Ingrese la contraseña: ");
		//Comprobar contraseña dado el usuario
		
	}
	
	public static void singUp() throws IOException, contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario { 
		//Duda si este metodo deberia estar en usuario
	boolean rehacer=false;
		
		do{	
			try {
				System.out.println("Ingrese un nombre de usuario: ");
				String user= scanner.nextLine();
				System.out.println("Ingrese una contraseña: ");
				String password= scanner.nextLine();
				System.out.println("Ingrese su correo electronico: ");
				String mail= scanner.nextLine();
				validarContraseña(user,password, mail);
				
				Usuario nuevoUsuario=new Usuario(user,password,mail);
			}
			catch(contraseniaCorta cc){
				cc.getMessage();
				System.out.println("Ingrese las datos nuevamente: ");
				scanner.next();
				rehacer=true;
			}
			catch(contraseniaMuyComun cmc){
				cmc.getMessage();
				System.out.println("Ingrese los datos nuevamente: ");
				scanner.next();
				rehacer=true;
			}
			catch(repiteContraseniaEnMailOUsuario rcmu){
				rcmu.getMessage();
				System.out.println("Ingrese los datos nuevamente: ");
				scanner.next();
				rehacer=true;
			}
		}while(rehacer);			
	}
	
	public static void validarContraseña(String usuario,String password, String mail) throws IOException, contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario{
	
		ControladorUsuario.validarConstrasenia(password, usuario, mail);
	}
}
