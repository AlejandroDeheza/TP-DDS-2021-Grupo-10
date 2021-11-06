package modelo.informe;

import modelo.hogarDeTransito.Hogar;
import modelo.hogarDeTransito.ReceptorHogares;
import modelo.mascota.MascotaEncontrada;
import modelo.mascota.MascotaRegistrada;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "informe_con_QR")
public class InformeConQR extends InformeRescate {

  @ManyToOne(cascade = CascadeType.ALL)
  private MascotaRegistrada mascotaRegistrada;

  // para hibernate
  private InformeConQR() {
  }

  public InformeConQR(Persona rescatista, Ubicacion ubicacionRescatista, MascotaEncontrada mascotaEncontrada,
                      ReceptorHogares receptorHogares, MascotaRegistrada mascotaRegistrada) {
    super(rescatista, ubicacionRescatista, mascotaEncontrada, receptorHogares);
    this.mascotaRegistrada = mascotaRegistrada;
  }

  public List<Hogar> getHogaresCercanos(Integer radioCercania) {
    return super.getHogaresCercanos(
        radioCercania,
        mascotaRegistrada.getAnimal(),
        mascotaRegistrada.getTamanio(),
        mascotaRegistrada.getCaracteristicas()
    );
  }

  public MascotaRegistrada getMascotaRegistrada() {
    return mascotaRegistrada;
  }


  @Override
  public void procesarInforme() {
    notificarAlDuenio();
    super.procesarInforme();
  }

  private void notificarAlDuenio() {
    mascotaRegistrada.getDuenio().getNotificadorPreferido().notificarEncontramosTuMascota(mascotaRegistrada);
  }

}
