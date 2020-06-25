package MiTest;


import BandejaDeMensajes.BandejaDeMensajes;
import BandejaDeMensajes.Mensaje;
import CategorizadorDeEmpresas.Sector;
import DatosDeOperaciones.*;
import ValidadorTransparencia.*;
import Operaciones.Egreso;
import Operaciones.Presupuesto;
import Organizacion.Empresa;
import Usuarios.Estandar;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.SimpleFormatter;

public class TestMensajes{

    @Test
    public void OrdernarMensajes() throws Exception{

        BandejaDeMensajes bandeja = new BandejaDeMensajes();

        Date ayer = new SimpleDateFormat("dd/MM/yyyy").parse("21/6/2020");
        Date anteayer = new SimpleDateFormat("dd/MM/yyyy").parse("20/6/2020");

        bandeja.crearMensaje("Mensaje ayer");
        bandeja.crearMensaje("Mensaje hoy");
        bandeja.crearMensaje("Mensaje anteayer");

        bandeja.getMensajes().get(2).setFecha(anteayer);
        bandeja.getMensajes().get(0).setFecha(ayer);

        bandeja.ordenarPorFechaRecientePrimero();

        bandeja.filtrarPorFecha(ayer).forEach(m->m.mostrarMensaje());
        bandeja.filtrarPorLeido(false).forEach(m->m.mostrarMensaje());
        bandeja.mostrarTodosLosMensajes();
    }

    @Test
    public void ValidacionesYMensajes() throws Exception {

        Sector construccion = new Sector("Construccion");
        Empresa miPyme = new Empresa(3,5000003.0,"Construccion",construccion);

        ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima(1);
        ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
        ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

        ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMenorValor, validacionMinima, validacionPresupuesto);

        Producto RAM = new Producto("Memoria RAM 4 gb DDR3");
        ItemEgreso RAMs = new ItemEgreso(RAM, 1, 3000);

        Producto placaDeVideo = new Producto("4GB DDR5");
        ItemEgreso placasDeVideo = new ItemEgreso(placaDeVideo, 2, 5000);

        TipoDocumento FacturaA = new TipoDocumento("Factura A");
        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yy");
        Date fechaDePedido = parseador.parse("31/01/20");
        Date fechaDeEntrega = parseador.parse("05/02/20");
        DocumentoComercial unDocumento = new DocumentoComercial(FacturaA, 11111, fechaDePedido, fechaDeEntrega, "Ejemplo");

        MedioDePago efectivo = new MedioDePago(1212, "Efectivo");

        Proveedor lautaroRobles = new Proveedor("Lautaro Robles", 41424242, 1823);
        Proveedor lautaroIturregui = new Proveedor("Lautaro Iturregui", 2224222, 1853);

        Egreso unaCompra = new Egreso(100, unDocumento, efectivo, lautaroRobles, miPyme, RAMs, placasDeVideo);

        ItemPresupuesto RAMpresupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3000);
        ItemPresupuesto placaVideoPresupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 5000);

        ItemPresupuesto RAM2presupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3500);
        ItemPresupuesto placaVide2Presupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 6000);

        Presupuesto primerPresupuesto = new Presupuesto(4000, unaCompra, unDocumento, "31/01/20", lautaroRobles, RAMpresupuesto, placaVideoPresupuesto);
        Presupuesto segundoPresupuesto = new Presupuesto(4210, unaCompra, unDocumento, "1/02/20", lautaroIturregui, RAM2presupuesto, placaVide2Presupuesto);

        unaCompra.agregarPresupuestos(primerPresupuesto,segundoPresupuesto);
        miPyme.agregarOperaciones(unaCompra);

        Estandar unUsuario = new Estandar(miPyme, "Lautaro", "1234", "lautaro@robles.com");

        unaCompra.agregarRevisor(unUsuario);

        List<Egreso> egresos = miPyme.getEgresos(); //Lo egresos que no han sido validados o no pasaron las pruebas anteriormente
        egresos.forEach(egreso -> validador.validarEgreso(egreso));

        unUsuario.verMensajes();
    }
}