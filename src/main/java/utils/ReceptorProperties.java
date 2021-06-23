package utils;

import excepciones.RepositorioPropertiesException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReceptorProperties {

  private Properties properties = new Properties();
  private String path = "src/main/resources/app.properties";

  public ReceptorProperties() {
    cargarPath();
  }

  public ReceptorProperties(String path) {
    this.path = path;
    cargarPath();
  }

  private void cargarPath() {
    InputStream stream = null;
    try {
      stream = new FileInputStream(path);
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
