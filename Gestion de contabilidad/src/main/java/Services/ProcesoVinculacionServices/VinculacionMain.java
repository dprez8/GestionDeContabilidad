package Services.ProcesoVinculacionServices;

import Domain.ApiVinculador.*;
import Domain.DatosDeOperaciones.ItemEgreso;
import Domain.DatosDeOperaciones.Producto;
import Domain.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Operaciones.Egreso.Egreso;
import Domain.Operaciones.Ingreso;
import Services.MercadoLibreServices.EleccionApi;
import com.google.gson.Gson;

import javax.xml.ws.Response;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class VinculacionMain {
    public static void main(String[] args) throws IOException {
        //PREPARACION DE EGRESOS E INGRESOS (ESTOS VENDRIAN DE ALGUNA PARTE DEL SISTEMA)

        Producto ram = new Producto("4GB DDR4");
        Producto placaDeVideo = new Producto("RTX 3090");
        Producto monitor = new Producto("4k 144hz");
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
        compra1.setOperacionNumero(1);
        compra2.setOperacionNumero(2);
        compra3.setOperacionNumero(3);

        Ingreso donacion = new Ingreso(5, "Donacion", 5000.0);
                donacion.setOperacionNumero(5);
                donacion.setFechaOperacion(LocalDate.of(2020, 9, 26));
        Ingreso venta = new Ingreso(7, "Venta de computadora", 10000.0);
                venta.setOperacionNumero(7);
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
        prop.load(new FileReader("src/main/resources/config.properties"));

        VinculacionApi vinculacionApi = new VinculacionApi();

        vinculacionApi.configurarVinculador(-3, 3, "OrdenValorPrimeroEgreso", prop);
        vinculacionApi.vincularEgresosIngresos(ingresos, egresos, prop);

        printTest(ingresos, egresos);
    }

    private static void printTest(List<Ingreso> ingresos, List<Egreso> egresos) {
        egresos.forEach(unEgreso -> {
            Integer numeroEgreso = unEgreso.getOperacionNumero();
            String fechaEgreso = unEgreso.getFechaOperacion().toString();
            Double valorEgreso = unEgreso.getValorTotal();

            if(unEgreso.getIngresoAsociado() != null) {
                Integer numeroIngreso = unEgreso.getIngresoAsociado().getOperacionNumero();
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
