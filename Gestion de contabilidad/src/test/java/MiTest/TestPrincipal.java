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

        DireccionPostal direccionPostal = new DireccionPostal();

        this.construccion       = new Sector("Construccion");
        this.miPyme             = new Empresa(3,50000003.0,"Construccion",construccion);
        this.pymeJuridica       = new EntidadJuridica("MiPyme",1234,"Nose",direccionPostal,1);
        this.pymeJuridica.setTipoEntidadJuridica(this.miPyme);

        this.fernando = new Estandar(pymeJuridica, "Lautaro", "1234", "lautaro@robles.com");

        this.repoEgresos = new Repositorio<Egreso>(new DaoMemoria<Egreso>(),Egreso.class); //Creo el repositorio de egresos

    }

    @Test
    public void creacionEgresoValida() throws ParseException {
        BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();

        Egreso compra = egresoBuilder.agregarProveedor(this.lautaroIturregui)
                .agregarFechaOperacion(new SimpleDateFormat("dd/MM/yy").parse("14/08/20"))
                .agregarMedioDePago(this.efectivo)
                .agregarDocumentoComercial(this.factura)
                .agregarDatosOrganizacion(this.pymeJuridica)
                .agregarItems(this.rams,this.placasDeVideo)
                .build();

        this.primerPresupuesto  = new Presupuesto(3, compra);
        this.segundoPresupuesto = new Presupuesto(4, compra);

        this.repoEgresos.agregar(compra);

        compra.addPresupuestos(primerPresupuesto,segundoPresupuesto);
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
}