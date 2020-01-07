package svb.consulta_ruc;

import java.util.HashMap;
import java.util.Map;


public class TestConsultaRUC_DNI {
    public TestConsultaRUC_DNI() {
        super();
    }

    public static void main(String[] args) {
     //   TestConsultaRUC_DNI testConsultaRUC_DNI = new TestConsultaRUC_DNI();
     String ruc = 
            //"44324600";
            //"74062843";
           // "71663399";
            //"07188876";
            //"79162456";
            //"61878988";
           // "10776388";
        //"09138261";
            "20603901771";
     
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
