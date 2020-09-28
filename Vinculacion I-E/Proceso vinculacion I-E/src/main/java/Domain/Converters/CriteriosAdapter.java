package Domain.Converters;

import Domain.Entities.Criterios.*;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CriteriosAdapter extends TypeAdapter<Criterio> {

    @Override
    public void write(final JsonWriter jsonWriter, final Criterio criterio ) throws IOException {
    }

    @Override
    public Criterio read( final JsonReader jsonReader ) throws IOException {

        JsonToken token = jsonReader.peek();

        if(token.equals(JsonToken.STRING)) {
            // Nos llego un solo criterio
            String criterioString = jsonReader.nextString();
            CriterioUnico criterioUnico = criterioFromString(criterioString);
            return criterioUnico;
        } else if(token.equals(JsonToken.BEGIN_ARRAY)) {
            // Nos llego varios criterios, por lo tanto MIX
            List<CriterioUnico> criterioList = new ArrayList<>();
            jsonReader.beginArray();
            while(!token.equals(JsonToken.END_ARRAY)) {
                String criterioString = jsonReader.nextString();
                criterioList.add(criterioFromString(criterioString));
                token = jsonReader.peek();
            }
            jsonReader.endArray();
            Mix criterioMix = new Mix();
            criterioMix.setCriteriosUnicos(criterioList);

            return criterioMix;
        }

        return null;
    }

    public CriterioUnico criterioFromString(String string) {
        CriterioUnico criterio = null;

        switch (string) {
            case "OrdenValorPrimeroEgreso": criterio = new OrdenValorPrimeroEgreso(); break;
            case "OrdenValorPrimeroIngreso": criterio = new OrdenValorPrimeroIngreso(); break;
            case "Fecha": criterio = new Fecha(); break;
        }

        return criterio;
    }
}
