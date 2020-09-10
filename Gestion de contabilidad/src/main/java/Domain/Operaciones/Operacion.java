package Domain.Operaciones;

import java.time.LocalDate;
import java.util.Date;

import Domain.Organizacion.*;

public abstract class Operacion {
	protected LocalDate fechaOperacion;
	protected Date fechaCarga;
	protected Organizacion organizacion;
	protected int operacionNumero;

	/**Setters & Getters*/
	public void setOperacionNumero(int operacionNumero) {
		this.operacionNumero = operacionNumero;
	}

	public void setFechaOperacion(LocalDate fecha) {
		this.fechaOperacion = fecha;
	}

	public LocalDate getFechaOperacion(){
		return fechaOperacion;
	}
}
