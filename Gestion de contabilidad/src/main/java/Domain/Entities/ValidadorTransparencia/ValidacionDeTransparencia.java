package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.DatosDeOperaciones.*;

import javax.persistence.*;


@Entity
@Table(name = "validacion_de_transparencia")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_de_validacion")
public abstract class ValidacionDeTransparencia extends EntidadPersistente {

	public abstract String validarEgreso(Egreso egreso);
	public String getIngresoString(Egreso egreso) {
		return 	"El egreso " + egreso.getId() + ", " + egreso.getFechaOperacion();
	}

	protected boolean validarPresupuesto(Egreso egreso, Presupuesto presupuesto) {
	if (coincideProveedor(egreso, presupuesto) &&
			coincideItems(egreso, presupuesto))
		{
			return true;
		}
	return false;
	}

	private boolean coincideProveedor(Egreso egreso, Presupuesto presupuesto) {
		return egreso.getProveedor().equals(presupuesto.getProveedor());
	}

	private boolean coincideItems(Egreso egreso, Presupuesto presupuesto) {
		return egreso.getItems()
				.stream()
				.allMatch(unItem->presupuesto.getItems()
						.stream()
						.anyMatch(unItemPresupuesto->itemCoincide(unItem,unItemPresupuesto)));
	}

	private boolean itemCoincide(ItemEgreso item, ItemPresupuesto itemPresupuesto) {
		return itemPresupuesto.getItemEgresoAsociado().equals(item)
				&& itemPresupuesto.valorTotal() == item.valorTotal()
				&& itemPresupuesto.getItem().equals(item.getItem());
	}

}

