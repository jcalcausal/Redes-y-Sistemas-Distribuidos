 package es.uma.rysd.app;

import javax.net.ssl.HttpsURLConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;

import com.google.gson.Gson;

import es.uma.rysd.entities.*;

public class SWClient {
	// TODO: Complete el nombre de la aplicación
    private final String app_name = "RedesSwApp";
    
    private final String url_api = "https://swapi.dev/api/";

    // Métodos auxiliares facilitados
    
    // Obtiene la URL del recurso id del tipo resource
	public String generateEndpoint(String resource, Integer id){
		return url_api + resource + "/" + id + "/";
	}
	
	// Dada una URL de un recurso obtiene su ID
	public Integer getIDFromURL(String url){
		String[] parts = url.split("/");

		return Integer.parseInt(parts[parts.length-1]);
	}
	
	// Consulta un recurso y devuelve cuántos elementos tiene
	public int getNumberOfResources(String resource) throws IOException{    	
		// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse		
    	// TODO: Cree la URL correspondiente: https://swapi.dev/api/{recurso}/ reemplazando el recurso por el parámetro 
    	URL servicio = new URL (this.url_api+resource+"/");
    	// TODO: Cree la conexión a partir de la URL
    	HttpsURLConnection connection = (HttpsURLConnection) servicio.openConnection();
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
    	connection.setRequestProperty("User-Agent", this.app_name);
    	connection.setRequestProperty("Accept", "application/json");
    	// TODO: Indique que es una petición GET
    	connection.setRequestMethod("GET");
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
    	if ((connection.getResponseCode()<200) || (connection.getResponseCode()>299)) {
    		System.out.println("Código de respuesta incorrecto: "+connection.getResponseCode());
    	}
    	// TODO: Deserialice la respuesta a CountResponse
        Gson parser = new Gson();
        // TODO: Obtenga el InputStream de la conexión
        InputStream in = connection.getInputStream();
        CountResponse c = parser.fromJson(new InputStreamReader(in), CountResponse.class);
        // TODO: Devuelva el número de elementos
        return c.count;
	}
	
	public People getPerson(String urlname) throws IOException{
    	People p = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");
    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
    	// TODO: Cree la conexión a partir de la URL recibida
    	URL servicio = new URL (urlname);
    	HttpsURLConnection connection = (HttpsURLConnection) servicio.openConnection();
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
    	connection.setRequestProperty("User-Agent", this.app_name);
    	connection.setRequestProperty("Accept", "application/json");
    	// TODO: Indique que es una petición GET
    	connection.setRequestMethod("GET");
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
    	if ((connection.getResponseCode()<200) || (connection.getResponseCode()>299)) {
    		return null;
    	}
    	// TODO: Deserialice la respuesta a People
    	Gson parser = new Gson();
    	InputStream in = connection.getInputStream();
        p = parser.fromJson(new InputStreamReader(in), People.class);
        // TODO: Para las preguntas 2 y 3 (no necesita completar esto para la pregunta 1)
    	// TODO: A partir de la URL en el campo homreworld obtenga los datos del planeta y almacénelo en atributo homeplanet
        p.homeplanet = getPlanet(p.homeworld);
    	return p;
	}
	
	public Vehicle getVehicle(String urlname) throws IOException{
    	Vehicle s = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");
    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
    	// TODO: Cree la conexión a partir de la URL recibida
    	URL servicio = new URL (urlname);
    	HttpsURLConnection connection = (HttpsURLConnection) servicio.openConnection();
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
    	connection.setRequestProperty("User-Agent", this.app_name);
    	connection.setRequestProperty("Accept", "application/json");
    	// TODO: Indique que es una petición GET
    	connection.setRequestMethod("GET");
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
    	if ((connection.getResponseCode()<200) || (connection.getResponseCode()>299)) {
    		return null;
    	}
    	// TODO: Deserialice la respuesta a People
    	Gson parser = new Gson();
    	InputStream in = connection.getInputStream();
        s = parser.fromJson(new InputStreamReader(in), Vehicle.class);
    	return s;
	}

	public Planet getPlanet(String urlname) throws IOException {
    	Planet p = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");
    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
    	// TODO: Cree la conexión a partir de la URL recibida
    	URL servicio = new URL (urlname);
    	HttpsURLConnection connection = (HttpsURLConnection) servicio.openConnection();
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
    	connection.setRequestProperty("User-Agent", this.app_name);
    	connection.setRequestProperty("Accept", "application/json");
    	// TODO: Indique que es una petición GET
    	connection.setRequestMethod("GET");
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
    	if ((connection.getResponseCode()<200) || (connection.getResponseCode()>299)) {
    		return null;
    	}
    	// TODO: Deserialice la respuesta a Planet
    	Gson parser = new Gson();
    	InputStream in = connection.getInputStream();
        p = parser.fromJson(new InputStreamReader(in), Planet.class);
        return p;
	}

	
	public Film getMovie(String urlname) throws IOException {
    	Film m = null;
    	// Por si acaso viene como http la pasamos a https
    	urlname = urlname.replaceAll("http:", "https:");
    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse
    	// TODO: Cree la conexión a partir de la URL recibida
    	URL servicio = new URL (urlname);
    	HttpsURLConnection connection = (HttpsURLConnection) servicio.openConnection();
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
    	connection.setRequestProperty("User-Agent", this.app_name);
    	connection.setRequestProperty("Accept", "application/json");
    	// TODO: Indique que es una petición GET
    	connection.setRequestMethod("GET");
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
    	if ((connection.getResponseCode()<200) || (connection.getResponseCode()>299)) {
    		return null;
    	}
    	// TODO: Deserialice la respuesta a Planet
    	Gson parser = new Gson();
    	InputStream in = connection.getInputStream();
        m = parser.fromJson(new InputStreamReader(in), Film.class);
        return m;
	}
	
	
	public People search(String name) throws IOException{
    	People p = null;
    	// TODO: Trate de forma adecuada las posibles excepciones que pueden producirse		    	
    	// TODO: Cree la conexión a partir de la URL (url_api + name tratado - vea el enunciado)
    	String nameTratado = URLEncoder.encode(name, "UTF-8");
    	String URLname = url_api+"people/?search="+nameTratado;
    	URL servicio = new URL (URLname);
    	HttpsURLConnection connection = (HttpsURLConnection) servicio.openConnection();
    	// TODO: Añada las cabeceras User-Agent y Accept (vea el enunciado)
    	connection.setRequestProperty("User-Agent", this.app_name);
    	connection.setRequestProperty("Accept", "application/json");
    	// TODO: Indique que es una petición GET
    	connection.setRequestMethod("GET");
    	// TODO: Compruebe que el código recibido en la respuesta es correcto
    	if ((connection.getResponseCode()<200) || (connection.getResponseCode()>299)) {
    		return null;
    	}
    	// TODO: Deserialice la respuesta a SearchResponse -> Use la primera posición del array como resultado
    	Gson parser = new Gson();
    	SearchResponse s;
    	InputStream in = connection.getInputStream();
        s = parser.fromJson(new InputStreamReader(in), SearchResponse.class);
        // TODO: Para las preguntas 2 y 3 (no necesita completar esto para la pregunta 1)
    	// TODO: A partir de la URL en el campo homreworld obtenga los datos del planeta y almacénelo en atributo homeplanet
        if (s.results.length>0) {
        	p = s.results[0];
        	p.homeplanet = getPlanet(p.homeworld);
        }
        return p;
    }

}
