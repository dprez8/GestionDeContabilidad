package Domain.Entities.EntidadPersistente;

import javax.persistence.*;

@MappedSuperclass
public class EntidadPersistente {
    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return id;
    }
}
