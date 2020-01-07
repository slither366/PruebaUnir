package venta.fotos_cliente.reference;

import java.util.ArrayList;

public class VariablesFotos {
    public VariablesFotos() {
        super();
    }
    
    public static String vIP_SERV_FTP = "";
    public static String vUSU_SERV_FTP = "";
    public static String vCLAVE_SERV_FTP = "";
    public static int vPUERTO_SERV_FTP = -1;
    
    public static String pVal_DNI = "";
    public static String pVal_Nombre = "";
    public static String pVal_ApePat = "";
    public static String pVal_ApeMat = "";
    
    public static ArrayList vListaImgxDNI = new ArrayList();
    
    public static void limpiaVariables(){
        vIP_SERV_FTP = "";
        vUSU_SERV_FTP = "";
        vCLAVE_SERV_FTP = "";
        vPUERTO_SERV_FTP = -1;
        
        pVal_DNI = "";
        pVal_Nombre = "";
        pVal_ApePat = "";
        pVal_ApeMat = "";
        
        vListaImgxDNI = new ArrayList();
    }
    
}
