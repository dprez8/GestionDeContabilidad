package Domain.Entities.Organizacion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.*;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.ValidadorTransparencia.Scheduler;

import javax.persistence.*;

@Entity
@Table(name = "organizacion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Organizacion extends EntidadPersistente {

	@Column(name = "nombre_ficticio")
    protected String nombreFicticio;


    @OneToMany(mappedBy = "organizacion",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER) //del otro lado es ManyToOne
    protected List<Operacion> operaciones;

	@OneToMany(mappedBy = "miOrganizacion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	protected List<Estandar> usuarios;

	@OneToOne(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	protected Scheduler scheduler;

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

	/**Obtengo la lista de ingresos que son subtipos de operaciones */
	public List<Ingreso> getIngresos() {
		return operaciones.stream()
				.filter(Ingreso.class::isInstance)
				.map(Ingreso.class::cast)
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

	public void setOperaciones(List<Operacion> operaciones) {
		this.operaciones = operaciones;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
}