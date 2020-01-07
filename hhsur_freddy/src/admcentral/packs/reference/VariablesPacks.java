package admcentral.packs.reference;

import common.FarmaTableModel;

import java.util.List;

public class VariablesPacks {
    public VariablesPacks() {
    }
    
    
    public static FarmaTableModel tableModelListaGlobalPacks; // jinit
    
    public static String ACCION = "";
    
    /** variables para el manejo de paquete de productos por pack**/
    public static List listaPaquete1;
    /**tablemodel usado en el dialogo de listado de paquetes1 del PACK**/
    public static FarmaTableModel tblModelListaGlobalPaquete1;
    /** variables para el manejo de paquete de productos por pack**/
    public static List listaPaquete2;
    /**tablemodel usado en el dialogo de listado de paquetes2 del PACK**/
    public static FarmaTableModel tblModelListaGlobalPaquete2;
    
    /**
     * variable donde se guarda el valor del paquete escogido para ver o 
     * modificar
     * */
    public static String Paquete;
    
    /**
     * variable donde se guarda el datamodel del paquete escogido para ver o 
     * modificar
     * */
    public static FarmaTableModel tblModelListaPaquete;
    
    /**
     * variable donde se guarda LA FILA(ROW) seleccionada del producto
     * */
    public static int RowProd;
    
    /**
     * variable que almacena si se grabo en base de datos
     * */
    public static boolean vAceptar;
    
    
    /**
     * Variables globales del detalle del paquete
     * **/
    public static String Vg_Cod_Pack;
    public static String Vg_Desc_Corta;
    public static String Vg_Desc_Larga;
    public static String Vg_Cant_Max;
    public static String Vg_Msg_Pack;
    public static String Vg_Cod_Paq1;
    public static String Vg_Cod_Paq2;
    
    /**
     * Variables globales del detalle del producto en un paquete
     * **/
    public static int Vg_pp_indice;
    public static String ACCION_PROD_PAQUETE;
    public static String Vg_pp_Cod_Prod;
    public static String Vg_pp_Cantidad;
    public static String Vg_pp_Porc_Dcto;
    public static String Vg_pp_Val_Frac;
    public static String Vg_pp_Accion;
    
}
