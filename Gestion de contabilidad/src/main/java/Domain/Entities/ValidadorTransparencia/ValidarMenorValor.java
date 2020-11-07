package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Comparator;

@Entity
@DiscriminatorValue("validacion_menor_valor")
public class ValidarMenorValor extends ValidacionDeTransparencia {

	@Override
	public String validarEgreso(Egreso egreso) {

		if(egreso.getPresupuestos().isEmpty()){
			return "El egreso no tiene presupuestos asociados";
		}

		String cuerpo;
		if(egreso.getPresupuestos().size() == 0)
			return "El egreso no tiene presupuestos";

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
