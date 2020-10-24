package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.Organizacion.Organizacion;

import java.time.LocalDate;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class Scheduler{
	private static long periodo; //Cada 5 seg por default
	
	public Scheduler(long _periodo){
		periodo = _periodo;
	}

	public static Calendar delay() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 17);
		today.set(Calendar.MINUTE, 5);
		today.set(Calendar.SECOND, 0);
		return today;
	}

	public static void arrancarTarea(Organizacion unaOrganizacion, ValidadorDeTransparencia validador){
		Inicializador hilo = new Inicializador(unaOrganizacion,validador);
		Timer timer    = new Timer();
		System.out.println(delay());
		timer.scheduleAtFixedRate(hilo,delay().getTime(),periodo);
	}

	/** Setter & Getters */
	public static void setPeriodo(long unPeriodo){
		periodo = unPeriodo;
	}

	public static long getPeriodo(){
		return periodo;
	}
}
