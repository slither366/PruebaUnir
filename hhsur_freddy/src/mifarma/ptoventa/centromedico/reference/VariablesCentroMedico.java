package mifarma.ptoventa.centromedico.reference;

import common.FarmaTableModel;

import java.util.ArrayList;

import javax.print.PrintService;


/**
 * Copyright (c) 2016 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : VariablesPtoVenta.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA      25.08.2006   Creación<br>
 * <br>
 * @author Christiam Castillo Gonzales<br>
 * @version 1.0<br>
 *
 */

public class VariablesCentroMedico {
    public VariablesCentroMedico() {
    }
    
    /**
     * Variable para centro médico 
     * @author CCASTILLO
     * @since 25/08/2016
     */
    public static String vTipComprobante = "";
    public static String vNumComprobante = "";
    public static String vCodLocalVtaComprobante = "";
    public static String vNumPedVtaComprobante = "";
    
    public static String vNumOrdenVta = "N";
    
    public static String vCodMedico = "";
    public static String vCodCMPMedico = "";
    public static String vDescMedico = "";
    public static String vDescEspecialidad = "";
    public static String vCodEspecialidad = "";
    public static String vCodPaciente = "0";
    
    

    public static String vCod_Prod = "";
    public static String vDesc_Prod = "";
    public static String vNom_Lab = "";
    public static String vUnid_Vta = "";
    public static String vVal_Prec_Vta = "";
    public static String vPorc_Dcto_1 = "";
    public static String vVal_Bono = "";
    public static String vDesc_Acc_Terap = "";
    public static String vStk_Prod = "";
    public static String vStk_Prod_Fecha_Actual = "";

    /**
     * Valor de la fraccion del producto en la receta.
     */
    public static String vVal_Frac = "";
    public static String vVal_Prec_Lista = "";
    public static String vVal_Igv_Prod = "";
    public static String vPorc_Igv_Prod = "";
    public static String vEst_Ped_Vta_Cab = "";
    public static String vInd_Prod_Habil_Vta = "S";

    public static String vCant_Ingresada = "";
    public static String vCant_Ingresada_Temp = "";
    public static double vTotalPrecVtaProd = 0;

    /**
     * Arreglo del la lista de productos de la Receta
     */
    public static ArrayList vArrayList_PedidoReceta = new ArrayList();

    /**
     * Arreglo del resumen de la receta.
     */
    public static ArrayList vArrayList_ResumenReceta = new ArrayList();

    public static String vCodigoBarra = "";

    //Resumen Pedido

    /**
     * Numero Pedido Receta
     */
    public static String vNum_Ped_Rec = "";
    public static String vVal_Bruto_Ped = "";
    public static String vVal_Neto_Ped = "";
    public static String vVal_Redondeo_Ped = "";
    public static String vVal_Igv_Ped = "";
    public static String vVal_Dcto_Ped = "";

    public static String vCant_Items_Ped = "";
    //Detalle de Pedido
    public static String vSec_Ped_Vta_Det = "";
    public static String vPorc_Dcto_Total = "";
    public static String vEst_Ped_Vta_Det = "";

    /**
     * Indicador para ver el Resumen Receta.
     */
    public static boolean vVerReceta = false;
    
    // DUBILLUZ 31.08.2016
    public static String vNombreInHashtableVal = "";
    
    
    public static FarmaTableModel tableModelListaProductosEmpresa;
    public static int vPosOld=0;
    //nueva posicion del producto en la tabla de lista de productos
    public static int vPosNew=0;
    
    
    public static ArrayList vListaProdFaltaCero = new ArrayList();
    
    //JHAMRC
    /* ************************ ANEXOS FILE **************************** */ 
    public static ArrayList vListaAnexos = new ArrayList();
    public static String vCodigoAnexo = "";
    public static String vNombreAnexo = "";
    public static String vObservacionAnexo = "";
    public static String vRutaLocal = "";
    public static String vNombreFile = "";
    public static String vExtensionFile = "";
    /* ***************************************************************** */
    
    public static String vMotivoLiberaAtencion = "";
    
    public static String vVarFiltroListaAtenciones = "";
    
}
