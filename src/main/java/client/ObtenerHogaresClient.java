package client;

import client.request.UsuarioRequest;
import client.response.UsuarioResponse;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class ObtenerHogaresClient {

    // https://app.swaggerhub.com/apis-docs/ezequieloscarescobar/hogares-transito-mascotas/1.0-oas3
    //    Algunos hogares solamente aceptan perros, otros solamente gatos y a otros les da lo mismo.
    //    Algunos hogares poseen patios y otros no. Si poseen patio, aceptan mascotas medianas o grandes; mientras que si no poseen, solamente aceptan mascotas chicas.
    //    Puede que un hogar no tenga disponibilidad por estar con su capacidad completa.
    //    El rescatista podrá elegir el radio de cercanía de los hogares de tránsito.
    //    Algunos hogares son más específicos para la admisión de mascotas y deben considerarse características puntuales.
    private final Client client;
    private static final String HOGARES_API = "https://api.refugiosdds.com.ar/api";
    private static final String HOGARES = "hogares";
    private static final String TOKEN = "hUkcMVqXTgkftoDDG8DrVrZbd44m1YJq6s8qfTmu211BWepPiCB78RRbZlf1";

    public ObtenerHogaresClient() {
        client = Client.create();
    }

    public ClientResponse obtenerHogares(String offset){
        WebResource recurso = this.client.resource(HOGARES_API).path(HOGARES);
        WebResource recursoConParametros = recurso.queryParam("offset",offset);
        WebResource.Builder builder = recursoConParametros.accept(MediaType.APPLICATION_JSON).header("Authorization","Bearer " + TOKEN);
        ClientResponse response = builder.get(ClientResponse.class);
        return response;
    }
}
