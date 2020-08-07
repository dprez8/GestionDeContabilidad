import Domain.CategorizadorDeEmpresas.Sector;
import Domain.DatosDeOperaciones.*;
import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;
import Domain.Organizacion.Empresa;
import Domain.Usuarios.Estandar;
import Domain.ValidadorTransparencia.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws Exception {
    /**
        Sector construccion = new Sector("Construccion");
        Empresa miPyme = new Empresa(3,5000003.0,"Construccion",construccion);

        ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima(1);
        ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
        ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

        ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMenorValor, validacionPresupuesto, validacionMinima);

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

        lanzarHilo(miPyme, validador);
    }
    **/
    /**Quizas esta parte pueda ir en la clase Schudeler*/
    /**public static void lanzarHilo(Empresa miPyme, ValidadorDeTransparencia validador){
        Scheduler hilo = new Scheduler(miPyme,validador);
        Timer timer    = new Timer();
        timer.schedule(hilo,0,5 * 1000); La instancia Scheduler llama a la funcion run con timer.schedule*/
        /**vole el usuario que imprimia mensaje del schedule, hay que arreglarlo aca en el main**/
    }
}
