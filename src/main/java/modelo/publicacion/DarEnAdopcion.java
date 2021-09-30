package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaRegistrada;
import modelo.persona.DatosDeContacto;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.pregunta.RespuestaDelDador;
import modelo.usuario.Usuario;
import repositorios.RepositorioDarEnAdopcion;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

public class DarEnAdopcion extends Publicacion {
  @ManyToOne
  private Usuario publicador;
  @ManyToOne
  private Asociacion asociacion;
  @OneToMany
  @JoinColumn(name = "Id_publicacion_dar_adopcion")
  private List<RespuestaDelDador> respuestasDelDador;
  @ManyToOne
  private MascotaRegistrada mascotaEnAdopcion;
  @Transient
  private RepositorioDarEnAdopcion repositorio;

  private Boolean estaActiva = true;

  public DarEnAdopcion(Usuario publicador, MascotaRegistrada mascotaEnAdopcion,
                       RepositorioDarEnAdopcion repositorio, List<RespuestaDelDador> respuestasDelDador,
                       Asociacion asociacion) {
    this.publicador = publicador;
    this.asociacion = asociacion;
    this.mascotaEnAdopcion = mascotaEnAdopcion;
    this.repositorio = repositorio;
    this.respuestasDelDador = respuestasDelDador;
  }

  @Override
  public void notificarAlPublicador(Usuario adoptante) {
    publicador.getNotificadorPreferido().notificarQuierenAdoptarTuMascota(adoptante, mascotaEnAdopcion);
    repositorio.marcarComoProcesada(this);
    estaActiva = false;
  }

  public int cantidadConLasQueMatchea(List<RespuestaDelAdoptante> comodidades) {
    return (int) respuestasDelDador.stream().filter(respuesta -> respuesta.correspondeConAlguna(comodidades)).count();
  }

  public Asociacion getAsociacion() {
    return asociacion;
  }

  public MascotaRegistrada getMascotaEnAdopcion() {
    return mascotaEnAdopcion;
  }

  public List<RespuestaDelDador> getRespuestasDelDador() {
    return respuestasDelDador;
  }

  public Boolean estaActiva() {
    return estaActiva;
  }
}
