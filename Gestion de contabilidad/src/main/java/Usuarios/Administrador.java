package Usuarios;

import Organizacion.*;

public class Administrador extends Usuario{

    public void asociarUsuarioAOrganizacion(Estandar usuario, EntidadJuridica unaEmpresa){
        unaEmpresa.setUsuario(usuario);
    }
}
