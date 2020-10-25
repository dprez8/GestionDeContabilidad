package Domain.Entities.ValidadorTransparencia;

import java.util.*;

import Domain.Entities.Operaciones.Egreso.Egreso;

public class ValidadorDeTransparencia {
	private List<ValidacionDeTransparencia> validaciones;

	public ValidadorDeTransparencia(ValidacionDeTransparencia ... unasValidaciones) {
		this.validaciones = new ArrayList<>();
		addValidaciones(unasValidaciones);
	}

	public void validarEgreso (Egreso egreso) {
		List<String> resultados = new ArrayList<>();
		validaciones.forEach(unaValidacion->
							 resultados.add(validarYConcatenarResultado(egreso,unaValidacion)));
		depositarEnLaBandeja(egreso,resultados);
		egreso.setValidado(true);
		resultados.clear();
	}

	private void depositarEnLaBandeja(Egreso egreso,List<String> mensajes){
		egreso.getRevisores()
				.forEach(revisor -> revisor
						.getBandejaDeMensajes()
						.addMensajesString(mensajes));
	}

	private String validarYConcatenarResultado(Egreso egreso, ValidacionDeTransparencia unaValidacion){
		return unaValidacion.validarEgreso(egreso) + " Número de egreso: " + egreso.getId();
	}
	public List<ValidacionDeTransparencia> getValidaciones() {
		return validaciones;
	}
	
	public void addValidaciones(ValidacionDeTransparencia ... nuevasValidaciones) {
		Collections.addAll(this.validaciones, nuevasValidaciones);
	}
}
