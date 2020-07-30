package MiTest;

import Domain.CategorizadorDeEmpresas.Sector;
import Domain.Exceptions.*;
import Domain.Organizacion.*;
import Domain.Usuarios.*;
import Domain.ValidadorDeContrasenia.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

public class TestValidadorContrasenia {

    public TestValidadorContrasenia() throws ParseException {
    }
    @Test
    public void testValidador() throws IOException, contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario {
       /*
        Properties prop=new Properties();
        FileInputStream ip= new FileInputStream("src/main/resources/config.properties");
        //"src/main/resources/10k-worst-passwords.txt"
        prop.load(ip);
        Usuario.setNuevoControlUsuario(new ValidarLongitudCorta(Integer.parseInt(prop.getProperty("longitudMinima"))), new ValidarTop10k(prop.getProperty("dataFilePath")), new ValidarIgualAMailOUsuario());
        //nose como hacer una config que devuelva objectos instanciados o que los instancie, creo que no puede

        Sector servicios    = new Sector("Servicios");
        Empresa CocaCola = new Empresa(12,130000,"SUMINISTRO DE AGUA",servicios);
        Estandar fernando = new Estandar(CocaCola,"Fernando","fortune12","fer12@mail.com");
        fernando.validarContrasenia();
        ip.close();*/
    }
}
