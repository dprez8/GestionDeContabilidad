package MiTest;

import CategorizadorDeEmpresas.*;
import Organizacion.Empresa;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategorizacionDeEmpresa {
    @Test
    public void testCategoria(){
        Categorizador categorizador = new Categorizador();

        categorizador.addCategoriaExistente("Micro", 1);
        categorizador.addCategoriaExistente("Pequenia", 2);
        categorizador.addCategoriaExistente("Mediana T1", 3);
        categorizador.addCategoriaExistente("Mediana T2", 4);

        Sector servicios    = new Sector("Servicios");
        Sector construccion = new Sector("Construccion");
        Sector comercio     = new Sector("Comercio");

        Categoria microConstruccion = new Categoria("Micro",0.0,15230000.0,1,6,construccion);
        Categoria medianaT1Construccion = new Categoria("Mediana T1",300000.0,15230000.0,6,12,construccion);
        Categoria microServicios    = new Categoria("Micro",0.0,8500000.0,1,7,servicios);
        Categoria microComercio     = new Categoria("Micro",0.0,29740000.0,1,7,comercio);

        Empresa miPyme = new Empresa(3,5000003.0,"Construccion",construccion);

        categorizador.agregarCategorias(microComercio,medianaT1Construccion,microConstruccion,microServicios);

        miPyme.cacularCategoria(categorizador);
        Assert.assertEquals(medianaT1Construccion,miPyme.getCategoria());

    }
}
