package utils;

import excepciones.RepositorioPropertiesException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ReceptorProperties {

  private final Properties properties = new Properties();

  public ReceptorProperties() {
    cargarPath( getRuta(null) );
  }

  // para tests
  public ReceptorProperties(String fileName) {
    cargarPath( getRuta(fileName) );
  }

  private void cargarPath(String ruta) {
    InputStream stream = null;
    try {
      stream = new FileInputStream(ruta);
      properties.load(stream);

    } catch (IOException e) {
      throw new RepositorioPropertiesException(e.toString());

    } finally {
      try {
        if (stream != null) stream.close();

      } catch (IOException e) {
        throw new RepositorioPropertiesException(e.toString());
      }
    }
  }

  public String getRuta(String nombre){
    if (nombre == null) {
      // TODO: si esto no funciona, se puede probar lo que esta en clase "GestorArchivos" metodo abrirArchivo()
      URL url = getClass().getClassLoader().getResource("app.properties");
      if (url == null) throw new RepositorioPropertiesException("No existe el file");
      return url.getPath();

    } else {
      File f = new File(".");
      return f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 2).concat(nombre);
    }
  }

  public Properties getProperties() {
    return properties;
  }

}
