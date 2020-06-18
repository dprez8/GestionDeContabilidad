package Operaciones;

public class ValidarCantidadMinima extends ValidacionDeTransparencia{
	private int cantidadMinimaDePresupuestos;

	public ValidarCantidadMinima(int cantidadMinimaDePresupuestos) {
		this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
	}
	
	public boolean validarEgreso(Egreso egreso){
		
		//mostrar el resultado de la validacion en la bandeja de mensajes
		
		boolean tieneMinima= egreso.getPresupuestos().size()>=cantidadMinimaDePresupuestos;
	
		if(tieneMinima){
			egreso.pasoValidacion();
			return true;
		}
		else
			return false;
	}
	
}
