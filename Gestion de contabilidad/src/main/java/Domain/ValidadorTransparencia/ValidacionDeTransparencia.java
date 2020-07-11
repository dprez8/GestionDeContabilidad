package Domain.ValidadorTransparencia;

import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;

public abstract class ValidacionDeTransparencia {

	public abstract boolean validarEgreso(Egreso egreso, Presupuesto presupuesto);

}

