package BandejaDeMensajes;

import java.util.*;
import java.io.FileInputStream;
import java.util.stream.Collectors;

public class BandejaDeMensajes{
    private List<Mensaje> mensajes;
    private OrdenarPorFecha OrdenarPorFecha;

    public BandejaDeMensajes(){
        mensajes = new ArrayList<Mensaje>();
        OrdenarPorFecha = new OrdenarPorFecha();
    }

    public void crearMensaje(String cuerpo){
        Mensaje msg = new Mensaje(cuerpo);
        mensajes.add(msg);
    }
    public void ordenarPorFechaRecienteUltimo(){
        Collections.sort(mensajes, OrdenarPorFecha);
    }
    public void ordenarPorFechaRecientePrimero(){
        Collections.sort(mensajes, OrdenarPorFecha);
        Collections.reverse(mensajes);
    }
    public List<Mensaje> filtrarPorLeido(boolean leido){
        return mensajes.stream().filter(a->a.isLeido()==leido).collect(Collectors.toList());
    }
    public List<Mensaje> filtrarPorFecha(Date fecha){
//        System.out.printf("Mostrando los mensajes del día  %td/%tm/%ty...\n", fecha, fecha, fecha); //solo se filtran no se deberia mostrar, al menos no en esta funcion
        return mensajes.stream().filter(a->a.getFecha().equals(fecha)).collect(Collectors.toList());
    }
//    public void mostrarMensajesDeXLista(List<Mensaje> unaListaDeMensajes){
//        unaListaDeMensajes.forEach(a->mostrarUnMensaje(a));
//    }
    public void mostrarTodosLosMensajes(){
        System.out.println("Mostrando todos los mensajes...");
        mensajes.forEach(a->mostrarUnMensaje(a));
    }
    public void mostrarUnMensaje(Mensaje mensaje){
        System.out.printf("[%td/%tm/%ty] [leido: %s] %s\n", mensaje.getFecha(), mensaje.getFecha(), mensaje.getFecha(), mensaje.isLeido(), mensaje.getCuerpo());
        mensaje.setLeido(true);
    }
}

class OrdenarPorFecha implements Comparator<Mensaje> {
    public int compare(Mensaje msgA, Mensaje msgB){
        return msgA.getFecha().compareTo(msgB.getFecha());
    }
}