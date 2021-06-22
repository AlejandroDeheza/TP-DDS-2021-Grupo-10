package repositorios;

import modelo.informe.InformeRescate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioInformes {
  private static RepositorioInformes repositorioInformes = new RepositorioInformes();
  private List<InformeRescate> informesPendientes = new ArrayList<>();
  private List<InformeRescate> informesProcesados = new ArrayList<>();

  public void agregarInformeRescate(InformeRescate informe) {
    informesPendientes.add(informe);
  }

  public List<InformeRescate> informesDeUltimosNDias(Integer diasPreviosABuscar) {
    LocalDate fechaFiltro = LocalDate.now().minusDays(diasPreviosABuscar);
    return informesPendientes.stream()
        .filter(informe -> informe.getFechaEncuentro().isAfter(fechaFiltro))
        .collect(Collectors.toList());
  }

  public void marcarInformeComoProcesado(InformeRescate informeAProcesar) {
    this.informesPendientes.remove(informeAProcesar);
    this.informesProcesados.add(informeAProcesar);
  }


  //el repositorio, en codigo de produccion, lo inyectamos por constructor
  //usamos el constructor solo para tests
  public RepositorioInformes() {}
  //usamos el getInstance en Main
  public static RepositorioInformes getInstance() {
    return repositorioInformes;
  }

  public List<InformeRescate> getInformesPendientes() {
    return informesPendientes;
  }

  public List<InformeRescate> getInformesProcesados() {
    return informesProcesados;
  }

}

