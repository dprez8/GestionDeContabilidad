package ValidadorDeTransparencia;

import java.util.ArrayList;
import java.util.Collections;
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
        validaciones.forEach(validador->validador.validarEgreso(egreso));
        // Retorna la sumatoria del precio de cada item del egreso
    }
	
	
	public void cargarValidacion(ValidacionDeTransparencia ... nuevasValidaciones){
		Collections.addAll(this.validaciones, nuevasValidaciones);
	}
}
