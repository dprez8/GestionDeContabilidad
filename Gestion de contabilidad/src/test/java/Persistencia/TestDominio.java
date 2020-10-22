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
import db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

public class TestDominio {

    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Egreso> repoEgresos;
    private Repositorio<ItemEgreso> repoItems;
    private Repositorio<ItemPresupuesto> repoItemPresupuesto;
    private Repositorio<Producto> repoProductos;
    private Repositorio<Pago> repoPagos;
    private Repositorio<MedioDePago> repoMedioDePagos;
    private Repositorio<Proveedor> repoProveedores;
    private Repositorio<DocumentoComercial> repoDocumentos;
    private Repositorio<TipoDocumento> repoTipoDocumento;
    private Repositorio<Presupuesto> repoPresupuestos;

    @Before
    public void antesDePersistir() {
        this.repoEntidadJuridica = new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
        this.repoUsuarios        = new Repositorio<>(new DaoHibernate<>(Usuario.class));
        this.repoEgresos         = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoItems           = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        this.repoProductos       = new Repositorio<>(new DaoHibernate<>(Producto.class));
        this.repoPagos           = new Repositorio<>(new DaoHibernate<>(Pago.class));
        this.repoMedioDePagos    = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        this.repoItemPresupuesto = new Repositorio<>(new DaoHibernate<>(ItemPresupuesto.class));
        this.repoProveedores     = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        this.repoDocumentos      = new Repositorio<>(new DaoHibernate<>(DocumentoComercial.class));
        this.repoTipoDocumento   = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        this.repoPresupuestos    = new Repositorio<>(new DaoHibernate<>(Presupuesto.class));
    }

    @Test
    public void persistirUnaEntidadJuridica (){

        Empresa pyme = new Empresa();
        pyme.setCantidadDePersonal(3);
        pyme.setVentasAnuales(5000000.3);
        pyme.setActividad("Construcciones");

        EntidadJuridica pepsi = new EntidadJuridica();
        pepsi.setCuit(1234);
        pepsi.setAltura(1234);
        pepsi.setRazonSocial("razonSocial");
        pepsi.setTipoEntidadJuridica(pyme);
        pepsi.setNombreFicticio("Pepsi");

        this.repoEntidadJuridica.agregar(pepsi);
        System.out.println("Numero"+ pepsi.getId());
    }

    @Test
    public void obtenerAPepsi () {
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
    public void recuperarAElPepe () {
        Usuario elPepe = repoUsuarios.buscar(1);
        Assert.assertEquals(Estandar.class,elPepe.getClass());
        System.out.println("tipo_usuario: " + elPepe.getClass());
    }

    @Test
    public void persistirUnEgresoYDosPresupuestos () {
        /**Creacion de los datos de egreso y sus presupuestos, ejemplo*/
        Producto RAM = new Producto("Memoria RAM 4 gb DDR3");
        ItemEgreso RAMs = new ItemEgreso();
        RAMs.setCantidad(1);
        RAMs.setPrecio(3000);
        RAMs.setProducto(RAM);

        Producto placaDeVideo = new Producto("4GB DDR5");
        ItemEgreso placasDeVideo = new ItemEgreso();
        placasDeVideo.setCantidad(2);
        placasDeVideo.setPrecio(5000);
        placasDeVideo.setProducto(placaDeVideo);

        this.repoProductos.agregar(RAM);
        this.repoProductos.agregar(placaDeVideo);

        this.repoItems.agregar(RAMs);
        this.repoItems.agregar(placasDeVideo);

        TipoDocumento FacturaA = new TipoDocumento("Factura A");
        DocumentoComercial unDocumento = new DocumentoComercial();
        unDocumento.setNumDocumento(111);
        unDocumento.setTipo(FacturaA);

        this.repoTipoDocumento.agregar(FacturaA);
        this.repoDocumentos.agregar(unDocumento);

        MedioDePago efectivo = new MedioDePago("Ticket","rapipago");
        Pago unPago = new Pago();
        unPago.setMedioDePago(efectivo);
        unPago.setFechaPago(LocalDate.of(2020, Month.AUGUST,14));
        unPago.setNumeroAsociado(1231231);

        this.repoMedioDePagos.agregar(efectivo);
        this.repoPagos.agregar(unPago);

        Proveedor lautaroRobles = new Proveedor("Lautaro Robles", 41424242);
        Proveedor lautaroIturregui = new Proveedor("Lautaro Iturregui", 2224222);

        this.repoProveedores.agregar(lautaroRobles);
        this.repoProveedores.agregar(lautaroIturregui);

        ItemPresupuesto RAMpresupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3000);
        ItemPresupuesto placaVideoPresupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 5000);

        ItemPresupuesto RAM2presupuesto = new ItemPresupuesto(RAM, RAMs, 1, 3500);
        ItemPresupuesto placaVide2Presupuesto = new ItemPresupuesto(placaDeVideo, placasDeVideo, 2, 6000);

        this.repoItemPresupuesto.agregar(RAMpresupuesto);
        this.repoItemPresupuesto.agregar(placaVideoPresupuesto);
        this.repoItemPresupuesto.agregar(RAM2presupuesto);
        this.repoItemPresupuesto.agregar(placaVide2Presupuesto);

        /*******************************************************************/
        BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();

        EntidadJuridica pepsi = this.repoEntidadJuridica.buscar(1);

        Egreso unaCompra = egresoBuilder.agregarProveedor(lautaroIturregui)
                .agregarFechaOperacion(LocalDate.now())
                .agregarPago(unPago)
                .agregarDocumentoComercial(unDocumento)
                .agregarDatosOrganizacion(pepsi)
                .agregarItems(RAMs,placasDeVideo)
                .build();

        this.repoEgresos.agregar(unaCompra);
        RAMs.setEgresoAsociado(unaCompra);
        placasDeVideo.setEgresoAsociado(unaCompra);

        this.repoItems.modificar(RAMs);
        this.repoItems.modificar(placasDeVideo);

        Estandar elPepe = (Estandar) repoUsuarios.buscar(1);
        unaCompra.addRevisores(elPepe);

        this.repoEgresos.modificar(unaCompra);

        /**Creacion de dos presupuestos con un egreso*/
        Presupuesto primerPresupuesto = new Presupuesto(4000, unaCompra);
        primerPresupuesto.setDocumento(unDocumento);
        primerPresupuesto.setFechaVigente(LocalDate.of(2020, Month.AUGUST,14));
        primerPresupuesto.setProveedor(lautaroIturregui);
        primerPresupuesto.addItems(placaVideoPresupuesto,RAMpresupuesto);

        Presupuesto segundoPresupuesto = new Presupuesto(4210, unaCompra);
        segundoPresupuesto.setDocumento(unDocumento);
        segundoPresupuesto.setFechaVigente(LocalDate.of(2020, Month.AUGUST,14));
        segundoPresupuesto.setProveedor(lautaroRobles);
        segundoPresupuesto.addItems(placaVide2Presupuesto,RAM2presupuesto);

        this.repoPresupuestos.agregar(primerPresupuesto);
        this.repoPresupuestos.agregar(segundoPresupuesto);
        RAMpresupuesto.setPresupuesto(primerPresupuesto);
        placaVideoPresupuesto.setPresupuesto(primerPresupuesto);
        placaVide2Presupuesto.setPresupuesto(segundoPresupuesto);
        RAM2presupuesto.setPresupuesto(segundoPresupuesto);
        this.repoItemPresupuesto.modificar(RAMpresupuesto);
        this.repoItemPresupuesto.modificar(placaVideoPresupuesto);
        this.repoItemPresupuesto.modificar(RAM2presupuesto);
        this.repoItemPresupuesto.modificar(placaVide2Presupuesto);

    }

    @Test
    public void obtenerEgresoYSusPresupuestos() {

        Egreso  unaCompra            = this.repoEgresos.buscar(0);
        Presupuesto primerPresupuesto = this.repoPresupuestos.buscar(1);
        Presupuesto segundoPresupuesto= this.repoPresupuestos.buscar(2);

        EntidadJuridica pepsiCompra = (EntidadJuridica) unaCompra.getOrganizacion();

        Assert.assertEquals(0,unaCompra.getId());
        Assert.assertEquals(0,primerPresupuesto.getEgresoAsociado().getId());
        Assert.assertEquals(0,segundoPresupuesto.getEgresoAsociado().getId());
        Assert.assertEquals("Ticket",unaCompra.getPago().getMedioDePago().getTipoPago());
        Assert.assertEquals("Factura A",unaCompra.getDocumento().getTipo().getNombreTipoDeDocumento());
        Assert.assertEquals("razonSocial",pepsiCompra.getRazonSocial());
        Assert.assertEquals("4GB DDR5",unaCompra.getItems().get(1).getProducto().getNombreProducto());
        Assert.assertEquals("Memoria RAM 4 gb DDR3",unaCompra.getItems().get(0).getProducto().getNombreProducto());
        Assert.assertEquals(2,unaCompra.getPresupuestos().get(0).getId());
        Assert.assertEquals(1,unaCompra.getPresupuestos().get(1).getId());
        Assert.assertEquals(1,unaCompra.getRevisores().get(0).getId());

    }

}
