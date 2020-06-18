package ValidadorDeTransparencia;


import Operaciones.Egreso;

public class ValidarConPresupuesto extends ValidacionDeTransparencia {

	
	public boolean validarEgreso(Egreso egreso){
		//mostrar el resultado de la validacion en la bandeja de mensajes
		return coincidePresupuesto(egreso);
	}
	
}
