package repositorios;

import modelo.informe.InformeMascotaEncontrada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioInformes {
  private static RepositorioInformes repositorioInformes = new RepositorioInformes();
  private List<InformeMascotaEncontrada> informesPendientes = new ArrayList<>();
  private List<InformeMascotaEncontrada> informesProcesados = new ArrayList<>();

  public void agregarInformeMascotaEncontrada(InformeMascotaEncontrada informe) {
    informesPendientes.add(informe);
  }

  public List<InformeMascotaEncontrada> listarMascotasEncontradasEnUltimosNDias(Integer diasPreviosABuscar) {
    LocalDate fechaFiltro = LocalDate.now().minusDays(diasPreviosABuscar);
    return informesPendientes.stream()
        .filter(informe -> informe.getFechaEncuentro().isAfter(fechaFiltro)) //Podria ser tambien informe.mascotaEncontrada.contexto.getFechaEncuentro()
        .collect(Collectors.toList());
  }

  public void marcarInformeComoProcesado(InformeMascotaEncontrada informeAProcesar) {
    this.informesPendientes.remove(informeAProcesar);
    this.informesProcesados.add(informeAProcesar);
  }


  //usamos el constructor solo para tests
  public RepositorioInformes() {}
  //usamos el getInstance en el codigo de produccion
  public static RepositorioInformes getInstance() {
    return repositorioInformes;
  }

  public List<InformeMascotaEncontrada> getInformesPendientes() {
    return informesPendientes;
  }

  public List<InformeMascotaEncontrada> getInformesProcesados() {
    return informesProcesados;
  }

}

