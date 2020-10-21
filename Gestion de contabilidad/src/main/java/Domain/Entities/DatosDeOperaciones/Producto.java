package Domain.Entities.DatosDeOperaciones;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name="producto")
public class Producto extends EntidadPersistente {

    @Column(name="nombre_producto")
    private String nombreProducto;

    public Producto(){
    }

    public Producto(String nombreProducto){
        this.nombreProducto = nombreProducto;
    }
    public String getNombreProducto() {
        return nombreProducto;
    }


    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

}

