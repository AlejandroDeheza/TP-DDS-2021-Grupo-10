package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaRegistrada;
import modelo.persona.DatosDeContacto;
import modelo.pregunta.Respuesta;
import modelo.usuario.Usuario;
import repositorios.RepositorioDarEnAdopcion;

import java.util.List;

public class DarEnAdopcion implements Publicacion {

  private DatosDeContacto contactoPosteador;
  private Asociacion asociacion;
  private List<Respuesta> respuestasDelDador;
  private MascotaRegistrada mascotaEnAdopcion;
  private RepositorioDarEnAdopcion repositorio;

  public DarEnAdopcion(DatosDeContacto contactoPosteador, MascotaRegistrada mascotaEnAdopcion,
                       RepositorioDarEnAdopcion repositorio, List<Respuesta> respuestasDelDador,
                       Asociacion asociacion) {
    this.contactoPosteador = contactoPosteador;
    this.asociacion = asociacion;
    this.mascotaEnAdopcion = mascotaEnAdopcion;
    this.repositorio = repositorio;
    this.respuestasDelDador = respuestasDelDador;
  }

  public int cantidadConLasQueMatchea(List<Respuesta> comodidades) {
    return (int) comodidades.stream().filter(comodidad -> comodidad.matcheaConAlguna(respuestasDelDador)).count();
  }

  @Override
  public void notificarAlPosteador(Usuario adoptante) {
    contactoPosteador.getNotificadorPreferido().notificarQuierenAdoptarTuMascota(adoptante, mascotaEnAdopcion);
    repositorio.marcarComoProcesada(this);
  }

  public Asociacion getAsociacion() {
    return asociacion;
  }

  public MascotaRegistrada getMascotaEnAdopcion() {
    return mascotaEnAdopcion;
  }

  public List<Respuesta> getRespuestasDelDador() {
    return respuestasDelDador;
  }

}
