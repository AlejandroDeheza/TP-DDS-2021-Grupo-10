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

  public List<MascotaRegistrada> obtenerMascotasDeUnDuenio(Usuario usuario){
    return entityManager().createQuery("from MascotaRegistrada m where m.duenio = :usuario",
        MascotaRegistrada.class).setParameter("usuario", usuario).getResultList();
  }

}
