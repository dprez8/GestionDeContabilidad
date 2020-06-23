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
		ListIterator<ValidacionDeTransparencia> listIteratorValidaciones = validaciones.listIterator();
		ListIterator<Presupuesto> listIteratorPresupuestos = egreso.getPresupuestos().listIterator();
		boolean estaValidadandoBien = true;
		while(listIteratorPresupuestos.hasNext()) {
			Presupuesto unPresupuesto = listIteratorPresupuestos.next();
			while (listIteratorValidaciones.hasNext() && estaValidadandoBien) {		/**dejo de validar con este presupuesto apenas me falla un validador**/
				ValidacionDeTransparencia unValidador = listIteratorValidaciones.next();
				estaValidadandoBien = unValidador.validarEgreso(egreso, unPresupuesto);
			}
			if (estaValidadandoBien) {		/**si me da true significa que unPresupuesto paso todas las validaciones entonces deja de validar**/
				egreso.setValidado(true);
				egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
						crearMensaje(String.format("Paso todas las validaciones, el egreso: %d", egreso.getOperacionNumero())));
				break;
			}
		}


//		boolean resultadoDeValidacion = egreso.getPresupuestos().stream().anyMatch(
//				unPresupuesto -> validaciones.stream().allMatch(validador->validador.validarEgreso(egreso, unPresupuesto))
//		);
//        if(resultadoDeValidacion){
//        	egreso.setValidado(true);
//			egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().crearMensaje("Se validó correctamente el egreso"));
//		} else {
//			egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().crearMensaje("No se validó el egreso"));
//		}
    }
	
	
	public void cargarValidacion(ValidacionDeTransparencia ... nuevasValidaciones){
		Collections.addAll(this.validaciones, nuevasValidaciones);
	}
}
