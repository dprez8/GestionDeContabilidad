package Domain.Operaciones;

import java.time.LocalDate;

import Domain.Organizacion.*;

public abstract class Operacion {
	protected LocalDate fechaOperacion;
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
