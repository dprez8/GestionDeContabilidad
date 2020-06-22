package Organizacion;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import Operaciones.*;

public abstract class Organizacion {
    protected String nombreFicticio;
    protected List<Operacion> operaciones; 
    protected List<Egreso> egresos;//no esta bien pero para simular el scheduler. Trato de a partir de la lista de operaciones
    								//poder obtener unicamente los Egresos.
	
	public void agregarOperaciones (Operacion ... unasOperaciones) {
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

	public Stream<Egreso> egresosNoVerificados(){
		return getEgresos().stream().filter(egresoAValidar->!egresoAValidar.fueVerificado());
	}

}