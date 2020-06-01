package MiTest;

import DatosDeOperaciones.Item;
import DatosDeOperaciones.Producto;
import Operaciones.Egreso;
import Validador.ControladorUsuario;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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
    @Test
    public void testPrecioTotalItems(){
        Producto RAM = new Producto("Memoria RAM 4 gb DDR3",3000);
        Item RAMs = new Item(RAM,1);

        Producto placaDeVideo = new Producto("4GB DDR5",6000);
        Item placasDeVideo = new Item(placaDeVideo,2);

        Egreso unaCompra = new Egreso();

        unaCompra.agregarItems(RAMs,placasDeVideo);

        Assert.assertEquals(15000,unaCompra.valorTotal(),0);

    }
}
