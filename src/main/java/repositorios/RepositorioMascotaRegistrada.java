package repositorios;

import modelo.mascota.MascotaRegistrada;
import modelo.usuario.Usuario;
import java.util.List;

public class RepositorioMascotaRegistrada extends Repositorio<MascotaRegistrada> {

  public RepositorioMascotaRegistrada() {
    super(MascotaRegistrada.class);
  }

  public List<MascotaRegistrada> obtenerMascotasDeUnDuenio(Usuario usuario){
    return entityManager().createQuery("from MascotaRegistrada m where m.duenio = :usuario", MascotaRegistrada.class)
        .setParameter("usuario", usuario)
        .getResultList();
  }

}
