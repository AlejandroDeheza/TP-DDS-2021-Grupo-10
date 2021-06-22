package repositorios;

import modelo.informe.Informe;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioInformes {
  private static RepositorioInformes repositorioInformes = new RepositorioInformes();
  private List<Informe> informesPendientes = new ArrayList<>();
  private List<Informe> informesProcesados = new ArrayList<>();

  public void agregarInformeMascotaEncontrada(Informe informe) {
    informesPendientes.add(informe);
  }

  public List<Informe> listarMascotasEncontradasEnUltimosNDias(Integer diasPreviosABuscar) {
    LocalDate fechaFiltro = LocalDate.now().minusDays(diasPreviosABuscar);
    return informesPendientes.stream()
        .filter(informe -> informe.getFechaEncuentro().isAfter(fechaFiltro))
        .collect(Collectors.toList());
  }

  public void marcarInformeComoProcesado(Informe informeAProcesar) {
    this.informesPendientes.remove(informeAProcesar);
    this.informesProcesados.add(informeAProcesar);
  }


  //usamos el constructor solo para tests
  public RepositorioInformes() {}
  //usamos el getInstance en el codigo de produccion
  public static RepositorioInformes getInstance() {
    return repositorioInformes;
  }

  public List<Informe> getInformesPendientes() {
    return informesPendientes;
  }

  public List<Informe> getInformesProcesados() {
    return informesProcesados;
  }

}

