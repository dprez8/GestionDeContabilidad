package BandejaDeMensajes;

import java.util.*;
import java.io.FileInputStream;
import java.util.stream.Collectors;

public class BandejaDeMensajes{
    private List<Mensaje> mensajes = new ArrayList<Mensaje>();

    public void crearMensaje(String cuerpo, Object... args){
        Mensaje msg = new Mensaje(String.format(cuerpo, args));
        mensajes.add(msg);
    }
    public void ordenarPorFechaRecienteUltimo(){
        mensajes.sort(Comparator.comparing(Mensaje::getFecha));
    }
    public void ordenarPorFechaRecientePrimero(){
        this.ordenarPorFechaRecienteUltimo();
        Collections.reverse(mensajes);
    }
    public List<Mensaje> filtrarPorLeido(boolean leido){
        return mensajes.stream().filter(a->a.isLeido()==leido).collect(Collectors.toList());
    }
    public List<Mensaje> filtrarPorFecha(Date fecha){
        return mensajes.stream().filter(a->a.getFecha().equals(fecha)).collect(Collectors.toList());
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }
}