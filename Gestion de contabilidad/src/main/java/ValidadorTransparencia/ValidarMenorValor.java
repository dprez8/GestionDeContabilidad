package ValidadorTransparencia;

import Operaciones.Egreso;
import Operaciones.Presupuesto;

import java.util.Comparator;

public class ValidarMenorValor extends ValidacionDeTransparencia{
	@Override
	public boolean validarEgreso(Egreso egreso, Presupuesto presupuesto){
		return egreso.getPresupuestos().stream().min(Comparator.comparing(Presupuesto::valorTotal)).get().equals(presupuesto);
	}
}
