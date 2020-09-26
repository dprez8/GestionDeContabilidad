import Domain.Entities.Condiciones.CondicionEntreFechas;
import Domain.Entities.Condiciones.CondicionSinIngresoAsociado;
import Domain.Entities.Condiciones.CondicionValor;
import Domain.Entities.Criterios.*;
import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Vinculador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestPrincipal {
    private static Vinculador vinculador;
    private OrdenValorPrimeroEgreso ordenValorPrimeroEgreso;
    private OrdenValorPrimeroIngreso ordenValorPrimeroIngreso;
    private Fecha ordenFecha;
    private Mix mix;

    private CondicionEntreFechas condicionEntreFechas;
    private CondicionValor condicionValor;
    private CondicionSinIngresoAsociado condicionSinIngresoAsociado;
    private Ingreso creditosHaberes,donacion;
    private Egreso compraUno,compraDos,compraTres;

    @Before
    public void antesDeTestear(){
        /***********Creacion de las operaciones a asociar***************/
        this.creditosHaberes = new Ingreso();
        this.creditosHaberes.setMonto(10000.0);
        this.creditosHaberes.setFecha(LocalDate.of(2020,9,25));

        this.donacion = new Ingreso();
        this.donacion.setMonto(500.0);
        this.donacion.setFecha(LocalDate.of(2020,9,26));


        this.compraUno  = new Egreso();
        this.compraUno.setFecha(LocalDate.of(2020,9,26));
        this.compraUno.setMonto(1000.0);

        this.compraDos  = new Egreso();
        this.compraDos.setFecha(LocalDate.of(2020,9,27));
        this.compraDos.setMonto(2500.0);

        this.compraTres = new Egreso();
        this.compraTres.setFecha(LocalDate.of(2020,9,23));
        this.compraTres.setMonto(10000.0);

        /***************Creacion de condiciones*************/
        this.condicionEntreFechas = new CondicionEntreFechas();
        condicionEntreFechas.setDiasDesde(-3);
        condicionEntreFechas.setDiasHasta(3);

        this.condicionValor = new CondicionValor();

        this.condicionSinIngresoAsociado = new CondicionSinIngresoAsociado();
        /***************Creacion criterio*******************/
        this.ordenValorPrimeroEgreso  = new OrdenValorPrimeroEgreso();
        this.ordenValorPrimeroIngreso = new OrdenValorPrimeroIngreso();
        this.ordenFecha               = new Fecha();
        this.mix                      = new Mix();


        /***************Creacion vinculador*****************/
        vinculador = Vinculador.instancia();
        vinculador.addEgreso(this.compraUno,this.compraDos,this.compraTres);
        vinculador.addIngreso(this.creditosHaberes,this.donacion);
        vinculador.addCondiciones(this.condicionValor);
        vinculador.addCondiciones(this.condicionEntreFechas);
        vinculador.addCondiciones(this.condicionSinIngresoAsociado);
    }

    @Test
    public void VinculacionOrdenValorPrimeroEgreso(){
        vinculador.setCriterio(this.ordenValorPrimeroEgreso);

        vinculador.vincular();

        vinculador.getEgresos().forEach(e->System.out.println(e.getMonto()));

        Assert.assertEquals(creditosHaberes,compraUno.getIngresoAsociado());
        Assert.assertNotEquals(donacion,compraUno.getIngresoAsociado());
        Assert.assertEquals(creditosHaberes,compraDos.getIngresoAsociado());
        Assert.assertNotEquals(creditosHaberes,compraTres.getIngresoAsociado());
        Assert.assertNotEquals(donacion,compraTres.getIngresoAsociado());
    }

    @Test
    public void VinculacionOrdenValorPrimeroIngreso() {
        vinculador.setCriterio(this.ordenValorPrimeroIngreso);

        vinculador.getIngresos().remove(this.donacion);
        this.donacion.setMonto(5000.0);
        vinculador.addIngreso(this.donacion);

        vinculador.vincular();


        Assert.assertEquals(donacion,compraUno.getIngresoAsociado());
        Assert.assertEquals(donacion,compraDos.getIngresoAsociado());
        Assert.assertEquals(creditosHaberes,compraTres.getIngresoAsociado());
    }

    @Test
    public void VinculacionOrdenFecha(){
        vinculador.setCriterio(this.ordenFecha);
        vinculador.getEgresos().remove(this.compraUno);
        this.compraUno.setFecha(LocalDate.of(2020,8,1));
        vinculador.addEgreso(this.compraUno);

        vinculador.getIngresos().remove(this.donacion);
        this.donacion.setMonto(5000.0);
        vinculador.addIngreso(this.donacion);

        vinculador.vincular();

        vinculador.getEgresos().forEach(e->System.out.println(e.getFecha()));
        vinculador.getEgresos().forEach(e->System.out.println(e.getMonto()));

        Assert.assertNotEquals(creditosHaberes,compraUno.getIngresoAsociado());
        Assert.assertEquals(creditosHaberes,compraTres.getIngresoAsociado());
        Assert.assertEquals(donacion,compraDos.getIngresoAsociado());
    }

    @Test
    public void VinculacionMix(){
        List<CriterioUnico> criterios = new ArrayList<>();

        vinculador.getIngresos().remove(this.donacion);
        this.donacion.setMonto(5000.0);
        vinculador.addIngreso(this.donacion);

        criterios.add(this.ordenFecha);
        criterios.add(this.ordenValorPrimeroEgreso);
        criterios.add(this.ordenValorPrimeroIngreso);

        this.mix.setCriteriosUnicos(criterios);

        vinculador.setCriterio(this.mix);

        vinculador.vincular();

        Assert.assertEquals(creditosHaberes,compraTres.getIngresoAsociado());
        Assert.assertNotEquals(creditosHaberes,compraUno.getIngresoAsociado());
        Assert.assertNotEquals(creditosHaberes,compraDos.getIngresoAsociado());
        Assert.assertEquals(donacion,compraUno.getIngresoAsociado());
        Assert.assertEquals(donacion,compraDos.getIngresoAsociado());
    }
}
