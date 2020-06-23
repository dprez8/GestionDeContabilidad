package ValidadorTransparencia;

import Operaciones.Egreso;
import Operaciones.Presupuesto;

public abstract class ValidacionDeTransparencia {

	public abstract boolean validarEgreso(Egreso egreso, Presupuesto presupuesto);

}

