package ValidadorTransparencia;

import Operaciones.Egreso;
import Operaciones.Presupuesto;

public class ValidarCantidadMinima extends ValidacionDeTransparencia{
	private int cantidadMinimaDePresupuestos;

	public ValidarCantidadMinima(int cantidadMinimaDePresupuestos) {
		this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
	}
	
	public boolean validarEgreso(Egreso egreso, Presupuesto presupuesto){
		if(egreso.getPresupuestos().size() >= this.cantidadMinimaDePresupuestos) {
			return true;
		}
		else {
			egreso.getRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
					crearMensaje("El egreso: %d no paso ValidarCantidadMinima, cantidad minima de presupuestos requerida es %d y se tiene %d", egreso.getOperacionNumero(), cantidadMinimaDePresupuestos, egreso.getPresupuestos().size()));
			return false;
		}
	}
}
