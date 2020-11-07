package Domain.Entities.ValidadorTransparencia;

import java.util.*;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.Egreso.Egreso;

import javax.persistence.*;

@Entity
@Table(name = "validador_de_transparencia")
public class ValidadorDeTransparencia extends EntidadPersistente {

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinTable(name = "validador_x_validaciones")
	private List<ValidacionDeTransparencia> validaciones;

	public ValidadorDeTransparencia(ValidacionDeTransparencia ... unasValidaciones) {
		this.validaciones = new ArrayList<>();
		addValidaciones(unasValidaciones);
	}

	protected ValidadorDeTransparencia(){
		this.validaciones = new ArrayList<>();
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
