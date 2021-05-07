package modelo.usuario;

import modelo.informe.InformeMascotaEncontrada;
import modelo.persona.Persona;
import repositorios.RepositorioInformes;

import java.util.List;

public class Voluntario extends Usuario {

  public Voluntario(String usuario, String contrasenia, Persona persona){
    super(usuario, contrasenia, persona);
  }

  public List<InformeMascotaEncontrada> obtenerInformesMascotasEncontradasDeUltimos10Dias(){
    return RepositorioInformes.getInstance().listarMascotasEncontradasEnUltimosNDias(Integer.valueOf(10));
  }

  public void confirmarAvisoAlDuenio(InformeMascotaEncontrada informeAProcesar){
    RepositorioInformes.getInstance().procesarInforme(informeAProcesar);
  }

}
