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
//			egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
//					crearMensaje(String.format("El egreso: %d paso ValidarCantidadMinima",egreso.getOperacionNumero())));
			return true;
		}
		else {
			egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
					crearMensaje(String.format("El egreso: %d no paso ValidarCantidadMinima, cantidad minima de presupuestos requerida es %d y se tiene %d", egreso.getOperacionNumero(), cantidadMinimaDePresupuestos, egreso.getPresupuestos().size())));
			return false;
		}
	}
}
