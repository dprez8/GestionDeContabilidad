package ValidadorTransparencia;

import Operaciones.Egreso;
import Operaciones.Presupuesto;

public class ValidarMenorValor extends ValidacionDeTransparencia{
	@Override
	public boolean validarEgreso(Egreso egreso){


		if(coincidePresupuesto(egreso)) { //si existe un presupuesto que coincida se procede

			Double minimoPresupuesto = null;

			for (Presupuesto presupuesto : egreso.getPresupuestos()) {
				if(minimoPresupuesto == null){
					minimoPresupuesto = presupuesto.total();
				}
				else if(presupuesto.total()<=minimoPresupuesto) {
					minimoPresupuesto = presupuesto.total();
				}
			}
			if(minimoPresupuesto==obtenerPresupuestoElegido(egreso).total()) {
				egreso.pasoValidacion();
				return true;}		}
		//mostrar el resultado de la validacion en la bandeja de mensajes
		return false;
	}
}
