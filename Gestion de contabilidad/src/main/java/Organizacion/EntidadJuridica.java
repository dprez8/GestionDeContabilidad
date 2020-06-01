package Organizacion;

import java.util.ArrayList;
import java.util.List;
import Operaciones.Operacion;;


public class EntidadJuridica extends Organizacion {
    private String razonSocial;
    private int cuit;
    private int direccionPostal;
    private int codigoDeInscripcionDefinitivaEnIGJ;
    private String actividad;
    private List<EntidadBase> entidadesBase;

    //private Estandar usuario;   (esperar package de diego sobre los usuarios)


    public EntidadJuridica() {
        entidadesBase = new ArrayList<>();
        operaciones = new ArrayList<>();
    }


    public String getActividad() {
        return actividad;
    }
}

