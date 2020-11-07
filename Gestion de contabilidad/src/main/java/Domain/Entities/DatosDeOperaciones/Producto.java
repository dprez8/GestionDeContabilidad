package Domain.Entities.DatosDeOperaciones;

import javax.persistence.*;

@Entity
@DiscriminatorValue("producto")
public class Producto extends TipoItem {

    public Producto(){
    }

    public Producto(String nombreProducto){
        this.nombre = nombreProducto;
    }
 
}

