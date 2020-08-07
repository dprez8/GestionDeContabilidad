package Domain.ValidadorTransparencia;

import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;

import java.util.Comparator;

public class ValidarMenorValor extends ValidacionDeTransparencia {
	@Override
	public String validarEgreso(Egreso egreso) {
		
		String cuerpo;
		Presupuesto presupuestoObtenido = egreso.getPresupuestos().stream()
											.filter(unPresupuesto->
												validarPresupuesto(egreso,unPresupuesto))
													.collect(Collectors.toList())
														.get(0);
																		

		Presupuesto menor = egreso.getPresupuestos().stream().min(Comparator.comparing(Presupuesto::getValorTotal)).get();

		if(menor.equals(presupuestoObtenido)){
			cuerpo = "El presupuesto elegido es el de menor valor.";
			return cuerpo;
		}		
			cuerpo = "El presupuesto elegido no es el de menor valor.";
			return cuerpo;		
	}
}
