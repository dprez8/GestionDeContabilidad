package Domain.Entities.Organizacion;

import javax.persistence.*;

@Entity
@Table(name = "entidad_base")
@PrimaryKeyJoinColumn(name = "id")
public class EntidadBase extends Organizacion {

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "juridica_id", referencedColumnName = "id")
    private EntidadJuridica entidadJuridica;


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EntidadJuridica getEntidadJuridica() {
        return entidadJuridica;
    }

    public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
        this.entidadJuridica = entidadJuridica;
    }
}
