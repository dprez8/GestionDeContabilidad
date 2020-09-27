package Domain.CategorizadorDeEmpresas;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import Domain.Organizacion.Empresa;

public class CategorizadorDeEmpresas {
	
	private static List<CategoriaPorSector> categoriasPorSector;
	

    public static CategoriaEmpresa obtenerCategoriaDe(Empresa unaEmpresa){
        List<CategoriaPorSector> listaCategoriasFiltrada = categoriasPorSector.stream().
        													filter(unaCategoriaSector ->unaCategoriaSector.cumpleRequisitos(unaEmpresa))
                                                            .collect(Collectors.toList());
     
        Optional<CategoriaPorSector> categoriaSectorObtenida = listaCategoriasFiltrada.stream().findFirst();
 
		if(categoriaSectorObtenida.isPresent()) {
			CategoriaPorSector categoriaEmp= categoriaSectorObtenida.get();
			return categoriaEmp.getCategoriaEmpresa();
			}
		return null;
    }
    
    public void addCategoriasPorSector(CategoriaPorSector... categoria_sector){
        Collections.addAll(this.categoriasPorSector, categoria_sector);
    }
}
