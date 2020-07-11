package Domain.Operaciones;

import java.util.Date;

import Domain.Organizacion.*;

public abstract class Operacion {
	protected Date fechaOperacion;
	protected Organizacion organizacion;
	protected int operacionNumero;

	public Operacion(int operacionNumero){
		this.fechaOperacion = new Date();
		this.operacionNumero = operacionNumero;
	}
}
