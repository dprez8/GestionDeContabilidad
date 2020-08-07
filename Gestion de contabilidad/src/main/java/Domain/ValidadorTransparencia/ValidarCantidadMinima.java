package Domain.ValidadorTransparencia;

import Domain.Operaciones.Egreso;

public class ValidarCantidadMinima extends ValidacionDeTransparencia {

	private int cantidadMinimaDePresupuestos;

	public ValidarCantidadMinima(int cantidadMinimaDePresupuestos) {
		this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
	}
	
	public String validarEgreso(Egreso egreso){
		String cuerpo;
		if(egreso.getPresupuestos().size() >= this.cantidadMinimaDePresupuestos) {
			cuerpo = "Se validó con la cantidad minima de presupuestos.";
		}
		else {
			cuerpo = "No se validó con la cantidad minima de presupuestos requeridas por el sistema.";
		}
		return cuerpo;
	}
}
