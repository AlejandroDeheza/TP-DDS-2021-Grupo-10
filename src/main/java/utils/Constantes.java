package utils;

import java.io.File;

public class Constantes {

  public static final String UPLOAD_DIRECTORY = "/upload";

  public String getUploadDirectory(){
    File f = new File(".");
    return f.getAbsolutePath().substring(0, f.getAbsolutePath().length() - 2).concat(UPLOAD_DIRECTORY);
  }
}
