package Domain.ValidadorTransparencia;

import java.util.*;

import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;

public class ValidadorDeTransparencia {
	

	private List<ValidacionDeTransparencia> validaciones;
	
	
	public List<ValidacionDeTransparencia> getValidaciones() {
		return validaciones;
	}


	public ValidadorDeTransparencia() {
		this.validaciones = new ArrayList<>();
	}

	public void validarEgreso (Egreso egreso) {
		validaciones.forEach(validador -> validador.validarEgreso(egreso));
		boolean resultadoDeValidacion = egreso.getPresupuestos().stream().anyMatch(presupuesto -> presupuesto.getValidado() == validaciones.size());
		if (resultadoDeValidacion) {
			egreso.getRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
					crearMensaje("Paso todas las validaciones, el egreso: %d", egreso.getOperacionNumero()));
		} else {
			egreso.getRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
					crearMensaje("Fallo la validacion del egreso %d. No hay presupuestos que hayan pasado todas las validaciones", egreso.getOperacionNumero()));
		}
        egreso.setValidado(true);
	}

//	public void mandaMensajeCuandoFallaValidacion(Egreso egreso) {
//		ListIterator<ValidacionDeTransparencia> listIteratorValidaciones = validaciones.listIterator();
//		ListIterator<Presupuesto> listIteratorPresupuestos = egreso.getPresupuestos().listIterator();
//		boolean estaValidadandoBien = true;
//		while (listIteratorPresupuestos.hasNext()) {
//			Presupuesto unPresupuesto = listIteratorPresupuestos.next();
//			while (listIteratorValidaciones.hasNext() && estaValidadandoBien) {        /**dejo de validar con este presupuesto apenas me falla un validador**/
//				ValidacionDeTransparencia unValidador = listIteratorValidaciones.next();
//				estaValidadandoBien = unValidador.validarEgreso(egreso, unPresupuesto);
//			}
//		}
//	}

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

	public void addValidaciones(ValidacionDeTransparencia ... nuevasValidaciones) {
		Collections.addAll(this.validaciones, nuevasValidaciones);
	}
}
