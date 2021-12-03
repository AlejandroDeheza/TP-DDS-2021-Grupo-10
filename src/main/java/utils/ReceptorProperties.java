package utils;

import excepciones.RepositorioPropertiesException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ReceptorProperties {

  private Properties properties = new Properties();
  // private String path = "app.properties";
  String fileName = "app.properties";
  //  String path=getClass().getResource(fileName).toString();
  //InputStream io = this.getClass().getClassLoader().getResourceAsStream(fileName);

  public ReceptorProperties() {
    cargarPath();
  }

  public ReceptorProperties(String fileName) {
    this.fileName = fileName;

    cargarPath();
  }

  private void cargarPath() {
    InputStream stream = null;
    try {
      URL url = getClass().getClassLoader().getResource(fileName);
      if (url == null)
        throw new RepositorioPropertiesException("No existe el file");
      stream = new FileInputStream(url.getPath());
      properties.load(stream);

    } catch (IOException e) {
      throw new RepositorioPropertiesException(e);
    } finally {
      try {
        if (stream != null) stream.close();
      } catch (IOException e) {
        throw new RepositorioPropertiesException(e);
      }
    }
  }

  public Properties getProperties() {
    return properties;
  }

}
