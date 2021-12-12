package utils;

import java.io.File;

public class Constantes {

  private final String UPLOAD_DIRECTORY = "/upload";
  private final String propertiesFileName = "app.properties";
  private final String mostCommonPasswordsFileName = "10k-most-common.txt";

  public String getUploadDirectoryPath(){
    return getDirectoryPath(UPLOAD_DIRECTORY);
  }

  public String getDirectoryPath(String name) {
    File f = new File(".");
    return f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 2).concat(name);
  }

  public String getPropertiesFileName(){
    return this.propertiesFileName;
  }

  public String getMostCommonPasswordsFileName(){
    return this.mostCommonPasswordsFileName;
  }
}
