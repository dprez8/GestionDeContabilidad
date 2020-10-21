package Persistencia;

import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Organizacion.Empresa;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PipedReader;
import java.time.LocalDate;
import java.time.Month;

public class TestDominio {

    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Egreso> repoEgresos;
    private Repositorio<ItemEgreso> repoItems;
    private Repositorio<Producto> repoProductos;

    @Before
    public void antesDePersistir() {
        this.repoEntidadJuridica = new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
        this.repoUsuarios        = new Repositorio<>(new DaoHibernate<>(Usuario.class));
        this.repoEgresos         = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoItems           = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        this.repoProductos       = new Repositorio<>(new DaoHibernate<>(Producto.class));
    }
    @Test
    public void persistirUnaEntidadJuridica(){

        Empresa pyme = new Empresa();
        pyme.setCantidadDePersonal(3);
        pyme.setVentasAnuales(5000000.3);
        pyme.setActividad("Construcciones");

        EntidadJuridica pepsi = new EntidadJuridica();
        pepsi.setCuit(1234);
        pepsi.setAltura(1234);
        pepsi.setRazonSocial("razonSocial");
        pepsi.setTipoEntidadJuridica(pyme);

        this.repoEntidadJuridica.agregar(pepsi);
        System.out.println("Numero"+ pepsi.getId());
    }

    @Test
    public void obtenerAPepsi() {
        EntidadJuridica pymeJuridica = this.repoEntidadJuridica.buscar(1);

        Assert.assertEquals("razonSocial",pymeJuridica.getRazonSocial());

        Empresa pyme = (Empresa) pymeJuridica.getTipoEntidadJuridica();

        Assert.assertEquals("Construcciones",pyme.getActividad());
    }

    @Test
    public void persistirAElPepe () throws contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, IOException {
        EntidadJuridica pepsi = repoEntidadJuridica.buscar(1);
        Estandar elPepe = new Estandar(pepsi, "elPepe", "456pepe", "elPepe@gmail.com");
        repoUsuarios.agregar(elPepe);
    }

    @Test
    public void recuperarAElPepe() {
        Usuario elPepe = repoUsuarios.buscar(1);
        Assert.assertEquals(Estandar.class,elPepe.getClass());
        System.out.println("tipo_usuario: " + elPepe.getClass());
    }

    @Test
    public void persistirUnEgreso () {
        /**Creacion de los datos de egreso y sus presupuestos, ejemplo*/
        Producto RAM = new Producto("Memoria RAM 4 gb DDR3");
        ItemEgreso RAMs = new ItemEgreso();
        RAMs.setCantidad(1);
        RAMs.setPrecio(3000);
        RAMs.setProducto(RAM);

        Producto placaDeVideo = new Producto("4GB DDR5");
        ItemEgreso placasDeVideo = new ItemEgreso(placaDeVideo, 2, 5000);
        placasDeVideo.setCantidad(2);
        placasDeVideo.setPrecio(5000);
        placasDeVideo.setProducto(placaDeVideo);

        this.repoProductos.agregar(RAM);
        this.repoProductos.agregar(placaDeVideo);

        this.repoItems.agregar(RAMs);
        this.repoItems.agregar(placasDeVideo);
/*
        TipoDocumento FacturaA = new TipoDocumento("Factura A");
        DocumentoComercial unDocumento = new DocumentoComercial();
        unDocumento.setNumDocumento(111);
        unDocumento.setTipo(FacturaA);

        MedioDePago efectivo = new MedioDePago("Ticket","rapipago");
        Pago unPago = new Pago();
        unPago.setMedioDePago(efectivo);
        unPago.setFechaPago(LocalDate.of(2020, Month.AUGUST,14));
        unPago.setNumeroAsociado(1231231);

        Proveedor lautaroRobles = new Proveedor("Lautaro Robles", 41424242);
        Proveedor lautaroIturregui = new Proveedor("Lautaro Iturregui", 2224222);

        ItemPresupuesto RAMpresupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3000);
        ItemPresupuesto placaVideoPresupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 5000);

        ItemPresupuesto RAM2presupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3500);
        ItemPresupuesto placaVide2Presupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 6000);

        /*******************************************************************/
        /*BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();

        EntidadJuridica pepsi = this.repoEntidadJuridica.buscar(1);

        Egreso unaCompra = egresoBuilder.agregarProveedor(lautaroIturregui)
                .agregarFechaOperacion(LocalDate.now())
                .agregarPago(unPago)
                .agregarDocumentoComercial(unDocumento)
                .agregarDatosOrganizacion(pepsi)
                .agregarItems(RAMs,placasDeVideo)
                .build();

        /**Creacion de dos presupuestos con un egreso*/
       /* Presupuesto primerPresupuesto = new Presupuesto(4000, unaCompra);
        primerPresupuesto.setDocumento(unDocumento);
        primerPresupuesto.setFechaVigente(LocalDate.of(2020, Month.AUGUST,14));
        primerPresupuesto.setProveedor(lautaroIturregui);
        primerPresupuesto.addItems(placaVideoPresupuesto,RAMpresupuesto);

        Presupuesto segundoPresupuesto = new Presupuesto(4210, unaCompra);
        segundoPresupuesto.setDocumento(unDocumento);
        segundoPresupuesto.setFechaVigente(LocalDate.of(2020, Month.AUGUST,14));
        segundoPresupuesto.setProveedor(lautaroRobles);
        segundoPresupuesto.addItems(placaVide2Presupuesto,RAM2presupuesto);

        /**Configuracion del egreso para pruebas*/
       /* Estandar elPepe = (Estandar) this.repoUsuarios.buscar(1);

        pepsi.addOperaciones(unaCompra);
        unaCompra.addRevisores(elPepe);
        unaCompra.addPresupuestos(primerPresupuesto,segundoPresupuesto);
        //this.repoEgresos.agregar(unaCompra);

        */
    }

}
