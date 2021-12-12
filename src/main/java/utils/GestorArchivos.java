package utils;

import excepciones.ArchivoException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {

  public List<String> getArchivoEnLista(String file) {
    List<String> archivoEnLista;
        
    try ( BufferedReader archivo = abrirArchivo(file) ) {
      archivoEnLista = enlistarArchivo(archivo);

    } catch (FileNotFoundException e) {
      throw new ArchivoException("Algo salio mal en abrirArchivo() en clase GestorArchivos", e);

    } catch (IOException e) {
      throw new ArchivoException("Algo salio mal en leerArchivo() en clase GestorArchivos", e);
    }

    return archivoEnLista;
  }

  private BufferedReader abrirArchivo(String file) throws FileNotFoundException {
    InputStream io = this.getClass().getClassLoader().getResourceAsStream(file);
    return new BufferedReader(new InputStreamReader(io, StandardCharsets.UTF_8));
  }

  private List<String> enlistarArchivo(BufferedReader archivo) throws IOException {
    List<String> archivoEnLista = new ArrayList<>();
    String unaLinea = archivo.readLine();
    while (unaLinea != null) {
      archivoEnLista.add(unaLinea);
      unaLinea = archivo.readLine();
    }
    return archivoEnLista;
  }

}
