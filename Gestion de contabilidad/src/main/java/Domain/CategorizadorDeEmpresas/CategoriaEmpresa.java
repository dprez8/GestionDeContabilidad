package Domain.CategorizadorDeEmpresas;

import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import Domain.Organizacion.Empresa;
@Entity
@Table
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
    }

    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String name){
        this.nombre=name;
    }
    
    public void addCategoriasPorSector(CategoriaPorSector... categoria_sector){
        Collections.addAll(this.categoriasPorSector, categoria_sector);
    }
}
