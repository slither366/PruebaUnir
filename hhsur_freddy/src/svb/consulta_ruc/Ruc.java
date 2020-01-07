/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svb.consulta_ruc;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import org.json.JSONObject;

/**
 *
 * @author Vic Aguilar
 */
public class Ruc {

    public static Map datosRUC(String ruc) {

        Map dictionary = new HashMap();

        HttpsURLConnection connection = null;
        String[] registro = new String[3];
        String json = null;
        try {
            URL url = new URL("https://api.sunat.cloud/ruc/" + ruc);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(SSLByPass.getInstancia().getSslSocketFactory());
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);  // Esto permite leer el contenido despues de la peticion
            connection.connect(); //Conectar
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream);
            JSONObject myjson = new JSONObject(json);

            dictionary.put("codigo_respuesta", "0");
            dictionary.put("descripcion_respuesta", "La consulta fue exitosa");
            dictionary.put("razon_social", (String) myjson.get("razon_social"));
            dictionary.put("domicilio_fiscal", (String) myjson.get("domicilio_fiscal"));
            dictionary.put("contribuyente_estado", (String) myjson.get("contribuyente_estado"));

        } catch (Exception e) {

            dictionary.put("codigo_respuesta", "99");
            dictionary.put("descripcion_respuesta", "La consulta tuvo un error :: " + e.getMessage());
            dictionary.put("razon_social", null);
            dictionary.put("domicilio_fiscal", null);
            dictionary.put("contribuyente_estado", null);

        }

        return dictionary;

    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }

}
