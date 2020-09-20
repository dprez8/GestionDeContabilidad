package Domain.BandejaDeMensajes;

import java.time.LocalDate;
import java.util.Date;

public class Mensaje{
	private int idMensaje;
    private LocalDate fechaCreacion;
    private String cuerpo;

    public Mensaje(String cuerpo){
        this.fechaCreacion=LocalDate.now();
        this.cuerpo = cuerpo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}