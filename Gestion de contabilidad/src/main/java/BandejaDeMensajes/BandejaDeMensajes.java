package BandejaDeMensajes;

import java.util.*;
import java.io.FileInputStream;

public class BandejaDeMensajes{
    private List<Mensaje> mensajes;
    private OrdenarPorFecha OrdenarPorFecha;

    public BandejaDeMensajes(){
        mensajes = new ArrayList<Mensaje>();
        OrdenarPorFecha = new OrdenarPorFecha();
    }

    public void crearMensaje(Date fecha, String cuerpo){
        Mensaje msg = new Mensaje(fecha, cuerpo);
        mensajes.add(msg);
    }
    public void ordenarPorFechaRecienteUltimo(){
        Collections.sort(mensajes, OrdenarPorFecha);
    }
    public void ordenarPorFechaRecientePrimero(){
        Collections.sort(mensajes, OrdenarPorFecha);
        Collections.reverse(mensajes);
    }
    public void filtrarPorLeido(boolean leido){
        if(leido)
            System.out.println("Mostrando mensajes leidos...");
        else
            System.out.println("Mostrando mensajes no leidos...");

        for (int i = 0; i < this.mensajes.size(); i++) {

            if(this.mensajes.get(i).leido == leido){
                mostrarUnMensaje(mensajes.get(i));
                this.mensajes.get(i).leido = true;
            }
        }
    }
    public void filtrarPorFecha(Date fecha){

        System.out.printf("Mostrando los mensajes del día  %td/%tm/%ty...\n", fecha, fecha, fecha);

        for (int i = 0; i < this.mensajes.size(); i++) {

            if(this.mensajes.get(i).fecha.equals(fecha)){
                mostrarUnMensaje(mensajes.get(i));
                this.mensajes.get(i).leido = true;
            }
        }
    }
    public void mostrarTodosLosMensajes(){
        System.out.println("Mostrando todos los mensajes...");
        for (int i = 0; i < this.mensajes.size(); i++) {
            mostrarUnMensaje(mensajes.get(i));
        }
    }
    public void mostrarUnMensaje(Mensaje mensaje){
        String leido = "sin leer";
        if(mensaje.leido)
            leido = "leido";

        System.out.printf("[%td/%tm/%ty] [%s] %s\n", mensaje.fecha, mensaje.fecha, mensaje.fecha, leido, mensaje.cuerpo);
    }
}

class OrdenarPorFecha implements Comparator<Mensaje> {
    public int compare(Mensaje msgA, Mensaje msgB){
        return msgA.fecha.compareTo(msgB.fecha);
    }
}