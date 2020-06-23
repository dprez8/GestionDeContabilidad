package BandejaDeMensajes;

import java.util.Comparator;
import java.util.Date;

public class Mensaje{
    public Date fecha;
    public boolean leido;
    public String cuerpo;

    // Cuando creamos un mensaje, le pasamos una fecha por temas de testing

    public Mensaje(Date fecha, String cuerpo){
        this.fecha = fecha;
        this.leido = false;
        this.cuerpo = cuerpo;
    }
}