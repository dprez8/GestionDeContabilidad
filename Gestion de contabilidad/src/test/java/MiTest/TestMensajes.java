package MiTest;

import BandejaDeMensajes.BandejaDeMensajes;
import Operaciones.Egreso;
import Organizacion.Empresa;
import Usuarios.Estandar;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.SimpleFormatter;

public class TestMensajes{
    @Test
    public void OrdernarMensajes() throws Exception{

        BandejaDeMensajes bandeja = new BandejaDeMensajes();

        Date hoy = new Date();
        Date ayer = new SimpleDateFormat("dd/MM/yyyy").parse("21/6/2020");
        Date anteayer = new SimpleDateFormat("dd/MM/yyyy").parse("20/6/2020");

        bandeja.crearMensaje(ayer, "Mensaje ayer");
        bandeja.crearMensaje(hoy, "Mensaje hoy");
        bandeja.crearMensaje(anteayer, "Mensaje anteayer");

        bandeja.imprimirMensajes();

        bandeja.ordenarPorFechaRecientePrimero();

        System.out.println("\nOrdenados por mas reciente primero");
        bandeja.imprimirMensajes();

        bandeja.ordenarPorFechaRecienteUltimo();

        System.out.println("\nOrdenados por mas reciente último");
        bandeja.imprimirMensajes();
    }

    @Test
    public void ValidacionesYMensajes() throws Exception{
        

    }
}