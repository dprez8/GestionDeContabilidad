package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Organizacion.Organizacion;
import Domain.Entities.Usuarios.Usuario;
import org.eclipse.jetty.util.annotation.Name;

import javax.persistence.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;

@Entity
@Table(name = "scheduler")
public class Scheduler extends EntidadPersistente {

	@Column(name = "hora_inicio")
	private int horaInicio;

	@Column(name = "minuto_inicio")
	private int minutoInicio;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	@ElementCollection
	private List<Integer> dias;

	protected Scheduler() {
		this.dias = new ArrayList<>();
	}

	public Scheduler(Usuario usuario) {
		this.usuario = usuario;
		this.horaInicio = 20;
		this.minutoInicio = 00;
		this.dias = new ArrayList<>();
		Integer diasDefault[] = {1,2,3,4,5,6,7};
		Collections.addAll(this.dias,diasDefault);
	}

	public Calendar delay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK,retornarDia(dias));
		calendar.set(Calendar.HOUR_OF_DAY, this.horaInicio);
		calendar.set(Calendar.MINUTE, this.minutoInicio);
		return calendar;
	}

	public void arrancarTarea(Organizacion unaOrganizacion, ValidadorDeTransparencia validador){
		Inicializador hilo = new Inicializador(unaOrganizacion,validador);
		Timer timer    = new Timer();
		System.out.println("AAAAAAAAAAAAAAAH" +retornarDia(dias));
		timer.schedule(hilo,delay().getTime());
	}

	private int retornarDia(List<Integer> dias) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Integer> getDias() {
		return dias;
	}

	public void setDias(List<Integer> dias) {
		this.dias = dias;
	}
}
