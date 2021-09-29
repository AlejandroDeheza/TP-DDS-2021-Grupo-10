package modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntitidadPersistente {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }
}
