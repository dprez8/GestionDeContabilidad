package Domain.Entities.ApiVinculador;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "configuracion_vinculador")
public class ConfiguracionVinculador extends EntidadPersistente {

    @ElementCollection
    private List<String> criterios;

    public ConfiguracionVinculador(){
        this.criterios = new ArrayList<>();
        this.criterios.add("OrdenValorPrimeroEgreso");
    }

    public List<String> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<String> criterios) {
        this.criterios = criterios;
    }
}
