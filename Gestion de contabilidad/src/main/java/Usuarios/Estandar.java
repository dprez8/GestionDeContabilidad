package Usuarios;

import DatosDeOperaciones.DocumentoComercial;
import DatosDeOperaciones.ItemEgreso;
import DatosDeOperaciones.MedioDePago;
import DatosDeOperaciones.Proveedor;
import Operaciones.Egreso; // Problema: Estandar importa de Egreso y Egreso de Usuario (solventar)
import Organizacion.*;

public class Estandar extends Usuario {
	
	private EntidadJuridica miOrganizacion; // Conoce su organizacion

	public Estandar(EntidadJuridica unaOrganizacion){
		this.miOrganizacion = unaOrganizacion;
	}

	public Egreso darAltaEgreso(DocumentoComercial documentoComercial, MedioDePago medioDePago,
							  Proveedor proveedor, ItemEgreso ... items) {
		// Ingresar a la base de datos el egreso
		Egreso egreso = new Egreso(documentoComercial, medioDePago, proveedor, this.miOrganizacion, items);

		miOrganizacion.addOperacion(egreso);
		return egreso;
	}
}

