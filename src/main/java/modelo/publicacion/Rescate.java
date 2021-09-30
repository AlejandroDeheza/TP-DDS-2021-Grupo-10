package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaEncontrada;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioRescates;
import modelo.persona.DatosDeContacto;

import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Representa a Publicacion de la 2da entrega
 *
 * @since Entrega 3
 */
public class Rescate extends Publicacion {
  @ManyToOne
  private Persona rescatista;
  @ManyToOne
  private Asociacion asociacion;
  @ManyToOne
  private MascotaEncontrada mascotaEncontrada;
  @Transient
  private RepositorioRescates repositorioRescates;
  private Boolean estaActiva = true;

  public Rescate(Persona rescatista, RepositorioRescates repositorioRescates,
                 MascotaEncontrada mascotaEncontrada, Asociacion asociacion) {
    this.rescatista = rescatista;
    this.asociacion = asociacion;
    this.mascotaEncontrada = mascotaEncontrada;
    this.repositorioRescates = repositorioRescates;
  }

  @Override
  public void notificarAlPublicador(Usuario duenio) {
    rescatista.getNotificadorPreferido().notificarDuenioReclamaSuMacota(duenio);
    repositorioRescates.marcarComoProcesada(this);
    estaActiva = false;
  }

  public Asociacion getAsociacion() {
    return asociacion;
  }

  public MascotaEncontrada getMascotaEncontrada() {
    return mascotaEncontrada;
  }

  public Boolean estaActiva() {
    return estaActiva;
  }

}
