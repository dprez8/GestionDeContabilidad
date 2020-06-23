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

	public Scheduler(Organizacion unaOrganizacion, ValidadorDeTransparencia validador){
		this.organizacion = unaOrganizacion;
		this.validador 	  = validador;
	}
	@Override
	public void run() {
		List<Egreso> egresos=organizacion.getEgresosValidados(false); //Lo egresos que no han sido validados o no pasaron las pruebas anteriormente
		egresos.forEach(egreso->validador.validarEgreso(egreso)); //valida cada uno de los egresos que no se habian validado
	}
}
