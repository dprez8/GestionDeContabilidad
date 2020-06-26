package Usuarios;

import BandejaDeMensajes.BandejaDeMensajes;
import DatosDeOperaciones.DocumentoComercial;
import DatosDeOperaciones.ItemEgreso;
import DatosDeOperaciones.MedioDePago;
import DatosDeOperaciones.Proveedor;
import Operaciones.Egreso; // Problema: Estandar importa de Egreso y Egreso de Usuario (solventar)
import Organizacion.*;

import java.util.Date;

// Revisar esta clase y revisar los usuarios en general, quizas la clase Estandar no sirva para nada

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

	public Egreso darAltaEgreso(int operacionNumero, DocumentoComercial documentoComercial, MedioDePago medioDePago,
							  Proveedor proveedor, ItemEgreso ... items) {
		// Ingresar a la base de datos el egreso
		Egreso egreso = new Egreso(operacionNumero, documentoComercial, medioDePago, proveedor, this.miOrganizacion, items);

		miOrganizacion.agregarOperaciones(egreso);
		return egreso;
		// ObjetoQueSeEncargaDeCrearCosas.CrearEgreso();
	}

	public BandejaDeMensajes getBandejaDeMensajes() {
		return bandejaDeMensajes;
	}
}

