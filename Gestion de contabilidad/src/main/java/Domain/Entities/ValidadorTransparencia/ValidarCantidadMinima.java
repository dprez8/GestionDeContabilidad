package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.Operaciones.Egreso.Egreso;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("validacion_cantidad_minima")
public class ValidarCantidadMinima extends ValidacionDeTransparencia {

	public ValidarCantidadMinima(){}
	
	public String validarEgreso(Egreso egreso){
		String cuerpo;
		if(egreso.getPresupuestos().isEmpty())
			return "El egreso no posee presupuestos asociados para la validacion.";
		if(egreso.getCantidadPresupuestos()==0){
			cuerpo = "Ningun presupuesto es requerido para su validacion.";
		}
		if(egreso.getPresupuestos().size() >= egreso.getCantidadPresupuestos() && egreso.getCantidadPresupuestos()!=0) {
			cuerpo = "Se validó con la cantidad minima de presupuestos.";
		}
		else {
			cuerpo = "No se validó con la cantidad minima de presupuestos requeridas por el sistema.";
		}
		return cuerpo;
	}
}
