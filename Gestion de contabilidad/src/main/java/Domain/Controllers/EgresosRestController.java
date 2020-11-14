package Domain.Controllers;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.EgresoRequest;
import Domain.Controllers.DTO.EgresoResponse;
import Domain.Controllers.DTO.ItemRequest;
import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.DatosDeOperaciones.*;
import Domain.Entities.Operaciones.Egreso.BuilderEgresoConcreto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Entities.Organizacion.EntidadBase;
import Domain.Entities.Organizacion.EntidadJuridica;
import Domain.Entities.Usuarios.Estandar;
import Domain.Repositories.Daos.DaoHibernate;
import Domain.Repositories.Repositorio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import db.EntityManagerHelper;
import spark.Request;
import spark.Response;

import javax.persistence.NoResultException;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.io.FilenameUtils.getExtension;

/*****************************************************************/
public class EgresosRestController {
    private Repositorio<EntidadJuridica> repoEntidadJuridica;
    private Repositorio<EntidadBase> repoEntidadBase;
    private List<Egreso> egresosMemo;
    private Respuesta respuesta;
    private Gson gson;

    public EgresosRestController(){
        this.repoEntidadJuridica = new Repositorio<>(new DaoHibernate<>(EntidadJuridica.class));
        this.repoEntidadBase     = new Repositorio<>(new DaoHibernate<>(EntidadBase.class));
        this.egresosMemo         = new ArrayList<>();
        this.respuesta           = new Respuesta();
    }


    public String procesarFicheroParte(File uploadDir, Part part) throws IOException {
        String uploaded_file_name = getFileName(part);
        // creo el fichero temporal, con un nombre generico generado y la extension del archivo que fue subido
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "." + getExtension(uploaded_file_name));
        // copiar el stream de lo que me pasaron al fichero temporal
        InputStream input = part.getInputStream();
        Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile.toAbsolutePath().toString();
    }

    public String cargarArchivoDocumentoComercial(Request request, Response response) throws IOException, ServletException {

        response.type("application/json");
        String jsonResponse;

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request, response);
        if(usuario == null) {
            return response.body();
        }

        File uploadDir = new File("src/main/resources/public/upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist

        String location = "temp";          // the directory location where files will be stored temporarily
        long maxFileSize = 12000000;       // the maximum size allowed for uploaded files
        long maxRequestSize = 12000000;    // the maximum size allowed for multipart/form-data requests
        int fileSizeThreshold = 1024;       // the size threshold after which files will be written to disk

        // configuracion standard
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                location, maxFileSize, maxRequestSize, fileSizeThreshold);
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig",
                multipartConfigElement);

        HashMap<String, String> filePaths = new HashMap<>();
        for (Part part : request.raw().getParts()) {
            if (part.getContentType().contains("text")){
                // TODO: me mandaron un "texto" y no un archivo, corroborar que los .txt no terminan acá
                this.respuesta.setCode(400);
                this.respuesta.setMessage("No se pueden subir archivos de texto");
                jsonResponse = this.gson.toJson(this.respuesta);
                response.body(jsonResponse);
                return response.body();
            } else {
                String path = procesarFicheroParte(uploadDir, part);
                if (path != "") {
                    filePaths.put(part.getName(), path);
                } else {
                    this.respuesta.setCode(400);
                    this.respuesta.setMessage("Problema al subir un fichero");
                    jsonResponse = this.gson.toJson(this.respuesta);
                    response.body(jsonResponse);
                    return response.body();
                }
            }
        }
        this.respuesta.setCode(200);
        this.respuesta.setMessage("Se produjo exitosamente la carga de archivos.");

        ArchivosSubidosResponse archivosSubidosResponse = new ArchivosSubidosResponse();

        archivosSubidosResponse.code    = this.respuesta.getCode();
        archivosSubidosResponse.message = this.respuesta.getMessage();
        archivosSubidosResponse.paths = filePaths;
        jsonResponse = this.gson.toJson(archivosSubidosResponse);
        response.body(jsonResponse);
        return response.body();
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public String cargarNuevoEgreso(Request request, Response response) {

        String jsonResponse;

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        EgresoRequest egresoRequest    = this.gson.fromJson(request.body(),EgresoRequest.class);

        Egreso egreso = asignarEgresoDesde(egresoRequest);

        if(egreso == null) {
            this.respuesta.setCode(400);
            this.respuesta.setMessage("Problema al cargar el egreso");
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }
        this.respuesta.setCode(200);
        this.respuesta.setMessage("Egreso cargado exitosamente");

        this.egresosMemo.add(egreso);

        CargaEgresoResponse cargaEgresoResponse = new CargaEgresoResponse();

        cargaEgresoResponse.code    = this.respuesta.getCode();
        cargaEgresoResponse.message = this.respuesta.getMessage();
        cargaEgresoResponse.id = egreso.getId();
        jsonResponse = this.gson.toJson(cargaEgresoResponse);
        response.body(jsonResponse);

        return response.body();
    }

    public String listadoDeEgresos(Request request, Response response) {

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }

        this.gson  = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .create();

        EntidadJuridica entidadJuridica = this.repoEntidadJuridica.buscar(usuario.getMiOrganizacion().getId());
        List<EgresoResponse> egresosAEnviar;
        String jsonResponse;

        List<Egreso> egresosBD = entidadJuridica.getEgresos();
        egresosAEnviar = egresosBD
                    .stream()
                    .map(this::mapearEgreso)
                    .collect(Collectors.toList());

        if (egresosAEnviar.isEmpty()) {
            this.respuesta.setCode(404);
            this.respuesta.setMessage("No hay egresos cargados");
            jsonResponse = this.gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }

        ListadoEgresos listadoEgresos = new ListadoEgresos();
        listadoEgresos.code    = 200;
        listadoEgresos.message = "Ok";
        listadoEgresos.egresos = egresosAEnviar;

        jsonResponse = this.gson.toJson(listadoEgresos);
        response.body(jsonResponse);
        egresosAEnviar.clear();

        return response.body();
    }

    public String mostrarEgreso(Request request, Response response) {

        Estandar usuario = (Estandar) PermisosRestController.verificarSesion(request,response);
        if(usuario == null) {
            return response.body();
        }
        String jsonResponse;
        Egreso egreso;

        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                .serializeNulls()
                .create();

        Repositorio<Egreso> repoEgresos = new Repositorio<>(new DaoHibernate<>(Egreso.class));
        egreso = repoEgresos.buscar(new Integer(request.params("egresoId")));

        if(egreso == null) {
            Gson gson = new Gson();
            this.respuesta.setCode(404);
            this.respuesta.setMessage("El egreso no existe");
            jsonResponse = gson.toJson(this.respuesta);
            response.body(jsonResponse);
            return response.body();
        }

        EgresoDetalladoResponse egresoDetallado = new EgresoDetalladoResponse();
        egresoDetallado.code         = 200;
        egresoDetallado.message      = "Ok";
        egresoDetallado.egreso       = egreso;
        egresoDetallado.estaSuscrito = verificarSuscripcion(usuario,egreso);

        jsonResponse = this.gson.toJson(egresoDetallado);
        response.body(jsonResponse);

        return jsonResponse;
    }

    private Egreso asignarEgresoDesde (EgresoRequest egresoRequest) {
        Proveedor proveedor;
        MedioDePago medioDePago;
        TipoDocumento tipoDocumento;

        Repositorio<Proveedor> repoProveedores   = new Repositorio<>(new DaoHibernate<>(Proveedor.class));
        Repositorio<TipoDocumento> repoTipoDocumento = new Repositorio<>(new DaoHibernate<>(TipoDocumento.class));
        Repositorio<MedioDePago> repoMediosDePagos = new Repositorio<>(new DaoHibernate<>(MedioDePago.class));
        Repositorio<Egreso> repoEgresos = new Repositorio<>(new DaoHibernate<>(Egreso.class));

        try {
             proveedor     = repoProveedores.buscar(egresoRequest.proveedor);
             medioDePago   = repoMediosDePagos.buscar(egresoRequest.medioDePago.id);
             tipoDocumento = repoTipoDocumento.buscar(egresoRequest.documentoComercial.tipo);
             if(proveedor == null || medioDePago == null || tipoDocumento == null) {
                return null;
             }
        }
        catch (NoResultException ex){
            return null;
        }
        Pago pago = new Pago();
        pago.setMedioDePago(medioDePago);
        pago.setCodigoAsociado(egresoRequest.medioDePago.dato);

        DocumentoComercial documentoComercial = new DocumentoComercial();
        documentoComercial.setTipo(tipoDocumento);
        documentoComercial.setNumDocumento(egresoRequest.documentoComercial.numeroDocumento);
        documentoComercial.setFechaDePedido(egresoRequest.documentoComercial.fechaDePedido);
        documentoComercial.setFechaDeEntrega(egresoRequest.documentoComercial.fechaDeEntrega);
        documentoComercial.setDescripcion(egresoRequest.documentoComercial.descripcion);
        

      
        Egreso egreso = new BuilderEgresoConcreto()
                .agregarFechaOperacion(egresoRequest.fechaOperacion)
                .agregarCantidadPresupuestos(egresoRequest.cantidadPresupuestos)
                .agregarProveedor(proveedor)
                .agregarPago(pago)
                .agregarDocumentoComercial(documentoComercial)
                .build();
       

        if(egresoRequest.organizacion_id!=0){
        	if(egresoRequest.tipo==0) {
        		EntidadJuridica entidadJuridica=repoEntidadJuridica.buscar(egresoRequest.organizacion_id);
        		egreso.setOrganizacion(entidadJuridica);
        	}
        	else {
        		EntidadBase entidadBase= repoEntidadBase.buscar(egresoRequest.organizacion_id);
        		egreso.setOrganizacion(entidadBase);
        	}
        }
        
        List<ItemEgreso> items = egresoRequest.items
                .stream()
                .map(item->mapearItem(item,egreso))
                .collect(Collectors.toList());

        egreso.agregarItems(items);
        repoEgresos.agregar(egreso);
            //Modificar items con el egreso, o sea linkearlos
       // relacionarItemsConEgreso(items,egreso);

        return egreso;
    }

    private ItemEgreso mapearItem(ItemRequest itemRequest,Egreso egreso) {
        ItemEgreso itemEgreso = new ItemEgreso();
        Item item=null;
        itemEgreso.setPrecio(itemRequest.precio);
        itemEgreso.setCantidad(itemRequest.cantidad);

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
       
        itemEgreso.setItem(item);
        itemEgreso.setEgresoAsociado(egreso);

        return  itemEgreso;
    }


    private void relacionarItemsConEgreso(List<ItemEgreso> items, Egreso egreso) {
        Repositorio<ItemEgreso> repoItems = new Repositorio<>(new DaoHibernate<>(ItemEgreso.class));
        items.forEach(unItem -> {
                unItem.setEgresoAsociado(egreso);
                repoItems.modificar(unItem);
        });
    }

    private EgresoResponse mapearEgreso(Egreso egreso) {
        EgresoResponse egresoResponse = new EgresoResponse();
        egresoResponse.id             = egreso.getId();
        egresoResponse.validado       = egreso.isValidado();
        egresoResponse.montoTotal = egreso.getValorTotal();
        egresoResponse.fechaOperacion = egreso.getFechaOperacion();
        return egresoResponse;
    }

    private boolean verificarSuscripcion(Estandar estandar, Egreso egreso) {
        if(egreso.getRevisores().isEmpty()){
            return false;
        }
        return egreso.getRevisores()
                        .stream()
                        .anyMatch(unRevisor->unRevisor.getId() == estandar.getId());
    }

    private class ArchivosSubidosResponse {
        public int code;
        public String message;
        public HashMap<String, String> paths;
    }

    private class CargaEgresoResponse {
        public int code;
        public String message;
        public int id;
    }

    private class ListadoEgresos {
        public int code;
        public String message;
        public List<EgresoResponse> egresos;
    }

    private class EgresoDetalladoResponse {
        @Expose
        public int code;
        @Expose
        public String message;
        @Expose
        public Egreso egreso;
        @Expose
        public boolean estaSuscrito;
    }
}
