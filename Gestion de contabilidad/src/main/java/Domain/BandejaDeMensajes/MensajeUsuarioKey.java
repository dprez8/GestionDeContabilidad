package Domain.BandejaDeMensajes;

import java.io.Serializable;

import javax.persistence.*;

@Embeddable
public class MensajeUsuarioKey implements Serializable {
    
	@Column(name = "usuario_id")
    public int usuarioId;
 
    @Column(name = "mensaje_id")
    public int mensajeId;
 
}
