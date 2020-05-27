package Organizacion;

import java.util.List;

public abstract class EntidadJuridica extends Organizacion {
    private String razonSocial;
    private int cuit;
    private int direccionPostal;
    private int codigoDeInscripcionDefinitivaEnIGJ;
    private String actividad;
    private List<EntidadBase> entidadesBase;
    //private List<Operacion> operaciones;
    //private Estandar usuario;
}
