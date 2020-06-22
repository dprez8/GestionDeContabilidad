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
    public void imprimirMensajes(){
        System.out.println("Mostrando todos los mensajes...");
        for (int i = 0; i < this.mensajes.size(); i++)
            System.out.println(this.mensajes.get(i).cuerpo);
    }
}

class OrdenarPorFecha implements Comparator<Mensaje> {
    public int compare(Mensaje msgA, Mensaje msgB){
        return msgA.fecha.compareTo(msgB.fecha);
    }
}