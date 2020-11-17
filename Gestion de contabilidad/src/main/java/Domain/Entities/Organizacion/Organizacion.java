package Domain.Entities.Organizacion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Entities.ApiVinculador.ConfiguracionVinculador;
import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.*;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Usuarios.Estandar;
import Domain.Entities.ValidadorTransparencia.SchedulerInit;
import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

@Entity
@Table(name = "organizacion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Organizacion extends EntidadPersistente {

	@Expose
	@Column(name = "nombre")
    protected String nombre;

    @OneToMany(mappedBy = "organizacion",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER) //del otro lado es ManyToOne
    protected List<Operacion> operaciones;

	@OneToMany(mappedBy = "miOrganizacion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	protected List<Estandar> usuarios;

	@OneToOne(mappedBy = "organizacion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	protected SchedulerInit schedulerInit;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="configuracion_vinculador_id", referencedColumnName = "id")
	private ConfiguracionVinculador configuracionVinculador;

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setOperaciones(List<Operacion> operaciones) {
		this.operaciones = operaciones;
	}

	public SchedulerInit getSchedulerInit() {
		return schedulerInit;
	}

	public void setSchedulerInit(SchedulerInit schedulerInit) {
		this.schedulerInit = schedulerInit;
	}

	public ConfiguracionVinculador getConfiguracionVinculador() {
		return configuracionVinculador;
	}

	public void setConfiguracionVinculador(ConfiguracionVinculador configuracionVinculador) {
		this.configuracionVinculador = configuracionVinculador;
	}
}