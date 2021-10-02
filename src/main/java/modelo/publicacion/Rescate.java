package modelo.publicacion;

import modelo.asociacion.Asociacion;
import modelo.mascota.MascotaEncontrada;
import modelo.persona.Persona;
import modelo.usuario.Usuario;
import repositorios.RepositorioRescates;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Representa a Publicacion de la 2da entrega
 *
 * @since Entrega 3
 */

@Entity
public class Rescate extends Publicacion {

  @ManyToOne
  private Persona rescatista;

  @ManyToOne
  private Asociacion asociacion;

  @ManyToOne
  private MascotaEncontrada mascotaEncontrada;

  private Boolean estaActiva = true;

  // para hibernate
  private Rescate() {

  }

  public Rescate(Persona rescatista, MascotaEncontrada mascotaEncontrada, Asociacion asociacion) {
    this.rescatista = rescatista;
    this.asociacion = asociacion;
    this.mascotaEncontrada = mascotaEncontrada;
  }

  @Override
  public void notificarAlPublicador(Usuario duenio) {
    rescatista.getNotificadorPreferido().notificarDuenioReclamaSuMacota(duenio);
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
