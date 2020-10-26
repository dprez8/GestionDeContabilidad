package Domain.Entities.DatosDeOperaciones;

import javax.persistence.*;

import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import com.google.gson.annotations.Expose;

@Entity
@Table(name="proveedor")
public class Proveedor {
	@Expose
	@Id
	@GeneratedValue
	@Column(name="proveedor_id")
	private int proveedorId;

	@Expose
	@Column(name="documento_proveedor")
	private int documento;

	@Expose
	@Column
	private String nombre;

	@Expose
	@ManyToOne
	@JoinColumn(name="pais_id", referencedColumnName = "pais_id")
	private Pais pais;

	@Expose
	@ManyToOne
	@JoinColumn(name="provincia_id", referencedColumnName = "provincia_id")
	private Provincia provincia;

	@Expose
	@ManyToOne
	@JoinColumn(name="ciudad_id", referencedColumnName = "ciudad_id")
	private Ciudad ciudad;

	@Expose
	@Column
	private String calle;

	@Expose
	@Column
	private int altura;

	@Expose
	@Column
	private String piso;

	@Expose
	@Column
	private int zipcode;

	protected Proveedor() {
	}

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

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
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

	public int getProveedorId() {
		return proveedorId;
	}

	public void setProveedorId(int proveedorId) {
		this.proveedorId = proveedorId;
	}
}

