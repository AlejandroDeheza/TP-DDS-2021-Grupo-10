package modelo.informe;

import client.ObtenerHogaresClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import modelo.hogares.Hogar;
import modelo.mascota.Mascota;
import modelo.persona.Persona;
import modelo.publicacion.Publicacion;
import repositorios.RepositorioPublicaciones;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InformeMascotaSinDuenio extends InformeMascotaEncontrada {

    ObtenerHogaresClient hogaresClient = new ObtenerHogaresClient();
    private Mascota mascota;

    public InformeMascotaSinDuenio(Persona rescatista, LocalDate fechaEncuentro, Ubicacion direccion, Ubicacion lugarDeEncuentro,Mascota mascota) {
        super(rescatista, fechaEncuentro, direccion, mascota.getFotos(), lugarDeEncuentro,mascota.getCaracteristicas());
        this.mascota=mascota;
    }

    public Persona getRescatista() {
      return this.getRescatista();
    };

    public Mascota getMascota() {
        return mascota;
    }

    @Override
    public void procesarInforme() {
        super.procesarInforme();
        RepositorioPublicaciones.getInstance().agregarPublicacion(new Publicacion(this.getRescatista().getDatosDeContacto(),getFotosMascota()));
    }

    public List<Hogar> getHogaresTransitorios(Integer radioCercania) throws JsonProcessingException{
        List<Hogar> hogares = this.hogaresClient.obtenerTodosLosHogares();
        return hogares.stream().filter( hogar -> hogar.esPosibleHogarDeTransito(radioCercania,getMascota(),this.getDireccion())).collect(Collectors.toList());
    }

}
