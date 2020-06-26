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
    private List<EntidadBase> entidadesBase = new ArrayList<>();
    private Estandar usuario;
    private CategoriaEntidadJuridica tipoEntidadJuridica;

    public EntidadJuridica(String nombre,int cuit,String razonSocial){
        super(nombre);
        this.cuit = cuit;
        this.razonSocial = razonSocial;
    }

    public void setUsuario(Estandar usuario) {
        this.usuario = usuario;
    }

}

