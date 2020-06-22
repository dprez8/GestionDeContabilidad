package Usuarios;

import BandejaDeMensajes.BandejaDeMensajes;
import DatosDeOperaciones.DocumentoComercial;
import DatosDeOperaciones.ItemEgreso;
import DatosDeOperaciones.MedioDePago;
import DatosDeOperaciones.Proveedor;
import Operaciones.Egreso; // Problema: Estandar importa de Egreso y Egreso de Usuario (solventar)
import Organizacion.*;

import java.util.Date;

public class Estandar extends Usuario {
	
	private EntidadJuridica miOrganizacion; // Conoce su organizacion
	private BandejaDeMensajes bandejaDeMensajes;

	public Estandar(EntidadJuridica unaOrganizacion,String nombre,String contrasenia, String mail){
		this.usuario = nombre;
		this.contrasenia = contrasenia;
		this.mail = mail;
		this.miOrganizacion = unaOrganizacion;
		this.bandejaDeMensajes = new BandejaDeMensajes();
	}

	public void crearMensaje(Date fecha, String cuerpo){
		this.bandejaDeMensajes.crearMensaje(fecha, cuerpo);
	}

	public Egreso darAltaEgreso(DocumentoComercial documentoComercial, MedioDePago medioDePago,
							  Proveedor proveedor, ItemEgreso ... items) {
		// Ingresar a la base de datos el egreso
		Egreso egreso = new Egreso(documentoComercial, medioDePago, proveedor, this.miOrganizacion, items);

		miOrganizacion.agregarOperaciones(egreso);
		return egreso;
	}
}

