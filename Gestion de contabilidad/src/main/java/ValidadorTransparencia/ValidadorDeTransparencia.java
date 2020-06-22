package ValidadorTransparencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import Operaciones.Egreso;

public class ValidadorDeTransparencia {
	

	private List<ValidacionDeTransparencia> validaciones = new ArrayList<>();
	
	
	public List<ValidacionDeTransparencia> getValidaciones() {
		return validaciones;
	}


	public ValidadorDeTransparencia(ValidacionDeTransparencia ... validations) {
		cargarValidacion(validations);
	}

	public void validarEgreso (Egreso egreso) {
		validaciones.forEach(validador->validador.validarEgreso(egreso));
        if(egreso.cantidadValidaciones()==validaciones.size()) //Si el egreso paso todas las validaciones no se vuelve a validar
        	egreso.validado();
        else
        	egreso.reiniciarValidaciones(); //esto permite volver a validar el egreso si no paso las pruebas
    }
	
	public Stream<Egreso> egresosNoValidados (Egreso ... egreso) {
		return Arrays.asList(egreso).stream().filter(elementoEgreso->!validado(elementoEgreso));
    }
	
	public boolean validado(Egreso egreso){
		return egreso.isValidado();
	}
	
	public void cargarValidacion(ValidacionDeTransparencia ... nuevasValidaciones){
		Collections.addAll(this.validaciones, nuevasValidaciones);
	}
}
