package MiTest;

import Domain.BandejaDeMensajes.*;
import Domain.CategorizadorDeEmpresas.*;
import Domain.Operaciones.Egreso.Egreso;
import Domain.Organizacion.*;
import Domain.ValidadorTransparencia.*;
import Domain.Operaciones.*;
import Domain.Usuarios.*;
import Repositories.DaoMemoria;
import Repositories.Repositorio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class TestPrincipal {

    private Repositorio<Egreso> repoEgresos;
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
        this.miPyme             = new Empresa(3,50000003.0,"Construccion",construccion);
        this.unaEntidad         = new EntidadJuridica("MiPyme",1234,"Nose",4321,1);
        this.unaEntidad.setTipoEntidadJuridica(this.miPyme);

        this.unUsuario = new Estandar(unaEntidad, "Lautaro", "1234", "lautaro@robles.com");

        this.unaCompra.addRevisores(unUsuario);

        this.repoEgresos = new Repositorio<Egreso>(new DaoMemoria<Egreso>(),Egreso.class); //Creo el repositorio de egresos
        this.repoEgresos.agregar(this.unaCompra);
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