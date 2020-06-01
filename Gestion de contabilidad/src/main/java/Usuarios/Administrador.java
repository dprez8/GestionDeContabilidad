package Usuarios;

import Organizacion.*;

public class Administrador extends Usuario{

    public Estandar darAltaUsuario(EntidadJuridica unaEmpresa){
        return new Estandar(unaEmpresa);
    }

    public Empresa darAltaEmpresa(){
        return new Empresa();
    }
    public Osc darAltaOsc(){
        return new Osc();
    }

    public void asociarUsuarioAOrganizacion(Estandar usuario, EntidadJuridica unaEmpresa){
        unaEmpresa.setUsuario(usuario);
    }
}
