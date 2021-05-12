package modelo.informe;

import excepciones.InformeMascotaEncontradaInvalidaException;
import modelo.mascota.Foto;
import modelo.persona.Persona;
import modelo.usuario.DuenioMascota;
import repositorios.RepositorioInformes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InformeMascotaEncontrada {

    private DuenioMascota duenioMascota;
    private Persona rescatista;
    private LocalDate fechaEncuentro;
    private String direccion;
    private List<Foto> fotosMascota = new ArrayList<>();
    private Ubicacion lugarDeEncuentro;
    private String estadoActualMascota;

    public InformeMascotaEncontrada(DuenioMascota duenioMascota, Persona rescatista, LocalDate fechaEncuentro,
                                    String direccion, List<Foto> fotosMascota, Ubicacion lugarDeEncuentro,
                                    String estadoActualMascota, RepositorioInformes repo) {

        this.validarInforme(fotosMascota);
        this.duenioMascota = duenioMascota;
        this.rescatista = rescatista;
        this.fechaEncuentro = fechaEncuentro;
        this.direccion = direccion;
        this.fotosMascota.addAll(fotosMascota);
        this.lugarDeEncuentro = lugarDeEncuentro;
        this.estadoActualMascota = estadoActualMascota;
        repo.agregarInformeMascotaEncontrada(this);
    }

    private void validarInforme(List<Foto> fotosMascota) {
        if(fotosMascota == null || fotosMascota.isEmpty())
            throw new InformeMascotaEncontradaInvalidaException(
                "Se debe ingresar al menos 1 Foto de la mascota encontrada");
    }

    public DuenioMascota getDuenioMascota() { return duenioMascota; }

    public Persona getRescatista() {
        return rescatista;
    }

    public LocalDate getFechaEncuentro() {
        return fechaEncuentro;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Foto> getFotosMascota() {
        return fotosMascota;
    }

    public Ubicacion getLugarDeEncuentro() {
        return lugarDeEncuentro;
    }

    public String getEstadoActualMascota() {
        return estadoActualMascota;
    }
}
