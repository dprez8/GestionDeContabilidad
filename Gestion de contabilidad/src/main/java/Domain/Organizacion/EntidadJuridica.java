package Domain.Organizacion;

import java.util.ArrayList;
import java.util.List;

import Domain.CategorizadorDeEmpresas.Categoria;
import Domain.Usuarios.Estandar;

public class EntidadJuridica extends Organizacion {
    private String razonSocial;
    private int cuit;
    private int direccionPostal;
    private int codigoDeInscripcionDefinitivaEnIGJ;
    private List<EntidadBase> entidadesBase;
    private Estandar usuario;
    private CategoriaEntidadJuridica tipoEntidadJuridica;

    public EntidadJuridica(String nombre,int cuit,String razonSocial,int direccionPostal,int codigoDeInscripcionDefinitivaEnIGJ){
        super(nombre);
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.direccionPostal = direccionPostal;
        this.codigoDeInscripcionDefinitivaEnIGJ = codigoDeInscripcionDefinitivaEnIGJ;
        this.entidadesBase = new ArrayList<>();
    }

    /**Setters & Getters*/
    public void setUsuario(Estandar usuario) {
        this.usuario = usuario;
    }

    public void setTipoEntidadJuridica(CategoriaEntidadJuridica tipoEntidadJuridica){
        this.tipoEntidadJuridica = tipoEntidadJuridica;
    }
}

