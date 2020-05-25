package MiTest;

import Validador.ControladorUsuario;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestJava {
    @Test
    public void testValidador() throws IOException {
        Properties prop=new Properties();
        FileInputStream ip= new FileInputStream("src/main/resources/config.properties");
        //"src/main/resources/10k-worst-passwords.txt"
        String contrasenia = "fortune12";
        try {
            prop.load(ip);
            ControladorUsuario controla = new ControladorUsuario(prop.getProperty("dataFilePath"), Integer.parseInt(prop.getProperty("longitudMinima")));
            controla.validarConstrasenia(contrasenia, "", "");
            ip.close();
        } catch (Exceptions.repiteContraseniaEnMailOUsuario repiteContraseniaEnMailOUsuario) {
            repiteContraseniaEnMailOUsuario.printStackTrace();
        } catch (Exceptions.contraseniaMuyComun contraseniaMuyComun) {
            contraseniaMuyComun.printStackTrace();
        } catch (Exceptions.contraseniaCorta contraseniaCorta) {
            contraseniaCorta.printStackTrace();
        }
    }
}
