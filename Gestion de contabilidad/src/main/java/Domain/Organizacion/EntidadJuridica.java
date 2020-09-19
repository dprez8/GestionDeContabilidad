package Domain.Organizacion;

import java.util.ArrayList;
import java.util.List;

import Domain.ApiPaises.Ciudad;
import Domain.ApiPaises.Pais;
import Domain.ApiPaises.Provincia;
import Domain.Usuarios.Estandar;

public class EntidadJuridica extends Organizacion {
    private String razonSocial;
    private int cuit;
    private int codigoDeInscripcionDefinitivaEnIGJ;
    private List<EntidadBase> entidadesBase;
    private Estandar usuario;
    private CategoriaEntidadJuridica tipoEntidadJuridica;
    
    private Pais pais;
	private Provincia provincia;
	private String ciudad;
	private String calle;
	private int altura;
	private String piso;
	private int zipcode;
	

    public EntidadJuridica(String nombre,int cuit,String razonSocial,int codigoDeInscripcionDefinitivaEnIGJ){
        super(nombre);
        this.cuit = cuit;
        this.razonSocial = razonSocial;
        this.codigoDeInscripcionDefinitivaEnIGJ = codigoDeInscripcionDefinitivaEnIGJ;
        this.entidadesBase = new ArrayList<>();
    }

    /**Setters & Getters*/
    public void setUsuario(Estandar usuario) {
        this.usuario = usuario;
    }

    public void setTipoEntidadJuridica(CategoriaEntidadJuridica tipoEntidadJuridica){
        this.tipoEntidadJuridica = tipoEntidadJuridica;
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

}

