package properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MisProperties {

  private static void cargarInfoPropertiesBase(Properties properties, String path){
    try{
      properties.load(new FileInputStream(path));
    }catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void cargarInfoPropertiesMain(Properties properties){
    MisProperties.cargarInfoPropertiesBase(properties, "src/main/resources/app.properties");
  }

  public static void cargarInfoPropertiesTests(Properties properties){
    MisProperties.cargarInfoPropertiesBase(properties, "src/test/resourcesTests/appTests.properties");
  }

}
