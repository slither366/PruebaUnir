package venta.fidelizacion.reference;

import java.util.ArrayList;
import java.util.List;

import venta.modulo_venta.reference.VariablesModuloVentas;

/**
 * Copyright (c) 2008 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : VariablesFidelizacion.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * DVELIZ      26.09.2008   Creaci�n<br>
 * <br>
 * @author Daniel Fernando Veliz La Rosa<br>
 * @version 1.0<br>
 * 
 */
 

public class VariablesFidelizacion {
    public VariablesFidelizacion() {
    }
    public static String        vCodCli         = "";
    public static String        vCodGrupoCia    = "001";
    
    public static String        vDniCliente     = "";
    public static String        vDniCliente_bk     = "";
    public static String        vNomCliente     = "";
    public static String        vNomCliente_bk  = "";
    
    
    public static String        vApePatCliente  = "";
    public static String        vApeMatCliente  = "";
    public static String        vTelefono       = "";
    public static String        vDireccion      = "";
    public static String        vSexo           = "";
    public static String        vFecNacimiento  = "";
    public static String        vFecNacimiento_bk  = "";

    public static String        vIndConexion    = "";
    
    public static ArrayList     vDataCliente    = new ArrayList();
    public static boolean       vSexoExists;
    public static String        vIndEstado      = "";
    public static String        vEmail      = "";
    
    /**
     * Variables auxiliares de fidelizacion
     * @author dubilluz
     * @since  29.09.2008
     */
    public static String vNumTarjeta = "";
    
    //ArrayList reemplazado con List JCALLO 03/03/2009
    public static List vListCampa�asFidelizacion = new ArrayList();   
    
    public static String vIndAgregoDNI = "";
    
    
    /**
     * Variable agregada para la impresion del nombre en boleta
     * @author dveliz
     * @since  02.10.2008
     */
     
     public static String vNomClienteImpr = "";
    
   public static boolean vIndExisteCliente = false;
   
   
    /**
     * Variable de parametro de validacion de DOC de IDENTIFICACION
     * @author JCALLO
     * @since  06.10.2008
     */
     public static String vDocValidos = "";
    

    /**
     * Bloqueo de USO descuento para DNI Anulados
     * Esta variable es para ver si da o no da Descuento de Campa�as
     * @author DUBILLUZ
     * @since  25.05.2009 
     */
    public static boolean vDNI_Anulado = false;
    //Esta variable guardara el ahorro en soles que un DNI tiene segun el perido maximo.
    public static double vAhorroDNI_x_Periodo = 0;
    public static double vMaximoAhorroDNIxPeriodo = 0;
    //Esta variable se utilizar� para coloar el ahorro del pedido
    public static double vAhorroDNI_Pedido = 0;
    public static boolean vIndComprarSinDcto = false;
    //Esto es para recalcular el ahorro del cliente
    public static boolean vRecalculaAhorroPedido = false;
    
    
    //JCORTEZ 02.07.09 se obtiene datos por DNI
     public static ArrayList  auxDataCli    = new ArrayList();
    public static String NumDniAux = "";
    
    //JCORTEZ 05.10.09 Se guarda tipo y num_doc
     public static String Tip_doc = "";
    public static String Desc_Tip_doc = "";
    public static String Num_Doc = "";
    public static String Usu_Confir = "";
    
    //Esta variable contiene
    ///dni@nobre@fechaNacimiento Finales.
    public static ArrayList vDatosFinalTerceraValidacion;
    
    //Jquispe 26.04.2010 indicador para ver si se lista campa�as no fidelizadas
    public static String vCampanaSinFidelizar;
    
    // Formas de Pago para el uso de Campa�as
    //DUBILLUZ - 09.06.2011
    public static String vIndUsoEfectivo  = "NULL";
    public static String vIndUsoTarjeta   = "NULL";
    public static String vCodFPagoTarjeta = "NULL";
    public static String vNamePagoTarjeta = "NULL";
    
    public static String tmpCodCampanaCupon = "N";
    
    public static String vNumTarjetaCreditoDebito_Campana = "";
    public static String tmp_NumTarjeta_unica_Campana = "";
    //dubilluz 07.12.2011
    public static String vColegioMedico = "";
    public static boolean vSIN_COMISION_X_DNI = false;

    /*
      NUM_CMP,
      'N' NOMBRE,
      'N' DESC_TIP_COLEGIO,
      'N' TIPO_COLEGIO,
      'N' COD_MEDICO
     * */
    public static String V_NUM_CMP = "";
    public static String V_NOMBRE = "";
    public static String V_DESC_TIP_COLEGIO = "";
    public static String V_TIPO_COLEGIO = "";
    public static String V_COD_MEDICO = "";
    public static String V_OLD_TIPO_COLEGIO = "";    
    
    
    public void limpiaVariables(){
        //SE LIMPIAN LAS VARABLES DE FIDELIZACION
        //29.09.2008 DUBILLUZ
        VariablesFidelizacion.vNumTarjeta = "";
        VariablesFidelizacion.vNomCliente = "";
        VariablesFidelizacion.vApeMatCliente = "";
        VariablesFidelizacion.vApePatCliente = "";
        VariablesFidelizacion.vDataCliente = new ArrayList();
        VariablesFidelizacion.vDireccion = "";
        VariablesFidelizacion.vDniCliente = "";
        VariablesFidelizacion.vEmail = "";
        VariablesFidelizacion.vFecNacimiento = "";
        VariablesFidelizacion.vIndAgregoDNI = "N";
        VariablesFidelizacion.vIndExisteCliente = false;
        VariablesFidelizacion.vNomClienteImpr = "";
        VariablesFidelizacion.vSexo = "";
        VariablesFidelizacion.vSexoExists = false;
        VariablesFidelizacion.vTelefono = "";

        VariablesFidelizacion.vListCampa�asFidelizacion = new ArrayList();

        VariablesModuloVentas.vMensCuponIngre = "";
        
        //Limpia Variables de Fidelizacion de FORMA DE PAGO
        //dubilluz 09.06.2011
        VariablesFidelizacion.vIndUsoEfectivo  = "NULL";
        VariablesFidelizacion.vIndUsoTarjeta   = "NULL";
        VariablesFidelizacion.vCodFPagoTarjeta = "NULL";
        VariablesFidelizacion.tmpCodCampanaCupon = "N";
        VariablesFidelizacion.vNumTarjetaCreditoDebito_Campana = "";
        VariablesFidelizacion.tmp_NumTarjeta_unica_Campana = "";
        VariablesFidelizacion.vColegioMedico = "";
        VariablesFidelizacion.vSIN_COMISION_X_DNI = false;

        ///////////////////////////////////////////////
        VariablesFidelizacion.V_NUM_CMP = "";
        VariablesFidelizacion.V_NOMBRE = "";
        VariablesFidelizacion.V_DESC_TIP_COLEGIO = "";
        VariablesFidelizacion.V_TIPO_COLEGIO = "";
        VariablesFidelizacion.V_COD_MEDICO = "";
        VariablesFidelizacion.V_OLD_TIPO_COLEGIO = "";
        ///////////////////////////////////////////////
        

        
        
    }
    public static String numPedVtaTextExpert = "";
    public static String secCompPagoTextExpert = "";
}
