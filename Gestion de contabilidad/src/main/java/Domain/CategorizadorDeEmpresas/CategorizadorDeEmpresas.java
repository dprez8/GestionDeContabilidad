package Domain.CategorizadorDeEmpresas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import Domain.Organizacion.Empresa;

public class CategorizadorDeEmpresas {
	
	private static List<CategoriaPorSector> categoriasPorSector  = new ArrayList<>();
	

    public static CategoriaEmpresa obtenerCategoriaDe(Empresa unaEmpresa){
        List<CategoriaPorSector> listaCategoriasFiltrada = categoriasPorSector.stream().
        													filter(unaCategoriaSector ->unaCategoriaSector.cumpleRequisitos(unaEmpresa))
                                                            .collect(Collectors.toList());
     
        Optional<CategoriaPorSector> categoriaSectorObtenida = listaCategoriasFiltrada.stream().findFirst();
 
		if(categoriaSectorObtenida.isPresent()) {
			CategoriaPorSector categoriaEmp= categoriaSectorObtenida.get();
			unaEmpresa.setCategoriaEmpresa(categoriaEmp.getCategoriaEmpresa());
			return categoriaEmp.getCategoriaEmpresa();
			}
		return null;
    }
    
    public static void addCategoriasPorSector(CategoriaPorSector... categoria_sector){
        Collections.addAll(categoriasPorSector, categoria_sector);
    }
}
