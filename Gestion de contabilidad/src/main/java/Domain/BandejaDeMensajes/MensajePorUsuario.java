package Domain.BandejaDeMensajes;



import javax.persistence.*;

import Domain.Usuarios.Usuario;

@Entity
@Table(name="Mensaje_x_Usuario")
public class MensajePorUsuario{
    
	@EmbeddedId
	MensajePorUsuario id;
	
	@ManyToOne
	@MapsId("usuarioId")
	@JoinColumn(name="usuario_id")
    private Usuario usuario;
 
	@ManyToOne
	@MapsId("mensajeId")
    @JoinColumn(name = "mensaje_id")
    private Mensaje mensaje;
 
}
