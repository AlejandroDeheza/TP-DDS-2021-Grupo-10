package modelo;

import excepciones.InformeMascotaEncontradaInvalidaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InformeMascotaEncontrada {

    private InformeQR codigoQRInformacion;
    private Persona rescatista;
    private LocalDate fechaEncuentroAnimal;
    private String direccion;
    private List<Foto> fotosAnimal = new ArrayList<>();
    private Ubicacion lugarDeEncuentro;
    private String estadoActualMascota;

    public InformeMascotaEncontrada(InformeQR codigoQRInformacion, Persona rescatista, LocalDate fechaEncuentroAnimal, String direccion, List<Foto> fotosAnimal, Ubicacion lugarDeEncuentro, String estadoActualMascota) {
        this.codigoQRInformacion = codigoQRInformacion;
        this.rescatista = rescatista;
        this.fechaEncuentroAnimal = fechaEncuentroAnimal;
        this.direccion = direccion;
        if(fotosAnimal == null || fotosAnimal.isEmpty()){
            throw new InformeMascotaEncontradaInvalidaException("Se debe ingresar al menos 1 Foto");
        }
        this.fotosAnimal.addAll(fotosAnimal);
        this.lugarDeEncuentro = lugarDeEncuentro;
        this.estadoActualMascota = estadoActualMascota;
    }

    public InformeQR getCodigoQRInformacion() {
        return codigoQRInformacion;
    }

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
