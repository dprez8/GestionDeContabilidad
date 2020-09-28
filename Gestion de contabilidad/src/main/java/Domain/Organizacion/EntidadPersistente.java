package Domain.Organizacion;

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
