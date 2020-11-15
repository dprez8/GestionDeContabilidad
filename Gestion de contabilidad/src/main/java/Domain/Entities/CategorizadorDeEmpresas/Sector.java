package Domain.Entities.CategorizadorDeEmpresas;

import Domain.Entities.Organizacion.Empresa;
import com.google.gson.annotations.Expose;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table(name="sector")
public class Sector {
	@Id
	@GeneratedValue
	@Column(name="sector_id")
	private int sectorId;

    @Expose
	@Column
    private String nombre;

	@OneToMany(mappedBy = "sector")
    List<CategoriaPorSector> categoriasPorSector;
	
	/*@ManyToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	@Transient
	private List<CategoriaEmpresa> categoriaEmpresas;

    @Transient
	private HashMap<String, Integer> categoriasExistentes;*/

    public Sector(){
    }

    public Sector(String nombre){
        this.categoriasPorSector = new ArrayList<>();
        this.nombre = nombre;
    }

    public void addCategoriasPorSector(CategoriaPorSector... categoria_sector){
    	for(CategoriaPorSector categoriaSector: categoria_sector)
    		categoriaSector.setSector(this);
        //Collections.addAll(this.categoriasPorSector, categoria_sector);
    }

	public int getSectorId() {
		return sectorId;
	}

	public void setSectorId(int sectorId) {
		this.sectorId = sectorId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
    
    
    
    /*
    public void addCategoriaExistente(String key, Integer value) {
        categoriasExistentes.put(key, value);
    }

    public void removeCategoriaExistente(String key){
        categoriasExistentes.remove(key);
    }*/
}
