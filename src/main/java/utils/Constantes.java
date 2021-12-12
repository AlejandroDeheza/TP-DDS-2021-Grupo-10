package utils;

import java.io.File;

public class Constantes {

  private static final String UPLOAD_DIRECTORY = "/upload";
  private static final String PROPERTIES_FILE_NAME = "app.properties";
  private static final String MOST_COMMON_PASSWORDS_FILE_NAME = "10k-most-common.txt";

  public static String getUploadDirectoryPath(){
    return getDirectoryPath(UPLOAD_DIRECTORY);
  }

  public static String getDirectoryPath(String name) {
    File f = new File(".");
    return f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 2).concat(name);
  }

  public static String getPropertiesFileName(){
    return PROPERTIES_FILE_NAME;
  }

  public static String getMostCommonPasswordsFileName(){
    return MOST_COMMON_PASSWORDS_FILE_NAME;
  }
}
