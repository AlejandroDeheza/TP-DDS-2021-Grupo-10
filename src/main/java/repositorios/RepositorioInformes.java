package repositorios;

import modelo.informe.InformeConQR;
import modelo.informe.InformeRescate;
import modelo.informe.InformeSinQR;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioInformes extends Repositorio<InformeRescate> {

  public RepositorioInformes() {
    super(InformeRescate.class);
  }

  public List<InformeConQR> listarInformesConQR() {
    return entityManager()
        .createQuery("from InformeConQR", InformeConQR.class)
        .getResultList();
  }

  public List<InformeSinQR> listarInformesSinQR() {
    return entityManager()
        .createQuery("from InformeSinQR", InformeSinQR.class)
        .getResultList();
  }

  public List<InformeRescate> informesDeUltimosNDias(Integer diasPreviosABuscar) {
    LocalDate fechaFiltro = LocalDate.now().minusDays(diasPreviosABuscar);
    return getInformesPendientes().stream()
        .filter(informe -> informe.getFechaEncuentro().isAfter(fechaFiltro))
        .collect(Collectors.toList());
  }

  public List<InformeRescate> getInformesPendientes() {
    return listarTodos().stream()
        .filter(i -> !i.getEstaProcesado()).collect(Collectors.toList());
  }

  public List<InformeRescate> getInformesProcesados() {
    return listarTodos().stream()
        .filter(InformeRescate::getEstaProcesado).collect(Collectors.toList());
  }

}

