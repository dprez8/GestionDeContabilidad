package Persistencia;

import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.CategoriaOperacion;
import Domain.Entities.Operaciones.CriterioOperacion;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Organizacion.Empresa;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.ValidadorTransparencia.*;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDominio {

    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Repositorio<Usuario> repoUsuarios;
    private Repositorio<Egreso> repoEgresos;
    private Repositorio<ItemEgreso> repoItems;
    private Repositorio<ItemPresupuesto> repoItemPresupuesto;
    private Repositorio<Item> repoItem;
    private Repositorio<TipoItem> repoTipoItem;
    private Repositorio<Pago> repoPagos;
    private Repositorio<MedioDePago> repoMedioDePagos;
    private Repositorio<Proveedor> repoProveedores;
    private Repositorio<DocumentoComercial> repoDocumentos;
    private Repositorio<TipoDocumento> repoTipoDocumento;
    private Repositorio<Presupuesto> repoPresupuestos;
    private Repositorio<Ingreso> repoIngresos;
    private Repositorio<CriterioOperacion> repoCriterios;
    private Repositorio<CategoriaOperacion> repoCategorias;
    private Repositorio<ValidadorDeTransparencia> repoValidadores;
    private Repositorio<ValidacionDeTransparencia> repoValidaciones;

    @Before
    public void antesDePersistir() {
        this.repoEntidadJuridica = new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
        this.repoUsuarios        = new Repositorio<>(new DaoHibernate<>(Usuario.class));
        this.repoEgresos         = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoItems           = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        this.repoItem       	 = new Repositorio<>(new DaoHibernate<>(Item.class));
        this.repoTipoItem        = new Repositorio<>(new DaoHibernate<>(TipoItem.class));
        this.repoPagos           = new Repositorio<>(new DaoHibernate<>(Pago.class));
        this.repoMedioDePagos    = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        this.repoItemPresupuesto = new Repositorio<>(new DaoHibernate<>(ItemPresupuesto.class));
        this.repoProveedores     = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        this.repoDocumentos      = new Repositorio<>(new DaoHibernate<>(DocumentoComercial.class));
        this.repoTipoDocumento   = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        this.repoPresupuestos    = new Repositorio<>(new DaoHibernate<>(Presupuesto.class));
        this.repoIngresos        = new Repositorio<>(new DaoHibernate<>(Ingreso.class));
        this.repoCriterios       = new Repositorio<>(new DaoHibernate<>(CriterioOperacion.class));
        this.repoCategorias       = new Repositorio<>(new DaoHibernate<>(CategoriaOperacion.class));
        this.repoValidadores      = new Repositorio<>(new DaoHibernate<>(ValidadorDeTransparencia.class));
        this.repoValidaciones     = new Repositorio<>(new DaoHibernate<>(ValidacionDeTransparencia.class));
    }

    @Test
    public void T0cargarTipoItem(){
    	TipoItem producto= new TipoItem("producto");
    	repoTipoItem.agregar(producto);
    }
    
    @Test
    public void T1persistirUnaEntidadJuridica (){

        Empresa pyme = new Empresa();
        pyme.setCantidadDePersonal(3);
        pyme.setVentasAnuales(5000000.3);
        pyme.setActividad("Construcciones");

        EntidadJuridica entidadJuridica = new EntidadJuridica();
        entidadJuridica.setCuit("1234");
        entidadJuridica.setAltura(1234);
        entidadJuridica.setRazonSocial("Entidad Juridica");
        pyme.setEntidadJuridica(entidadJuridica);
        entidadJuridica.setNombre("Organizacion");

        this.repoEntidadJuridica.agregar(entidadJuridica);
        SchedulerInit schedulerInit = new SchedulerInit();
        schedulerInit.setOrganizacion(entidadJuridica);
        entidadJuridica.setSchedulerInit(schedulerInit);
        this.repoEntidadJuridica.modificar(entidadJuridica);
        System.out.println("Numero "+ entidadJuridica.getId());
    }


    @Test
    public void T2obtenerAPepsi () {
        EntidadJuridica pymeJuridica = this.repoEntidadJuridica.buscar(1);

        Assert.assertEquals("Entidad Juridica",pymeJuridica.getRazonSocial());

        //Lo comento pq ahora es la empresa quien tiene como atributo a la EntidadJuridica 
        //para el orden a la hora de persistir
       //Empresa pyme = (Empresa) pymeJuridica.getTipoEntidadJuridica();
       // Assert.assertEquals("Construcciones",pyme.getActividad());
    }

    @Test
    public void T3persistirAUnUsuarioEstandar () throws contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, IOException {
        EntidadJuridica entidadJuridica = repoEntidadJuridica.buscar(1);
        Estandar usuario = new Estandar(entidadJuridica, "javier", "una_contrasenia_segura", "javier@gmail.com");
        repoUsuarios.agregar(usuario);
    }

    @Test
    public void T4recuperarAUnUsuario () {
        Usuario javier = repoUsuarios.buscar(1);
        Assert.assertEquals(Estandar.class,javier.getClass());
        System.out.println("tipo_usuario: " + javier.getClass());
    }

    @Test
    public void T5persistir2Items() {
        TipoItem producto=repoTipoItem.buscar(0);
    	Item RAM = new Item("Memoria RAM 4GB DDR3",producto);

        Item placaDeVideo = new Item("Placa de video 4GB DDR5",producto);

        this.repoItem.agregar(RAM);
        this.repoItem.agregar(placaDeVideo);
    }

    @Test
    public void T6persistirUnEgresoYDosPresupuestos () {
        Item producto1 = this.repoItem.buscar(1);
        ItemEgreso placasDeVideo = new ItemEgreso();
        placasDeVideo.setCantidad(2);
        placasDeVideo.setPrecio(5000);
        placasDeVideo.setItem(producto1);

        Item producto2 = this.repoItem.buscar(2);
        ItemEgreso RAMs = new ItemEgreso();
        RAMs.setCantidad(1);
        RAMs.setPrecio(3000);
        RAMs.setItem(producto2);

        this.repoItems.agregar(RAMs);
        this.repoItems.agregar(placasDeVideo);

        TipoDocumento tipoDocumento = this.repoTipoDocumento.buscar(1);
        DocumentoComercial unDocumento = new DocumentoComercial();
        unDocumento.setNumDocumento(111);
        unDocumento.setTipo(tipoDocumento);

        this.repoDocumentos.agregar(unDocumento);

        MedioDePago medioDePago = this.repoMedioDePagos.buscar(1);
        Pago unPago = new Pago();
        unPago.setMedioDePago(medioDePago);
        unPago.setCodigoAsociado("1231231");

        this.repoPagos.agregar(unPago);

        Proveedor lautaroRobles = new Proveedor("Lautaro Robles", 41424242);
        Proveedor lautaroIturregui = new Proveedor("Lautaro Iturregui", 2224222);

        this.repoProveedores.agregar(lautaroRobles);
        this.repoProveedores.agregar(lautaroIturregui);

        ItemPresupuesto RAMpresupuesto = new ItemPresupuesto(producto1, RAMs, 1, 3000);
        ItemPresupuesto placaVideoPresupuesto = new ItemPresupuesto(producto2, placasDeVideo, 2, 5000);

        ItemPresupuesto RAM2presupuesto = new ItemPresupuesto(producto1, RAMs, 1, 3500);
        ItemPresupuesto placaVide2Presupuesto = new ItemPresupuesto(producto2, placasDeVideo, 2, 6000);

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
                .agregarCantidadPresupuestos(1)
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
    public void T7obtenerEgresoYSusPresupuestos() {

        Egreso  unaCompra            = this.repoEgresos.buscar(1);
        Presupuesto primerPresupuesto = this.repoPresupuestos.buscar(1);
        Presupuesto segundoPresupuesto= this.repoPresupuestos.buscar(2);

        EntidadJuridica pepsiCompra = (EntidadJuridica) unaCompra.getOrganizacion();

        Assert.assertEquals(1,unaCompra.getId());
        Assert.assertEquals(1,primerPresupuesto.getEgresoAsociado().getId());
        Assert.assertEquals(1,segundoPresupuesto.getEgresoAsociado().getId());
        Assert.assertEquals("Factura A",unaCompra.getDocumento().getTipo().getNombreTipoDeDocumento());
        Assert.assertEquals("Entidad Juridica",pepsiCompra.getRazonSocial());
        Assert.assertEquals("Placa de video 4GB DDR5",unaCompra.getItems().get(1).getItem().getDescripcion());
        Assert.assertEquals("Memoria RAM 4GB DDR3",unaCompra.getItems().get(0).getItem().getDescripcion());
        Assert.assertEquals(2,unaCompra.getPresupuestos().get(0).getId());
        Assert.assertEquals(1,unaCompra.getPresupuestos().get(1).getId());
        Assert.assertEquals(1,unaCompra.getRevisores().get(0).getId());

    }

    @Test
    public void T8persistirValidadorAPepsi (){
        EntidadJuridica pepsi = this.repoEntidadJuridica.buscar(1);

        /**Creacion de los validadores*/
        ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima();
        ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
        ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

        ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMinima,validacionPresupuesto,validacionMenorValor);
        this.repoValidadores.agregar(validador);

        SchedulerInit schedulerInit = pepsi.getSchedulerInit();

        schedulerInit.setValidadorDeTransparencia(validador);
        pepsi.setSchedulerInit(schedulerInit);
        this.repoEntidadJuridica.modificar(pepsi);
        //schedulerInit.arrancarTarea();

        //List<Egreso> egresos = pepsi.getEgresos().stream().filter(a -> a.isValidado() == false).collect(Collectors.toList()); //Lo egresos que no han sido validados o no pasaron las pruebas anteriormente
        //egresos.forEach(egreso -> validador.validarEgreso(egreso));

        //Assert.assertEquals(true,pepsi.getEgresos().get(0).isValidado());
    }

    @Test
    public void T9persistirUnIngreso() {
        EntidadJuridica pepsi = this.repoEntidadJuridica.buscar(1);
        Ingreso ingreso = new Ingreso();
        ingreso.setDescripcion("Pago de afiliaciones");
        ingreso.setMontoTotal(20000.0);
        ingreso.setOrganizacion(pepsi);
        ingreso.setFechaOperacion(LocalDate.now());
        this.repoIngresos.agregar(ingreso);
    }

    @Test
    public void T91persistirCriteriosYCategorias() {
        CriterioOperacion criterio1 = new CriterioOperacion("Clientes");
        CategoriaOperacion mayorista = new CategoriaOperacion();
        mayorista.setDescripcion("Mayorista");
        mayorista.setCriterio(criterio1);

        CategoriaOperacion minorista = new CategoriaOperacion();
        minorista.setDescripcion("Minorista");
        minorista.setCriterio(criterio1);

        CriterioOperacion criterio2 = new CriterioOperacion("Mercado");
        CriterioOperacion criterioNacional = new CriterioOperacion("Nacional");
        CriterioOperacion criterioInternacional = new CriterioOperacion("Internacional");

        CategoriaOperacion interior = new CategoriaOperacion();
        interior.setDescripcion("Interior");
        CategoriaOperacion exterior = new CategoriaOperacion();
        exterior.setDescripcion("Exterior");
        CategoriaOperacion inter1 = new CategoriaOperacion();
        inter1.setDescripcion("America");
        CategoriaOperacion inter2 = new CategoriaOperacion();
        inter2.setDescripcion("Asia");
        CategoriaOperacion inter3 = new CategoriaOperacion();
        inter3.setDescripcion("Africa");
        CategoriaOperacion inter4 = new CategoriaOperacion();
        inter4.setDescripcion("Europa");
        CategoriaOperacion inter5 = new CategoriaOperacion();
        inter5.setDescripcion("Oceania");

        criterioNacional.setCriterioPadre(criterio2);
        criterioInternacional.setCriterioPadre(criterio2);

        interior.setCriterio(criterioNacional);
        exterior.setCriterio(criterioNacional);

        inter1.setCriterio(criterioInternacional);
        inter2.setCriterio(criterioInternacional);
        inter3.setCriterio(criterioInternacional);
        inter4.setCriterio(criterioInternacional);
        inter5.setCriterio(criterioInternacional);

        this.repoCriterios.agregar(criterio1);
        this.repoCriterios.agregar(criterio2);
        this.repoCriterios.agregar(criterioNacional);
        this.repoCriterios.agregar(criterioInternacional);

        this.repoCategorias.agregar(mayorista);
        this.repoCategorias.agregar(minorista);
        this.repoCategorias.agregar(interior);
        this.repoCategorias.agregar(exterior);
        this.repoCategorias.agregar(inter1);
        this.repoCategorias.agregar(inter2);
        this.repoCategorias.agregar(inter3);
        this.repoCategorias.agregar(inter4);
        this.repoCategorias.agregar(inter5);
    }

    @Test
    public void T99persistirAUnUsuarioAdministrador () throws contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, IOException {
        EntidadJuridica entidadJuridica = repoEntidadJuridica.buscar(1);
        Administrador usuario = new Administrador();
        usuario.setNombre("Fernando");
        usuario.setContrasenia("una_contrasenia_insegura");
        usuario.setMail("feer@gmail.com");
        repoUsuarios.agregar(usuario);
        repoEntidadJuridica.modificar(entidadJuridica);
    }
}
