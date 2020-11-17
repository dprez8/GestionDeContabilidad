package Domain.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;

import Domain.Controllers.AdaptersJson.LocalDateAdapter;
import Domain.Controllers.DTO.EgresoRequest;
import Domain.Controllers.Utils.FormFileManager;
import Domain.Controllers.jwt.TokenService;
import Domain.Entities.DatosDeOperaciones.DocumentoComercial;
import Domain.Entities.Operaciones.Egreso.Egreso;
import com.google.gson.Gson;

import Domain.Entities.DatosDeOperaciones.TipoDocumento;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import com.google.gson.GsonBuilder;
import spark.Request;
import spark.Response;

public class DocumentoComercialController extends GenericController {

	public DocumentoComercialController(TokenService tokenService, String tokenPrefix) {
		super(tokenService,tokenPrefix);
		repoDocumentoComercial = new Repositorio<>(new DaoHibernate<>(DocumentoComercial.class));
	}

	private Repositorio<TipoDocumento> repoTipo;
	private Repositorio<DocumentoComercial> repoDocumentoComercial;

	private static List<String> tiposDocumentoComercial = Arrays.asList("application/pdf",
			"text/html",
			"text/plain",
			"image/jpeg",
			"image/png",
			"image/bmp",
			"application/msword");

	public String cargarArchivo(Request request, Response response) throws IOException, ServletException {
		this.gson  = new GsonBuilder().create();

		File uploadDir = null;
		try {
			uploadDir = FormFileManager.crearDirectorio();
		} catch (Exception exception) {
			return error(response, "Error interno del servidor al crear directorio para la carga de ficheros");
		}

		// configuracion standard
		request.raw().setAttribute("org.eclipse.jetty.multipartConfig", FormFileManager.crearMultiPartConfigElement());

		HashMap<String, String> filePaths = new HashMap<>();
		for (Part part : request.raw().getParts()) {
			String contentType = part.getContentType();
			if (tiposDocumentoComercial.contains(contentType)){
				String path = "";
				try {
					path = FormFileManager.procesarFicheroParte(part);
				} catch (Exception exception) {
					return error(response, "Problema al procesar el fichero");
				}

				if (path != "") {
					filePaths.put(part.getName(), path);
				} else {
					return error(response, "Problema al subir un fichero");
				}
			} else {
				return error(response, "Se encontró un tipo de archivo no válido");
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

	public String modificarArchivo(Request request, Response response) throws IOException, ServletException {
		this.gson  = new GsonBuilder().create();

		File uploadDir = null;
		try {
			uploadDir = FormFileManager.crearDirectorio();
		} catch (Exception exception) {
			return error(response, "Error interno del servidor al crear directorio para la carga de ficheros");
		}

		request.raw().setAttribute("org.eclipse.jetty.multipartConfig", FormFileManager.crearMultiPartConfigElement());

		for (Part part : request.raw().getParts()) {
			String contentType = part.getContentType();
			if (tiposDocumentoComercial.contains(contentType)){
				String path = "";
				try {
					path = FormFileManager.procesarFicheroParte(part);
				} catch (FileNotFoundException exception) {
					return error(response, "No se encontro el fichero a reemplazar");
				} catch (Exception exception) {
					return error(response, "Problema al procesar el fichero");
				}

				if (path == "") {
					return error(response, "Problema al reemplazar el fichero");
				}
			} else {
				return error(response, "Se encontró un tipo de archivo no válido");
			}
		}

		return respuesta(response, 200, "Se produjo exitosamente la modificacion de archivos.");
	}

	public String borrarArchivo(Request request, Response response) {
		this.gson  = new GsonBuilder()
				.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
				.create();

		ArchivosBorrarRequest archivosBorrarRequest = null;
		try {
			archivosBorrarRequest = this.gson.fromJson(request.body(), ArchivosBorrarRequest.class);
		}
		catch(Exception ex){
			return error(response, "Datos erroneos para borrar archivos");
		}

		DocumentoComercial documentoComercial = null;
		try {
			documentoComercial = this.repoDocumentoComercial.buscar(archivosBorrarRequest.documentoComercial);
		} catch (Exception exception) {

		}

		if (documentoComercial == null) {
			return error(response, "No se encontro el documento comercial");
		}

		for (String path : archivosBorrarRequest.paths) {
			try {
				FormFileManager.borrarFichero(path);
			} catch (FileNotFoundException exception) {
				return error(response, "No se encontró fichero a borrar");
			} catch (Exception exception) {
				return error(response, "Error al borrar fichero");
			}
		}

		documentoComercial.setPathAdjunto(null);
		this.repoDocumentoComercial.modificar(documentoComercial);

		return respuesta(response, 200, "Se borró correctamente el fichero");
	}

	public String listadoTiposDocumento(Request request, Response response){
		
		 	Gson gson = new Gson();
	        List<TipoDocumento> tiposDocumento;
	        TipoDocumentoRespuesta tipoRespuesta = new TipoDocumentoRespuesta();

	   	 	this.repoTipo = new Repositorio<TipoDocumento>(new DaoHibernate<TipoDocumento>(TipoDocumento.class));
	      
	        try {
		        tiposDocumento= this.repoTipo.buscarTodos();
		        tipoRespuesta.code = 200;
		        tipoRespuesta.message = "Tipos de documento cargados exitosamente";
		        tipoRespuesta.data= tiposDocumento;
		        response.status(200);
	        }
	        catch (NullPointerException ex){
	        	tipoRespuesta  .code=404;
	            tipoRespuesta.message="No se logró cargar los tipos de documento";
	            response.status(404);
	         }
	        
	        catch(NoResultException nf){
	        	tipoRespuesta.code=404;
	            tipoRespuesta.message="Ningun tipo de documento registrado, por favor crearlo";
	            response.status(404);
	        }
	       
	       
	        String jsonTiposDocumento = gson.toJson(tipoRespuesta);
	        response.body(jsonTiposDocumento);

	        return response.body();
	}
	
	public class TipoDocumentoRespuesta{
		public int code;
		public String message;
		public List<TipoDocumento> data;
	}

	private class ArchivosSubidosResponse {
		public int code;
		public String message;
		public HashMap<String, String> paths;
	}

	private class ArchivosBorrarRequest {
		public int documentoComercial;
		public String[] paths;
	}

}
