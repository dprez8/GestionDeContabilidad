package ValidadorTransparencia;

import Operaciones.Egreso;
import Operaciones.Presupuesto;

public class ValidarCantidadMinima extends ValidacionDeTransparencia{
	private int cantidadMinimaDePresupuestos;

	public ValidarCantidadMinima(int cantidadMinimaDePresupuestos) {
		this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
	}
	
	public boolean validarEgreso(Egreso egreso, Presupuesto presupuesto){
		return egreso.getPresupuestos().size() >= this.cantidadMinimaDePresupuestos;
	}
	
}
