package ValidadorDeTransparencia;


import Operaciones.Egreso;

public class ValidarConPresupuesto extends ValidacionDeTransparencia {

	@Override
	public boolean validarEgreso(Egreso egreso){
		//mostrar el resultado de la validacion en la bandeja de mensajes
		
		boolean coincide=coincidePresupuesto(egreso);
		
		if(coincide){
			egreso.pasoValidacion();
			return true;
		}
		else
			return false;
	}
	
}
