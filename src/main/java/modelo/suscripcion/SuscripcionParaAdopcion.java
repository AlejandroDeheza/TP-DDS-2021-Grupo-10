package modelo.suscripcion;

import java.util.List;
import modelo.EntidadPersistente;
import modelo.asociacion.Asociacion;
import modelo.pregunta.RespuestaDelAdoptante;
import modelo.publicacion.DarEnAdopcion;
import modelo.usuario.Usuario;
import repositorios.RepositorioSuscripcionesParaAdopciones;

import javax.persistence.*;

@Entity
@Table(name = "suscripcion_adopcion")
public class SuscripcionParaAdopcion extends EntidadPersistente {

  @ManyToOne(cascade = CascadeType.ALL)
  private Usuario suscriptor;

  @ManyToOne(cascade = CascadeType.ALL)
  private Asociacion asociacion;

  @Embedded
  private Preferencia preferenciaDelAdoptante;

  @OneToMany
  @JoinColumn(name = "Id_suscripcion_para_adopcion")
  private List<RespuestaDelAdoptante> comodidadesDelAdoptante;

  private Boolean estaActiva = true;

  @Transient
  private RepositorioSuscripcionesParaAdopciones repositorioSuscripciones;

  // para hibernate
  private SuscripcionParaAdopcion() {

  }

  public SuscripcionParaAdopcion(Usuario suscriptor, Asociacion asociacion,
                                 Preferencia preferenciaDelAdoptante,
                                 List<RespuestaDelAdoptante> comodidadesDelAdoptante,
                                 RepositorioSuscripcionesParaAdopciones repositorioSuscripciones) {
    this.suscriptor = suscriptor;
    this.asociacion = asociacion;
    this.preferenciaDelAdoptante = preferenciaDelAdoptante;
    this.comodidadesDelAdoptante = comodidadesDelAdoptante;
    this.repositorioSuscripciones = repositorioSuscripciones;
  }

  public void enviarRecomendaciones(List<DarEnAdopcion> recomendaciones) {
    if (!recomendaciones.isEmpty())
      suscriptor.getNotificadorPreferido().notificarRecomendacionesDeAdopciones(recomendaciones);
  }

  public void enviarLinkDeBaja() {
    suscriptor.getNotificadorPreferido().notificarLinkDeBajaSuscripcionAdopciones("<inserte link de baja>");
    estaActiva = false;
    repositorioSuscripciones.darDeBaja(this);
  } // TODO: revisar cuando podamos

  public Asociacion getAsociacion() {
    return asociacion;
  }

  public Preferencia getPreferenciaDelAdoptante() {
    return preferenciaDelAdoptante;
  }

  public List<RespuestaDelAdoptante> getComodidadesDelAdoptante() {
    return comodidadesDelAdoptante;
  }

  public Boolean estaActiva() {
    return estaActiva;
  }
}
