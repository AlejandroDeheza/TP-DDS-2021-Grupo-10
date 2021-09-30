package repositorios;

import modelo.informe.InformeRescate;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioInformes implements WithGlobalEntityManager {

  public void agregarInformeRescate(InformeRescate informe) {
    entityManager().persist(informe);
  }

  public List<InformeRescate> informesDeUltimosNDias(Integer diasPreviosABuscar) {
    LocalDate fechaFiltro = LocalDate.now().minusDays(diasPreviosABuscar);
    return getInformesPendientes().stream()
        .filter(informe -> informe.getFechaEncuentro().isAfter(fechaFiltro))
        .collect(Collectors.toList());
  }

  public void marcarInformeComoProcesado(InformeRescate informeAProcesar) {
    entityManager().remove(informeAProcesar);
    entityManager().persist(informeAProcesar);
  }

  public List<InformeRescate> getInformesPendientes() {
    return entityManager()
        .createQuery("from InformeRescate", InformeRescate.class)
        .getResultList().stream()
        .filter(i -> !i.getEsta_procesado()).collect(Collectors.toList());
  }

  public List<InformeRescate> getInformesProcesados() {
    return entityManager()
        .createQuery("from InformeRescate", InformeRescate.class)
        .getResultList().stream()
        .filter(InformeRescate::getEsta_procesado).collect(Collectors.toList());
  }

}

