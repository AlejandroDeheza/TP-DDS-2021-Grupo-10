package modelo.publicacion;

import modelo.mascota.Foto;
import modelo.usuario.Usuario;
import modelo.persona.DatosDeContacto;

import java.util.List;

public class Publicacion {
    DatosDeContacto datosDeContactoRescatista;
    List<Foto> fotos;
    Usuario duenioMascota;

   public Publicacion(DatosDeContacto datosDeContacto,List<Foto> fotos){
       this.datosDeContactoRescatista=datosDeContacto;
       this.fotos=fotos;
   }

    public void agregarDuenio(Usuario usuario) {
        this.duenioMascota=usuario;
    }
    public Boolean tieneDuenio(){
       if (duenioMascota != null)
        return  true;
       return  false;
    }
    public DatosDeContacto getDatosDeContactoRescatista(){
       return datosDeContactoRescatista;
    }

  public List<Foto> getFotos() {
    return fotos;
  }
}
