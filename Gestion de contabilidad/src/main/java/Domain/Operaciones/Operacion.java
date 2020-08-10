package Domain.Operaciones;

import java.util.Date;

import Domain.Organizacion.*;

public abstract class Operacion {
	protected Date fechaOperacion;
	protected Organizacion organizacion;
	protected int operacionNumero;

	public Operacion(){
		this.fechaOperacion = new Date();
	}

	public void setOperacionNumero(int operacionNumero) {
		this.operacionNumero = operacionNumero;
	}

	public void setFechaOperacion(Date fecha) {
		this.fechaOperacion = fecha;
	}
}
