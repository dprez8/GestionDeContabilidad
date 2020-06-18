package Organizacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import DatosDeOperaciones.ItemEgreso;
import Operaciones.*;
import Usuarios.Estandar;

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
	public List<Egreso> getEgresos() {
		return egresos;
	}
	
	public Stream<Egreso> egresosNoValidados(){
		return egresos.stream().filter(egresoAValidar->!egresoAValidar.isValidado());
	}
	
}