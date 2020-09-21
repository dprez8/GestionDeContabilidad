package Domain.Entities.Criterios;

import Domain.Entities.Criterios.Criterio;
import Domain.Entities.Criterios.CriterioUnico;

import java.util.ArrayList;
import java.util.List;

public class Mix implements Criterio {
    private List<CriterioUnico> criterios;

    public Mix(){
        this.criterios = new ArrayList<>();
    }

    @Override
    public void aplicate() {

    }
}
