package Domain.Entities.Operaciones;

import java.time.LocalDate;
import java.util.Date;

import Domain.Entities.Organizacion.*;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operacion {

	@Expose
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

	/**Setters & Getters*/

	public void setFechaOperacion(LocalDate fecha) {
		this.fechaOperacion = fecha;
	}

	public LocalDate getFechaOperacion(){
		return fechaOperacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
