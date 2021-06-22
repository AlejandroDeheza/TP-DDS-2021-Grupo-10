package util;

import excepciones.RepositorioPropertiesException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReceptorProperties {

    private Properties properties = new Properties();
    private String path = "src/main/resources/app.properties";

    public ReceptorProperties() {
        cargarPath();
    }

    public ReceptorProperties(String path){
        this.path = path;
        cargarPath();
    }

    private void cargarPath(){
        try{
            properties.load(new FileInputStream(path));
        } catch (IOException e) {
            throw new RepositorioPropertiesException(e.getMessage());
        }
    }

    public Properties getProperties() {
        return properties;
    }

}
