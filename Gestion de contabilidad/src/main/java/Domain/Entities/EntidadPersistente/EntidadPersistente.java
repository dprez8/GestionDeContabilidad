package Domain.Entities.EntidadPersistente;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@MappedSuperclass
public class EntidadPersistente {
    @Expose
    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }
}
