package Domain.Entities.BandejaDeMensajes;

import Domain.Entities.Usuarios.Usuario;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;
@Entity
@Table
public class Mensaje{
	@Id
	@Column(name="mensaje_id")
	private int mensajeId;
	@Column(columnDefinition = "DATE")
    private LocalDate fechaCreacion;
	@Column
    private String cuerpo;

    public Mensaje(String cuerpo){
        this.fechaCreacion=LocalDate.now();
        this.cuerpo = cuerpo;
    }

    public LocalDate getFecha() {
        return fechaCreacion;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setFecha(LocalDate fecha) {
        this.fechaCreacion = fecha;
    }
}