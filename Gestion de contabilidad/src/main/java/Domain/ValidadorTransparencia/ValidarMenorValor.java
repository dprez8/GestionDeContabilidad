package Domain.ValidadorTransparencia;

import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;

import java.util.Comparator;

public class ValidarMenorValor extends ValidacionDeTransparencia {
	@Override
	public void validarEgreso(Egreso egreso) {
		Presupuesto menor = egreso.getPresupuestos().stream().min(Comparator.comparing(Presupuesto::getValorTotal)).get();
		menor.incrementValidado();
	}
}
