package ValidadorTransparencia;

import Operaciones.Egreso;

public class ValidarCantidadMinima extends ValidacionDeTransparencia{
	private int cantidadMinimaDePresupuestos;

	public ValidarCantidadMinima(int cantidadMinimaDePresupuestos) {
		this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
	}
	
	public boolean validarEgreso(Egreso egreso){
		
		//mostrar el resultado de la validacion en la bandeja de mensajes
		return egreso.getPresupuestos().size()>=cantidadMinimaDePresupuestos;
	}
	
}
