package modelo;

import java.util.List;

public class DuenioMascota extends Usuario{

  private List<Mascota> mascotas;

  public DuenioMascota(String usuario, String contrasenia, Persona duenioMascota, List<Mascota> mascotas){
    super(usuario, contrasenia, duenioMascota);
    this.mascotas = mascotas;
  }
}
