package ValidadorTransparencia;

import Operaciones.Egreso;

public class ValidarCantidadMinima extends ValidacionDeTransparencia{
	private int cantidadMinimaDePresupuestos;

	public ValidarCantidadMinima(int cantidadMinimaDePresupuestos) {
		this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
	}
	
	public boolean validarEgreso(Egreso egreso){
		return egreso.getPresupuestos().size()>=this.cantidadMinimaDePresupuestos;
	}
	
}
