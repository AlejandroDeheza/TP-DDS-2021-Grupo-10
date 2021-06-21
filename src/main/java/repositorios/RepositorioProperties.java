package repositorios;

import excepciones.RepositorioPropertiesException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RepositorioProperties {

    private static RepositorioProperties repositorioProperties;
    private Properties properties = new Properties();
    private String path = "src/main/resources/app.properties";

    public static RepositorioProperties getInstance() {
        if (repositorioProperties == null) {
            repositorioProperties = new RepositorioProperties();
        }
        return repositorioProperties;
    }

    public RepositorioProperties(){
        cargarPath();
    }

    private void cargarPath(){
        try{
            properties.load(new FileInputStream(path));
        }catch (FileNotFoundException e) {
//            e.printStackTrace();
            throw new RepositorioPropertiesException(e.getMessage());
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RepositorioPropertiesException(e.getMessage());
        }
    }

    public RepositorioProperties(String path){
        this.path=path;
        cargarPath();
    }

    public Properties getProperties() {
        return properties;
    }

}
