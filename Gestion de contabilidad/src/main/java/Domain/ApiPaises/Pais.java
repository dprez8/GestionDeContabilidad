package Domain.ApiPaises;

import java.util.List;
import java.util.Optional;

public class Pais {
	public String id;
	public String name;
	public String currency_id;
	private int idPais;
	private static int contadorPaises;
	

	public void setIdPais(int idPais) {
		this.idPais = idPais;
	}

	public int getIdPais(){
		return idPais;
	}
	public static void asignarIdAPaises(List<Pais> paises){
		paises.forEach(pais->pais.asignarId());
	}
	
	public void asignarId(){
		contadorPaises++;
		this.idPais=contadorPaises;
	}

	public static Optional<Pais> paisConId(List<Pais> listaPaises,int idElegido){
		return listaPaises.stream().filter(p->p.idPais==idElegido).findFirst();
	}
}
