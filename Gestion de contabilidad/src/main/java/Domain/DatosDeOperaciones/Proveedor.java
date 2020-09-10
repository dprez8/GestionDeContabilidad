package Domain.DatosDeOperaciones;

import Domain.DireccionPostal.DireccionPostal;


public class Proveedor {
	private String nombre;
	private int documento;
	private DireccionPostal direccion;
	
	
	public Proveedor(String nombre, int documento, DireccionPostal direccion) {
		this.nombre = nombre;
		this.documento = documento;
		this.direccion = direccion;
	}

	/**Setters & Getters*/

	public String getNombre(){
		return nombre;
	}
}

