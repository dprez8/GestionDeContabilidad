package Domain.Entities.Organizacion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.*;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Usuarios.Estandar;

import javax.persistence.*;

@Entity
@Table(name = "organizacion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Organizacion extends EntidadPersistente {

	@Column(name = "nombre_ficticio")
    protected String nombreFicticio;

	@Transient
    //@OneToMany(mappedBy = "organizacion",cascade = CascadeType.ALL) //del otro lado es ManyToOne
    protected List<Operacion> operaciones;

	@OneToMany(mappedBy = "miOrganizacion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	protected List<Estandar> usuarios;


    public Organizacion(){
    	this.operaciones = new ArrayList<>();
    	this.usuarios 	 = new ArrayList<>();
	}

	/****************Setters & Getters***************************/
	public void addOperaciones (Operacion ... unasOperaciones) {
        Collections.addAll(this.operaciones, unasOperaciones);
    }

	public List<Operacion> getOperaciones() {
		return operaciones;
	}

	/**Obtengo la lista de egresos que son subtipos de operaciones */
	public List<Egreso> getEgresos() {
		return operaciones.stream()
				.filter(Egreso.class::isInstance)
				.map(Egreso.class::cast)
				.collect(Collectors.toList());
	}

	public List<Estandar> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Estandar> usuarios) {
		this.usuarios = usuarios;
	}

	public String getNombreFicticio() {
		return nombreFicticio;
	}

	public void setNombreFicticio(String nombreFicticio) {
		this.nombreFicticio = nombreFicticio;
	}

}