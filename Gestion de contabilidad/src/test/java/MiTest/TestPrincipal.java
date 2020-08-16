package MiTest;

import Domain.BandejaDeMensajes.*;
import Domain.CategorizadorDeEmpresas.*;
import Domain.DatosDeOperaciones.*;
import Domain.DireccionPostal.DireccionPostal;
import Domain.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Operaciones.Egreso.Egreso;
import Domain.Organizacion.*;
import Domain.Operaciones.*;
import Domain.Usuarios.*;
import Repositories.DaoMemoria;
import Repositories.Repositorio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class TestPrincipal {

    private Repositorio<Egreso> repoEgresos;;
    private Presupuesto primerPresupuesto;
    private Presupuesto segundoPresupuesto;
    private Sector construccion;
    private Empresa miPyme;
    private EntidadJuridica pymeJuridica;
    private Estandar fernando;
    private Proveedor lautaroIturregui;
    private MedioDePago efectivo;
    private DocumentoComercial factura;
    private Producto ram,placaDeVideo;
    private ItemEgreso rams,placasDeVideo;

    @Before
    public void antesDeTestear(){

        /**Creacion de los datos de egreso*/
        this.ram  = new Producto("4GB DDR3");
        this.rams = new ItemEgreso(this.ram, 1, 3000);

        this.placaDeVideo  = new Producto("4GB DDR5");
        this.placasDeVideo = new ItemEgreso(this.placaDeVideo, 2, 5000);

        TipoDocumento facturaA = new TipoDocumento("Factura A");
        this.factura = new DocumentoComercial(facturaA, 11111);

         this.efectivo = new MedioDePago(1212, "Efectivo");

        DireccionPostal direPostalIturregui = new DireccionPostal();
        this.lautaroIturregui = new Proveedor("Lautaro Iturregui",2,direPostalIturregui);

        /**Creacion de una organizacion ejemplo*/
        DireccionPostal direccionPostal = new DireccionPostal();

        this.construccion       = new Sector("Construccion");
        this.miPyme             = new Empresa(3,50000003.0,"Construccion",construccion);
        this.pymeJuridica       = new EntidadJuridica("MiPyme",1234,"Nose",direccionPostal,1);
        this.pymeJuridica.setTipoEntidadJuridica(this.miPyme);
        /**Creacion de un usuario estandar*/
        this.fernando = new Estandar(pymeJuridica, "Lautaro", "1234", "lautaro@robles.com");

        /**Creacion de un repositorio de egresos*/
        this.repoEgresos = new Repositorio<Egreso>(new DaoMemoria<Egreso>(),Egreso.class); //Creo el repositorio de egresos

    }

    @Test
    public void creacionEgresoValida() {
        BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();

        Egreso compra = egresoBuilder.agregarProveedor(this.lautaroIturregui)
                .agregarFechaOperacion(LocalDate.of(2020, Month.AUGUST,14))
                .agregarMedioDePago(this.efectivo)
                .agregarDocumentoComercial(this.factura)
                .agregarDatosOrganizacion(this.pymeJuridica)
                .agregarItems(this.rams,this.placasDeVideo)
                .build();

        Assert.assertEquals(compra.getProveedor().getNombre(), "Lautaro Iturregui");
        Assert.assertEquals(compra.getFechaOperacion().toString(), "2020-08-14");
        Assert.assertEquals(compra.getMedioDePago(), this.efectivo);
        Assert.assertEquals(compra.getDocumento(), this.factura);
        Assert.assertEquals(compra.getOrganizacion(), this.pymeJuridica);

        this.repoEgresos.agregar(compra);
    }

    @Test
    public void categorizacionDeEmpresas(){

        this.construccion.addCategoriaExistente("Micro", 1);
        this.construccion.addCategoriaExistente("Pequenia", 2);
        this.construccion.addCategoriaExistente("Mediana T1", 3);
        this.construccion.addCategoriaExistente("Mediana T2", 4);

        /**Categorias del sector construccion*/
        Categoria microConstruccion     = new Categoria("Micro",0.0,	19450000.0,1,12);
        Categoria pequeniaConstruccion  = new Categoria("Pequenia",19450000.0,115370000.0,12,45);
        Categoria medianaT1Construccion = new Categoria("Mediana T1",115370000.0,643710000.0,45,200);
        Categoria medianaT2Construccion = new Categoria("Mediana T2",643710000.0,965460000.0,200,590);

        this.construccion.addCategorias(pequeniaConstruccion,medianaT1Construccion,microConstruccion,medianaT2Construccion);

        this.miPyme.cacularCategoria();
        System.out.printf(miPyme.getCategoria().getNombre());
        Assert.assertEquals(pequeniaConstruccion,miPyme.getCategoria());
    }
    @Test
    public void validadorDeContrasenias(){

    }
}