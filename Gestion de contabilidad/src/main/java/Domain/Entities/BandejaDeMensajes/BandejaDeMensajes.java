package Domain.Entities.BandejaDeMensajes;

import Domain.Entities.Usuarios.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class BandejaDeMensajes{

    private List<Mensaje> mensajes = new ArrayList<Mensaje>();
    private Usuario usuario;
	
    
    public BandejaDeMensajes(Usuario usuario){
		this.usuario=usuario;
    }

    public void crearMensaje(String cuerpo){
        Mensaje msg = new Mensaje(cuerpo,this.usuario);
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
