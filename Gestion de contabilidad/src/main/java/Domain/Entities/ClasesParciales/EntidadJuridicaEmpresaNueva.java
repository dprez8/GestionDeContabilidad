package Domain.Entities.ClasesParciales;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.gson.annotations.Expose;

import Domain.Entities.CategorizadorDeEmpresas.CategoriaEmpresa;
import Domain.Entities.CategorizadorDeEmpresas.Sector;

public class EntidadJuridicaEmpresaNueva {
	public String nombreFicticio;
	public String razonSocial;
	public String cuit;
	public int codigoDeInscripcionDefinitivaEnIGJ;
	public int pais;
    public int provincia;
    public int ciudad;
    public String calle;
    public int altura;
    public String piso;
    public int zipcode;
    public int cantidadDePersonal;
    public double ventasAnuales;
    public String actividad;
    public int sectorId;

}
