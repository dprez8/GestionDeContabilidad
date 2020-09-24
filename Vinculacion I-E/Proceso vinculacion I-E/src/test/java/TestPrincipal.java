import Domain.Entities.Condiciones.CondicionEntreFechas;
import Domain.Entities.Condiciones.CondicionValor;
import Domain.Entities.Criterios.Criterio;
import Domain.Entities.Criterios.CriterioUnico;
import Domain.Entities.Criterios.OrdenValorPrimeroEgreso;
import Domain.Entities.Operaciones.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Vinculador;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TestPrincipal {
    private Vinculador vinculador;
    private List<Ingreso> ingresos;
    private List<Egreso> egresos;
    private OrdenValorPrimeroEgreso ordenValorPrimeroEgreso;
    private CondicionEntreFechas condicionEntreFechas;
    private CondicionValor condicionValor;

    @Before
    public void antesDeTestear(){
        /***********Creacion de las operaciones a asociar***************/
        this.ingresos = new ArrayList<>();

        Ingreso creditosHaberes = new Ingreso();
        creditosHaberes.setMonto(10000.0);
        creditosHaberes.setFecha(LocalDate.now());

        Ingreso donacion = new Ingreso();
        donacion.setMonto(500.0);
        donacion.setFecha(LocalDate.now());

        Collections.addAll(this.ingresos,creditosHaberes,donacion);

        this.egresos = new ArrayList<>();

        Egreso compraUno  = new Egreso();
        compraUno.setFecha(LocalDate.now());
        compraUno.setMonto(1000.0);

        Egreso compraDos  = new Egreso();
        compraDos.setFecha(LocalDate.now());
        compraDos.setMonto(2500.0);

        Egreso compraTres = new Egreso();
        compraTres.setFecha(LocalDate.now());
        compraTres.setMonto(10000.0);

        Collections.addAll(this.egresos,compraDos,compraUno,compraTres);

        /***************Creacion de condiciones*************/
        this.condicionEntreFechas = new CondicionEntreFechas();
        condicionEntreFechas.setDiasDesde(-3);
        condicionEntreFechas.setDiasHasta(3);

        this.condicionValor = new CondicionValor();

        /***************Creacion criterio*******************/
        this.ordenValorPrimeroEgreso = new OrdenValorPrimeroEgreso();

        /***************Creacion vinculador*****************/
        this.vinculador = new Vinculador();
        this.vinculador.setIngresos(this.ingresos);
        this.vinculador.setEgresos(this.egresos);
        this.vinculador.addCondiciones(this.condicionValor,this.condicionEntreFechas);
        this.vinculador.setCriterio(this.ordenValorPrimeroEgreso);

    }

    /*@Test
    public void VinculacionExitosa(){

    }*/
}
