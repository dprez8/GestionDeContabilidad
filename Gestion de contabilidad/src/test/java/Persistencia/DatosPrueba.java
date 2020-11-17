package Persistencia;

import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.ApiVinculador.ConfiguracionVinculador;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.CategoriaOperacion;
import Domain.Entities.Operaciones.CriterioOperacion;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Organizacion.Empresa;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.ValidadorTransparencia.*;
import Domain.Exceptions.ExcepcionCreacionEgreso;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import db.EntityManagerHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.print.Doc;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatosPrueba {

    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Repositorio<EntidadBase> repoEntidadBase;
    private Repositorio<Organizacion> repoOrganizacion;
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
    private Repositorio<SchedulerInit> repoScheduler;
    private Repositorio<Pais> repoPaises;
    private Repositorio<Provincia> repoProvincias;
    private Repositorio<Ciudad> repoCiudades;

    @Before
    public void antesDePersistir() {
        this.repoEntidadJuridica = new Repositorio<>(new DaoHibernate<>(EntidadJuridica.class));
        this.repoEntidadBase = new Repositorio<>(new DaoHibernate<>(EntidadBase.class));
        this.repoOrganizacion = new Repositorio<>(new DaoHibernate<>(Organizacion.class));
        this.repoUsuarios = new Repositorio<>(new DaoHibernate<>(Usuario.class));
        this.repoEgresos = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoItems = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        this.repoItem = new Repositorio<>(new DaoHibernate<>(Item.class));
        this.repoTipoItem = new Repositorio<>(new DaoHibernate<>(TipoItem.class));
        this.repoPagos = new Repositorio<>(new DaoHibernate<>(Pago.class));
        this.repoMedioDePagos = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        this.repoItemPresupuesto = new Repositorio<>(new DaoHibernate<>(ItemPresupuesto.class));
        this.repoProveedores = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        this.repoDocumentos = new Repositorio<>(new DaoHibernate<>(DocumentoComercial.class));
        this.repoTipoDocumento = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        this.repoPresupuestos = new Repositorio<>(new DaoHibernate<>(Presupuesto.class));
        this.repoIngresos = new Repositorio<>(new DaoHibernate<>(Ingreso.class));
        this.repoCriterios = new Repositorio<>(new DaoHibernate<>(CriterioOperacion.class));
        this.repoCategorias = new Repositorio<>(new DaoHibernate<>(CategoriaOperacion.class));
        this.repoValidadores = new Repositorio<>(new DaoHibernate<>(ValidadorDeTransparencia.class));
        this.repoValidaciones = new Repositorio<>(new DaoHibernate<>(ValidacionDeTransparencia.class));
        this.repoScheduler = new Repositorio<>(new DaoHibernate<>(SchedulerInit.class));
        this.repoPaises = new Repositorio<>(new DaoHibernate<>(Pais.class));
        this.repoProvincias = new Repositorio<>(new DaoHibernate<>(Provincia.class));
        this.repoCiudades = new Repositorio<>(new DaoHibernate<>(Ciudad.class));
    }

    @Test
    public void T1DatosDePrueba () throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
        /**Creacion de los validadores*/
        ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima();
        ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
        ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

        ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMinima,validacionPresupuesto,validacionMenorValor);
        this.repoValidadores.agregar(validador);
        Pais pais;
        Provincia provincia;
        Ciudad ciudad;
        /*******************************************************/
        Empresa empresa1 = new Empresa();
        empresa1.setCantidadDePersonal(150);
        empresa1.setActividad("Construcción");
        empresa1.setVentasAnuales(600000000);

        EntidadJuridica eeafBa = new EntidadJuridica();
        eeafBa.setNombre("Equipo Argentino de Antropología Forense - EAAF");
        eeafBa.setNombreFicticio("Oficina Central Buenos Aires");
        eeafBa.setRazonSocial("EAAF BA");
        eeafBa.setCuit("30-15269857-2");
        eeafBa.setAltura(951);
        eeafBa.setCalle("Av.Medrano");
        eeafBa.setTipoEntidadJuridica(empresa1);
        eeafBa.setConfiguracionVinculador(new ConfiguracionVinculador());
        pais = (Pais) EntityManagerHelper.getEntityManager().createQuery("from Pais where pais_code = 'AR'").getSingleResult();
        provincia = (Provincia) EntityManagerHelper.getEntityManager().createQuery("from Provincia where name = 'Capital Federal'").getSingleResult();
        ciudad = (Ciudad) EntityManagerHelper.getEntityManager().createQuery("from Ciudad where name = 'Capital Federal'").getSingleResult();
        eeafBa.setPais(pais);
        eeafBa.setProvincia(provincia);
        eeafBa.setCiudad(ciudad);
        this.repoOrganizacion.agregar(eeafBa);

        SchedulerInit schedulerJuridica1 = new SchedulerInit();
        schedulerJuridica1.setOrganizacion(eeafBa);
        schedulerJuridica1.setValidadorDeTransparencia(validador);
        eeafBa.setSchedulerInit(schedulerJuridica1);
        this.repoOrganizacion.modificar(eeafBa);
        /***************************************************************/
        Empresa empresa2 = new Empresa();
        empresa1.setCantidadDePersonal(580);
        empresa1.setActividad("Construcción");
        empresa1.setVentasAnuales(960000000);

        EntidadJuridica eeafNy = new EntidadJuridica();
        eeafNy.setNombre("Equipo Argentino de Antropología Forense - EAAF");
        eeafNy.setNombreFicticio("Oficina Central Nueva York");
        eeafNy.setRazonSocial("EAAF NY");
        eeafNy.setCuit("30-15789655-7");
        eeafNy.setAltura(720);
        eeafNy.setCalle("Liberty Ave");
        eeafNy.setTipoEntidadJuridica(empresa2);
        eeafNy.setConfiguracionVinculador(new ConfiguracionVinculador());
        provincia = (Provincia) EntityManagerHelper.getEntityManager().createQuery("from Provincia where provincia_id = '32'").getSingleResult();
        eeafNy.setProvincia(provincia);
        this.repoOrganizacion.agregar(eeafNy);

        SchedulerInit schedulerJuridica2 = new SchedulerInit();
        schedulerJuridica2.setOrganizacion(eeafNy);
        schedulerJuridica2.setValidadorDeTransparencia(validador);
        eeafNy.setSchedulerInit(schedulerJuridica2);
        this.repoOrganizacion.modificar(eeafNy);
        /***************************************************************/
        Empresa empresa3 = new Empresa();
        empresa3.setCantidadDePersonal(240);
        empresa3.setActividad("Construcción");
        empresa3.setVentasAnuales(643710000);

        EntidadJuridica eeafM = new EntidadJuridica();
        eeafM.setNombre("Equipo Argentino de Antropología Forense - EAAF");
        eeafM.setNombreFicticio("Oficina Central México");
        eeafM.setRazonSocial("EAAF M");
        eeafM.setCuit("30-77896583-9");
        eeafM.setAltura(55);
        eeafM.setCalle("Roberto Gayol");
        eeafM.setTipoEntidadJuridica(empresa3);
        eeafM.setConfiguracionVinculador(new ConfiguracionVinculador());
        pais = (Pais) EntityManagerHelper.getEntityManager().createQuery("from Pais where pais_code = 'MX'").getSingleResult();
        provincia = (Provincia) EntityManagerHelper.getEntityManager().createQuery("from Provincia where name = 'Estado De México'").getSingleResult();
        eeafM.setPais(pais);
        eeafM.setProvincia(provincia);
        this.repoOrganizacion.agregar(eeafM);

        SchedulerInit schedulerJuridica3 = new SchedulerInit();
        schedulerJuridica3.setOrganizacion(eeafM);
        schedulerJuridica3.setValidadorDeTransparencia(validador);
        eeafM.setSchedulerInit(schedulerJuridica3);
        this.repoOrganizacion.modificar(eeafM);
        /***********Empresa *********************/
        Empresa pyme = new Empresa();
        pyme.setCantidadDePersonal(8);
        pyme.setVentasAnuales(8000000);
        pyme.setActividad("Servicio de Alojamiento");

        /*********** entidad juridica****************/
        EntidadJuridica surcosCS = new EntidadJuridica();
        surcosCS.setNombreFicticio("Surcos");
        surcosCS.setRazonSocial("Surcos CS");
        surcosCS.setCuit("30-25888897-8");
        surcosCS.setCalle("Jerónimo Salguero");
        surcosCS.setAltura(2800);
        surcosCS.setTipoEntidadJuridica(pyme);
        surcosCS.setNombre("Colectivo de Derechos de Infancia y Adolescencia - CDIA");
        pais = (Pais) EntityManagerHelper.getEntityManager().createQuery("from Pais where pais_code = 'AR'").getSingleResult();
        provincia = (Provincia) EntityManagerHelper.getEntityManager().createQuery("from Provincia where name = 'Capital Federal'").getSingleResult();
        ciudad = (Ciudad) EntityManagerHelper.getEntityManager().createQuery("from Ciudad where name = 'Capital Federal'").getSingleResult();
        surcosCS.setPais(pais);
        surcosCS.setProvincia(provincia);
        surcosCS.setCiudad(ciudad);
        this.repoOrganizacion.agregar(surcosCS);

        SchedulerInit schedulerJuridica4 = new SchedulerInit();
        schedulerJuridica4.setOrganizacion(surcosCS);
        schedulerJuridica4.setValidadorDeTransparencia(validador);
        surcosCS.setSchedulerInit(schedulerJuridica4);
        surcosCS.setConfiguracionVinculador(new ConfiguracionVinculador());
        this.repoOrganizacion.modificar(surcosCS);

        /************Creacion de una entidad base para una juridica ********************/
        EntidadBase entidadBase = new EntidadBase();
        entidadBase.setNombreFicticio("Andhes");
        entidadBase.setNombre("Colectivo de Derechos de Infancia y Adolescencia - CDIA");
        SchedulerInit schedulerBase1 = new SchedulerInit();
        entidadBase.setEntidadJuridica(surcosCS);
        entidadBase.setSchedulerInit(schedulerBase1);
        schedulerBase1.setOrganizacion(entidadBase);
        schedulerBase1.setValidadorDeTransparencia(validador);
        entidadBase.setConfiguracionVinculador(new ConfiguracionVinculador());

        surcosCS.addEntidadesBase(entidadBase);
        this.repoOrganizacion.modificar(surcosCS);
        /********************************************************************************************/
        Estandar aroco = new Estandar();
        aroco.setNombre("Alejandro");
        aroco.setApellido("Roco");
        aroco.setUsername("aroco");
        aroco.setContrasenia("*_aroco20!-?");
        aroco.setMiOrganizacion(eeafBa);
        
        this.repoUsuarios.agregar(aroco);

        Estandar rrojas = new Estandar();
        rrojas.setNombre("Rocío");
        rrojas.setApellido("Rojas");
        rrojas.setUsername("rrojas");
        rrojas.setContrasenia("*-_rrojas!?");
        rrojas.setMiOrganizacion(eeafBa);

        this.repoUsuarios.agregar(rrojas);


        Estandar jazul = new Estandar();
        jazul.setNombre("Julieta");
        jazul.setApellido("Azul");
        jazul.setUsername("jazul");
        jazul.setContrasenia("!-*jazul_!?");
        jazul.setMiOrganizacion(surcosCS);

        this.repoUsuarios.agregar(jazul);

        /************************************************************************/
    }

    @Test
    public void T2CargaEgresosEEAFBA() throws ExcepcionCreacionEgreso {
        EntidadJuridica eaafBa = this.repoEntidadJuridica.buscar(1);

        Pais pais = (Pais) EntityManagerHelper.getEntityManager().createQuery("from Pais where pais_code = 'AR'").getSingleResult();
        Provincia provincia = (Provincia) EntityManagerHelper.getEntityManager().createQuery("from Provincia where name = 'Capital Federal'").getSingleResult();
        Ciudad ciudad = (Ciudad) EntityManagerHelper.getEntityManager().createQuery("from Ciudad where name = 'Capital Federal'").getSingleResult();

        Proveedor pintureriasSorrentino = new Proveedor("Pinturerías Serrentino",1234567,eaafBa);
        pintureriasSorrentino.setAltura(10);
        pintureriasSorrentino.setCiudad(ciudad);
        pintureriasSorrentino.setProvincia(provincia);
        pintureriasSorrentino.setPais(pais);
        pintureriasSorrentino.setPiso("0");
        pintureriasSorrentino.setCalle("Belgrano");
        pintureriasSorrentino.setZipcode(1234);

        this.repoProveedores.agregar(pintureriasSorrentino);
        MedioDePago credito = this.repoMedioDePagos.buscar(3);

        Pago pago = new Pago();
        pago.setCodigoAsociado("4509 9535 6623 3704");
        pago.setMedioDePago(credito);

        this.repoPagos.agregar(pago);

        TipoItem producto = (TipoItem) EntityManagerHelper.getEntityManager().createQuery("from TipoItem where nombre = 'Producto'").getSingleResult();
        /********************************************/
        Item pinturaz10 = new Item();
        pinturaz10.setDescripcion("PINTURA Z10 LATEX SUPERCUBRITIVO 20L");
        pinturaz10.setOrganizacion(eaafBa);
        pinturaz10.setTipoItem(producto);

        this.repoItem.agregar(pinturaz10);

        ItemEgreso pinturaZ10 = new ItemEgreso();
        pinturaZ10.setCantidad(1);
        pinturaZ10.setPrecio(9625);
        pinturaZ10.setItem(pinturaz10);

        this.repoItems.agregar(pinturaZ10);
        /********************************************/
        Item pinturaLoxon = new Item();
        pinturaLoxon.setDescripcion("PINTURA LOXON FTES IMPERMEABILIZANTE 10L");
        pinturaLoxon.setOrganizacion(eaafBa);
        pinturaLoxon.setTipoItem(producto);

        this.repoItem.agregar(pinturaLoxon);

        ItemEgreso pinturaLoxonI = new ItemEgreso();
        pinturaLoxonI.setCantidad(1);
        pinturaLoxonI.setPrecio(6589.4);
        pinturaLoxonI.setItem(pinturaLoxon);
        this.repoItems.agregar(pinturaLoxonI);
        /********************************************/
        Item pinturaBrik = new Item();
        pinturaBrik.setDescripcion("PINTURA BRIKOL PISOS NEGRO 4L");
        pinturaBrik.setOrganizacion(eaafBa);
        pinturaBrik.setTipoItem(producto);

        this.repoItem.agregar(pinturaBrik);

        ItemEgreso pinturaBrikol = new ItemEgreso();
        pinturaBrikol.setCantidad(1);
        pinturaBrikol.setPrecio(3738.29);
        pinturaBrikol.setItem(pinturaBrik);
        this.repoItems.agregar(pinturaBrikol);
        /********************************************/

        TipoDocumento facturaA = this.repoTipoDocumento.buscar(1);

        DocumentoComercial documentoComercial = new DocumentoComercial();
        documentoComercial.setDescripcion("Compra pintura - 3 pagos s/i");
        documentoComercial.setFechaDeEntrega(LocalDate.now());
        documentoComercial.setFechaDePedido(LocalDate.now());
        documentoComercial.setTipo(facturaA);
        documentoComercial.setNumDocumento(1234);
        /********************************************/
        Egreso egreso = new BuilderEgresoConcreto()
                            .agregarDatosOrganizacion(eaafBa)
                            .agregarItems(pinturaBrikol,pinturaLoxonI,pinturaZ10)
                            .agregarPago(pago)
                            .agregarFechaOperacion(LocalDate.of(2020,3,10))
                            .agregarCantidadPresupuestos(3)
                            .agregarProveedor(pintureriasSorrentino)
                            .agregarDocumentoComercial(documentoComercial)
                            .build();
        this.repoEgresos.agregar(egreso);
        pinturaBrikol.setEgresoAsociado(egreso);
        pinturaLoxonI.setEgresoAsociado(egreso);
        pinturaZ10.setEgresoAsociado(egreso);
        this.repoItems.modificar(pinturaBrikol);
        this.repoItems.modificar(pinturaLoxonI);
        this.repoItems.modificar(pinturaZ10);

        /*******************************************************/
        Proveedor edesur = new Proveedor("Edesur",12345678,eaafBa);
        edesur.setAltura(15);
        edesur.setCiudad(ciudad);
        edesur.setProvincia(provincia);
        edesur.setPais(pais);
        edesur.setCalle("Colombres");
        edesur.setPiso("0");
        edesur.setZipcode(1234);

        this.repoProveedores.agregar(edesur);
        MedioDePago efectivo = this.repoMedioDePagos.buscar(1);

        Pago pago2 = new Pago();
        pago2.setCodigoAsociado("4509 SFAF");
        pago2.setMedioDePago(efectivo);

        this.repoPagos.agregar(pago2);

        TipoItem servicio = (TipoItem) EntityManagerHelper.getEntityManager().createQuery("from TipoItem where nombre = 'Servicio'").getSingleResult();
        /********************************************/
        Item edesurItem = new Item();
        edesurItem.setDescripcion("FACTURA SERVICIO DE LUZ PERIODO JUNIO 2020");
        edesurItem.setOrganizacion(eaafBa);
        edesurItem.setTipoItem(servicio);

        this.repoItem.agregar(edesurItem);

        ItemEgreso edesurIE = new ItemEgreso();
        edesurIE.setCantidad(1);
        edesurIE.setPrecio(2100);
        edesurIE.setItem(edesurItem);

        this.repoItems.agregar(edesurIE);
       /*************************************************/

        DocumentoComercial documentoComercial2 = new DocumentoComercial();
        documentoComercial2.setDescripcion("Pago edesur");
        documentoComercial2.setFechaDeEntrega(LocalDate.now());
        documentoComercial2.setFechaDePedido(LocalDate.now());
        documentoComercial2.setTipo(facturaA);
        documentoComercial2.setNumDocumento(1234);
        /********************************************/
        Egreso egreso2 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(eaafBa)
                .agregarItems(edesurIE)
                .agregarPago(pago2)
                .agregarFechaOperacion(LocalDate.of(2020,7,8))
                .agregarCantidadPresupuestos(0)
                .agregarProveedor(edesur)
                .agregarDocumentoComercial(documentoComercial2)
                .build();
        this.repoEgresos.agregar(egreso2);
        edesurIE.setEgresoAsociado(egreso2);
        this.repoItems.modificar(edesurIE);
        /***************************************************************************/
        Proveedor metrogas = new Proveedor("Metrogas",123456789,eaafBa);
        metrogas.setAltura(15);
        metrogas.setCiudad(ciudad);
        metrogas.setProvincia(provincia);
        metrogas.setPais(pais);
        metrogas.setCalle("Meeks");
        metrogas.setPiso("0");
        metrogas.setZipcode(1234);

        this.repoProveedores.agregar(metrogas);

        Pago pago3 = new Pago();
        pago3.setCodigoAsociado("4509 SFAFD");
        pago3.setMedioDePago(efectivo);

        this.repoPagos.agregar(pago3);
        /********************************************/
        Item metrogasItem = new Item();
        metrogasItem.setDescripcion("FACTURA SERVICIO DE GAS PERIODO JUNIO 2020");
        metrogasItem.setOrganizacion(eaafBa);
        metrogasItem.setTipoItem(servicio);

        this.repoItem.agregar(metrogasItem);

        ItemEgreso metrogasIE = new ItemEgreso();
        metrogasIE.setCantidad(1);
        metrogasIE.setPrecio(3500);
        metrogasIE.setItem(metrogasItem);

        this.repoItems.agregar(metrogasIE);
        /*************************************************/

        DocumentoComercial documentoComercial3 = new DocumentoComercial();
        documentoComercial3.setDescripcion("Pago metrogas");
        documentoComercial3.setFechaDeEntrega(LocalDate.now());
        documentoComercial3.setFechaDePedido(LocalDate.now());
        documentoComercial3.setTipo(facturaA);
        documentoComercial3.setNumDocumento(12345);
        /********************************************/
        Egreso egreso3 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(eaafBa)
                .agregarItems(metrogasIE)
                .agregarPago(pago3)
                .agregarFechaOperacion(LocalDate.of(2020,7,9))
                .agregarCantidadPresupuestos(0)
                .agregarProveedor(metrogas)
                .agregarDocumentoComercial(documentoComercial3)
                .build();
        this.repoEgresos.agregar(egreso3);
        metrogasIE.setEgresoAsociado(egreso3);
        this.repoItems.modificar(metrogasIE);

        /*************************************************************************************/

        Proveedor mitoasSA = new Proveedor("Mitoas SA",123456789,eaafBa);
        mitoasSA.setAltura(16);
        mitoasSA.setCiudad(ciudad);
        mitoasSA.setProvincia(provincia);
        mitoasSA.setPais(pais);
        mitoasSA.setCalle("Sarasa");
        mitoasSA.setPiso("0");
        mitoasSA.setZipcode(1234);

        this.repoProveedores.agregar(mitoasSA);

        MedioDePago debito = this.repoMedioDePagos.buscar(4);

        Pago pago4 = new Pago();
        pago4.setCodigoAsociado("5031 7557 3453 0604");
        pago4.setMedioDePago(debito);

        this.repoPagos.agregar(pago4);
        /********************************************/
        Item pavaItem = new Item();
        pavaItem.setDescripcion("PAVA ELECTRICA SMARTLIFE 1,5 LTS 1850W");
        pavaItem.setOrganizacion(eaafBa);
        pavaItem.setTipoItem(producto);

        this.repoItem.agregar(pavaItem);

        ItemEgreso pavaIE = new ItemEgreso();
        pavaIE.setCantidad(3);
        pavaIE.setPrecio(4500);
        pavaIE.setItem(pavaItem);

        this.repoItems.agregar(pavaIE);
        /*************************************************/
        Item cafeteraItem = new Item();
        cafeteraItem.setDescripcion("CAFETERA SMARTLIFE 1095 ACERO INOX.");
        cafeteraItem.setOrganizacion(eaafBa);
        cafeteraItem.setTipoItem(producto);

        this.repoItem.agregar(cafeteraItem);

        ItemEgreso cafeteraIE = new ItemEgreso();
        cafeteraIE.setCantidad(2);
        cafeteraIE.setPrecio(6300);
        cafeteraIE.setItem(cafeteraItem);

        this.repoItems.agregar(cafeteraIE);
        /*************************************************/

        DocumentoComercial documentoComercial4 = new DocumentoComercial();
        documentoComercial4.setDescripcion("Pago debito");
        documentoComercial4.setFechaDeEntrega(LocalDate.now());
        documentoComercial4.setFechaDePedido(LocalDate.now());
        documentoComercial4.setTipo(facturaA);
        documentoComercial4.setNumDocumento(122345);
        /********************************************/
        Egreso egreso4 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(eaafBa)
                .agregarItems(cafeteraIE,pavaIE)
                .agregarPago(pago4)
                .agregarFechaOperacion(LocalDate.of(2020,8,3))
                .agregarCantidadPresupuestos(0)
                .agregarProveedor(mitoasSA)
                .agregarDocumentoComercial(documentoComercial4)
                .build();
        this.repoEgresos.agregar(egreso4);
        cafeteraIE.setEgresoAsociado(egreso4);
        pavaIE.setEgresoAsociado(egreso4);
        this.repoItems.modificar(cafeteraIE);
        this.repoItems.modificar(pavaIE);
        /*************************************************************************************/

        Proveedor ingenieriaComercial = new Proveedor("Ingeniería Comercial SRL",1232456789,eaafBa);
        ingenieriaComercial.setAltura(126);
        ingenieriaComercial.setCiudad(ciudad);
        ingenieriaComercial.setProvincia(provincia);
        ingenieriaComercial.setPais(pais);
        ingenieriaComercial.setCalle("Medrano");
        ingenieriaComercial.setPiso("0");
        ingenieriaComercial.setZipcode(12234);

        this.repoProveedores.agregar(ingenieriaComercial);

        Pago pago5 = new Pago();
        pago5.setCodigoAsociado("0604S");
        pago5.setMedioDePago(efectivo);

        this.repoPagos.agregar(pago5);
        /********************************************/
        Item telefonoItem = new Item();
        telefonoItem.setDescripcion("TELEFONOS INALAMBRICOS MOTOROLA DUO BLANCO");
        telefonoItem.setOrganizacion(eaafBa);
        telefonoItem.setTipoItem(producto);

        this.repoItem.agregar(telefonoItem);

        ItemEgreso telefonoIE = new ItemEgreso();
        telefonoIE.setCantidad(2);
        telefonoIE.setPrecio(8500);
        telefonoIE.setItem(telefonoItem);

        this.repoItems.agregar(telefonoIE);
        /*************************************************/

        DocumentoComercial documentoComercial5 = new DocumentoComercial();
        documentoComercial5.setDescripcion("Pago efectivo");
        documentoComercial5.setFechaDeEntrega(LocalDate.now());
        documentoComercial5.setFechaDePedido(LocalDate.now());
        documentoComercial5.setTipo(facturaA);
        documentoComercial5.setNumDocumento(1223245);
        /********************************************/
        Egreso egreso5 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(eaafBa)
                .agregarItems(telefonoIE)
                .agregarPago(pago5)
                .agregarFechaOperacion(LocalDate.of(2020,9,27))
                .agregarProveedor(ingenieriaComercial)
                .agregarCantidadPresupuestos(6)
                .agregarDocumentoComercial(documentoComercial5)
                .build();

        this.repoEgresos.agregar(egreso5);
        telefonoIE.setEgresoAsociado(egreso5);
        this.repoItems.modificar(telefonoIE);
        /*************************************************************************************/

        Proveedor corralonLaprida = new Proveedor("Corralón Laprida SRL",1256789,eaafBa);
        corralonLaprida.setAltura(1265);
        corralonLaprida.setCiudad(ciudad);
        corralonLaprida.setProvincia(provincia);
        corralonLaprida.setPais(pais);
        corralonLaprida.setCalle("Campus");
        corralonLaprida.setPiso("0");
        corralonLaprida.setZipcode(5555);

        this.repoProveedores.agregar(corralonLaprida);

        Pago pago6 = new Pago();
        pago6.setCodigoAsociado("0604SA");
        pago6.setMedioDePago(efectivo);

        this.repoPagos.agregar(pago6);
        /********************************************/
        Item cementoItem = new Item();
        cementoItem.setDescripcion("CEMENTO LOMA NEGRA");
        cementoItem.setOrganizacion(eaafBa);
        cementoItem.setTipoItem(producto);

        this.repoItem.agregar(cementoItem);

        ItemEgreso cementoIE = new ItemEgreso();
        cementoIE.setCantidad(10);
        cementoIE.setPrecio(704.4);
        cementoIE.setItem(cementoItem);

        this.repoItems.agregar(cementoIE);
        /*************************************************/
        Item arenaItem = new Item();
        arenaItem.setDescripcion("ARENA FINA EN BOLSÓN X M30");
        arenaItem.setOrganizacion(eaafBa);
        arenaItem.setTipoItem(producto);

        this.repoItem.agregar(arenaItem);

        ItemEgreso arenaIE = new ItemEgreso();
        arenaIE.setCantidad(5);
        arenaIE.setPrecio(3100);
        arenaIE.setItem(arenaItem);

        this.repoItems.agregar(arenaIE);
        /*************************************************/
        Item hierroItem = new Item();
        hierroItem.setDescripcion("HIERRO ACINDAR");
        hierroItem.setOrganizacion(eaafBa);
        hierroItem.setTipoItem(producto);

        this.repoItem.agregar(hierroItem);

        ItemEgreso hierroIE = new ItemEgreso();
        hierroIE.setCantidad(4);
        hierroIE.setPrecio(891);
        hierroIE.setItem(hierroItem);

        this.repoItems.agregar(hierroIE);
        /*************************************************/
        Item ladrilloItem = new Item();
        ladrilloItem.setDescripcion("BLOQUE LADRILLO CEMENTO");
        ladrilloItem.setOrganizacion(eaafBa);
        ladrilloItem.setTipoItem(producto);

        this.repoItem.agregar(ladrilloItem);

        ItemEgreso ladrilloIE = new ItemEgreso();
        ladrilloIE.setCantidad(800);
        ladrilloIE.setPrecio(227);
        ladrilloIE.setItem(ladrilloItem);

        this.repoItems.agregar(ladrilloIE);
        /*************************************************/

        DocumentoComercial documentoComercial6 = new DocumentoComercial();
        documentoComercial6.setDescripcion("Pago efectivo");
        documentoComercial6.setFechaDeEntrega(LocalDate.now());
        documentoComercial6.setFechaDePedido(LocalDate.now());
        documentoComercial6.setTipo(facturaA);
        documentoComercial6.setNumDocumento(122322245);
        /********************************************/
        Egreso egreso6 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(eaafBa)
                .agregarItems(ladrilloIE,arenaIE,hierroIE,cementoIE)
                .agregarPago(pago6)
                .agregarFechaOperacion(LocalDate.of(2020,10,1))
                .agregarCantidadPresupuestos(4)
                .agregarProveedor(corralonLaprida)
                .agregarDocumentoComercial(documentoComercial6)
                .build();

        this.repoEgresos.agregar(egreso6);
        hierroIE.setEgresoAsociado(egreso6);
        cementoIE.setEgresoAsociado(egreso6);
        arenaIE.setEgresoAsociado(egreso6);
        ladrilloIE.setEgresoAsociado(egreso6);
        this.repoItems.modificar(ladrilloIE);
        this.repoItems.modificar(hierroIE);
        this.repoItems.modificar(arenaIE);
        this.repoItems.modificar(ladrilloIE);
        /*************************************************************************************/

        Pago pago7 = new Pago();
        pago7.setCodigoAsociado("0605S");
        pago7.setMedioDePago(efectivo);

        this.repoPagos.agregar(pago7);
        /********************************************/

        ItemEgreso bloqueIE = new ItemEgreso();
        bloqueIE.setCantidad(800);
        bloqueIE.setPrecio(250);
        bloqueIE.setItem(cementoItem);

        this.repoItems.agregar(bloqueIE);
        /*************************************************/

        DocumentoComercial documentoComercial7 = new DocumentoComercial();
        documentoComercial7.setDescripcion("Pago efectivo");
        documentoComercial7.setFechaDeEntrega(LocalDate.now());
        documentoComercial7.setFechaDePedido(LocalDate.now());
        documentoComercial7.setTipo(facturaA);
        documentoComercial7.setNumDocumento(1223246);
        /********************************************/
        Egreso egreso7 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(eaafBa)
                .agregarItems(bloqueIE)
                .agregarPago(pago7)
                .agregarFechaOperacion(LocalDate.of(2020,10,5))
                .agregarProveedor(corralonLaprida)
                .agregarCantidadPresupuestos(0)
                .agregarDocumentoComercial(documentoComercial7)
                .build();

        this.repoEgresos.agregar(egreso7);
        bloqueIE.setEgresoAsociado(egreso7);
        this.repoItems.modificar(bloqueIE);
        /********************** CDIA - Surcos *****************************************/

        EntidadJuridica surcos = this.repoEntidadJuridica.buscar(5);

        Proveedor edesurSurcos = new Proveedor("Edesur",12345678,surcos);
        edesurSurcos.setAltura(15);
        edesurSurcos.setCiudad(ciudad);
        edesurSurcos.setProvincia(provincia);
        edesurSurcos.setPais(pais);
        edesurSurcos.setCalle("Colombres");
        edesurSurcos.setPiso("0");
        edesurSurcos.setZipcode(1234);

        this.repoProveedores.agregar(edesurSurcos);

        Pago pago8 = new Pago();
        pago8.setCodigoAsociado("0505S");
        pago8.setMedioDePago(efectivo);

        this.repoPagos.agregar(pago8);
        /********************************************/
        Item edesurCItem = new Item();
        edesurCItem.setDescripcion("FACTURA SERVICIO DE LUZ PERIODO JUNIO 2020");
        edesurCItem.setOrganizacion(surcos);
        edesurCItem.setTipoItem(servicio);
        this.repoItem.agregar(edesurCItem);

        ItemEgreso edesurCIE = new ItemEgreso();
        edesurCIE.setCantidad(1);
        edesurCIE.setPrecio(800);
        edesurCIE.setItem(edesurCItem);

        this.repoItems.agregar(edesurCIE);
        /*************************************************/

        DocumentoComercial documentoComercial8 = new DocumentoComercial();
        documentoComercial8.setDescripcion("Pago efectivo");
        documentoComercial8.setFechaDeEntrega(LocalDate.now());
        documentoComercial8.setFechaDePedido(LocalDate.now());
        documentoComercial8.setTipo(facturaA);
        documentoComercial8.setNumDocumento(1223246);
        /********************************************/
        Egreso egreso8 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(surcos)
                .agregarItems(edesurCIE)
                .agregarPago(pago8)
                .agregarFechaOperacion(LocalDate.of(2020,7,10))
                .agregarProveedor(edesurSurcos)
                .agregarCantidadPresupuestos(0)
                .agregarDocumentoComercial(documentoComercial8)
                .build();

        this.repoEgresos.agregar(egreso8);
        edesurCIE.setEgresoAsociado(egreso8);
        this.repoItems.modificar(edesurCIE);
        /***********************************************************************/

        Proveedor metrogasSurcos = new Proveedor("Metrogas",1234567,surcos);
        metrogasSurcos.setAltura(152);
        metrogasSurcos.setCiudad(ciudad);
        metrogasSurcos.setProvincia(provincia);
        metrogasSurcos.setPais(pais);
        metrogasSurcos.setCalle("Sarasa");
        metrogasSurcos.setPiso("0");
        metrogasSurcos.setZipcode(1234);
        this.repoProveedores.agregar(metrogasSurcos);

        Pago pago9 = new Pago();
        pago9.setCodigoAsociado("0605S");
        pago9.setMedioDePago(efectivo);

        this.repoPagos.agregar(pago9);
        /********************************************/
        Item metrogasCItem = new Item();
        metrogasCItem.setDescripcion("FACTURA SERVICIO DE GAS PERIODO JUNIO 2020");
        metrogasCItem.setOrganizacion(surcos);
        metrogasCItem.setTipoItem(servicio);

        this.repoItem.agregar(metrogasCItem);

        ItemEgreso metrogasCIE = new ItemEgreso();
        metrogasCIE.setCantidad(1);
        metrogasCIE.setPrecio(800);
        metrogasCIE.setItem(metrogasCItem);

        this.repoItems.agregar(metrogasCIE);
        /*************************************************/

        DocumentoComercial documentoComercial9 = new DocumentoComercial();
        documentoComercial9.setDescripcion("Pago efectivo");
        documentoComercial9.setFechaDeEntrega(LocalDate.now());
        documentoComercial9.setFechaDePedido(LocalDate.now());
        documentoComercial9.setTipo(facturaA);
        documentoComercial9.setNumDocumento(1223246);
        /********************************************/
        Egreso egreso9 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(surcos)
                .agregarItems(metrogasCIE)
                .agregarPago(pago9)
                .agregarFechaOperacion(LocalDate.of(2020,7,10))
                .agregarProveedor(metrogasSurcos)
                .agregarCantidadPresupuestos(0)
                .agregarDocumentoComercial(documentoComercial9)
                .build();

        this.repoEgresos.agregar(egreso9);
        metrogasCIE.setEgresoAsociado(egreso9);
        this.repoItems.modificar(metrogasCIE);

        /***********************************************************************/

        Proveedor telasZN = new Proveedor("Telas ZN",1235567,surcos);
        telasZN.setAltura(153);
        telasZN.setCiudad(ciudad);
        telasZN.setProvincia(provincia);
        telasZN.setPais(pais);
        telasZN.setCalle("Molino");
        telasZN.setPiso("0");
        telasZN.setZipcode(1254);

        this.repoProveedores.agregar(telasZN);

        Pago pago10 = new Pago();
        pago10.setCodigoAsociado("0805S");
        pago10.setMedioDePago(efectivo);

        this.repoPagos.agregar(pago10);
        /********************************************/
        Item cortinasItem = new Item();
        cortinasItem.setDescripcion("CORTINAS BLACKOUT VINILICO 2 PAÑOS");
        cortinasItem.setOrganizacion(surcos);
        cortinasItem.setTipoItem(producto);

        this.repoItem.agregar(cortinasItem);

        ItemEgreso cortinasIE = new ItemEgreso();
        cortinasIE.setCantidad(5);
        cortinasIE.setPrecio(4200);
        cortinasIE.setItem(cortinasItem);

        this.repoItems.agregar(cortinasIE);
        /*************************************************/

        DocumentoComercial documentoComercial10 = new DocumentoComercial();
        documentoComercial10.setDescripcion("Pago efectivo");
        documentoComercial10.setFechaDeEntrega(LocalDate.now());
        documentoComercial10.setFechaDePedido(LocalDate.now());
        documentoComercial10.setTipo(facturaA);
        documentoComercial10.setNumDocumento(122346);
        /********************************************/
        Egreso egreso10 = new BuilderEgresoConcreto()
                .agregarDatosOrganizacion(surcos)
                .agregarItems(cortinasIE)
                .agregarPago(pago10)
                .agregarFechaOperacion(LocalDate.of(2020,7,10))
                .agregarProveedor(telasZN)
                .agregarCantidadPresupuestos(0)
                .agregarDocumentoComercial(documentoComercial10)
                .build();

        this.repoEgresos.agregar(egreso10);
        cortinasIE.setEgresoAsociado(egreso10);
        this.repoItems.modificar(cortinasIE);

    }
}
