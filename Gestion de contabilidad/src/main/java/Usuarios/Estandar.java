package Usuarios;

import Operaciones.Egreso; //Problema: Estandar importa de Egreso y Egreso de Usuario (solventar)
import Organizacion.Organizacion;

public class Estandar extends Usuario{
	
	private Organizacion miOrganizacion;//conoce su organizacion

	public Estandar(Organizacion unaOrganizacion){
		this.miOrganizacion = unaOrganizacion;
	}

	public void darAltaEgreso(Egreso egreso) {
		
		//ingresar a la base de datos el egreso
		miOrganizacion.AddOperacion(egreso);
	}
	
}

