package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.Operaciones.Egreso.Egreso;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("validacion_cantidad_minima")
public class ValidarCantidadMinima extends ValidacionDeTransparencia {

	@Column(name = "cantidad_minima_presupuestos")
	private int cantidadMinimaDePresupuestos;

	public ValidarCantidadMinima(int cantidadMinimaDePresupuestos) {
		this.cantidadMinimaDePresupuestos = cantidadMinimaDePresupuestos;
	}

	public ValidarCantidadMinima(){}
	
	public String validarEgreso(Egreso egreso){
		String cuerpo;
		if(egreso.getPresupuestos().isEmpty())
			return "El egreso no posee presupuestos asociados para la validacion";
		if(egreso.getPresupuestos().size() >= this.cantidadMinimaDePresupuestos) {
			cuerpo = "Se validó con la cantidad minima de presupuestos.";
		}
		else {
			cuerpo = "No se validó con la cantidad minima de presupuestos requeridas por el sistema.";
		}
		return cuerpo;
	}
}
