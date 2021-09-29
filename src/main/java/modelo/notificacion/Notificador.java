package modelo.notificacion;

import modelo.EntitidadPersistente;
import modelo.mascota.MascotaRegistrada;
import modelo.publicacion.DarEnAdopcion;
import modelo.usuario.Usuario;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.util.List;


@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Tipo")
@Entity
public abstract class Notificador extends EntitidadPersistente {

  public abstract void notificarEncontramosTuMascota(MascotaRegistrada mascotaRegistrada);

  public abstract void notificarQuierenAdoptarTuMascota(Usuario adoptante, MascotaRegistrada mascotaEnAdopcion);

  public abstract void notificarDuenioReclamaSuMacota(Usuario duenio);

  public abstract void notificarLinkDeBajaSuscripcionAdopciones(String linkDeBaja);

  public abstract void notificarRecomendacionesDeAdopciones(List<DarEnAdopcion> recomendaciones);
}
