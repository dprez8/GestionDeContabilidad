package Domain.Entities.BandejaDeMensajes;

import Domain.Entities.Usuarios.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Embeddable
public class BandejaDeMensajes {

    @ManyToMany
    @JoinTable(
            name="mensaje_x_usuario",
            inverseJoinColumns=
            @JoinColumn(name="mensaje_id", referencedColumnName="mensaje_id"),
            joinColumns=
            @JoinColumn(name="usuario_id", referencedColumnName="id")
    )
    private List<Mensaje> mensajes = new ArrayList<Mensaje>();

    public void crearMensaje(String cuerpo){
        Mensaje msg = new Mensaje(cuerpo);
        mensajes.add(msg);
    }

    public void addMensajes(List<String> variosMensajes){
        variosMensajes.forEach(unCuerpo->crearMensaje(unCuerpo));
    }
    public void ordenarPorFechaRecienteUltimo(){
        mensajes.sort(Comparator.comparing(Mensaje::getFecha));
    }
    public void ordenarPorFechaRecientePrimero(){
        this.ordenarPorFechaRecienteUltimo();
        Collections.reverse(mensajes);
    }
    /*public List<Mensaje> filtrarPorLeido(boolean leido){
        return mensajes.stream().filter(a->a.isLeido()==leido).collect(Collectors.toList());
    }*/
    public List<Mensaje> filtrarPorFecha(LocalDate fecha){
        return mensajes.stream().filter(a->a.getFecha().equals(fecha)).collect(Collectors.toList());
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }
}

@Entity
@Table(name="mensaje_x_usuario")
class MensajePorUsuario{

    @EmbeddedId
    MensajeUsuarioKey id;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name="id")
    public Usuario usuario;

    @ManyToOne
    @MapsId("mensajeId")
    @JoinColumn(name = "mensaje_id")
    public Mensaje mensaje;

    @Column
    public boolean leido;

}

@Embeddable
class MensajeUsuarioKey implements Serializable {

    @Column(name = "usuario_id")
    public int usuarioId;

    @Column(name = "mensaje_id")
    public int mensajeId;

}