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
    private LocalDate fechaEncuentroAnimal;
    private String direccion;
    private List<Foto> fotosAnimal = new ArrayList<>();
    private Ubicacion lugarDeEncuentro;
    private String estadoActualMascota;

    public InformeMascotaEncontrada(DuenioMascota duenioMascota, Persona rescatista, LocalDate fechaEncuentroAnimal, String direccion, List<Foto> fotosAnimal, Ubicacion lugarDeEncuentro, String estadoActualMascota) {

        if(fotosAnimal == null || fotosAnimal.isEmpty()){
            throw new InformeMascotaEncontradaInvalidaException("Se debe ingresar al menos 1 Foto");
        }
        this.duenioMascota = duenioMascota;
        this.rescatista = rescatista;
        this.fechaEncuentroAnimal = fechaEncuentroAnimal;
        this.direccion = direccion;
        this.fotosAnimal.addAll(fotosAnimal);
        this.lugarDeEncuentro = lugarDeEncuentro;
        this.estadoActualMascota = estadoActualMascota;

        RepositorioInformes.getInstance().
        agregarInformeMascotaEncontrada(this);
    }

    public DuenioMascota getDuenioMascota() { return duenioMascota; }

    public Persona getRescatista() {
        return rescatista;
    }

    public LocalDate getFechaEncuentroAnimal() {
        return fechaEncuentroAnimal;
    }

    public String getDireccion() {
        return direccion;
    }

    public List<Foto> getFotosAnimal() {
        return fotosAnimal;
    }

    public Ubicacion getLugarDeEncuentro() {
        return lugarDeEncuentro;
    }

    public String getEstadoActualMascota() {
        return estadoActualMascota;
    }
}
