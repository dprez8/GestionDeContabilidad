package Domain.Entities.BandejaDeMensajes;



import javax.persistence.*;

import Domain.Entities.Usuarios.Usuario;

@Entity
@Table(name="Mensaje_x_Usuario")
public class MensajePorUsuario{
    
	@EmbeddedId
	MensajeUsuarioKey id;
	
	@ManyToOne
	@MapsId("usuarioId")
	@JoinColumn(name="usuario_id")
    public Usuario usuario;
 
	@ManyToOne
	@MapsId("mensajeId")
    @JoinColumn(name = "mensaje_id")
    public Mensaje mensaje;
	
	@Column
	public boolean leido;
 
}
