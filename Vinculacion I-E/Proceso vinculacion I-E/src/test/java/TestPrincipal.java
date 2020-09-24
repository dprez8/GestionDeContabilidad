import Domain.Entities.Condiciones.CondicionEntreFechas;
import Domain.Entities.Condiciones.CondicionSinIngresoAsociado;
import Domain.Entities.Condiciones.CondicionValor;
import Domain.Entities.Criterios.Criterio;
import Domain.Entities.Criterios.CriterioUnico;
import Domain.Entities.Criterios.OrdenValorPrimeroEgreso;
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
    private Vinculador vinculador;
    private OrdenValorPrimeroEgreso ordenValorPrimeroEgreso;
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
        this.ordenValorPrimeroEgreso = new OrdenValorPrimeroEgreso();

        /***************Creacion vinculador*****************/
        this.vinculador = new Vinculador();
        this.vinculador.addEgreso(this.compraUno,this.compraDos,this.compraTres);
        this.vinculador.addIngreso(this.creditosHaberes,this.donacion);
        this.vinculador.addCondiciones(this.condicionValor);
        this.vinculador.addCondiciones(this.condicionEntreFechas);
        this.vinculador.addCondiciones(this.condicionSinIngresoAsociado);
        this.vinculador.setCriterio(this.ordenValorPrimeroEgreso);

    }

    @Test
    public void VinculacionExitosa(){
        vinculador.vincular();
        Assert.assertEquals(creditosHaberes,compraUno.getIngresoAsociado());
        Assert.assertNotEquals(donacion,compraUno.getIngresoAsociado());
        Assert.assertEquals(creditosHaberes,compraDos.getIngresoAsociado());
        Assert.assertNotEquals(creditosHaberes,compraTres.getIngresoAsociado());
        Assert.assertNotEquals(donacion,compraTres.getIngresoAsociado());
    }
}
