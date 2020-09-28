package Domain.Entities.CategorizadorDeEmpresas;

import Domain.Entities.Organizacion.Empresa;

import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.*;

@Entity
@Table
public class Sector {
	@Id
	@Column(name="sector_id")
	private int sectorId;
	@Column
    private String nombre;
	//@ManyToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	@Transient
	private List<CategoriaEmpresa> categoriaEmpresas;
	
	@OneToMany(mappedBy = "categoriaEmpresa")
    List<CategoriaPorSector> categorias;
	
    
    @Transient
	private HashMap<String, Integer> categoriasExistentes;

    public Sector(String nombre){
        this.categoriaEmpresas = new ArrayList<>();
        this.categoriasExistentes = new HashMap<String,Integer>();
        this.nombre = nombre;
    }

    public CategoriaEmpresa obtenerCategoriaDe(Empresa unaEmpresa){
        List<CategoriaEmpresa> listaCategoriasFiltrada = this.categoriaEmpresas.stream().filter(unaCategoriaEmpresa ->
                                                                                    unaCategoriaEmpresa.dentroDelMinMax(unaEmpresa))
                                                                                        .collect(Collectors.toList());
        CategoriaEmpresa categoriaEmpresaObtenida = listaCategoriasFiltrada.stream().max(Comparator.comparingInt(a->categoriasExistentes.get(a.getNombre()))).get();
        return categoriaEmpresaObtenida;
    }
    public void addCategorias(CategoriaEmpresa... categoriaEmpresas){
        Collections.addAll(this.categoriaEmpresas, categoriaEmpresas);
    }

    public void addCategoriaExistente(String key, Integer value) {
        categoriasExistentes.put(key, value);
    }

    public void removeCategoriaExistente(String key){
        categoriasExistentes.remove(key);
    }
}
