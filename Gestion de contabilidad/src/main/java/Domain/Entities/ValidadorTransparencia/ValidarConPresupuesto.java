package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("validacion_con_presupuesto")
public class ValidarConPresupuesto extends ValidacionDeTransparencia {

	@Override
	public String validarEgreso(Egreso egreso) {
		String cuerpo;
		if(egreso.getPresupuestos().size() == 0)
			return "El egreso no tiene presupuestos";

		Presupuesto presupuestoElegido = egreso.getPresupuestos().stream()
											.filter(unPresupuesto->
												validarPresupuesto(egreso,unPresupuesto))
												.findAny()
												.orElse(null);

		if (presupuestoElegido != null){
			cuerpo = "Se validó el egreso con uno de los presupuestos.";
			}
		else 
			cuerpo = "No se validó el egreso con alguno de los presupuesto.";
		
		return cuerpo;
	}
}
