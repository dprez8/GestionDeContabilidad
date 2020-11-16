package MiTest;

import Domain.Entities.CategorizadorDeEmpresas.CategorizadorDeEmpresas;
import Domain.Entities.CategorizadorDeEmpresas.*;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Exceptions.ExcepcionCreacionEgreso;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Organizacion.*;
import Domain.Entities.Operaciones.*;
import Domain.Entities.Usuarios.*;
import Domain.Entities.ValidadorDeContrasenia.ValidadorDeContrasenia;
import Domain.Entities.ValidadorDeContrasenia.ValidarIgualAMailOUsuario;
import Domain.Entities.ValidadorDeContrasenia.ValidarLongitudCorta;
import Domain.Entities.ValidadorDeContrasenia.ValidarTop10k;
import Domain.Repositories.Daos.DaoMemoria;
import Domain.Repositories.Repositorio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.time.Month;
import java.util.*;
import java.time.LocalDate;

public class TestPrincipal {

    private Repositorio<Egreso> repoEgresos;
    private Presupuesto primerPresupuesto;
    private Presupuesto segundoPresupuesto;
    private Sector construccion, industria, servicios, agropecuaria, comercio;
    private Empresa miPyme;
    private EntidadJuridica pymeJuridica;
    private Estandar fernando;
    private Proveedor lautaroIturregui;
    private MedioDePago rapiPago; 
    private Pago pago;
    private DocumentoComercial factura;
    private Item ram,placaDeVideo;
    private TipoItem producto;
    private TipoItem servicio;
    private ItemEgreso rams,placasDeVideo;
    private ValidadorDeContrasenia validadorDeContrasenia;

    @Before
    public void antesDeTestear() throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta{

        TipoDocumento facturaA = new TipoDocumento("Factura A");
        this.factura = new DocumentoComercial(facturaA, 11111);
        this.rapiPago = new MedioDePago("rapipago");
        this.pago = new Pago("1231231",rapiPago);

        /**Creacion de una organizacion ejemplo*/

        this.construccion       = new Sector("Construccion");
        this.miPyme             = new Empresa();
        this.miPyme.setSector(construccion);
        this.miPyme.setActividad("Construccion");
        this.miPyme.setVentasAnuales(50000003.0);
        this.miPyme.setCantidadDePersonal(3);

        this.pymeJuridica       = new EntidadJuridica();
        pymeJuridica.setTipoEntidadJuridica(miPyme);

        this.lautaroIturregui = new Proveedor("Lautaro Iturregui",2, this.pymeJuridica);

        /**Creacion de los datos de egreso*/
        this.producto= new TipoItem("producto");
        this.servicio= new TipoItem("servicio");
        this.ram  = new Item("4GB DDR3",this.producto, pymeJuridica);
        this.rams = new ItemEgreso(this.ram, 1, 3000);

        this.placaDeVideo  = new Item("4GB DDR5",this.producto, pymeJuridica);
        this.placasDeVideo = new ItemEgreso(this.placaDeVideo, 2, 5000);

        /**Creacion de un usuario estandar*/
        this.fernando = new Estandar(pymeJuridica,"Fer","Fernando", "Yerbas","1234JHBHJVHJ", "fernando@herbas.com");

        /**Creacion de un repositorio de egresos*/
        this.repoEgresos = new Repositorio<Egreso>(new DaoMemoria<Egreso>()); //Creo el repositorio de egresos

        /**Creacion del validador de contraseñas*/
        this.validadorDeContrasenia = new ValidadorDeContrasenia();
    }

    @Test
    public void creacionEgresoValida() throws ExcepcionCreacionEgreso {
        BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();

        Egreso compra = egresoBuilder.agregarProveedor(this.lautaroIturregui)
                .agregarFechaOperacion(LocalDate.of(2020, Month.AUGUST,14))
                .agregarPago(this.pago)
                .agregarDocumentoComercial(this.factura)
                .agregarDatosOrganizacion(this.pymeJuridica)
                .agregarItems(this.rams,this.placasDeVideo)
                .build();

        Assert.assertEquals(compra.getProveedor().getNombre(), "Lautaro Iturregui");
        Assert.assertEquals(compra.getFechaOperacion().toString(), "2020-08-14");
        Assert.assertEquals(compra.getPago().getMedioDePago(), this.rapiPago);
        Assert.assertEquals(compra.getDocumento(), this.factura);
        Assert.assertEquals(compra.getOrganizacion(), this.pymeJuridica);

        this.repoEgresos.agregar(compra);
    }

    @Test
    public void categorizacionDeEmpresas(){
 
        /**Categorias del sector construccion*/
        CategoriaEmpresa micro   = new CategoriaEmpresa("Micro");
        CategoriaEmpresa pequenia  = new CategoriaEmpresa("Pequenia");
        CategoriaEmpresa medianaT1= new CategoriaEmpresa("Mediana T1");
        CategoriaEmpresa medianaT2 = new CategoriaEmpresa("Mediana T2");

        CategoriaPorSector microConstruccion     = new CategoriaPorSector(0.0,	19450000.0,1,12);
        CategoriaPorSector pequeniaConstruccion  = new CategoriaPorSector(19450000.0,115370000.0,12,45);
        CategoriaPorSector medianaT1Construccion = new CategoriaPorSector(115370000.0,643710000.0,45,200);
        CategoriaPorSector medianaT2Construccion = new CategoriaPorSector(643710000.0,965460000.0,200,590);
        
  
        micro.addCategoriasPorSector(microConstruccion);
        pequenia.addCategoriasPorSector(pequeniaConstruccion);
        medianaT1.addCategoriasPorSector(medianaT1Construccion);
        medianaT2.addCategoriasPorSector(medianaT2Construccion);
      
        
        construccion.addCategoriasPorSector(microConstruccion,pequeniaConstruccion,medianaT1Construccion,medianaT2Construccion);
        
        CategorizadorDeEmpresas.addCategoriasPorSector(pequeniaConstruccion,medianaT1Construccion,microConstruccion,medianaT2Construccion);  
        this.miPyme.cacularCategoria();
        System.out.printf(miPyme.getCategoriaEmpresa().getNombre());
        Assert.assertEquals(pequenia,miPyme.getCategoriaEmpresa());
    }

    @Test//(expected = contraseniaCorta.class)
    public void validadorDeContrasenias() throws IOException, contraseniaCorta, contraseniaMuyComun, repiteContraseniaEnMailOUsuario {
        /**Leo un archivo de config mediante la clase Properties*/
        Properties prop=new Properties();
        prop.load(new FileReader("src/main/resources/config/config.properties"));

        ValidarLongitudCorta validarLongitudCorta           = new ValidarLongitudCorta(Integer.parseInt(prop.getProperty("LONGITUDMINIMA")));
        ValidarTop10k validarTop10k                         = new ValidarTop10k(prop.getProperty("DATAFILEPATH"));
        ValidarIgualAMailOUsuario validarIgualAMailOUsuario = new ValidarIgualAMailOUsuario();

        ValidadorDeContrasenia.addValidaciones(validarLongitudCorta,validarTop10k,validarIgualAMailOUsuario);

        ValidadorDeContrasenia.validarContrasenia(this.fernando);

        /**Para capturar la excepcion, si se desea, comentar la linea de arriba y el expected en la anotacion @Test*/
        /*
        String error = new String();
        try{
            this.validadorDeContrasenia.validarContrasenia(this.fernando);
        }catch (contraseniaCorta ex) {
            error = ex.getMessage();
        }catch (contraseniaMuyComun ex){
            error = ex.getMessage();
        }catch (repiteContraseniaEnMailOUsuario ex){
            error = ex.getMessage();
        }
        System.out.println(error);
        */
    }
}