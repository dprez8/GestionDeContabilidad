package Domain.ValidadorTransparencia;

import Domain.Organizacion.Organizacion;

import java.util.Timer;

public class Scheduler{
	private long periodo = 5000; //Cada 5 seg por default

	public Scheduler(long periodo){
		this.periodo = periodo;
	}

	public void arrancarTarea(Organizacion unaOrganizacion, ValidadorDeTransparencia validador){
		Inicializador hilo = new Inicializador(unaOrganizacion,validador);
		Timer timer    = new Timer();
		timer.schedule(hilo,0,this.periodo); 
	}

	/** Setter & Getters */
	public void setPeriodo(long unPeriodo){
		this.periodo = unPeriodo;
	}

	public long getPeriodo(){
		return periodo;
	}
}
