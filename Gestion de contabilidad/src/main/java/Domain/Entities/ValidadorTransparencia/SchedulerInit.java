package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Organizacion.Organizacion;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "scheduler")
public class Scheduler extends EntidadPersistente {

	@Expose
	@Column(name = "hora_inicio")
	private int horaInicio;

	@Expose
	@Column(name = "minuto_inicio")
	private int minutoInicio;

	@Expose
	@ElementCollection
	private List<Integer> dias;

	@JoinColumn(name = "organizacion_id")
	@OneToOne(fetch = FetchType.LAZY)
	private Organizacion organizacion;

	@Transient
	private ValidadorDeTransparencia validadorDeTransparencia;

	public Scheduler() {
		this.horaInicio = 20;
		this.minutoInicio = 00;
		this.dias = new ArrayList<>();
		Integer diasDefault[] = {1,2,3,4,5,6,7};
		Collections.addAll(this.dias,diasDefault);
	}

	public Calendar delay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, retornarDiaActualSegun(this.dias));
		calendar.set(Calendar.HOUR_OF_DAY, this.horaInicio);
		calendar.set(Calendar.MINUTE, this.minutoInicio);
		calendar.set(Calendar.SECOND, 0);
		return calendar;
	}

	public void arrancarTarea(){
		Inicializador hilo = new Inicializador(this.organizacion,this.validadorDeTransparencia);
		Timer timer    = new Timer();
		timer.schedule(hilo,delay().getTime());
		System.out.println("holaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	private int retornarDiaActualSegun(List<Integer> dias) {
		Integer dia = dias.stream()
							.filter(unDia->
									DayOfWeek.of(unDia) == LocalDate.now().getDayOfWeek())
							.findAny()
							.orElse(null);
		switch(dia) {
			case 0:
				return Calendar.SUNDAY;
			case 1:
				return Calendar.MONDAY;
			case 2:
				return Calendar.TUESDAY;
			case 3:
				return Calendar.WEDNESDAY;
			case 4:
				return Calendar.THURSDAY;
			case 5:
				return Calendar.FRIDAY;
			case 6:
				return Calendar.SATURDAY;
			default:
				return 0;
		}
	}
	/** Setter & Getters */
	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = Math.max(0, Math.min(24, horaInicio));
	}

	public int getMinutoInicio() {
		return minutoInicio;
	}

	public void setMinutoInicio(int minutoInicio) {
		this.minutoInicio = Math.max(0, Math.min(60, minutoInicio));
	}

	public List<Integer> getDias() {
		return dias;
	}

	public void setDias(List<Integer> dias) {
		this.dias = dias;
	}

	public Organizacion getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	public ValidadorDeTransparencia getValidadorDeTransparencia() {
		return validadorDeTransparencia;
	}

	public void setValidadorDeTransparencia(ValidadorDeTransparencia validadorDeTransparencia) {
		this.validadorDeTransparencia = validadorDeTransparencia;
	}
}
