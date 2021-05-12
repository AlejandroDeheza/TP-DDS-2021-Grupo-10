package utils;

import modelo.mascota.Animal;
import modelo.mascota.Foto;
import modelo.mascota.Mascota;
import modelo.mascota.Sexo;
import modelo.mascota.caracteristica.Caracteristica;
import modelo.mascota.caracteristica.CaracteristicaConValoresPosibles;
import modelo.persona.DatosDeContacto;
import modelo.persona.Persona;
import modelo.persona.TipoDocumento;
import modelo.usuario.Administrador;
import modelo.usuario.DuenioMascota;
import repositorios.RepositorioCaracteristicas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DummyData {

    public static DatosDeContacto getDummyDatosDeContacto() {
        return new DatosDeContacto("01147474747", "pablo@mail.com");
    }

    public static Persona getDummyPersona() {
        return new Persona("PersonaNombre", "PersonaApellido", TipoDocumento.DNI,
            "11111111", getDummyDatosDeContacto(),
            LocalDate.of(1995, 8, 7));
    }

    public static Persona getDummyPersonaSinDatosDeContacto() {
        return new Persona("PersonaNombre", "PersonaApellido", TipoDocumento.DNI,
            "11111111", null,
            LocalDate.of(1995, 8, 7));
    }

    public static Persona getDummyPersonaSinDatosDeContactoNiNombreNiApellido() {
        return new Persona(null, null, TipoDocumento.DNI, "11111111",
            new DatosDeContacto(null, null), LocalDate.of(1995, 8, 7));
    }

    public static DuenioMascota getDummyDuenioMascota() {
        return new DuenioMascota("DuenioMascota", "Password1234", getDummyPersona());
    }

    public static Administrador getDummyAdministrador(RepositorioCaracteristicas repo) {
        return new Administrador("Admin", "Password1234", getDummyPersona(), repo);
    }

    public static CaracteristicaConValoresPosibles getDummyCaracteristicaParaAdmin() {
        return new CaracteristicaConValoresPosibles("Comportamiento", Arrays.asList("Bueno", "Malo"));
    }

    public static List<Caracteristica> getDummyListaCaracteristicasParaMascota(RepositorioCaracteristicas repo) {
        Caracteristica caracteristica = new Caracteristica(
            "Comportamiento", "Bueno", repo);
        List<Caracteristica> listaCaracteristica = new ArrayList<>();
        listaCaracteristica.add(caracteristica);
        return listaCaracteristica;
    }

    public static List<Foto> getDummyFotosMascota() {
        List<Foto> fotosMascota = new ArrayList<>();
        Foto foto = new Foto(null, null);
        fotosMascota.add(foto);
        return fotosMascota;
    }

    public static Mascota getDummyMascota(RepositorioCaracteristicas repo) {
        return new Mascota(Animal.PERRO, "Perro", "PerroApodo",
            LocalDate.of(2018, 3, 4), Sexo.MACHO, "Descripcion Dummy",
            getDummyListaCaracteristicasParaMascota(repo), getDummyFotosMascota());
    }

}
