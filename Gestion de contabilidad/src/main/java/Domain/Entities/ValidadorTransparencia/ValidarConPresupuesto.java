package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("validacion_con_presupuesto")
public class ValidarConPresupuesto extends ValidacionDeTransparencia {

	@Transient
	private Repositorio<Egreso> repoEgresos;

	public ValidarConPresupuesto() {
		repoEgresos = new Repositorio<>(new DaoHibernate<>(Egreso.class));
	}

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

		if (presupuestoElegido != null) {
			cuerpo = "Se validó el egreso con uno de los presupuestos.";
			// actualizo el validador
			if (!egreso.getPresupuestoValidado().equals(presupuestoElegido)) {
				egreso.setPresupuestoValidado(presupuestoElegido);
				repoEgresos.modificar(egreso);
			}
		}
		else 
			cuerpo = "No se validó el egreso con alguno de los presupuesto.";
		
		return cuerpo;
	}
}
