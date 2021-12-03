package modelo.persona;

import excepciones.DatosDeContactoIncompletosException;
import modelo.EntidadPersistente;
import modelo.notificacion.Notificador;
import modelo.notificacion.TipoNotificadorPreferido;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Persona extends EntidadPersistente {

  private String nombre;

  private String apellido;

  @Embedded
  private DocumentoIdentidad documentoIdentidad;

  @Embedded
  private DatosDeContacto datosDeContacto;

  private LocalDate fechaNacimiento;

  @Enumerated(EnumType.STRING)
  private TipoNotificadorPreferido notificadorPreferido;

  // para hibernate
  private Persona() {

  }

  public Persona(String nombre, String apellido, DocumentoIdentidad documentoIdentidad, DatosDeContacto datosDeContacto,
                 LocalDate fechaNacimiento, TipoNotificadorPreferido notificadorPreferido) {
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
    this.notificadorPreferido = notificadorPreferido;
  }

  private void validarQueTengaDatosDeContacto(String nombre, String apellido, DatosDeContacto datosDeContacto) {
    if ((nombre == null || apellido == null) && datosDeContacto.noHayDatosDeContacto()) {
      throw new DatosDeContactoIncompletosException(
          "Se debe agregar al menos un dato de contacto o el nombre y apellido");
    }
    if (datosDeContacto.noExisteCorreoAsociado()) {
      throw new DatosDeContactoIncompletosException("El dato de contacto debe tener un correo asociado");
    }
  }

  public Notificador getNotificadorPreferido() {
    return this.notificadorPreferido.getNotificador(datosDeContacto);
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
