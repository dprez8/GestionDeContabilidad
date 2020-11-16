package Services.ProcesoVinculacionServices;


import Domain.Entities.CategorizadorDeEmpresas.Sector;
import Domain.Entities.DatosDeOperaciones.Item;
import Domain.Entities.DatosDeOperaciones.ItemEgreso;
import Domain.Entities.DatosDeOperaciones.TipoItem;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Ingreso;
import Domain.Entities.Organizacion.Empresa;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Exceptions.ExcepcionCreacionEgreso;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VinculacionMain {
    public static void main(String[] args) throws IOException, ExcepcionCreacionEgreso {
        //PREPARACION DE EGRESOS E INGRESOS (ESTOS VENDRIAN DE ALGUNA PARTE DEL SISTEMA)

        /**Creacion de una organizacion ejemplo*/

        Sector construccion = new Sector("Construccion");
        Empresa miPyme = new Empresa();
        miPyme.setSector(construccion);
        miPyme.setActividad("Construccion");
        miPyme.setVentasAnuales(50000003.0);
        miPyme.setCantidadDePersonal(3);
        EntidadJuridica unaEntidad = new EntidadJuridica();
        unaEntidad.setTipoEntidadJuridica(miPyme);

    	TipoItem producto= new TipoItem("producto");
        Item ram = new Item("4GB DDR4", producto, unaEntidad);
        Item placaDeVideo = new Item("RTX 3090", producto, unaEntidad);
        Item monitor = new Item("4k 144hz", producto, unaEntidad);
        ItemEgreso rams = new ItemEgreso(ram, 1, 1000.0);
        ItemEgreso placasDeVideo = new ItemEgreso(placaDeVideo, 1, 2500.0);
        ItemEgreso monitores = new ItemEgreso(monitor, 1, 5000.0);

        BuilderEgresoConcreto egresoBuilder = new BuilderEgresoConcreto();
        Egreso compra1 = egresoBuilder
                .agregarFechaOperacion(LocalDate.of(2020, Month.SEPTEMBER,26))
                .agregarItems(rams)
                .build();
        egresoBuilder = new BuilderEgresoConcreto();
        Egreso compra2 = egresoBuilder
                .agregarFechaOperacion(LocalDate.of(2020, Month.SEPTEMBER,27))
                .agregarItems(placasDeVideo)
                .build();
        egresoBuilder = new BuilderEgresoConcreto();
        Egreso compra3 = egresoBuilder
                .agregarFechaOperacion(LocalDate.of(2020, Month.SEPTEMBER,23))
                .agregarItems(monitores)
                .build();
        compra1.setId(1);
        compra2.setId(2);
        compra3.setId(3);

        Ingreso donacion = new Ingreso("Donacion", 5000.0);
                donacion.setId(5);
                donacion.setFechaOperacion(LocalDate.of(2020, 9, 26));
        Ingreso venta = new Ingreso("Venta de computadora", 10000.0);
                venta.setId(7);
                venta.setFechaOperacion(LocalDate.of(2020, 9, 25));

        List<Ingreso> ingresos = new ArrayList<>();
        List<Egreso> egresos = new ArrayList<>();

        ingresos.add(donacion);
        ingresos.add(venta);

        egresos.add(compra1);
        egresos.add(compra2);
        egresos.add(compra3);

        //LLAMO AL ARCHIVO DE CONFIG PARA OBTENER LA URL A LA API DE VINCULADOR
        Properties prop=new Properties();
        prop.load(new FileReader("src/main/resources/config/config.properties"));

        VinculacionApi vinculacionApi = new VinculacionApi();

        vinculacionApi.configurarVinculador(-3, 3, "OrdenValorPrimeroEgreso", prop);
        vinculacionApi.vincularEgresosIngresos(ingresos, egresos, prop);

        printTest(ingresos, egresos);
    }

    private static void printTest(List<Ingreso> ingresos, List<Egreso> egresos) {
        egresos.forEach(unEgreso -> {
            Integer numeroEgreso = unEgreso.getId();
            String fechaEgreso = unEgreso.getFechaOperacion().toString();
            Double valorEgreso = unEgreso.getValorTotal();

            if(unEgreso.getIngresoAsociado() != null) {
                Integer numeroIngreso = unEgreso.getIngresoAsociado().getId();
                String fechaIngreso = unEgreso.getIngresoAsociado().getFechaOperacion().toString();
                Double valorIngreso = unEgreso.getIngresoAsociado().montoSobrante() +
                        unEgreso.getIngresoAsociado().montoEnUso();

                System.out.println(
                        "Egreso N°"+numeroEgreso+":\n"+
                        "   Fecha: "+fechaEgreso+"\n"+
                        "   Monto: $"+valorEgreso+"\n"+
                        "   Ingreso asociado: -> {N°"+numeroIngreso+" | "+fechaIngreso+" | $"+valorIngreso+"}"
                );
            } else {
                System.out.println(
                        "Egreso N°"+numeroEgreso+":\n"+
                        "   Fecha: "+fechaEgreso+"\n"+
                        "   Monto: $"+valorEgreso+"\n"+
                        "   Ingreso asociado: -> {null}"
                );
            }
        });
    }
}
