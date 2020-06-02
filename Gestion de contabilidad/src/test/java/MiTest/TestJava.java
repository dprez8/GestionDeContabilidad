package MiTest;

import DatosDeOperaciones.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    //Momento de instanciacion
    Producto RAM = new Producto("Memoria RAM 4 gb DDR3",3000);
    ItemEgreso RAMs = new ItemEgreso(RAM,1);

    Producto placaDeVideo = new Producto("4GB DDR5",6000);
    ItemEgreso placasDeVideo = new ItemEgreso(placaDeVideo,2);

    TipoDocumento FacturaA = new TipoDocumento("Factura A");
    SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yy");
    Date fechaDePedido = parseador.parse("31/01/20");
    Date fechaDeEntrega = parseador.parse("05/02/20");
    DocumentoComercial unDocumento = new DocumentoComercial(FacturaA,11111,fechaDePedido,fechaDeEntrega,"Ejemplo");

    MedioDePago efectivo = new MedioDePago(1212,"Efectivo");

    Proveedor lautaro = new Proveedor("Lautaro",41424242,1823);

    public TestJava() throws ParseException {
    }
    @Test
    public void testEgreso(){

        Empresa manaos = new Empresa();
        Egreso unaCompra = new Egreso(unDocumento,efectivo,lautaro,manaos,RAMs,placasDeVideo);

        Assert.assertEquals(15000,unaCompra.valorTotal(),0);
    }
    @Test
    public void testUsuarios(){
        /****Parte administrador****/
        Administrador viktor = new Administrador();

        Empresa CocaCola = viktor.darAltaEmpresa();

        Osc MercadoLibre = viktor.darAltaOsc();

        System.out.print(CocaCola);
        System.out.print(MercadoLibre);

        Estandar fernando = viktor.darAltaUsuario(CocaCola);
        viktor.asociarUsuarioAOrganizacion(fernando,CocaCola);
        /***************************/
        /***Parte Estandar**********/
        Egreso compraDePizzas = fernando.darAltaEgreso(unDocumento,efectivo,lautaro,placasDeVideo,RAMs);
    }
}
