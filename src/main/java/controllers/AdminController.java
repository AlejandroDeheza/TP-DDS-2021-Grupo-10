package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class AdminController extends Controller{

  public ModelAndView mostrarAdmin(Request request, Response response) {
    if (noEsAdmin(request)) {
      response.redirect("/");
      return null;
    }
    return new ModelAndView(getMap(request), "admin.html.hbs");
  }
}
