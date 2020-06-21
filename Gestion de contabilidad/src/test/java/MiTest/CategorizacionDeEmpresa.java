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

        Sector servicios    = new Sector("Servicios");
        Sector construccion = new Sector("Construccion");
        Sector comercio     = new Sector("Comercio");

        Categoria microConstruccion = new Categoria("Micro",0.0,15230000.0,1,12,construccion);
        Categoria microServicios    = new Categoria("Micro",0.0,8500000.0,1,7,servicios);
        Categoria microComercio     = new Categoria("Micro",0.0,29740000.0,1,7,comercio);

        Empresa miPyme = new Empresa(10,10000000.0,"Construccion",construccion);

        Categoria.agregarActivadadesExceptuadas("Comisionista","AgenciaDeViaje");
        categorizador.agregarCategorias(microComercio,microConstruccion,microServicios);

        miPyme.cacularCategoria(categorizador);
        Assert.assertEquals(microConstruccion,miPyme.getCategoria());

    }
}
