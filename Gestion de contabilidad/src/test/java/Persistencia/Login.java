package Persistencia;

import org.junit.Before;
import org.junit.Test;

import Domain.Entities.Organizacion.Empresa;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;

public class Login {

	private Repositorio<Usuario> repoUsuarios;
	private Repositorio<EntidadJuridica> repoEntidadJuridica;
	
    @Before
    public void antesDePersistir() {
        this.repoUsuarios = new Repositorio<Usuario>(new DaoHibernate<Usuario>(Usuario.class));
        this.repoEntidadJuridica = new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
    }
	 @Test
	 public void crearUsuario(){
		 EntidadJuridica coca= new EntidadJuridica();
		 EntidadJuridica pepsi = new EntidadJuridica(); 
		 Estandar eteSech = new Estandar(coca, "eteSech", "1234compa", "eteSech@gmail.com");
		 Estandar elPepe = new Estandar(pepsi, "elPepe", "456pepe", "elPepe@gmail.com");
		 repoUsuarios.agregar(eteSech);
		 repoUsuarios.agregar(elPepe);
		 
	 }
}
