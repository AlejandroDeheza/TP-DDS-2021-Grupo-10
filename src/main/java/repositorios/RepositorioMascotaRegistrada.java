package repositorios;

import modelo.mascota.MascotaRegistrada;
import modelo.usuario.Usuario;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.util.List;

public class RepositorioMascotaRegistrada implements WithGlobalEntityManager {

  public void agregar(MascotaRegistrada mascotaRegistrada) {
    entityManager().persist(mascotaRegistrada);
  }

  public MascotaRegistrada getPorId(Long id) {
    return entityManager().find(MascotaRegistrada.class, id);
  }

  public MascotaRegistrada getPorNombre(String nombre) {
    return entityManager().createQuery("from MascotaRegistrada c where c.nombre like :nombre",
        MascotaRegistrada.class).setParameter("nombre", "%" + nombre + "%").getResultList().get(0);
  }

}
