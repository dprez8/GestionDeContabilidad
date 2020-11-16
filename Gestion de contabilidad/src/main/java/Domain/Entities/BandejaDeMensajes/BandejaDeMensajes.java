package Domain.Entities.BandejaDeMensajes;

import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class BandejaDeMensajes{

    private List<Mensaje> mensajes = new ArrayList<Mensaje>();
    private Usuario usuario;
	private Repositorio<Mensaje> repoMensajes;

	public BandejaDeMensajes(){

    }

    public BandejaDeMensajes(Usuario usuario){
		this.usuario=usuario;
		this.repoMensajes = new Repositorio<Mensaje>(new DaoHibernate<Mensaje>(Mensaje.class));
    }

    public void crearMensaje(String cuerpo){
        Mensaje msg = new Mensaje(cuerpo,this.usuario);
        mensajes.add(msg);
        this.repoMensajes.agregar(msg);
    }

    public void addMensajesString(List<String> variosMensajes){
        String cuerpo = variosMensajes.stream().map(Object::toString).collect(Collectors.joining("\n"));
        crearMensaje(cuerpo);
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

    public void addMensajes(Mensaje ... mensajes) {
        Collections.addAll(this.mensajes,mensajes);
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }
}
