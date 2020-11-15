package Domain.Entities.Organizacion;

import java.util.ArrayList;
import java.util.List;

import Domain.Entities.ApiPaises.Ciudad;
import Domain.Entities.ApiPaises.Pais;
import Domain.Entities.ApiPaises.Provincia;
import Domain.Entities.Usuarios.Administrador;
import Domain.Entities.Usuarios.Estandar;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "entidad_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class EntidadJuridica extends Organizacion {
	@Expose
	@Column(name = "nombre_ficticio")
	private String nombreFicticio;

	@ManyToOne
	@JoinColumn(name = "administrador_id",referencedColumnName = "administrador_id")
	Administrador administrador;
	
	@Expose
	@Column(name = "razon_social")
    private String razonSocial;

	@Expose
	@Column(name = "cuit")
    private int cuit;

	@Expose
	@Column(name = "codigo_igj")
    private int codigoDeInscripcionDefinitivaEnIGJ;

	@OneToMany(mappedBy = "entidadJuridica",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EntidadBase> entidadesBase;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "tipo_entidad_id")
    private CategoriaEntidadJuridica tipoEntidadJuridica;

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
	@Column(name = "calle")
	private String calle;

	@Expose
	@Column(name = "altura")
	private int altura;

	@Expose
	@Column(name = "piso")
	private String piso;

	@Expose
	@Column(name = "zipcode")
	private int zipcode;

	public EntidadJuridica(){
        super();
        this.entidadesBase = new ArrayList<>();
    }

    /**Setters & Getters*/
    public void setTipoEntidadJuridica(CategoriaEntidadJuridica tipoEntidadJuridica){
        this.tipoEntidadJuridica = tipoEntidadJuridica;
    }

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public int getCuit() {
		return cuit;
	}

	public void setCuit(int cuit) {
		this.cuit = cuit;
	}

	public int getCodigoDeInscripcionDefinitivaEnIGJ() {
		return codigoDeInscripcionDefinitivaEnIGJ;
	}

	public void setCodigoDeInscripcionDefinitivaEnIGJ(int codigoDeInscripcionDefinitivaEnIGJ) {
		this.codigoDeInscripcionDefinitivaEnIGJ = codigoDeInscripcionDefinitivaEnIGJ;
	}

	public List<EntidadBase> getEntidadesBase() {
		return entidadesBase;
	}

	public void setEntidadesBase(List<EntidadBase> entidadesBase) {
		this.entidadesBase = entidadesBase;
	}

	public CategoriaEntidadJuridica getTipoEntidadJuridica() {
		return tipoEntidadJuridica;
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

}

