package ValidadorTransparencia;

import Operaciones.Egreso;
import Operaciones.Presupuesto;

public abstract class ValidacionDeTransparencia {

	public boolean validarEgreso(Egreso egreso, Presupuesto presupuesto){
		return false;
	}
	
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
	
	return  (egreso.getDocumento().equals(presupuesto.getDocumento())) && 
		    (egreso.getItems().equals(presupuesto.getItems())) &&
		    (egreso.equals(presupuesto.getEgresoAsociado()));
		    //&&(egreso.getProveedor().equals(presupuesto.getProveedor()));    
	}
}

