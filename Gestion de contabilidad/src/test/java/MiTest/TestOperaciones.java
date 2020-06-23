package MiTest;

import CategorizadorDeEmpresas.Sector;
import DatosDeOperaciones.*;
import Operaciones.Egreso;
import Organizacion.Empresa;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestOperaciones {
    public TestOperaciones() throws ParseException {
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

    Sector servicios    = new Sector("Servicios");

    @Test
    public void testEgreso(){

        Empresa manaos = new Empresa(12,130000,"SUMINISTRO DE AGUA",servicios);
        Egreso unaCompra = new Egreso(unDocumento,efectivo,lautaro,manaos,RAMs,placasDeVideo);

        Assert.assertEquals(13000,unaCompra.valorTotal(),0);
    }
}
