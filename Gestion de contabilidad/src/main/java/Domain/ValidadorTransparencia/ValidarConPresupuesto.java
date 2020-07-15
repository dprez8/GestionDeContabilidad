package Domain.ValidadorTransparencia;


import Domain.DatosDeOperaciones.ItemEgreso;
import Domain.DatosDeOperaciones.ItemPresupuesto;
import Domain.Operaciones.Egreso;
import Domain.Operaciones.Presupuesto;

public class ValidarConPresupuesto extends ValidacionDeTransparencia {

	private String msg = "El egreso %d no pudo ser validado, no coinciden proveedor, " +
			"items ni documento comercial del egreso con ningun presupuesto";
	@Override
	public void validarEgreso(Egreso egreso) {
		boolean validado = egreso.getPresupuestos().stream().anyMatch(presupuesto -> validarPresupuesto(egreso, presupuesto));
		if (!validado)
			egreso.getRevisores().forEach(revisor ->
					revisor.getBandejaDeMensajes().crearMensaje(msg, egreso.getOperacionNumero()));
	}

	private boolean validarPresupuesto(Egreso egreso, Presupuesto presupuesto) {
		if (coincideProveedor(egreso, presupuesto) &&
			coincideItems(egreso, presupuesto) &&
			coincideDocumentoComercial(egreso, presupuesto))
		{
			presupuesto.incrementValidado();
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
