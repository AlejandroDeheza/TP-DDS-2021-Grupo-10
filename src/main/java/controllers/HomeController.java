package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HomeController {

  public ModelAndView getHome(Request request, Response response) {
    Map<String, Object> modelo = new HashMap<>();
    return new ModelAndView(modelo, "index.html.hbs");
  }
}
