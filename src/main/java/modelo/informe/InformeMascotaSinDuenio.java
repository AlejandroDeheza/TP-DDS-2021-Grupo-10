package modelo.informe;

import client.ObtenerHogaresClient;
import client.response.HogaresResponse;
import modelo.hogares.Hogar;
import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.Persona;
import modelo.publicacion.Publicacion;
import modelo.usuario.Usuario;
import repositorios.RepositorioInformes;
import repositorios.RepositorioPublicaciones;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class InformeMascotaSinDuenio extends InformeMascotaEncontrada {

    private Mascota mascota;
    ObtenerHogaresClient hogaresClient = new ObtenerHogaresClient();

    public InformeMascotaSinDuenio(Persona rescatista, LocalDate fechaEncuentro, Ubicacion direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro, Caracteristica estadoActualMascota, RepositorioInformes repo) {
        super(rescatista, fechaEncuentro, direccion, fotosMascota, lugarDeEncuentro, estadoActualMascota, repo);
        this.mascota = mascota;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Persona getRescatista() {
      return this.getRescatista();
    };

    @Override
    public void procesarInforme() {
        super.procesarInforme();
        RepositorioPublicaciones.getInstance().agregarPublicacion(new Publicacion(this.getRescatista().getDatosDeContacto(),this.getMascota().getFotos()));
    }

    public List<Hogar> getHogaresTransitorios(Integer radioCercania){

        List<Hogar> hogares = this.hogaresClient.obtenerTodosLosHogares();
        return hogares.stream().filter( hogar -> hogar.esPosibleHogarDeTransito(radioCercania,this.getMascota(),this.getDireccion())).collect(Collectors.toList());
    }

}
