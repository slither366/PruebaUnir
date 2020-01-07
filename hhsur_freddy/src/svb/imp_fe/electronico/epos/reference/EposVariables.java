package svb.imp_fe.electronico.epos.reference;

import common.FarmaDBUtility;

import java.util.ArrayList;


public class EposVariables {
    //LTAVARA 20/08/2014

    /**Variables para conexion con el EPOS**/
    public static String vIpSocketServidor = "";
    public static Integer vPuertoEPOS = 0;
    public static String vModo = "";
    public static String vRucEmisor = "";
    //public static  String vIdPos="";

    public static Boolean vFlagComprobanteE = false;
    //public static Boolean vIndLocalElectronico=false;

    public EposVariables() {
    }

    public static void limpiaVariables() {
        vIpSocketServidor = "";
        vPuertoEPOS = 0;
        vModo = "";
        vRucEmisor = "";
        //vIdPos="";
        vFlagComprobanteE = false;
    }

    public static void activaElectronico() {
        vFlagComprobanteE = true;
    }

    public static void desactivaElectronico() {
        vFlagComprobanteE = false;
    }
    
    public static String simboloSoles="Error";
    public static String describeSoles="Error";
        
    public static void simboloSoles_DB(){
        try{
            ArrayList parametros = new ArrayList();
            simboloSoles=FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.RECUPERA_SIMBOLO_SOLES", 
                                                                     parametros);
        }catch(Exception e){
            System.out.println("Error al recuperar el simbolo de moneda soles");
            e.printStackTrace();
        }
    }
    
    public static void describeSoles_DB(){
        try{
            ArrayList parametros = new ArrayList();
            describeSoles=FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.RECUPERA_DESCRIPCION_SOLES", 
                                                                     parametros);
        }catch(Exception e){
            System.out.println("Error al recuperar la descripcion de moneda soles");
            e.printStackTrace();
        }
    }


}
