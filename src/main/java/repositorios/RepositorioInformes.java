package repositorios;

import modelo.informe.InformeRescate;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioInformes implements WithGlobalEntityManager {

  public List<InformeRescate> informesDeUltimosNDias(Integer diasPreviosABuscar) {
    LocalDate fechaFiltro = LocalDate.now().minusDays(diasPreviosABuscar);
    return getInformesPendientes().stream()
        .filter(informe -> informe.getFechaEncuentro().isAfter(fechaFiltro))
        .collect(Collectors.toList());
  }

  public List<InformeRescate> getInformesPendientes() {
    return entityManager()
        .createQuery("from InformeRescate", InformeRescate.class)
        .getResultList().stream()
        .filter(i -> !i.getEstaProcesado()).collect(Collectors.toList());
  }

  public List<InformeRescate> getInformesProcesados() {
    return entityManager()
        .createQuery("from InformeRescate", InformeRescate.class)
        .getResultList().stream()
        .filter(InformeRescate::getEstaProcesado).collect(Collectors.toList());
  }

  public void agregarInforme(InformeRescate informeRescate) {
    entityManager().persist(informeRescate);
  }
}

