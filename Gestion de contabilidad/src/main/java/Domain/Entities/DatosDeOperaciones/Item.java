package Domain.Entities.DatosDeOperaciones;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import Domain.Entities.EntidadPersistente.EntidadPersistente;

@Entity
@Table(name = "item")
public class Item extends EntidadPersistente{
	 	@Expose
	    @Column(name="descripcion")
	    private String descripcion;
	 	@Expose
	 	@ManyToOne
	 	@JoinColumn(name= "tipo_item_id",referencedColumnName = "id")
	 	private TipoItem tipoItem;

	 	public Item() {}

	    public Item(String descripcion, TipoItem tipoItem) {
			this.descripcion = descripcion;
			this.tipoItem = tipoItem;
		}

		public TipoItem getTipoItem() {
			return tipoItem;
		}

		public void setTipoItem(TipoItem tipoItem) {
			this.tipoItem = tipoItem;
		}

		public String getDescripcion() {
	        return descripcion;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

}
