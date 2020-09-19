package Domain.DatosDeOperaciones;

import javax.persistence.*;

import Domain.ApiPaises.Ciudad;
import Domain.ApiPaises.Pais;
import Domain.ApiPaises.Provincia;
@Entity
@Table
public class Proveedor {
	@Id
	@Column(name="documento_proveedor")
	private int documento;
	@Column
	private String nombre;
	@ManyToOne
	private Pais pais;
	@ManyToOne
	private Provincia provincia;
	@Column
	private String ciudad;
	@Column
	private String calle;
	@Column
	private int altura;
	@Column
	private String piso;
	@Column
	private int zipcode;
	
	public Proveedor(String nombre, int documento) {
		this.nombre = nombre;
		this.documento = documento;
	}
	

	/**Setters & Getters*/

	
	public int getDocumento() {
		return documento;
	}

	public void setDocumento(int documento) {
		this.documento = documento;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre(){
		return nombre;
	}
}

