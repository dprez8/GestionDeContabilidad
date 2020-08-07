package Domain.ValidadorTransparencia;

import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;

import java.util.stream.Collectors;

public class ValidarConPresupuesto extends ValidacionDeTransparencia {

	@Override
	public String validarEgreso(Egreso egreso) {
		String cuerpo;
		Presupuesto presupuestoElegido = egreso.getPresupuestos().stream()
											.filter(unPresupuesto->
												validarPresupuesto(egreso,unPresupuesto))
													.collect(Collectors.toList())
														.get(0);

		if (presupuestoElegido != null){
			cuerpo = "Se validó el egreso con uno de los presupuestos.";
			}
		else 
			cuerpo = "No se validó el egreso con alguno de los presupuesto.";
		
		return cuerpo;
	}
}
