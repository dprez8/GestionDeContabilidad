package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.ItemPresupuestoRequest;
import Domain.Controllers.DTO.ItemRequest;
import Domain.Controllers.DTO.PresupuestoRequest;
import Domain.Controllers.DTO.Respuesta;
import Domain.Controllers.Utils.FormFileManager;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Operaciones.Presupuesto;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PresupuestoRestController extends GenericController {
    private Repositorio<Presupuesto> repoPresupuestos;
    private Repositorio<Egreso> repoEgresos;
    private Repositorio<ItemPresupuesto> repoItems;
    private Repositorio<ItemEgreso> repoItemsEgreso;
    private Repositorio<Proveedor> repoProveedor;
    Repositorio<TipoDocumento> repoTipoDocumento;
    private Respuesta respuesta;
    private Gson gson;

    public PresupuestoRestController(TokenService tokenService, String tokenPrefix) {
        super(tokenService, tokenPrefix);
        this.repoPresupuestos   = new Repositorio<>(new DaoHibernate<>(Presupuesto.class));
        this.repoEgresos        = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        this.repoItems          = new Repositorio<>(new DaoHibernate<>(ItemPresupuesto.class));
        this.repoItemsEgreso    = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        this.repoProveedor      = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        this.repoTipoDocumento  = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        this.respuesta          = new Respuesta();
    }

    /*FALTA LISTADO PARA EL GET*/

    public String cargarNuevoPresupuesto(Request request, Response response) {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();
        PresupuestoRequest presupuestoRequest = null;
        try {
            presupuestoRequest = gson.fromJson(request.body(), PresupuestoRequest.class);
        } catch (Exception exception) {
            return error(response, "Error en los datos esperados enviados");
        }

        Egreso egreso = this.repoEgresos.buscar(presupuestoRequest.egreso);

        Presupuesto presupuesto = asignarPresupuestoDesde(presupuestoRequest, egreso);

        if (presupuesto == null) {
            return error(response, "Problema al cargar el presupuesto");
        }
        this.respuesta.setCode(200);
        this.respuesta.setMessage("Presupuesto cargado exitosamente");

        PresupuestoRestController.CargaPresupuestoResponse cargaPresupuestoResponse = new PresupuestoRestController.CargaPresupuestoResponse();

        cargaPresupuestoResponse.code    = this.respuesta.getCode();
        cargaPresupuestoResponse.message = this.respuesta.getMessage();
        cargaPresupuestoResponse.id = egreso.getId();

        return toJson(response, cargaPresupuestoResponse);
    }

    private Presupuesto asignarPresupuestoDesde(PresupuestoRequest presupuestoRequest, Egreso egreso) {
        if(presupuestoRequest.items.isEmpty() || presupuestoRequest.fechaVigente == null){
            return null;
        }
        Presupuesto presupuesto = new Presupuesto(egreso);
        presupuesto.setFechaVigente(presupuestoRequest.fechaVigente);
        // FIXME: explota hibernate con esto
        //presupuesto.setCategorias(egreso.getCategorias());

        Proveedor proveedor;
        try {
            proveedor = repoProveedor.buscar(presupuestoRequest.proveedor);
        }
        catch (Exception ex){
            return null;
        }
        presupuesto.setProveedor(proveedor);

        List<ItemPresupuesto> items = presupuestoRequest.items
                .stream()
                .map(item->mapearItem(item, presupuesto))
                .collect(Collectors.toList());
        
        for(ItemPresupuesto itemPresupuesto:items){
        	presupuesto.addItems(itemPresupuesto);
        }

        this.repoPresupuestos.agregar(presupuesto);
        //relacionarItemsConPresupuesto(items,presupuesto);
        return presupuesto;
    }

    private ItemPresupuesto mapearItem(ItemPresupuestoRequest itemPresupuestoRequest, Presupuesto presupuesto) {

        ItemEgreso itemEgreso = null;
        try {
            itemEgreso = repoItemsEgreso.buscar(itemPresupuestoRequest.itemEgreso);
        }
        catch (Exception ex){
            return null;
        }

        ItemPresupuesto itemPresupuesto = new ItemPresupuesto();
        itemPresupuesto.setItemEgresoAsociado(itemEgreso);
        itemPresupuesto.setItem(itemEgreso.getItem());
        itemPresupuesto.setPrecio(itemPresupuestoRequest.precio);
        itemPresupuesto.setCantidad(itemEgreso.getCantidad());
        itemPresupuesto.setPresupuesto(presupuesto);

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
