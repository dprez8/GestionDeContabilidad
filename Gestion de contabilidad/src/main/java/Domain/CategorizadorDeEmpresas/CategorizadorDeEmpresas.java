package Domain.CategorizadorDeEmpresas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Domain.Entities.CategorizadorDeEmpresas.CategoriaEmpresa;
import Domain.Entities.CategorizadorDeEmpresas.CategoriaPorSector;
import Domain.Entities.Organizacion.Empresa;

public class CategorizadorDeEmpresas {
	
	private static List<CategoriaPorSector> categoriasPorSector  = new ArrayList<>();
	

    public static CategoriaEmpresa obtenerCategoriaDe(Empresa empresa){
        List<CategoriaPorSector> listaCategoriasFiltrada = categoriasPorSector.stream().
        													filter(unaCategoriaSector ->unaCategoriaSector.cumpleRequisitos(empresa))
                                                            .collect(Collectors.toList());
     
        Optional<CategoriaPorSector> categoriaSectorObtenida = listaCategoriasFiltrada.stream().findFirst();
 
		if(categoriaSectorObtenida.isPresent()) {
			CategoriaPorSector categoriaEmp= categoriaSectorObtenida.get();
			empresa.setCategoriaEmpresa(categoriaEmp.getCategoriaEmpresa());
			return categoriaEmp.getCategoriaEmpresa();
			}
		return null;
    }
    
    public static void addCategoriasPorSector(CategoriaPorSector... categoria_sector){
        Collections.addAll(categoriasPorSector, categoria_sector);
    }
}
