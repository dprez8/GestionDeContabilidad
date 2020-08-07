package MiTest;


import Domain.BandejaDeMensajes.BandejaDeMensajes;
import Domain.BandejaDeMensajes.Mensaje;
import Domain.CategorizadorDeEmpresas.Sector;
import Domain.DatosDeOperaciones.*;
import Domain.Organizacion.EntidadJuridica;
import Domain.Organizacion.Organizacion;
import Domain.ValidadorTransparencia.*;
import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;
import Domain.Organizacion.Empresa;
import Domain.Usuarios.Estandar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class TestValidadorEgreso {

    private Egreso unaCompra;
    private Presupuesto primerPresupuesto;
    private Presupuesto segundoPresupuesto;
    private Sector construccion;
    private Empresa miPyme;
    private EntidadJuridica unaEntidad;
    private Estandar unUsuario;
    private ValidarCantidadMinima validacionMinima;
    private ValidadorDeTransparencia validador;

    @Before
    public void antesDeValidar(){

        this.validacionMinima = new ValidarCantidadMinima(1);
        this.validador = new ValidadorDeTransparencia(validacionMinima);

        this.unaCompra = new Egreso(1);

        this.primerPresupuesto  = new Presupuesto(3, unaCompra);
        this.segundoPresupuesto = new Presupuesto(4, unaCompra);

        unaCompra.addPresupuestos(primerPresupuesto,segundoPresupuesto);

        this.construccion       = new Sector("Construccion");
        this.miPyme             = new Empresa(3,5000003.0,"Construccion",construccion);
        this.unaEntidad         = new EntidadJuridica("MiPyme",1234,"Nose",4321,1);

        this.unUsuario = new Estandar(unaEntidad, "Lautaro", "1234", "lautaro@robles.com");

        this.unaCompra.addRevisores(unUsuario);
    }

    @Test
    public void validarEgresos(){
        List<Mensaje> mensajes = new ArrayList<>();
        validador.validarEgreso(this.unaCompra);
        /*unaCompra.getRevisores()
                 .forEach(unRevisor->unRevisor
                         .getBandejaDeMensajes()
                         .getMensajes());*/

        mensajes.addAll(unUsuario.getBandejaDeMensajes().getMensajes());
        mensajes.forEach(unMsj->System.out.printf(unMsj.getCuerpo()));
        mensajes.forEach(unMsj->
                Assert.assertEquals(
                        "Se validó con la cantidad minima de presupuestos. Número de egreso: 1",unMsj.getCuerpo()));
    }
/*
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

        for(Mensaje mensaje : unUsuario.getBandejaDeMensajes().getMensajes()){
            System.out.printf("[%td/%tm/%ty] [leido: %s] %s\n", mensaje.getFecha(), mensaje.getFecha(), mensaje.getFecha(), mensaje.isLeido(), mensaje.getCuerpo());
            mensaje.setLeido(true);
        }
    }*/
}