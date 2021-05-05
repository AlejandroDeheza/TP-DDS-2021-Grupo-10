package modelo;

import java.util.ArrayList;
import java.util.List;

public class DuenioMascota extends Usuario{

  private List<Mascota> mascotas;

  public DuenioMascota(String usuario, String contrasenia, Persona duenioMascota, List<Mascota> mascotas){
    super(usuario, contrasenia, duenioMascota);
    this.mascotas = mascotas;
  }

  public void agregarMascota(Mascota mascota){
    if(this.mascotas == null){this.mascotas = new ArrayList<>();}
    this.mascotas.add(mascota);
  }

  public void eliminarMascota(Mascota mascota){
    this.mascotas.remove(mascota);
  }
}
