import Domain.BandejaDeMensajes.Mensaje;
import Domain.CategorizadorDeEmpresas.Sector;
import Domain.DatosDeOperaciones.*;
import Domain.DireccionPostal.DireccionPostal;
import Domain.Operaciones.*;
import Domain.ValidadorTransparencia.*;
import Domain.Organizacion.*;
import Domain.Operaciones.Egreso.*;
import Domain.Usuarios.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class Main {
    public static void main(String[] args) throws Exception {

        /**Creacion de los validadores*/
        ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima(1);
        ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
        ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

        ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMinima,validacionPresupuesto,validacionMenorValor);

        /**Creacion del Scheduler*/
        /*
        Scheduler hilo = new Scheduler(10000); //esta en ms, serian 10 seg
*/
        /**Creacion de los datos de egreso y sus presupuestos, ejemplo*/
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

        DireccionPostal direPostalIturregui = new DireccionPostal();
        DireccionPostal direPostalRobles    = new DireccionPostal();

        Proveedor lautaroRobles = new Proveedor("Lautaro Robles", 41424242, direPostalRobles);
        Proveedor lautaroIturregui = new Proveedor("Lautaro Iturregui", 2224222, direPostalIturregui);

        ItemPresupuesto RAMpresupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3000);
        ItemPresupuesto placaVideoPresupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 5000);

        ItemPresupuesto RAM2presupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3500);
        ItemPresupuesto placaVide2Presupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 6000);

        /**Creacion de una organizacion ejemplo*/
        DireccionPostal direccionPostal = new DireccionPostal();

        Sector construccion = new Sector("Construccion");
        Empresa miPyme = new Empresa(3,5000003.0,"Construccion",construccion);
        EntidadJuridica unaEntidad  = new EntidadJuridica("MiPyme",1234,"Nose",direccionPostal,1);
        unaEntidad.setTipoEntidadJuridica(miPyme);

        /**Construccion del egreso*/
        BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();

        Egreso unaCompra = egresoBuilder.agregarProveedor(lautaroIturregui)
                            .agregarFechaOperacion(fechaDeEntrega)
                            .agregarMedioDePago(efectivo)
                            .agregarDocumentoComercial(unDocumento)
                            .agregarDatosOrganizacion(unaEntidad)
                            .agregarItems(RAMs,placasDeVideo)
                            .build();

        /**Creacion de dos presupuestos con un egreso*/
        Presupuesto primerPresupuesto = new Presupuesto(4000, unaCompra);
        primerPresupuesto.setDocumento(unDocumento);
        primerPresupuesto.setFechaVigente("31/12/20");
        primerPresupuesto.setProveedor(lautaroIturregui);
        primerPresupuesto.addItems(placaVideoPresupuesto,RAMpresupuesto);

        Presupuesto segundoPresupuesto = new Presupuesto(4210, unaCompra);
        segundoPresupuesto.setDocumento(unDocumento);
        segundoPresupuesto.setFechaVigente("30/11/20");
        segundoPresupuesto.setProveedor(lautaroRobles);
        segundoPresupuesto.addItems(placaVide2Presupuesto,RAM2presupuesto);

        /**Creacion de un usuario, el cual sera revisor*/
        Estandar unUsuario = new Estandar(unaEntidad, "Lautaro", "1234", "lautaro@robles.com");

        /**Configuracion del egreso para pruebas*/
        unaEntidad.addOperaciones(unaCompra);
        unaCompra.addRevisores(unUsuario);
        unaCompra.addPresupuestos(primerPresupuesto,segundoPresupuesto);

        /**Inicio scheduler para validar el egreso*/
        Scheduler.arrancarTarea(unaEntidad,validador);

        /**Solo es necesario un revisor para ver los mensajes*/
        List<Mensaje> mensajes = new ArrayList<>();
        Estandar revisor;

        revisor = unaCompra.getRevisores().get(0);

        mensajes.addAll(revisor.getBandejaDeMensajes().getMensajes());
        mensajes.forEach(unMsj->System.out.println(unMsj.getCuerpo()));
        mensajes.clear();
    }
}
