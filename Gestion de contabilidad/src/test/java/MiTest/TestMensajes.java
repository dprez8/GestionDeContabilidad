package MiTest;

import BandejaDeMensajes.BandejaDeMensajes;
import CategorizadorDeEmpresas.Sector;
import DatosDeOperaciones.*;
import ValidadorTransparencia.*;
import Operaciones.Egreso;
import Operaciones.Presupuesto;
import Organizacion.Empresa;
import Usuarios.Estandar;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.logging.SimpleFormatter;

public class TestMensajes{
    @Test
    public void OrdernarMensajes() throws Exception{

        BandejaDeMensajes bandeja = new BandejaDeMensajes();

        Date hoy = new Date();
        Date ayer = new SimpleDateFormat("dd/MM/yyyy").parse("21/6/2020");
        Date anteayer = new SimpleDateFormat("dd/MM/yyyy").parse("20/6/2020");

        bandeja.crearMensaje(ayer, "Mensaje ayer");
        bandeja.crearMensaje(hoy, "Mensaje hoy");
        bandeja.crearMensaje(anteayer, "Mensaje anteayer");

        bandeja.imprimirMensajes();

        bandeja.ordenarPorFechaRecientePrimero();

        System.out.println("\nOrdenados por mas reciente primero");
        bandeja.imprimirMensajes();

        bandeja.ordenarPorFechaRecienteUltimo();

        System.out.println("\nOrdenados por mas reciente último");
        bandeja.imprimirMensajes();
    }

    @Test
    public void ValidacionesYMensajes() throws Exception {

        Sector construccion = new Sector("Construccion");
        Empresa miPyme = new Empresa(3,5000003.0,"Construccion",construccion);

        ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima(3);
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

        Proveedor lautaro = new Proveedor("Lautaro", 41424242, 1823);

        Egreso unaCompra = new Egreso(unDocumento, efectivo, lautaro, miPyme, RAMs, placasDeVideo);

        ItemPresupuesto RAMpresupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3000);
        ItemPresupuesto placaVideoPresupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 5000);

        ItemPresupuesto RAM2presupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3500);
        ItemPresupuesto placaVide2Presupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 6000);

        Presupuesto primerPresupuesto = new Presupuesto(unaCompra, unDocumento, "31/01/20", RAMpresupuesto, placaVideoPresupuesto);
        Presupuesto segundoPresupuesto = new Presupuesto(unaCompra, unDocumento, "1/02/20", RAM2presupuesto, placaVide2Presupuesto);

        unaCompra.agregarPresupuestos(primerPresupuesto,segundoPresupuesto);
        miPyme.agregarOperaciones(unaCompra);

        Estandar unUsuario = new Estandar(miPyme, "Lautaro", "1234", "lautaro@robles.com");

        unaCompra.agregarRevisor(unUsuario);

        lanzarHilo(miPyme, validador, unUsuario);
    }

    /**Quizas esta parte pueda ir en la clase Schudeler*/
    public void lanzarHilo(Empresa miPyme, ValidadorDeTransparencia validador, Estandar unUsuario){
        Scheduler hilo = new Scheduler(miPyme,validador);
        Timer timer    = new Timer();
        timer.schedule(hilo,20,10*1000); /**La instancia Scheduler llama a la funcion run con timer.schedule*/

        unUsuario.verMensajes();
    }
}