package Domain.Entities.Organizacion;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("osc")
public class Osc extends CategoriaEntidadJuridica{

    @Expose
    @Column(name = "codigo_osc")
    private String codigoOsc;

	public String getCodigoOsc() {
		return codigoOsc;
	}

	public void setCodigoOsc(String codigoOsc) {
		this.codigoOsc = codigoOsc;
	}

    
}
