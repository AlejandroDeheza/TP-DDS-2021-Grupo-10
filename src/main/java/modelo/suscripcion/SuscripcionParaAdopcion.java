package modelo.suscripcion;

import java.util.List;

import modelo.asociacion.Asociacion;
import modelo.persona.DatosDeContacto;
import modelo.pregunta.Respuesta;
import modelo.publicacion.DarEnAdopcion;

public class SuscripcionParaAdopcion {

  private DatosDeContacto contactoSuscriptor;
  private Asociacion asociacion;
  private Preferencia preferenciaDelAdoptante;
  private List<Respuesta> comodidadesDelAdoptante;

  public SuscripcionParaAdopcion(DatosDeContacto contactoSuscriptor, Asociacion asociacion,
                                 Preferencia preferenciaDelAdoptante, List<Respuesta> comodidadesDelAdoptante) {
    this.contactoSuscriptor = contactoSuscriptor;
    this.asociacion = asociacion;
    this.preferenciaDelAdoptante = preferenciaDelAdoptante;
    this.comodidadesDelAdoptante = comodidadesDelAdoptante;
  }
  
  public void enviarRecomendaciones(List<DarEnAdopcion> recomendaciones) {
    contactoSuscriptor.getNotificadorPreferido().notificarRecomendacionesDeAdopciones(recomendaciones);
  }

  public void enviarLinkDeBaja() {
    contactoSuscriptor.getNotificadorPreferido().notificarLinkDeBajaSuscripcionAdopciones("<inserte link de baja>");
  } // TODO: revisar cuando podamos

  public Asociacion getAsociacion() {
    return asociacion;
  }

  public Preferencia getPreferenciaDelAdoptante() {
    return preferenciaDelAdoptante;
  }

  public List<Respuesta> getComodidadesDelAdoptante() {
    return this.comodidadesDelAdoptante;
  }

}
