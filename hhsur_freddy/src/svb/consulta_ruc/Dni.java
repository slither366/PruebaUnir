/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svb.consulta_ruc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/**
 *
 * @author Vic Aguilar
 */
public class Dni {

    public static Map datosDNI(String dni) {

        Map dictionary = new HashMap();

        HttpsURLConnection connection = null;
        String nombre = null;
        String json = null;
        try {
            URL url = new URL("https://api.reniec.cloud/dni/" + dni);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(SSLByPass.getInstancia().getSslSocketFactory());
            connection.setRequestMethod("POST");
            connection.setDoInput(true);  // Esto permite leer el contenido despues de la peticion
            connection.setConnectTimeout(1000);
            connection.connect(); //Conectar
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream);
            JSONObject myjson = new JSONObject(json);

            dictionary.put("codigo_respuesta", "0");
            dictionary.put("descripcion_respuesta", "La consulta fue exitosa");
            dictionary.put("nombres", (String) myjson.get("nombres"));
            dictionary.put("apellido_paterno", (String) myjson.get("apellido_paterno"));
            dictionary.put("apellido_materno", (String) myjson.get("apellido_materno"));

        } catch (Exception e) {

            dictionary.put("codigo_respuesta", "99");
            dictionary.put("descripcion_respuesta", "La consulta tuvo un error :: " + e.getMessage());
            dictionary.put("nombres", null);
            dictionary.put("apellido_paterno", null);
            dictionary.put("apellido_materno", null);
        }

        return dictionary;

    }

    public static Map datosDNI2(String dni)
      {
        Map dictionary = new HashMap();
        try
        {
          URL url = new URL("http://epsilon.britishschool.edu.pe:8094/WSRENIEC.asmx/CUSSPersona?dni=" + dni);
          URLConnection uc = url.openConnection();
          uc.setConnectTimeout(1000);
          uc.connect();
          BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
          
          String contenido = "";
          String inputLine;
          while ((inputLine = in.readLine()) != null) {
            contenido = contenido + inputLine + "\n";
          }
          contenido = contenido.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<string xmlns=\"http://tempuri.org/\">", "").replace("</string>", "");
          String[] contenidoArray = contenido.split("\\|");
          String[] divisionNAArray = contenidoArray[2].split(",");
          String[] ApellidosArray = divisionNAArray[0].split(" ");
          dictionary.put("apellido_paterno", ApellidosArray[0]);
          dictionary.put("apellido_materno", ApellidosArray[1]);
          dictionary.put("codigo_respuesta", "0");
          dictionary.put("descripcion_respuesta", "La consulta fue exitosa");
          dictionary.put("nombres", divisionNAArray[1]);
          in.close();
        }
        catch (IOException ex)
        {
          dictionary.put("codigo_respuesta", "99");
          dictionary.put("descripcion_respuesta", "La consulta tuvo un error :: " + ex.getMessage());
          dictionary.put("nombres", null);
          dictionary.put("apellido_paterno", null);
          dictionary.put("apellido_materno", null);
        }
        return dictionary;
      }
    
    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

}
