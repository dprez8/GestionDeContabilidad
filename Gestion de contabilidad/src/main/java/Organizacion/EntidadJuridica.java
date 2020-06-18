package Organizacion;

import java.util.ArrayList;
import java.util.List;
import Operaciones.Operacion;
import Usuarios.Estandar;

public class EntidadJuridica extends Organizacion {
    private String razonSocial;
    private int cuit;
    private int direccionPostal;
    private int codigoDeInscripcionDefinitivaEnIGJ;

    private List<EntidadBase> entidadesBase;
    private Estandar usuario;

    public void setUsuario(Estandar usuario) {
        this.usuario = usuario;
    }

    public EntidadJuridica() {
        entidadesBase = new ArrayList<>();
        operaciones = new ArrayList<>();
    }

    public void addOperacion(Operacion operacion){
        operaciones.add(operacion);
    }
}

