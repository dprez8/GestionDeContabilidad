package ValidadorTransparencia;
import java.util.List;
import java.util.TimerTask;
import java.util.stream.Stream;

import Operaciones.Egreso;
import Organizacion.*;
import Usuarios.Estandar;

public class Scheduler extends TimerTask{
	private Organizacion organizacion;
	private ValidadorDeTransparencia validador;
	private Estandar usuario;

	public Scheduler(Organizacion unaOrganizacion, ValidadorDeTransparencia validador, Estandar usuario){
		this.organizacion = unaOrganizacion;
		this.validador 	  = validador;
		this.usuario	  = usuario;
	}
	@Override
	public void run() {
		List<Egreso> egresos=organizacion.getEgresos(); //Lo egresos que no han sido validados o no pasaron las pruebas anteriormente
		if(faltanEgresosPorValidar(egresos)){
			egresos.forEach(egreso->validador.validarEgreso(egreso)); //valida cada uno de los egresos que no se habian validado
		}
		usuario.verMensajes();
	}

	private boolean faltanEgresosPorValidar(List<Egreso> egresos){
		return !(egresos.stream().anyMatch(unEgreso->unEgreso.getValidado()));
	}
}
