package Domain.Usuarios;

import javax.persistence.*;

import Domain.Operaciones.CriterioOperacion;
import Domain.Organizacion.*;
@Entity
@DiscriminatorValue("administrador")
public class Administrador extends Usuario{

    public void asociarUsuarioAOrganizacion(Estandar usuario, EntidadJuridica unaEmpresa){
        unaEmpresa.setUsuario(usuario);
    }

    public void asignarJerarquia(CriterioOperacion criterioPadre, CriterioOperacion criterioHijo){
        criterioPadre.setCriterioHijo(criterioHijo);
        criterioHijo.setCriterioPadre(criterioPadre);
    }
}
