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
        	egreso.validar();
        egreso.yaVerificado();
    }
	
	public Stream<Egreso> egresosNoVerificados (Egreso ... egreso) {
		return Arrays.asList(egreso).stream().filter(elementoEgreso->!egresoVerificado(elementoEgreso));
    }
	
	public boolean egresoVerificado(Egreso egreso){
		return egreso.fueVerificado();
	}
	
	public void cargarValidacion(ValidacionDeTransparencia ... nuevasValidaciones){
		Collections.addAll(this.validaciones, nuevasValidaciones);
	}
}
