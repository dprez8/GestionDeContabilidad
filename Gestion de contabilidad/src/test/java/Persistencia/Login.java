package Persistencia;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.ValidadorDeContrasenia.ValidadorDeContrasenia;
import Domain.Entities.ValidadorDeContrasenia.ValidarIgualAMailOUsuario;
import Domain.Entities.ValidadorDeContrasenia.ValidarLongitudCorta;
import Domain.Entities.ValidadorDeContrasenia.ValidarTop10k;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;

public class Login {

	private Repositorio<Usuario> repoUsuarios;
	private Repositorio<EntidadJuridica> repoEntidadJuridica;
	
    @Before

    public void antesDePersistir() throws FileNotFoundException, IOException {
        this.repoUsuarios = new Repositorio<Usuario>(new DaoHibernate<Usuario>(Usuario.class));
        this.repoEntidadJuridica = new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
       
        Properties prop=new Properties();
        prop.load(new FileReader("src/main/resources/config/config.properties"));

        ValidarLongitudCorta validarLongitudCorta           = new ValidarLongitudCorta(Integer.parseInt(prop.getProperty("LONGITUDMINIMA")));
        ValidarTop10k validarTop10k                         = new ValidarTop10k(prop.getProperty("DATAFILEPATH"));
        ValidarIgualAMailOUsuario validarIgualAMailOUsuario = new ValidarIgualAMailOUsuario();

        ValidadorDeContrasenia.addValidaciones(validarLongitudCorta,validarTop10k,validarIgualAMailOUsuario);
    }
	 @Test
	 public void crearUsuario() throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta{
		 EntidadJuridica coca= new EntidadJuridica();
		 EntidadJuridica pepsi = new EntidadJuridica(); 
		 Estandar eteSech = new Estandar(coca, "eteSech", "1234hgchh", "eteSech@gmail.com");
		 Estandar elPepe = new Estandar(pepsi, "elPepe", "4356p4epe", "elPepe@gmail.com");
		 repoUsuarios.agregar(eteSech);
		 repoUsuarios.agregar(elPepe);
		 
	 }
}
