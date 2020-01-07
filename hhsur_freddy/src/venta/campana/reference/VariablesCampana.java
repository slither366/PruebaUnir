package venta.campana.reference;

import java.util.ArrayList;

/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : VariablesCampana.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * DVELIZ      19.08.2008   Creaci�n<br>
 * <br>
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 * 
 */
 
 
public class VariablesCampana {

    public static String    vCodCampana     = "";
    public static String    vCodLocal       = "";
    public static String    vCodCli         = "";
    public static String    vNumPedVta      = "";
    public static String    vCodCupon       = "";
    public static String    vCodGrupoCia    = "001";
    
    public static String    vDniCliente     = "";
    public static String    vNomCliente     = "";
    public static String    vApePatCliente  = "";
    public static String    vApeMatCliente  = "";
    public static String    vTelefono       = "";
    public static String    vNumCMP         = "";
    public static String    vMedico         = "";
    public static String    vSexo           = "";
    public static String    vFecNacimiento  = "";
    
    public static String    vDescCamp       = "";
    public static boolean   vFlag           = false;
    
    public static ArrayList vListaCupones   = new ArrayList();;
    public static ArrayList vDataCliente    = null;
    
    public static boolean   vFlagMandatory  = false;
    public static boolean   vSexoExists     = false;
    
    public static int       vNumIngreso     = 0;
    
    public VariablesCampana() {
    }
}
