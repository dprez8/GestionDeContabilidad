package ValidadorTransparencia;

import DatosDeOperaciones.ItemEgreso;
import DatosDeOperaciones.ItemPresupuesto;
import Operaciones.Egreso;
import Operaciones.Presupuesto;

public abstract class ValidacionDeTransparencia {

	public abstract boolean validarEgreso(Egreso egreso);

	public boolean coincidePresupuesto(Egreso egreso){
		return egreso.getPresupuestos().stream().anyMatch(presupuesto->this.compararEgresoPresupuesto(egreso, presupuesto));
	}

	public Presupuesto obtenerPresupuestoElegido(Egreso egreso){
		
		for (Presupuesto presupuesto : egreso.getPresupuestos()) {
		        if (this.compararEgresoPresupuesto(egreso, presupuesto)) {  
		        	return presupuesto;
		        } 
		}
		return null;
	}

	public boolean compararEgresoPresupuesto(Egreso egreso,Presupuesto presupuesto){
	
		int datosIguales =0;
		
		for(int i=0;i>egreso.getItems().size();i++){
			ItemEgreso itemEgreso=egreso.getItems().get(i);
			ItemPresupuesto itemPresupuesto=presupuesto.getItems().get(i);
	
			if ((itemEgreso.getPrecio() == itemPresupuesto.getPrecio()) && 
				(itemEgreso.getCantidad() == itemPresupuesto.getCantidad()) &&
				(itemEgreso.getProducto().equals(itemPresupuesto.getProducto())))
				datosIguales++;
		}	
		
		if(datosIguales==egreso.getItems().size())
			return true;
		else
			return  false;
  
	}
}

