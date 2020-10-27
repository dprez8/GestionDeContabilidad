package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;

import java.util.Comparator;
import java.util.stream.Collectors;

public class ValidarMenorValor extends ValidacionDeTransparencia {
	@Override
	public String validarEgreso(Egreso egreso) {
		
		String cuerpo;
		Presupuesto presupuestoObtenido = egreso.getPresupuestos().stream()
											.filter(unPresupuesto->
												validarPresupuesto(egreso,unPresupuesto))
											.findAny()
											.orElse(null);

		Presupuesto menor = egreso.getPresupuestos().stream().min(Comparator.comparing(Presupuesto::getValorTotal)).get();

		if(menor.equals(presupuestoObtenido)){
			cuerpo = "El presupuesto elegido es el de menor valor.";
			return cuerpo;
		}		
			cuerpo = "El presupuesto elegido no es el de menor valor.";
			return cuerpo;		
	}
}
