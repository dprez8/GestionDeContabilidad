package Persistencia;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Domain.Entities.ApiPaises.Moneda;
import Domain.Entities.CategorizadorDeEmpresas.Sector;
import Domain.Entities.DatosDeOperaciones.DocumentoComercial;
import Domain.Entities.DatosDeOperaciones.ItemEgreso;
import Domain.Entities.DatosDeOperaciones.MedioDePago;
import Domain.Entities.DatosDeOperaciones.Pago;
import Domain.Entities.DatosDeOperaciones.Producto;
import Domain.Entities.DatosDeOperaciones.Proveedor;
import Domain.Entities.DatosDeOperaciones.TipoDocumento;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Organizacion.Empresa;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.ValidadorDeContrasenia.ValidadorDeContrasenia;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Daos.DaoMemoria;

public class Operaciones {

    private Repositorio<Egreso> repoEgresos;
    private Repositorio<Ingreso> repoIngresos;
    private Repositorio<Moneda> repoMonedas;
    private Repositorio<EntidadJuridica> repoEntidades;
    private Presupuesto primerPresupuesto;
    private Presupuesto segundoPresupuesto;
    private Sector construccion;
    private Empresa miPyme;
    private EntidadJuridica pymeJuridica;
    private Estandar fernando;
    private Egreso compra;
    private Ingreso ingreso;
    private Proveedor lautaroIturregui;
    private MedioDePago rapiPago; 
    private Pago pago;
    private DocumentoComercial factura;
    private Producto ram,placaDeVideo;
    private ItemEgreso rams,placasDeVideo;
    private ValidadorDeContrasenia validadorDeContrasenia;

    @Before
    public void antesDeTestear() throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta{

        this.pymeJuridica = new EntidadJuridica();

        /**Creacion de un repositorios*/
       
        this.repoEgresos = new Repositorio<Egreso>(new DaoHibernate<Egreso>(Egreso.class));
        this.repoIngresos = new Repositorio<Ingreso>(new DaoHibernate<Ingreso>(Ingreso.class));
        this.repoMonedas = new Repositorio<Moneda>(new DaoHibernate<Moneda>(Moneda.class));
        this.repoEntidades = new Repositorio<EntidadJuridica>(new DaoHibernate<EntidadJuridica>(EntidadJuridica.class));
        

    	this.repoEntidades.agregar(pymeJuridica);
    	
    }

    @Test
    public void creacionEgresoValida() {
        
    	BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();

        this.compra = egresoBuilder.agregarFechaOperacion(LocalDate.of(2020, Month.AUGUST,14))
        							.agregarDatosOrganizacion(pymeJuridica)
                                    .build();
    	
        this.repoEgresos.agregar(compra);
    }
    
    
    @Test
    public void agregoOperaciones() {
    	Moneda peso= repoMonedas.buscar(3);
    
    	this.ingreso= new Ingreso("JalaBolismo",4334,pymeJuridica,peso); 
    	
    	pymeJuridica.addOperaciones(this.compra,this.ingreso);
    
    	this.repoIngresos.agregar(ingreso);
    }
    

}
