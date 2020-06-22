package MiTest;

import BandejaDeMensajes.BandejaDeMensajes;
import Operaciones.Egreso;
import Organizacion.Empresa;
import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.SimpleFormatter;

public class TestMensajes{
    @Test
    public void testMensaje() throws Exception{

        BandejaDeMensajes bandeja = new BandejaDeMensajes();

        Date hoy = new Date();
        Date ayer = new SimpleDateFormat("dd/MM/yyyy").parse("21/6/2020");
        Date anteayer = new SimpleDateFormat("dd/MM/yyyy").parse("20/6/2020");

        bandeja.crearMensaje(ayer, "Mensaje ayer");
        bandeja.crearMensaje(hoy, "Mensaje hoy");
        bandeja.crearMensaje(anteayer, "Mensaje anteayer");

        bandeja.printMsgs();

        bandeja.ordenarPorFecha();

        bandeja.printMsgs();

    }
}