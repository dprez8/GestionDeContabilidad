package Domain.Organizacion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import Domain.Operaciones.*;
import Domain.Operaciones.Egreso.Egreso;

import javax.persistence.*;

@Entity
@Table(name = "organizacion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Organizacion extends EntidadPersistente{

	@Column(name = "nombre_ficticio")
    protected String nombreFicticio;

    @OneToMany(mappedBy = "organizacion",cascade = CascadeType.ALL) //del otro lado es ManyToOne
    protected List<Operacion> operaciones;

    public Organizacion(){
    	this.operaciones = new ArrayList<>();
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
}