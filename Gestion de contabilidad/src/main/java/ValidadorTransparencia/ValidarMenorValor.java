package ValidadorTransparencia;

import Operaciones.Egreso;
import Operaciones.Presupuesto;

import java.util.Comparator;

public class ValidarMenorValor extends ValidacionDeTransparencia{
	@Override
	public boolean validarEgreso(Egreso egreso, Presupuesto presupuesto) {
		if(egreso
				.getPresupuestos()
				.stream()
				.min(Comparator.comparing(Presupuesto::getValorTotal))
				.get()
				.equals(presupuesto)
		){
//			egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
//					crearMensaje(String.format("El egreso: %d y el presupuesto: %d paso ValidarMenorValor", egreso.getOperacionNumero(), presupuesto.getOperacionNumero())));
			return true;
		}
		else{
			egreso
					.getRevisores()
					.forEach(revisor -> revisor.getBandejaDeMensajes().crearMensaje("El egreso: %d y el presupuesto: %d no paso ValidarMenorValor", egreso.getOperacionNumero(), presupuesto.getOperacionNumero()));
			return false;
		}
	}
}
