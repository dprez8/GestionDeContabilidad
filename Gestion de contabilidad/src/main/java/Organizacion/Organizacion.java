package Organizacion;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import Operaciones.*;

public abstract class Organizacion {
    protected String nombreFicticio;
    protected List<Operacion> operaciones;

    public Organizacion(String nombreFicticio){
    	this.nombreFicticio = nombreFicticio;
    	operaciones = new ArrayList<>();
	}
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