package Domain.Entities;

import java.util.ArrayList;
import java.util.List;

public class Mix implements Criterio{
    private List<CriterioUnico> criterios;

    public Mix(){
        this.criterios = new ArrayList<>();
    }

    @Override
    public void aplicate() {

    }
}
