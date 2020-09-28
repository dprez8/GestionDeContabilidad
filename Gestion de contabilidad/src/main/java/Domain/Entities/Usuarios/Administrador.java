package Domain.Entities.Usuarios;

import javax.persistence.*;

import Domain.Entities.Operaciones.CriterioOperacion;
import Domain.Entities.Organizacion.*;
@Entity
@DiscriminatorValue("administrador")
public class Administrador extends Usuario{

    public void asignarJerarquia(CriterioOperacion criterioPadre, CriterioOperacion criterioHijo){
        criterioPadre.setCriterioHijo(criterioHijo);
        criterioHijo.setCriterioPadre(criterioPadre);
    }
}
