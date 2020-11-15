package Domain.Entities.Usuarios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import Domain.Entities.BandejaDeMensajes.BandejaDeMensajes;
import Domain.Entities.Operaciones.CriterioOperacion;
import Domain.Entities.Organizacion.*;
import Domain.Exceptions.contraseniaCorta;
import Domain.Exceptions.contraseniaMuyComun;
import Domain.Exceptions.repiteContraseniaEnMailOUsuario;

@Entity
@DiscriminatorValue("administrador")
public class Administrador extends Usuario{

	@OneToMany(mappedBy = "administrador",fetch = FetchType.LAZY)
	List<EntidadJuridica> juridicas;
	
	public Administrador(){
		this.juridicas= new ArrayList<>();
	}

	public Administrador(String username, String nombre,String apellido,String contrasenia, String mail, EntidadJuridica ... entidadesJuridicas) throws IOException, contraseniaMuyComun, repiteContraseniaEnMailOUsuario, contraseniaCorta {
		super(username,nombre,apellido,contrasenia,mail);
		this.juridicas= new ArrayList<>();
		Collections.addAll(this.juridicas,entidadesJuridicas);
	}
	
    public void asignarJerarquia(CriterioOperacion criterioPadre, CriterioOperacion criterioHijo){
        criterioPadre.setCriterioHijo(criterioHijo);
        criterioHijo.setCriterioPadre(criterioPadre);
    }

	public List<EntidadJuridica> getJuridicas() {
		return juridicas;
	}

	public void setJuridicas(List<EntidadJuridica> juridicas) {
		this.juridicas = juridicas;
	}
}
