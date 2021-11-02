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

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //Correcto
  private Usuario publicador;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //Correcto
  private Asociacion asociacion;

  @OneToMany(cascade = CascadeType.ALL) //Correcto
  @JoinColumn(name = "Id_publicacion_dar_adopcion")
  private List<RespuestaDelDador> respuestasDelDador;

  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) //Correcto
  private MascotaRegistrada mascotaEnAdopcion;

  private Boolean estaActiva = true;

  // para hibernate
  private DarEnAdopcion() {

  }

  public DarEnAdopcion(Usuario publicador, MascotaRegistrada mascotaEnAdopcion,
                       List<RespuestaDelDador> respuestasDelDador, Asociacion asociacion) {
    this.publicador = publicador;
    this.asociacion = asociacion;
    this.mascotaEnAdopcion = mascotaEnAdopcion;
    this.respuestasDelDador = respuestasDelDador;
  }

  @Override
  public void notificarAlPublicador(Usuario adoptante) {
    publicador.getNotificadorPreferido().notificarQuierenAdoptarTuMascota(adoptante, mascotaEnAdopcion);
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
