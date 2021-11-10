package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController extends Controller{

  public ModelAndView getHome(Request request, Response response) {
    return new ModelAndView(getMap(request), "index.html.hbs");
  }
}
