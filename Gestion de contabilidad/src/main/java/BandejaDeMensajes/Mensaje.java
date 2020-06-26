package BandejaDeMensajes;

import java.util.Comparator;
import java.util.Date;

public class Mensaje{
    private Date fecha;
    private boolean leido;
    private String cuerpo;

    public Mensaje(String cuerpo){
        this.fecha = new Date();
        this.leido = false;
        this.cuerpo = cuerpo;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}