package Domain.Entities.Organizacion;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "entidad_base")
@PrimaryKeyJoinColumn(name = "id")
public class EntidadBase extends Organizacion {
    @Expose
    @Column(name = "nombre_ficticio")
    private String nombreFicticio;

    @ManyToOne
    @JoinColumn(name = "juridica_id", referencedColumnName = "id")
    private EntidadJuridica entidadJuridica;

    public EntidadJuridica getEntidadJuridica() {
        return entidadJuridica;
    }

    public String getNombreFicticio() {
        return nombreFicticio;
    }

    public void setNombreFicticio(String nombreFicticio) {
        this.nombreFicticio = nombreFicticio;
    }

    public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
        this.entidadJuridica = entidadJuridica;
    }
}
