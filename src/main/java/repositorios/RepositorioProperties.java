package repositorios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class RepositorioProperties {

    private static RepositorioProperties repositorioProperties = new RepositorioProperties();
    private Properties properties = new Properties();
    private String path = "src/main/resources/app.properties";
    private Properties testProperties = new Properties();

    public static RepositorioProperties getInstance() {
        return repositorioProperties;
    }

    public RepositorioProperties(){
        try{
            properties.load(new FileInputStream(path));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RepositorioProperties(String testPath){
        try{
            properties.load(new FileInputStream(testPath));
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public Properties getTestProperties() {
        try {
            testProperties.load(new FileInputStream("src/test/resources/app.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testProperties;
    }
}
