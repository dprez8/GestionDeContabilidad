package MiTest;

import CategorizadorDeEmpresas.Sector;
import DatosDeOperaciones.*;
import Exceptions.contraseniaCorta;
import Exceptions.contraseniaMuyComun;
import Exceptions.repiteContraseniaEnMailOUsuario;
import Operaciones.Egreso;
import CategorizadorDeEmpresas.Categoria;
import Organizacion.Empresa;
import Organizacion.Osc;
import Usuarios.Administrador;
import Usuarios.Estandar;
import Usuarios.Usuario;
import ValidadorDeContrasenia.ValidarIgualAMailOUsuario;
import ValidadorDeContrasenia.ValidarLongitudCorta;
import ValidadorDeContrasenia.ValidarTop10k;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class TestJava {
    Sector servicios    = new Sector("Servicios");
    @Test
    public void testValidador() throws IOException, contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario {
        Properties prop=new Properties();
        FileInputStream ip= new FileInputStream("src/main/resources/config.properties");
        //"src/main/resources/10k-worst-passwords.txt"
        prop.load(ip);
        Usuario.setNuevoControlUsuario(new ValidarLongitudCorta(Integer.parseInt(prop.getProperty("longitudMinima"))), new ValidarTop10k(prop.getProperty("dataFilePath")), new ValidarIgualAMailOUsuario());
        //nose como hacer una config que devuelva objectos instanciados o que los instancie, creo que no puede

        Empresa CocaCola = new Empresa(12,130000,"SUMINISTRO DE AGUA",servicios);
        Estandar fernando = new Estandar(CocaCola,"Fernando","fortune12","fer12@mail.com");
        fernando.validarContraseña();
        ip.close();
    }
    //Momento de instanciacion
    Producto RAM = new Producto("Memoria RAM 4 gb DDR3");
    ItemEgreso RAMs = new ItemEgreso(RAM,1,3000);

    Producto placaDeVideo = new Producto("4GB DDR5");
    ItemEgreso placasDeVideo = new ItemEgreso(placaDeVideo,2,5000);

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

        Empresa manaos = new Empresa(12,130000,"SUMINISTRO DE AGUA",servicios);
        Egreso unaCompra = new Egreso(unDocumento,efectivo,lautaro,manaos,RAMs,placasDeVideo);

        Assert.assertEquals(15000,unaCompra.valorTotal(),0);
    }
    @Test
    public void testUsuarios(){
        /****Parte administrador****/
        Administrador viktor = new Administrador();

        Empresa CocaCola = new Empresa(12,130000,"SUMINISTRO DE AGUA",servicios);

        Osc MercadoLibre = new Osc();

        System.out.print(CocaCola);
        System.out.print(MercadoLibre);

        Estandar fernando = new Estandar(CocaCola,"Fernando","fortune12","fer12@mail.com");
        viktor.asociarUsuarioAOrganizacion(fernando,CocaCola);
        /***************************/
        /***Parte Estandar**********/
        Egreso compraDePizzas = fernando.darAltaEgreso(unDocumento,efectivo,lautaro,placasDeVideo,RAMs);
    }
}
