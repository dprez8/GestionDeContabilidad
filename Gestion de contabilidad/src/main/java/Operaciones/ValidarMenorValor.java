package Operaciones;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import Operaciones.Presupuesto;

public class ValidarMenorValor extends ValidacionDeTransparencia{

	public boolean validarEgreso(Egreso egreso){
		
		Presupuesto presupuestoCoincide;
		
		if(coincidePresupuesto(egreso)) { //si existe un presupuesto que coincida se procede
			
			double minimoPresupuesto=1000000000;
			
			for (Presupuesto presupuesto : egreso.getPresupuestos()) {
		       if(presupuesto.total()>=minimoPresupuesto)
		    	   minimoPresupuesto=presupuesto.total();		
			}
			if(minimoPresupuesto>=obtenerPresupuestoElegido(egreso).total()) {
				egreso.pasoValidacion();
				return true;}
		}
		//mostrar el resultado de la validacion en la bandeja de mensajes
		return false;
	}
}
