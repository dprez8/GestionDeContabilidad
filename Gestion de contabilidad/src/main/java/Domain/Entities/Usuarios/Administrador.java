package Domain.Entities.Usuarios;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import Domain.Entities.Operaciones.CriterioOperacion;
import Domain.Entities.Organizacion.*;
@Entity
@DiscriminatorValue("administrador")
public class Administrador extends Usuario{

	@OneToMany(mappedBy = "administrador")
	List<EntidadJuridica> juridicas;
	
	public Administrador(){
		this.juridicas= new ArrayList<>();
	}
	
    public void asignarJerarquia(CriterioOperacion criterioPadre, CriterioOperacion criterioHijo){
        criterioPadre.setCriterioHijo(criterioHijo);
        criterioHijo.setCriterioPadre(criterioPadre);
    }
}
