package Domain.ValidadorTransparencia;

import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;

public class ValidarCantidadMinima extends ValidacionDeTransparencia {

	private int cantidadMinimaDePresupuestos;

	public ValidarCantidadMinima(int cantidadMinimaDePresupuestos) {
		this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
	}
	
	public void validarEgreso(Egreso egreso) {
		if (egreso.getPresupuestos().size() >= this.cantidadMinimaDePresupuestos) {
			egreso.getPresupuestos().forEach(presupuesto -> presupuesto.incrementValidado());
		}
		else {
			egreso.getRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
					crearMensaje("El egreso: %d no paso ValidarCantidadMinima, cantidad minima de presupuestos requerida es %d y se tiene %d", egreso.getOperacionNumero(), cantidadMinimaDePresupuestos, egreso.getPresupuestos().size()));
		}
	}
}
