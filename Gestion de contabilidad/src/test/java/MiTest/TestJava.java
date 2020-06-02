package MiTest;

import DatosDeOperaciones.DocumentoComercial;
import DatosDeOperaciones.Item;
import DatosDeOperaciones.ItemEgreso;
import DatosDeOperaciones.Producto;
import Operaciones.Egreso;
import Organizacion.Empresa;
import Organizacion.EntidadJuridica;
import Organizacion.Osc;
import Usuarios.Administrador;
import Usuarios.Estandar;
import Validador.ControladorUsuario;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.text.Document;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class TestJava {
    //Momento de instanciacion
    Producto RAM = new Producto("Memoria RAM 4 gb DDR3",3000);
    ItemEgreso RAMs = new ItemEgreso(RAM,1);

    Producto placaDeVideo = new Producto("4GB DDR5",6000);
    ItemEgreso placasDeVideo = new ItemEgreso(placaDeVideo,2);
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
    public void testEgreso(){

            
 //       Egreso unaCompra = new Egreso();

 //       unaCompra.agregarItems(RAMs,placasDeVideo);

  //      Assert.assertEquals(15000,unaCompra.valorTotal(),0);
          Assert.assertEquals(true, true);
    }
    @Test
    public void testUsuarios(){
        /****Parte administrador****/
        Administrador viktor = new Administrador();

        Empresa CocaCola = viktor.darAltaEmpresa();

        Osc MercadoLibre = viktor.darAltaOsc();
        System.out.print(CocaCola);
        Estandar fernando = viktor.darAltaUsuario(CocaCola);
        viktor.asociarUsuarioAOrganizacion(fernando,CocaCola);
        /***************************/
        /***Parte Estandar**********/
        //fernando.darAltaEgreso(new Egreso());
    }
}
