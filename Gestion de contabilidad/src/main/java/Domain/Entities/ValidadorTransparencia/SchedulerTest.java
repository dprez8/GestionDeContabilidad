package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.BandejaDeMensajes.Mensaje;
import Domain.Entities.CategorizadorDeEmpresas.Sector;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.*;
import Domain.Entities.Organizacion.*;
import Domain.Entities.Operaciones.Egreso.*;
import Domain.Entities.Usuarios.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class SchedulerTest {
    public static void main(String[] args) throws Exception {

        /**Creacion de los validadores*/
        ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima(1);
        ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
        ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

        ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMinima, validacionPresupuesto, validacionMenorValor);

        /**Creacion de los datos de egreso y sus presupuestos, ejemplo*/
        Producto RAM = new Producto("Memoria RAM 4 gb DDR3");
        ItemEgreso RAMs = new ItemEgreso(RAM, 1, 3000);

        Producto placaDeVideo = new Producto("4GB DDR5");
        ItemEgreso placasDeVideo = new ItemEgreso(placaDeVideo, 2, 5000);

        TipoDocumento FacturaA = new TipoDocumento("Factura A");

        DocumentoComercial unDocumento = new DocumentoComercial(FacturaA, 11111);

        MedioDePago efectivo = new MedioDePago("efectivo");
        Pago unPago = new Pago("1231231", efectivo);

        Proveedor lautaroRobles = new Proveedor("Lautaro Robles", 41424242);
        Proveedor lautaroIturregui = new Proveedor("Lautaro Iturregui", 2224222);

        ItemPresupuesto RAMpresupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3000);
        ItemPresupuesto placaVideoPresupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 5000);

        ItemPresupuesto RAM2presupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3500);
        ItemPresupuesto placaVide2Presupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 6000);

        /**Creacion de una organizacion ejemplo*/

        Sector construccion = new Sector("Construccion");
        Empresa miPyme = new Empresa();
        miPyme.setSector(construccion);
        miPyme.setActividad("Construccion");
        miPyme.setVentasAnuales(50000003.0);
        miPyme.setCantidadDePersonal(3);
        EntidadJuridica unaEntidad = new EntidadJuridica();
        unaEntidad.setTipoEntidadJuridica(miPyme);

        /**Construccion del egreso*/
        BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();

        Egreso unaCompra = egresoBuilder.agregarProveedor(lautaroIturregui)
                .agregarFechaOperacion(LocalDate.now())
                .agregarPago(unPago)
                .agregarDocumentoComercial(unDocumento)
                .agregarDatosOrganizacion(unaEntidad)
                .agregarItems(RAMs, placasDeVideo)
                .build();

        /**Creacion de dos presupuestos con un egreso*/
        Presupuesto primerPresupuesto = new Presupuesto(4000, unaCompra);
        primerPresupuesto.setDocumento(unDocumento);
        primerPresupuesto.setFechaVigente(LocalDate.of(2020, Month.AUGUST, 14));
        primerPresupuesto.setProveedor(lautaroIturregui);
        primerPresupuesto.addItems(placaVideoPresupuesto, RAMpresupuesto);

        Presupuesto segundoPresupuesto = new Presupuesto(4210, unaCompra);
        segundoPresupuesto.setDocumento(unDocumento);
        segundoPresupuesto.setFechaVigente(LocalDate.of(2020, Month.AUGUST, 14));
        segundoPresupuesto.setProveedor(lautaroRobles);
        segundoPresupuesto.addItems(placaVide2Presupuesto, RAM2presupuesto);

        /**Creacion de un usuario, el cual sera revisor*/
        Estandar unUsuario = new Estandar(unaEntidad, "Lautaro", "1234", "lautaro@robles.com");

        /**Configuracion del egreso para pruebas*/
        unaEntidad.addOperaciones(unaCompra);
        unaCompra.addRevisores(unUsuario);
        unaCompra.addPresupuestos(primerPresupuesto, segundoPresupuesto);

        /**Creacion del Scheduler*/
        SchedulerInit schedulerInit = new SchedulerInit();

        /**Inicio scheduler para validar el egreso*/
        schedulerInit.setHoraInicio(18);
        schedulerInit.setMinutoInicio(16);
        schedulerInit.setValidadorDeTransparencia(validador);
        schedulerInit.setOrganizacion(unaEntidad);
        schedulerInit.arrancarTarea();

        /**Solo es necesario un revisor para ver los mensajes*/
        List<Mensaje> mensajes = new ArrayList<>();
        Estandar revisor;

        revisor = unaCompra.getRevisores().get(0);
        /**Por alguna razon, antes de que se ejecute el Scheduler, pasan muchas cosas, por lo tanto, espero a que se valide
         * la compra. Aca entra otro problema, si o si debo colocar este system.out dentro del while, sin el no muestra los msj*/

        mensajes.addAll(revisor.getBandejaDeMensajes().getMensajes());
        mensajes.forEach(msj->System.out.println(msj.getCuerpo()));
        mensajes.clear();
    }
}
