package servicios.repositorios;

import modelo.InformeMascotaEncontrada;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InformesRepository {

    private static InformesRepository informesRepository;
    private List<InformeMascotaEncontrada> informesPendientes = new ArrayList<>();
    private List<InformeMascotaEncontrada> informesProcesados = new ArrayList<>();

    private InformesRepository() {};

    public static InformesRepository getInstance() {
        if(informesRepository == null) {
            informesRepository = new InformesRepository();
        }
        return informesRepository;
    }

    public List<InformeMascotaEncontrada> listarMascotasEncontradasEnUltimosNDias(Integer diasPreviosABuscar){
        LocalDate fechaFiltro = LocalDate.now().minusDays(diasPreviosABuscar);
        return informesPendientes.stream()
                .filter(informe -> informe.getFechaEncuentroAnimal().isAfter(fechaFiltro))
                .collect(Collectors.toList());
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

