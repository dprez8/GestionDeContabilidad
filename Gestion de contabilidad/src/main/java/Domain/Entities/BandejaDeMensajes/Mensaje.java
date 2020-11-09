package Domain.Entities.BandejaDeMensajes;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Usuarios.Usuario;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

@Entity
@Table(name="mensaje")
public class Mensaje extends EntidadPersistente {

	@Expose
	@Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaCreacion;

	@Expose
	@Column(name = "cuerpo")
	@Lob
	@Type(type = "org.hibernate.type.TextType")
    private String cuerpo;

	@Expose
	@Column
	public boolean leido;	

	@ManyToOne
	@JoinColumn(name="usuario_id", referencedColumnName = "id")
	Usuario usuario;

    public Mensaje(String cuerpo,Usuario usuario){
        this.fechaCreacion=LocalDateTime.now();
        this.cuerpo = cuerpo;
        this.usuario = usuario;
    }

    public Mensaje(){
	}

    public LocalDateTime getFecha() {
        return fechaCreacion;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fechaCreacion = fecha;
    }

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
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