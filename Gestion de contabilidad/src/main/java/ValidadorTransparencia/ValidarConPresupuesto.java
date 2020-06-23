package ValidadorTransparencia;


import DatosDeOperaciones.ItemEgreso;
import DatosDeOperaciones.ItemPresupuesto;
import Operaciones.Egreso;
import Operaciones.Presupuesto;

public class ValidarConPresupuesto extends ValidacionDeTransparencia {

	@Override
	public boolean validarEgreso(Egreso egreso, Presupuesto presupuesto){
		if(coincideProveedor(egreso,presupuesto) && coincideItems(egreso,presupuesto) && coincideDocumentoComercial(egreso,presupuesto)) {
			egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
					crearMensaje(String.format("El egreso: %d y el presupuesto: %d paso ValidarConPresupuesto", egreso.getOperacionNumero(), presupuesto.getOperacionNumero())));
			return true;
		}
		else {
			egreso.obtenerRevisores().forEach(revisor -> revisor.getBandejaDeMensajes().
					crearMensaje(String.format("El egreso: %d y el presupuesto: %d no paso ValidarConPresupuesto", egreso.getOperacionNumero(), presupuesto.getOperacionNumero())));
			return false;
		}
	}

	private boolean coincideProveedor(Egreso egreso,Presupuesto presupuesto){
		return egreso.getProveedor().equals(presupuesto.getProveedor());
	}

	private boolean coincideItems(Egreso egreso, Presupuesto presupuesto){
		return egreso.getItems()
				.stream()
				.allMatch(unItem->presupuesto.getItems()
						.stream()
						.anyMatch(unItemPresupuesto->itemCoincide(unItem,unItemPresupuesto)));
	}

	private boolean itemCoincide(ItemEgreso item, ItemPresupuesto itemPresupuesto){
		return itemPresupuesto.getItemEgresoAsociado().equals(item)
				&& itemPresupuesto.valorTotal() == item.valorTotal()
				&& itemPresupuesto.getProducto().equals(item.getProducto());
	}

	private boolean coincideDocumentoComercial(Egreso egreso, Presupuesto presupuesto){
		return egreso.getDocumento().equals(presupuesto.getDocumento());
	}

}
