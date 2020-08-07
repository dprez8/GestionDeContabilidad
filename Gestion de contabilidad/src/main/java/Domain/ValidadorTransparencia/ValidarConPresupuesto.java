package Domain.ValidadorTransparencia;


import Domain.DatosDeOperaciones.ItemEgreso;
import Domain.DatosDeOperaciones.ItemPresupuesto;
import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;

public class ValidarConPresupuesto extends ValidacionDeTransparencia {

	@Override
	public String validarEgreso(Egreso egreso) {
		String cuerpo;
		Presupuesto presupuestoElegido = egreso.getPresupuestos().stream()
											.filter(unPresupuesto->
												validarPresupuesto(egreso,unPresupuesto))
													.collect(Collectors.toList())
														.get(0);

		if (presupuestoElegido!=NULL){
			cuerpo = "Se validó el egreso con uno de los presupuestos.";
			presupuestoElegido.incrementValidado();
			}
		else 
			cuerpo = "No se validó el egreso con alguno de los presupuesto.";
		
		return cuerpo;
	}
}
