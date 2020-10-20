package Persistencia;

import org.junit.Test;

import Domain.Entities.Usuarios.Usuario;
import db.EntityManagerHelper;

public class ObtenerUsuario {
	@Test
	public void obtenerUsuario(){
	
     UsuarioRequest usuarioRequest= new UsuarioRequest();
     usuarioRequest.username="Diego";
     usuarioRequest.password="EteSech";
     Usuario usuario = (Usuario) EntityManagerHelper
			.createQuery("from Usuario where nombre = :username and contrasenia = :password")
            .setParameter("username",usuarioRequest.username)
            .setParameter("password",usuarioRequest.password)
            .getSingleResult();
	System.out.println("Usuario: "+usuario.getNombre()+"Contrasenia"+usuario.getContrasenia());
	
	}
	public class UsuarioRequest{
		public String username;
		public String password;
	}

}
