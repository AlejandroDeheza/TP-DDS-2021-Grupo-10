package utils;

public class Constantes {

  public static final String UPLOAD_DIRECTORY = "images/upload";

  public String getUploadDirectory(){
//    return getClass().getClassLoader().getResource("").getPath().concat(UPLOAD_DIRECTORY).substring(1);
    return UPLOAD_DIRECTORY;
  }
}
