package Domain.Organizacion;

public class EntidadBase extends Organizacion {
    private String descripcion;
    private EntidadJuridica entidadJuridica;

    public EntidadBase(String nombre,String descripcion){
        super(nombre);
        this.descripcion = descripcion;
    }
}
