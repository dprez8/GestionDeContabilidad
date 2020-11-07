package Domain.Entities.DatosDeOperaciones;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("servicio")
public class Servicio extends TipoItem {

    public Servicio(){
    }

    public Servicio(String nombreServicio){
        this.nombre = nombreServicio;
    }

}
