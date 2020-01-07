package dental;

import java.util.HashMap;
import java.util.Map;

import svb.consulta_ruc.Consulta;


public class TestConsultaRUC {
    public TestConsultaRUC() {
        super();
    }
    
    public static void main(String[] args) {

        String ruc = "44324600";
               
               Map dictionary = new HashMap();
               
               dictionary = Consulta.DevolverInfo(ruc);
               
               if(dictionary.get("codigo_respuesta").equals("0"))
               {
                   if(ruc.length()==8)
                   {
                       System.out.println(dictionary.get("descripcion_respuesta"));
                       System.out.println(dictionary.get("nombres"));
                       System.out.println(dictionary.get("apellido_paterno"));
                       System.out.println(dictionary.get("apellido_materno"));
                   }else
                   {
                       System.out.println(dictionary.get("descripcion_respuesta"));
                       System.out.println(dictionary.get("razon_social"));
                       System.out.println(dictionary.get("domicilio_fiscal"));
                       System.out.println(dictionary.get("contribuyente_estado"));
                   }
               }else
               {
                   System.out.println("La consulta presento ERRORES ");
               }

    }
}
