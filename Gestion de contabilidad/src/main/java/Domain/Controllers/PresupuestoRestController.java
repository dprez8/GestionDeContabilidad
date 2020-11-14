package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.ItemRequest;
import Domain.Controllers.DTO.PresupuestoRequest;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Usuarios.Estandar;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PresupuestoRestController {
    private Repositorio<Presupuesto> repoPresupuestos;
    private Repositorio<Egreso> repoEgresos;
    private Repositorio<ItemPresupuesto> repoItems;
    private Respuesta respuesta;
    private Gson gson;

    public PresupuestoRestController() {
        this.repoPresupuestos   = new Repositorio<>(new DaoHibernate<>(Presupuesto.class));
        this.repoEgresos        = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoItems          = new Repositorio<>(new DaoHibernate<>(ItemPresupuesto.class));
        this.respuesta          = new Respuesta();
    }

    /*FALTA LISTADO PARA EL GET*/

    public String cargarNuevoPresupuesto(Request request, Response response) {
        response.type("application/json");
        String jsonResponse;

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        PresupuestoRequest presupuestoRequest = this.gson.fromJson(request.body(),PresupuestoRequest.class);

        Egreso egreso= this.repoEgresos.buscar(presupuestoRequest.egresoId);

        Presupuesto presupuesto = asignarPresupuestoDesde(presupuestoRequest, egreso);

        if(presupuesto == null) {
            this.respuesta.setCode(400);
            this.respuesta.setMessage("Problema al cargar el presupuesto");
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }
        this.respuesta.setCode(200);
        this.respuesta.setMessage("Presupuesto cargado exitosamente");

        PresupuestoRestController.CargaPresupuestoResponse cargaPresupuestoResponse = new PresupuestoRestController.CargaPresupuestoResponse();

        cargaPresupuestoResponse.code    = this.respuesta.getCode();
        cargaPresupuestoResponse.message = this.respuesta.getMessage();
        cargaPresupuestoResponse.id = egreso.getId();

        jsonResponse = this.gson.toJson(cargaPresupuestoResponse);
        response.body(jsonResponse);
        return response.body();
    }

    private Presupuesto asignarPresupuestoDesde(PresupuestoRequest presupuestoRequest, Egreso egreso) {
        if(presupuestoRequest.items.isEmpty() || presupuestoRequest.fechaVigente == null || presupuestoRequest.numeroOperacion == 0 ){
            return null;
        }
        Presupuesto presupuesto = new Presupuesto(presupuestoRequest.numeroOperacion, egreso);
        presupuesto.setFechaVigente(presupuestoRequest.fechaVigente);
        /*FALTA QUE ME MANDEN MAS DATOS DEL PRESUPUESTO, COMO DOCUMENTO COMERCIAL, PROVEEDOR Y CATEGORIA*/

        List<ItemPresupuesto> items = presupuestoRequest.items
                .stream()
                .map(item->mapearItem(item,presupuesto))
                .collect(Collectors.toList());
        
        for(ItemPresupuesto itemPresupuesto:items){
        	presupuesto.addItems(itemPresupuesto);
        }

        this.repoPresupuestos.agregar(presupuesto);
        //relacionarItemsConPresupuesto(items,presupuesto);
        return presupuesto;
    }

    private ItemPresupuesto mapearItem(ItemRequest itemRequest,Presupuesto presupuesto) {
        ItemPresupuesto itemPresupuesto = new ItemPresupuesto();
        Item item=null;
        itemPresupuesto.setPrecio(itemRequest.precio);
        itemPresupuesto.setCantidad(itemRequest.cantidad);
        Repositorio<Item> repoItem = new Repositorio<>(new DaoHibernate<>(Item.class));
        Repositorio<TipoItem> repoTipoItem = new Repositorio<>(new DaoHibernate<>(TipoItem.class));

        
        if(itemRequest.itemId==0) {//si el item es nuevo
        	TipoItem tipoItem=repoTipoItem.buscar(itemRequest.tipoItem);
        	item=new Item(itemRequest.descripcion,tipoItem);
        	repoItem.agregar(item);
        }
        else{//si eligio un item que ya se encontraba en la base de datos
        	item=repoItem.buscar(itemRequest.itemId);
        }
        
        itemPresupuesto.setItem(item);
        itemPresupuesto.setPresupuesto(presupuesto);
        itemPresupuesto.setItemEgresoAsociado(presupuesto.getEgresoAsociado().getItems().stream().filter(itemsEgreso -> itemsEgreso.getItem().equals(itemPresupuesto.getItem())).findFirst().get());

        return itemPresupuesto;
    }



    private void relacionarItemsConPresupuesto(List<ItemPresupuesto> items, Presupuesto presupuesto) {
        items.forEach(unItemPresupuesto -> {
            unItemPresupuesto.setPresupuesto(presupuesto);
            unItemPresupuesto.setItemEgresoAsociado(presupuesto.getEgresoAsociado().getItems().stream().filter(itemsEgreso -> itemsEgreso.getItem().equals(unItemPresupuesto.getItem())).findFirst().get());
            this.repoItems.modificar(unItemPresupuesto);
        });
    }

    private class CargaPresupuestoResponse {
        public int code;
        public String message;
        public int id;
    }

}
