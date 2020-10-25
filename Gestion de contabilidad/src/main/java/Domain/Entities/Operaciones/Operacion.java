package Domain.Entities.Operaciones;

import java.time.LocalDate;
import java.util.Date;

import Domain.Entities.Organizacion.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operacion {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="id")
	private int id;

	@Column(name="fecha_operacion", columnDefinition = "DATE")
	protected LocalDate fechaOperacion;

	@Column(name="fecha_carga", columnDefinition = "DATE")
	protected Date fechaCarga;

	@ManyToOne
	@JoinColumn(name = "organizacion_id", referencedColumnName = "id")
	protected Organizacion organizacion;

	@Column(name="operacion_numero")
	protected int operacionNumero;

	/**Setters & Getters*/
	public void setOperacionNumero(int operacionNumero) {
		this.operacionNumero = operacionNumero;
	}

	public int getOperacionNumero() {
		return this.operacionNumero;
	}

	public void setFechaOperacion(LocalDate fecha) {
		this.fechaOperacion = fecha;
	}

	public LocalDate getFechaOperacion(){
		return fechaOperacion;
	}

	public int getId() {
		return id;
	}
}
