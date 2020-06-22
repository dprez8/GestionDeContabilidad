package ValidadorDeTransparencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import Operaciones.Egreso;

public class ValidadorDeTransparencia {
	

	private List<ValidacionDeTransparencia> validaciones = new ArrayList<>();
	
	
	public List<ValidacionDeTransparencia> getValidaciones() {
		return validaciones;
	}


	public ValidadorDeTransparencia(ValidacionDeTransparencia validations) {
		cargarValidacion(validations);
	}
	
	
	public void validarEgreso (Egreso egreso) {

        boolean resultadoDeValidacion = validaciones.stream().allMatch(validador->validador.validarEgreso(egreso));

        if(resultadoDeValidacion){
			egreso.obtenerRevisores().forEach(revisor -> revisor.crearMensaje(new Date(), "Se validó correctamente el egreso"));
		} else {
			egreso.obtenerRevisores().forEach(revisor -> revisor.crearMensaje(new Date(), "No se validó el egreso"));
		}
    }
	
	
	public void cargarValidacion(ValidacionDeTransparencia ... nuevasValidaciones){
		Collections.addAll(this.validaciones, nuevasValidaciones);
	}
}
