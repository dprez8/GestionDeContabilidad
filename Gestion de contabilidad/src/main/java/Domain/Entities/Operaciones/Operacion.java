package Domain.Entities.Operaciones;

import java.time.LocalDate;
import java.time.LocalDateTime;

import Domain.Entities.Organizacion.*;
import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_operacion")
public abstract class Operacion {

	@Expose
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ConfirmationCodeGenerator")
	@TableGenerator(table = "SEQUENCES", name = "ConfirmationCodeGenerator")
	@Column(name="id")
	private int id;

	@Expose
	@Column(name="fecha_operacion", columnDefinition = "DATE")
	protected LocalDate fechaOperacion;

	@Column(name="fecha_carga", columnDefinition = "TIMESTAMP")
	protected LocalDateTime fechaCarga;

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

	public LocalDateTime getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga() {
		this.fechaCarga = LocalDateTime.now();
	}

	public Organizacion getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}
}
