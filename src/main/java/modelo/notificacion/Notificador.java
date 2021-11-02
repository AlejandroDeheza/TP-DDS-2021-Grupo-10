package modelo.notificacion;

import modelo.mascota.MascotaRegistrada;
import modelo.publicacion.DarEnAdopcion;
import modelo.usuario.Usuario;
import java.util.List;

public interface Notificador {

  void notificarEncontramosTuMascota(MascotaRegistrada mascotaRegistrada);

  void notificarQuierenAdoptarTuMascota(Usuario adoptante, MascotaRegistrada mascotaEnAdopcion);

  void notificarDuenioReclamaSuMacota(Usuario duenio);

  void notificarLinkDeBajaSuscripcionAdopciones(String linkDeBaja);

  void notificarRecomendacionesDeAdopciones(List<DarEnAdopcion> recomendaciones);
}
