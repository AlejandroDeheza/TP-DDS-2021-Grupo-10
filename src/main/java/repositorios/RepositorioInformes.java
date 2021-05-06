package repositorios;

import modelo.informe.InformeMascotaEncontrada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositorioInformes {

    private static RepositorioInformes repositorioInformes;
    private List<InformeMascotaEncontrada> informesPendientes = new ArrayList<>();
    private List<InformeMascotaEncontrada> informesProcesados = new ArrayList<>();

    private RepositorioInformes() {};

    public static RepositorioInformes getInstance() {
        if(repositorioInformes == null) {
            repositorioInformes = new RepositorioInformes();
        }
        return repositorioInformes;
    }

    public List<InformeMascotaEncontrada> listarMascotasEncontradasEnUltimosNDias(Integer diasPreviosABuscar){
        LocalDate fechaFiltro = LocalDate.now().minusDays(diasPreviosABuscar);
        return informesPendientes.stream()
                .filter(informe -> informe.getFechaEncuentroAnimal().isAfter(fechaFiltro))
                .collect(Collectors.toList());
    }

    public void procesarInforme(InformeMascotaEncontrada informeAProcesar){
        this.informesPendientes.remove(informeAProcesar);
        this.informesProcesados.add(informeAProcesar);
    }

    public List<InformeMascotaEncontrada> getInformesPendientes() {
        return informesPendientes;
    }

    public List<InformeMascotaEncontrada> getInformesProcesados() {
        return informesProcesados;
    }

    public void agregarInformeMascotaEncontrada(InformeMascotaEncontrada informe) {
        informesPendientes.add(informe);
    }

}

