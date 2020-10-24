package Domain.Entities.BandejaDeMensajes;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Usuarios.Usuario;

import java.time.LocalDate;

import javax.persistence.*;

import com.google.gson.annotations.Expose;

@Entity
@Table(name="mensaje")
public class Mensaje extends EntidadPersistente {

	@Expose
	@Column(columnDefinition = "DATE")
    private LocalDate fechaCreacion;

	@Expose
	@Column
    private String cuerpo;

	@Expose
	@Column
	public boolean leido;	

	@ManyToOne
	@JoinColumn(name="usuario_id", referencedColumnName = "id")
	Usuario usuario;

    public Mensaje(String cuerpo,Usuario usuario){
        this.fechaCreacion=LocalDate.now();
        this.cuerpo = cuerpo;
        this.usuario = usuario;
    }

    public Mensaje(){
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}