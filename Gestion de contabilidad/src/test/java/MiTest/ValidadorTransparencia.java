package MiTest;

import Organizacion.*;
import org.junit.Test;
import ValidadorTransparencia.*;

import java.util.Timer;

public class ValidadorTransparencia {
    Empresa miPyme = new Empresa();

    ValidarCantidadMinima validacionMinima = new ValidarCantidadMinima(3);
    ValidarConPresupuesto validacionPresupuesto = new ValidarConPresupuesto();
    ValidarMenorValor validacionMenorValor = new ValidarMenorValor();

    ValidadorDeTransparencia validador = new ValidadorDeTransparencia(validacionMenorValor,validacionMinima,validacionPresupuesto);

    /**Quizas esta parte pueda ir en la clase Schudeler*/
    public void lanzarHilo(){
        Scheduler hilo = new Scheduler(miPyme,validador);
        Timer timer    = new Timer();
        timer.schedule(hilo,20,10*1000); /**La instancia Scheduler llama a la funcion run con timer.schedule*/
    }
    @Test
    public void correrHilo(){
        lanzarHilo();
    }
}
