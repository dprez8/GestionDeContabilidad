package Operaciones;
import java.util.stream.Stream;

import Organizacion.*;
import ValidadorDeTransparencia.ValidadorDeTransparencia;

public class Scheduler implements Runnable{
	private Organizacion organizacion;
	private ValidadorDeTransparencia validador;
	private int frecuencia;
	
	public Scheduler(Organizacion organizacion,ValidadorDeTransparencia validador, int frecuencia) {
		this.organizacion = organizacion;
		this.validador= validador;
		this.frecuencia=frecuencia;
	}
	
	public void run(){
		
		while(true) {	//se ejecuta contantemente con cierta frecuencia asignada por parametro
			try {
				Thread.sleep(frecuencia);
			} catch (InterruptedException e) {
				e.printStackTrace(); //seria bueno algo mas descriptivo
			}
			Stream<Egreso> egresos=organizacion.egresosNoValidados(); //Lo egresos que no han sido validados o no pasaron las pruebas anteriormente
			egresos.forEach(egreso->validador.validarEgreso(egreso)); //valida cada uno de los egresos que no se habian validado	
		}
	}
}
