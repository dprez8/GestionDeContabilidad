package Domain.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import Domain.Controllers.jwt.TokenService;
import com.google.gson.Gson;

import Domain.Controllers.DTO.Respuesta;
import Domain.Entities.Operaciones.CategoriaOperacion;
import Domain.Entities.Operaciones.CriterioOperacion;
import Domain.Entities.Operaciones.Presupuesto;
import Domain.Entities.Operaciones.Egreso.Egreso;
import Domain.Repositories.Repositorio;
import Domain.Repositories.Daos.DaoHibernate;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;

public class CategoriasEgresosController extends GenericController{


	private Repositorio<CriterioOperacion> repoCriterio;
	private Repositorio<CategoriaOperacion> repoCategoria;
	private Repositorio<Egreso> repoEgreso;
	private Repositorio<Presupuesto> repoPresupuesto;

	public CategoriasEgresosController(TokenService tokenService, String tokenPrefix){
		super(tokenService,tokenPrefix);
	}
	public String crearCategoria(Request request, Response response) throws IOException {

		/**
		 * {
		 *     descripcion:"",
		 *     criterioId:1
		 * }
		 **/
		Gson gson = new Gson();
		String json = request.raw().getReader().lines().collect(Collectors.joining());
		JsonObject jsonRequest = gson.fromJson(json, JsonObject.class);
		this.repoCriterio = new Repositorio<>(new DaoHibernate<>(CriterioOperacion.class));
		this.repoCategoria = new Repositorio<>(new DaoHibernate<>(CategoriaOperacion.class));
		CategoriaResponse categoriaResponse = new CategoriaResponse();

		try {
			CriterioOperacion criterio= this.repoCriterio.buscar(jsonRequest.get("criterioId").getAsInt());

			CategoriaOperacion categoria = new CategoriaOperacion();

			categoria.setCriterio(criterio);
			categoria.setDescripcion(jsonRequest.get("nombre").getAsString());

			repoCategoria.agregar(categoria);


			categoriaResponse.code=200;
			categoriaResponse.message="Categoria creada exitosamente";
			categoriaResponse.categoriaId= categoria.getId();
		}
		catch (Exception ex){
			return respuesta(response,404,"Error al crear la Categoria");
		}


		String jsonCategoria = gson.toJson(categoriaResponse);

		response.body(jsonCategoria);

		return response.body();
	}

	public String asociarCategoriaEgreso(Request request, Response response){

		Gson gson = new Gson();
		Respuesta respuesta= new Respuesta();
		CategoriaRequest categoriaRequest;
		categoriaRequest = gson.fromJson(request.body(),CategoriaRequest.class);
		this.repoCategoria = new Repositorio<>(new DaoHibernate<>(CategoriaOperacion.class));
		this.repoEgreso = new Repositorio<>(new DaoHibernate<>(Egreso.class));

		try {
			Egreso egreso= this.repoEgreso.buscar(categoriaRequest.id);

			if(!categoriaRequest.categorias.isEmpty()) {

				categoriaRequest.categorias.forEach(categoriaId -> {
					CategoriaOperacion categoria = this.repoCategoria.buscar(categoriaId);
					egreso.addCategorias(categoria);
				});
				this.repoEgreso.modificar(egreso);
				respuesta.setCode(200);
				respuesta.setMessage("Categorias asociadas exitosamente");
			}

		}
		catch (Exception ex){
			respuesta.setCode(400);
			respuesta.setMessage("Categorias no pudieron cargarse");
		}


		String jsonCategorias = gson.toJson(respuesta);

		response.body(jsonCategorias);

		return response.body();
	}

	public class CategoriaRequest{
		public int id;
		public List<Integer> categorias;
	}

	public class CategoriaResponse{
		public int code;
		public String message;
		public int categoriaId;
	}
}
