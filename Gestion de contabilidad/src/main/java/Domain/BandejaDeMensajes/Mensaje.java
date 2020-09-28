package Domain.BandejaDeMensajes;

import java.time.LocalDate;

import javax.persistence.*;

import Domain.Usuarios.Usuario;
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
	@Column
	public boolean leido;
	@ManyToOne
	Usuario usuario;

    public Mensaje(String cuerpo,Usuario usuario){
        this.fechaCreacion=LocalDate.now();
        this.cuerpo = cuerpo;
        this.usuario = usuario;
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

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
    
    
}