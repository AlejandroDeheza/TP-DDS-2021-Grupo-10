package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaEncontrada;
import modelo.usuario.Usuario;
import repositorios.RepositorioRescates;
import modelo.persona.DatosDeContacto;

/**
 * Representa a Publicacion de la 2da entrega
 *
 * @since  Entrega 3
 */
public class Rescate implements Publicacion {

  private DatosDeContacto contactoPosteador;
  private Asociacion asociacion;
  private MascotaEncontrada mascotaEncontrada;
  private RepositorioRescates repositorioRescates;

  public Rescate(DatosDeContacto contactoPosteador, RepositorioRescates repositorioRescates,
                 MascotaEncontrada mascotaEncontrada, Asociacion asociacion) {
    this.contactoPosteador = contactoPosteador;
    this.asociacion = asociacion;
    this.mascotaEncontrada = mascotaEncontrada;
    this.repositorioRescates = repositorioRescates;
  }

  @Override
  public void notificarAlPosteador(Usuario duenio) {
    contactoPosteador.getNotificadorPreferido().notificarDuenioReclamaSuMacota(duenio);
    repositorioRescates.marcarComoProcesada(this);
  }

  public Asociacion getAsociacion() {
    return asociacion;
  }

  public MascotaEncontrada getMascotaEncontrada() {
    return mascotaEncontrada;
  }

}
