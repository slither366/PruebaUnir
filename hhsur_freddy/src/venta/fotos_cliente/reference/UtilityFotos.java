package venta.fotos_cliente.reference;


import common.FarmaUtility;

import java.sql.SQLException;

import java.util.ArrayList;

//import common.FarmaUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UtilityFotos {
    
    private static final Logger log = LoggerFactory.getLogger(UtilityFotos.class);
    
    public UtilityFotos() {
        super();
    }
    
    public static boolean cargaValoresFTP() {
        boolean vResultado = false;
        try {
            ArrayList vLista = new ArrayList();
            DBFotos.cargaDatosLoginFTP(vLista);
            if (vLista.size() == 1)
            {
                VariablesFotos.vIP_SERV_FTP = FarmaUtility.getValueFieldArrayList(vLista, 0, 0);
                VariablesFotos.vUSU_SERV_FTP = FarmaUtility.getValueFieldArrayList(vLista, 0, 1);
                VariablesFotos.vCLAVE_SERV_FTP = FarmaUtility.getValueFieldArrayList(vLista, 0, 2);
                VariablesFotos.vPUERTO_SERV_FTP = Integer.parseInt(FarmaUtility.getValueFieldArrayList(vLista, 0, 3).trim());
                vResultado = true;
            }
            else
                vResultado = false;
        } catch (Exception sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            vResultado = false;
        }
        return vResultado;
    }
    
    public static boolean cargaDatosDNI(String pDNI) {
        boolean vResultado = false;
        try {
            ArrayList vLista = new ArrayList();
            DBFotos.datosCliente(vLista,pDNI);
            if (vLista.size() == 1)
            {
                VariablesFotos.pVal_DNI = FarmaUtility.getValueFieldArrayList(vLista, 0, 0);
                VariablesFotos.pVal_Nombre = FarmaUtility.getValueFieldArrayList(vLista, 0, 1);
                VariablesFotos.pVal_ApePat = FarmaUtility.getValueFieldArrayList(vLista, 0, 2);
                VariablesFotos.pVal_ApeMat = FarmaUtility.getValueFieldArrayList(vLista, 0, 3);
                vResultado = true;
            }
            else
                vResultado = false;
        } catch (Exception sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            vResultado = false;
        }
        return vResultado;
    }
    
    public static boolean cargaListaImgxDNI(String pDNI) {
        boolean vResultado = false;
        try {
            VariablesFotos.vListaImgxDNI = new ArrayList();
            DBFotos.getListaImagenesxDni(VariablesFotos.vListaImgxDNI,pDNI);
            vResultado = true;
        } catch (Exception sqle) {
            // TODO: Add catch code
            sqle.printStackTrace();
            vResultado = false;
        }
        return vResultado;
    }
    
    
}
