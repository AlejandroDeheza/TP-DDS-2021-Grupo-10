package modelo.usuario;

import excepciones.MascotaYaRegistradaException;
import modelo.mascota.Mascota;
import modelo.persona.Persona;
import servicios.validacionUsuario.impl.ValidadorAutenticacion;
import servicios.validacionUsuario.impl.ValidadorContrasenias;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

  private String usuario;
  private String contrasenia;
  private TipoUsuario tipo;
  private Persona persona;
  private List<Mascota> listaMascotas = new ArrayList<>();
  private ValidadorAutenticacion validadorAutenticacion;

  public Usuario(String usuario, String contrasenia, TipoUsuario tipo, Persona persona) {
    new ValidadorContrasenias().correrValidaciones(contrasenia);
    this.usuario = usuario;
    this.contrasenia = contrasenia;
    this.tipo = tipo;
    this.persona = persona;
    this.validadorAutenticacion = new ValidadorAutenticacion(this.contrasenia);
  }

  public void autenticarUsuario(String contraseniaIngresada) {
    validadorAutenticacion.autenticarUsuario(contraseniaIngresada);
  }

  public void agregarMascota(Mascota mascota) {
    validarExistenciaMascota(mascota);
    this.listaMascotas.add(mascota);
  }

  private void validarExistenciaMascota(Mascota mascota) {
    if (this.listaMascotas.contains(mascota))
      throw new MascotaYaRegistradaException("Ya tiene registrada la mascota");
  }

  public void eliminarMascota(Mascota mascota) {
    this.listaMascotas.remove(mascota);
  }


  public String getUsuario() {
    return usuario;
  }

  public TipoUsuario getTipo() {
    return tipo;
  }

  public Persona getPersona() {
    return persona;
  }
}
