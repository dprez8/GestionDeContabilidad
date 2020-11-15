package Domain.Entities.CategorizadorDeEmpresas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import Domain.Entities.Organizacion.Empresa;

import java.io.Serializable;

@Entity
@Table(name="categoria_empresa")
public class CategoriaEmpresa {
   
	@Id
	@GeneratedValue
	@Column(name="categoria_id")
	private int categoriaId;
	@Column
	private String nombre;

	@OneToMany(mappedBy = "categoriaEmpresa")
    List<CategoriaPorSector> categoriasPorSector;
 
    public CategoriaEmpresa(String categoria){
        this.nombre      = categoria;
        this.categoriasPorSector = new ArrayList<>();

    }

    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String name){
        this.nombre=name;
    }
    
    public void addCategoriasPorSector(CategoriaPorSector... categoria_sector){
    	for(CategoriaPorSector categoriaSector: categoria_sector) {
    		categoriaSector.setCategoriaEmpresa(this);}
    	//Collections.addAll(this.categoriasPorSector, categoria_sector);
    }
}

