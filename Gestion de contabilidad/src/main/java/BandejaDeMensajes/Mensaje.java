package BandejaDeMensajes;

import java.util.Comparator;
import java.util.Date;

public class Mensaje{
    public Date fecha;
    public boolean leido;
    public String cuerpo;

    public Mensaje(Date fecha, String cuerpo){
        this.fecha = fecha;
        this.leido = false;
        this.cuerpo = cuerpo;
    }
}

class OrdenarPorFecha implements Comparator<Mensaje> {
    public int compare(Mensaje msgA, Mensaje msgB){
        return msgA.fecha.compareTo(msgB.fecha);
    }
}