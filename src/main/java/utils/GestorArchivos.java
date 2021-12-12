package utils;

import excepciones.ArchivoException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {

  private BufferedReader archivo;
  private List<String> archivoEnLista;

  public List<String> getArchivoEnLista(String file) {
    try {
      abrirArchivo(file);
      enlistarArchivo();

    } catch (FileNotFoundException e) {
      throw new ArchivoException("Algo salio mal en abrirArchivo() en clase GestorArchivos", e);

    } catch (IOException e) {
      throw new ArchivoException("Algo salio mal en leerArchivo() en clase GestorArchivos", e);

    } finally {
      cerrarArchivo();
    }

    return archivoEnLista;
  }

  private void abrirArchivo(String file) throws FileNotFoundException {
    InputStream io = this.getClass().getClassLoader().getResourceAsStream(file);
    archivo = new BufferedReader(new InputStreamReader(io, StandardCharsets.UTF_8));
  }

  private void enlistarArchivo() throws IOException {
    archivoEnLista = new ArrayList<>();
    String unaLinea = archivo.readLine();
    while (unaLinea != null) {
      archivoEnLista.add(unaLinea);
      unaLinea = archivo.readLine();
    }
  }

  private void cerrarArchivo() {
    try {
      if (archivo != null)
        archivo.close();

    } catch (Exception e) {
      throw new ArchivoException("Algo salio mal en cerrarArchivo() en clase GestorArchivos", e);
    }
  }
}
