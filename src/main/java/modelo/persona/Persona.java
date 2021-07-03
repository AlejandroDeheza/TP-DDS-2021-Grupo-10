package modelo.persona;

import excepciones.DatosDeContactoIncompletosException;
import modelo.notificacion.Notificador;

import java.time.LocalDate;
import java.util.Objects;

public class Persona {

  private String nombre;
  private String apellido;
  private DocumentoIdentidad documentoIdentidad;
  private DatosDeContacto datosDeContacto;
  private LocalDate fechaNacimiento;

  public Persona(String nombre, String apellido, DocumentoIdentidad documentoIdentidad, DatosDeContacto datosDeContacto,
                 LocalDate fechaNacimiento) {
    validarQueTengaDatosDeContacto(
        nombre,
        apellido,
        Objects.requireNonNull(datosDeContacto, "Falta referencia a instancia de DatosDeContacto")
    );
    this.nombre = nombre;
    this.apellido = apellido;
    this.documentoIdentidad = documentoIdentidad;
    this.datosDeContacto = datosDeContacto;
    this.fechaNacimiento = fechaNacimiento;
  }

  private void validarQueTengaDatosDeContacto(String nombre, String apellido, DatosDeContacto datosDeContacto) {
    if ((nombre == null || apellido == null) && datosDeContacto.noHayDatosDeContacto()) {
      throw new DatosDeContactoIncompletosException(
          "Se debe agregar al menos un dato de contacto o el nombre y apellido");
    }
    if (datosDeContacto.noExisteCorreoAsociado()) {
      throw new DatosDeContactoIncompletosException("El dato de contacto debe tener un correo asociado");
    } // TODO: deberiamos sacar esta validacion si usamos Twilio. Si no tiene email, usamos el telefono
  }

  public Notificador getNotificadorPreferido() {
    return datosDeContacto.getNotificadorPreferido();
  }

  public String getNombre() {
    return nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public DocumentoIdentidad getDocumentoIdentidad() {
    return documentoIdentidad;
  }

  public DatosDeContacto getDatosDeContacto() {
    return datosDeContacto;
  }

  public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
  }
}
