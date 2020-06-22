package BandejaDeMensajes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.io.FileInputStream;

public class BandejaDeMensajes{
    private List<Mensaje> mensajes;

    public BandejaDeMensajes(){
        mensajes = new ArrayList<Mensaje>();
    }

    public void crearMensaje(Date fecha, String cuerpo){
        Mensaje msg = new Mensaje(fecha, cuerpo);
        mensajes.add(msg);
    }
    public void ordenarPorFecha(){
        Collections.sort(mensajes, new OrdenarPorFecha());
    }
    public void printMsgs(){
        for (int i = 0; i < this.mensajes.size(); i++)
            System.out.println(this.mensajes.get(i).cuerpo);
    }
}