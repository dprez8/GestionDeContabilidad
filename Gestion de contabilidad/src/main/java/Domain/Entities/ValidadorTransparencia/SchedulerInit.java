package Domain.Entities.ValidadorTransparencia;

import Domain.Entities.EntidadPersistente.EntidadPersistente;
import Domain.Entities.Organizacion.Organizacion;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "scheduler")
public class SchedulerInit extends EntidadPersistente {

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

	@ManyToOne
	@JoinColumn(name = "validador_id",referencedColumnName = "id")
	private ValidadorDeTransparencia validadorDeTransparencia;

	@Transient
	private Tarea tarea;
	@Transient
	private Timer timer;

	public SchedulerInit() {
		this.horaInicio = 20;
		this.minutoInicio = 00;
		this.dias = new ArrayList<>();
		Integer diasDefault[] = {1,2,3,4,5,6,7};
		Collections.addAll(this.dias,diasDefault);
	}

	public void arrancarTarea(){
		this.tarea.setOrganizacion(this.organizacion);
		this.tarea.setValidador(this.validadorDeTransparencia);
		this.tarea.setDias(this.dias);
		this.tarea.setHoraInicio(this.horaInicio);
		this.tarea.setMinutoInicio(this.minutoInicio);
		/*1000*60*60*24 = a un dia de demora*/
		this.timer.schedule(tarea, tarea.delay().getTime(),10000);

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

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
}
