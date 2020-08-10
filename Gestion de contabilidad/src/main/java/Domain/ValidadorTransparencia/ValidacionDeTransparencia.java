package Domain.ValidadorTransparencia;

import Domain.Operaciones.Egreso.Egreso;
import Domain.Operaciones.Presupuesto;
import Domain.DatosDeOperaciones.*;

public abstract class ValidacionDeTransparencia {

	public abstract String validarEgreso(Egreso egreso);

	protected boolean validarPresupuesto(Egreso egreso, Presupuesto presupuesto) {
	if (coincideProveedor(egreso, presupuesto) &&
			coincideItems(egreso, presupuesto) &&
			coincideDocumentoComercial(egreso, presupuesto))
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
				&& itemPresupuesto.getProducto().equals(item.getProducto());
	}

	private boolean coincideDocumentoComercial(Egreso egreso, Presupuesto presupuesto) {
		return egreso.getDocumento().equals(presupuesto.getDocumento());
	}
}

