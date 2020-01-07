/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svb.consulta_ruc;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vic Aguilar
 */
public class Consulta {

    public static Map DevolverInfo(String documentoIdentidad) {
        Map dictionary = new HashMap();

        if (documentoIdentidad.length() == 8) {
            dictionary = Dni.datosDNI(documentoIdentidad);
            if ((dictionary.get("nombres").toString().isEmpty()) && (!dictionary.get("codigo_respuesta").equals("99"))) {
                   dictionary = Dni.datosDNI2(documentoIdentidad);
                 }
        } else {
            if (documentoIdentidad.length() == 11) {

                dictionary = Ruc.datosRUC(documentoIdentidad);
            }
        }
        return dictionary;
    }

}
