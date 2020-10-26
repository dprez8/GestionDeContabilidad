package Domain.Entities.Operaciones;

import java.time.LocalDate;

import Domain.Entities.Organizacion.*;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Operacion {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ConfirmationCodeGenerator")
	@TableGenerator(table = "SEQUENCES", name = "ConfirmationCodeGenerator")
	@Column(name="id")
	private int id;

	@Column(name="fecha_operacion", columnDefinition = "DATE")
	protected LocalDate fechaOperacion;

	@Column(name="fecha_carga", columnDefinition = "DATE")
	protected LocalDate fechaCarga;

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

	public LocalDate getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(LocalDate fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Organizacion getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
}
