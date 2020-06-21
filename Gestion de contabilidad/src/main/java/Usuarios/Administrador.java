package Usuarios;

import Operaciones.CriterioOperacion;
import Organizacion.*;

public class Administrador extends Usuario{

    public void asociarUsuarioAOrganizacion(Estandar usuario, EntidadJuridica unaEmpresa){
        unaEmpresa.setUsuario(usuario);
    }

    public void asignarJerarquia(CriterioOperacion criterioPadre, CriterioOperacion criterioHijo){
        criterioPadre.setCriterioHijo(criterioHijo);
        criterioHijo.setCriterioPadre(criterioPadre);
    }
}
