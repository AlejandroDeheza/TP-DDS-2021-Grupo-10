package utils;

import modelo.informe.InformeMascotaEncontrada;
import modelo.informe.InformeQR;
import modelo.informe.Ubicacion;
import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.mascota.Sexo;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.usuario.DuenioMascota;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DummyData {

    public static DatosDeContacto getDummyDatosDeContacto() {
        return new DatosDeContacto("01147474747", "pablo@mail.com");
    }

    public static Persona getDummyPersona() {
        return new Persona("PersonaNombre", "PersonaApellido", TipoDocumento.DNI, "11111111",getDummyDatosDeContacto(), LocalDate.of(1995, 8, 7));
    }

    public static DuenioMascota getDummyDuenioMascota() {
        return new DuenioMascota("DuenioMascota", "Password1234", getDummyPersona());
    }

    public static InformeMascotaEncontrada getDummyInformeMascotaEncontrada() {
        InformeQR informeQR = new InformeQR(getDummyDuenioMascota(), getDummyMascota());
        LocalDate fechaDeHoy = LocalDate.now();
        String direccion = "Av. Corrientes 576";
        Ubicacion ubicacion = new Ubicacion("57,44","57,55");
        String estadoActualMascota = "Bien de salud, pero asustado";
        return new InformeMascotaEncontrada(informeQR,getDummyPersona(),fechaDeHoy,direccion,getDummyFotosMascota(),ubicacion,estadoActualMascota);
    }

    public static Mascota getDummyMascota() {
        return new Mascota(Animal.PERRO, "Perro", "PerroApodo",LocalDate.of(2018, 3, 4), Sexo.MACHO, "Descripcion Dummy", getDummyListaCaracteristicas(), getDummyFotosMascota());
    }

    public static List<Foto> getDummyFotosMascota() {
        List<Foto> fotosMascota = new ArrayList<>();
        Foto foto = new Foto();
        fotosMascota.add(foto);
        return fotosMascota;
    }

    public static List<Caracteristica> getDummyListaCaracteristicas() {
        Caracteristica caracteristica = new Caracteristica("Comportamiento", Collections.singletonList("Bueno"));
        List<Caracteristica> listaCaracteristica = new ArrayList<>();
        listaCaracteristica.add(caracteristica);
        return listaCaracteristica;
    }

}
