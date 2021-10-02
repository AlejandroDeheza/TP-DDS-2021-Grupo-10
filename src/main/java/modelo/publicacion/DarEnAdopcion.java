package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaRegistrada;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.pregunta.RespuestaDelDador;
import modelo.usuario.Usuario;
import repositorios.RepositorioDarEnAdopcion;
import javax.persistence.*;
import java.util.List;

@Entity
public class DarEnAdopcion extends Publicacion {

  @ManyToOne(cascade = CascadeType.ALL)
  private Usuario publicador;

  @ManyToOne(cascade = CascadeType.ALL)
  private Asociacion asociacion;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "Id_publicacion_dar_adopcion")
  private List<RespuestaDelDador> respuestasDelDador;

  @ManyToOne(cascade = CascadeType.ALL)
  private MascotaRegistrada mascotaEnAdopcion;

  @Transient
  private RepositorioDarEnAdopcion repositorio;

  private Boolean estaActiva = true;

  // para hibernate
  private DarEnAdopcion() {

  }

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
    estaActiva = false;
    repositorio.actualizar(this);
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
