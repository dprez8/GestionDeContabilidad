package ValidadorTransparencia;

import java.util.*;

import Operaciones.Egreso;
import Operaciones.Presupuesto;

public class ValidadorDeTransparencia {
	

	private List<ValidacionDeTransparencia> validaciones = new ArrayList<>();
	
	
	public List<ValidacionDeTransparencia> getValidaciones() {
		return validaciones;
	}


	public ValidadorDeTransparencia(ValidacionDeTransparencia ... validations) {
		cargarValidacion(validations);
	}
	

	public void validarEgreso (Egreso egreso) {
		boolean resultadoDeValidacion = egreso.getPresupuestos().stream().anyMatch(
				unPresupuesto -> validaciones.stream().allMatch(validador->validador.validarEgreso(egreso, unPresupuesto)));
		if(resultadoDeValidacion){
			egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
					crearMensaje(String.format("Paso todas las validaciones, el egreso: %d", egreso.getOperacionNumero())));
		}
		else{
			mandaMensajeCuandoFallaValidacion(egreso);
		}
        egreso.setValidado(true);
	}

	public void mandaMensajeCuandoFallaValidacion(Egreso egreso) {
		ListIterator<ValidacionDeTransparencia> listIteratorValidaciones = validaciones.listIterator();
		ListIterator<Presupuesto> listIteratorPresupuestos = egreso.getPresupuestos().listIterator();
		boolean estaValidadandoBien = true;
		while (listIteratorPresupuestos.hasNext()) {
			Presupuesto unPresupuesto = listIteratorPresupuestos.next();
			while (listIteratorValidaciones.hasNext() && estaValidadandoBien) {        /**dejo de validar con este presupuesto apenas me falla un validador**/
				ValidacionDeTransparencia unValidador = listIteratorValidaciones.next();
				estaValidadandoBien = unValidador.validarEgreso(egreso, unPresupuesto);
			}
		}
	}

//	public void validarEgreso (Egreso egreso) { ///NO BORRAR, esta funcion te valida y guarda msj bien detallado
//		ListIterator<ValidacionDeTransparencia> listIteratorValidaciones = validaciones.listIterator();
//		ListIterator<Presupuesto> listIteratorPresupuestos = egreso.getPresupuestos().listIterator();
//		boolean estaValidadandoBien = true;
//		while(listIteratorPresupuestos.hasNext()) {
//			Presupuesto unPresupuesto = listIteratorPresupuestos.next();
//			while (listIteratorValidaciones.hasNext() && estaValidadandoBien) {		/**dejo de validar con este presupuesto apenas me falla un validador**/
//				ValidacionDeTransparencia unValidador = listIteratorValidaciones.next();
//				estaValidadandoBien = unValidador.validarEgreso(egreso, unPresupuesto);
//			}
//			if (estaValidadandoBien) {		/**si me da true significa que unPresupuesto paso todas las validaciones entonces deja de validar**/
//				egreso.setValidado(true);
//				egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
//						crearMensaje(String.format("Paso todas las validaciones, el egreso: %d", egreso.getOperacionNumero())));
//				break;
//			}
//		}
//    }
	
	
	public void cargarValidacion(ValidacionDeTransparencia ... nuevasValidaciones){
		Collections.addAll(this.validaciones, nuevasValidaciones);
	}
}
