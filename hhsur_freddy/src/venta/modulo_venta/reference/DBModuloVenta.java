package venta.modulo_venta.reference;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaDBUtilityRemoto;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import mifarma.ptoventa.centromedico.reference.VariablesCentroMedico;

import venta.convenio.reference.VariablesConvenio;
import venta.convenioBTLMF.reference.UtilityConvenioBTLMF;
import venta.convenioBTLMF.reference.VariablesConvenioBTLMF;
import venta.fidelizacion.reference.VariablesFidelizacion;
import venta.recetario.reference.VariablesRecetario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import venta.caja.reference.VariablesCaja;


/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DBVentas.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * LMESIA         14/02/2006   Creaci�n<br>
 * JCALLO         04/03/2009   Modificacion <br>
 * ASOSA          02/02/2010   Modificacion <br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */
public class DBModuloVenta
{
  private static final Logger log = LoggerFactory.getLogger(DBModuloVenta.class);
  
  private static ArrayList parametros = new ArrayList();
  private static List prmts = new ArrayList();

  public DBModuloVenta(){
  }
  /**
   * Carga la lista de productos pero a�adi tambien
   * si tiene promoci�n
   * @author Dubilluz
   * @since  14.06.2007
   */
    public static void cargaListaProductosVenta(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    log.debug("invocando a PTOVENTA_VTA_LISTA.VTA_LISTA_PROD(?,?)");
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA_LISTA.VTA_LISTA_PROD(?,?)",parametros,true);
  }
  
  public static void obtieneInfoProducto(ArrayList pArrayList, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    log.debug("invocando a PTOVENTA_VTA_DOS.VTA_OBTIENE_INFO_PROD(?,?,?)");
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA_DOS.VTA_OBTIENE_INFO_PROD(?,?,?)",parametros);
  }
  
  public static void obtieneInfoDetalleProducto(ArrayList pArrayList, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    log.debug("invocando a PTOVENTA_VTA.VTA_OBTIENE_INFO_COMPL_PROD(?,?,?)");
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_INFO_COMPL_PROD(?,?,?)",parametros);
  }
  
  public static void obtienePrincActXProducto(FarmaTableModel pTableModel, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
    log.debug("invocando a PTOVENTA_VTA.VTA_OBTIENE_PRINC_ACT_PROD(?,?)");
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA.VTA_OBTIENE_PRINC_ACT_PROD(?,?)",parametros, false);
  }
  
  public static void obtieneStockProducto_ForUpdate(ArrayList pArrayList, String pCodProd,
                                                      String pValFracVta) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(new Integer(pValFracVta));
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_STK_PROD_FORUPDATE(?,?,?,?)",parametros);
  }
  
  /**
   * Se compromete stock del local.
   * @param pCodProd
   * @param pCantMov
   * @param pTipoOperacion
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 29.05.2008
   */
  public static void actualizaStkComprometidoProd(String pCodProd, int pCantMov, String pTipoOperacion) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(new Integer(pCantMov));
    parametros.add(pTipoOperacion);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(new Integer(VariablesModuloVentas.vVal_Frac.trim()));
    log.debug("invocando a PTOVENTA_VTA.VTA_ACTUALIZA_STK_COMPROMETIDO(?,?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_ACTUALIZA_STK_COMPROMETIDO(?,?,?,?,?,?,?)",parametros,false);
  }
  
  public static void grabarCabeceraPedido(boolean vIsEnfermera,
                                          String pNumPedVta
                                          ) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
    parametros.add(VariablesModuloVentas.vCod_Cli_Local);//codigo cliente local
    parametros.add("");//secuencia movimiento de caja
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Bruto_Ped)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Neto_Ped)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Redondeo_Ped)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Igv_Ped)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Dcto_Ped)));
    parametros.add(VariablesModuloVentas.vTip_Ped_Vta);//tipo pedido venta
    parametros.add(new Double(FarmaVariables.vTipCambio));
    parametros.add(VariablesModuloVentas.vNum_Ped_Diario);
    parametros.add(VariablesModuloVentas.vCant_Items_Ped);
    parametros.add(VariablesModuloVentas.vEst_Ped_Vta_Cab);//estado
    parametros.add(VariablesModuloVentas.vTip_Comp_Ped);
    if(VariablesModuloVentas.vNom_Cli_Ped.length()>0)
    	parametros.add(VariablesModuloVentas.vNom_Cli_Ped);
    else
    	parametros.add(VariablesFidelizacion.vNomCliente+" "+VariablesFidelizacion.vApePatCliente+" "+VariablesFidelizacion.vApeMatCliente);
    parametros.add(VariablesModuloVentas.vDir_Cli_Ped);
    parametros.add(VariablesModuloVentas.vRuc_Cli_Ped);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(VariablesModuloVentas.vIndDistrGratuita);


        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(new JDialog(), null)){

            /**NUEVO
		     * @Autor: FRAMIREZ
		     * @Fecha: 12.12.2011
		     * */
            //Indicador Pedido-Convenio
            if (VariablesConvenioBTLMF.vCodConvenio.equalsIgnoreCase(""))
                parametros.add(FarmaConstants.INDICADOR_N);
            else
                parametros.add(FarmaConstants.INDICADOR_S);
            parametros.add(VariablesConvenioBTLMF.vCodConvenio);
        } else {
    /**NUEVO
     * @Autor: Luis R.
     * @Fecha: 19-03-2007
     * */
    //Indicador Pedido-Convenio
    if(VariablesConvenio.vCodConvenio.equalsIgnoreCase(""))
      parametros.add(FarmaConstants.INDICADOR_N);
    else
      parametros.add(FarmaConstants.INDICADOR_S);
    parametros.add(VariablesConvenio.vCodConvenio);  

        }
    parametros.add(FarmaVariables.vNuSecUsu);//JCHAVEZ 07102009
    //GRABA VARIABLES DE FORMA_PAGO_FIDELIZACION 
    //dubilluz 09.06.2011
    parametros.add(VariablesFidelizacion.vIndUsoEfectivo.trim());
    parametros.add(VariablesFidelizacion.vIndUsoTarjeta.trim());
    parametros.add(VariablesFidelizacion.vCodFPagoTarjeta.trim()); 
    log.debug("SecUsuario Local "+FarmaVariables.vNuSecUsu);
    //log.info("Cabecera Pedido: "+parametros);
    //dubilluz - 07.12.2011
    parametros.add(VariablesFidelizacion.vColegioMedico.trim()); 

        if (UtilityConvenioBTLMF.esActivoConvenioBTLMF(new JDialog(), null) &&
        	VariablesConvenioBTLMF.vCodConvenio != null &&
        	VariablesConvenioBTLMF.vCodConvenio.trim().length() > 0) {
            /**NUEVO
	     * @Autor: Fredy Ramirez C.(FRAMIREZ)
	     * @Fecha: 12-12-2011
	    **/

            if (VariablesConvenioBTLMF.vCodCliente == null ||
                VariablesConvenioBTLMF.vCodCliente.trim().equals(""))
                VariablesConvenioBTLMF.vCodCliente = "0000000000";
            parametros.add(VariablesConvenioBTLMF.vCodCliente);
            parametros.add("S");
        } else {
            parametros.add(VariablesConvenio.vCodCliente);//28
            parametros.add("N");
        }
      
      // GRABA EL NUMERO DE SOLICITUD DE LA VENTA NEGATIVA SI HAY 
      // DUBILLUZ 20.11.2013
        parametros.add(VariablesModuloVentas.vCodSolicitudVtaNegativa);
      
      if(VariablesModuloVentas.VNUM_CMP_IN.trim().length()>0)  
        parametros.add(VariablesModuloVentas.VNUM_CMP_IN);
      else
        parametros.add("");  
          
      if(VariablesModuloVentas.VDATOS_MEDICO_IN.trim().length()>0)  
        parametros.add(VariablesModuloVentas.VDATOS_MEDICO_IN);
      else
        parametros.add("");  

      if(VariablesModuloVentas.vCodCia_Receta.trim().length()>0)  
        parametros.add(VariablesModuloVentas.vCodCia_Receta);
      else
        parametros.add("");  


      if(VariablesModuloVentas.vCodLocal_Receta.trim().length()>0)  
        parametros.add(VariablesModuloVentas.vCodLocal_Receta);
      else
        parametros.add("");  


      if(VariablesModuloVentas.vNumPedido_Receta.trim().length()>0)  
        parametros.add(VariablesModuloVentas.vNumPedido_Receta);
      else
        parametros.add("");  

            
        log.debug("SecUsuario Local " + FarmaVariables.vNuSecUsu);
    
      //dubilluz 01.09.2015
      if(VariablesModuloVentas.vIsVtaSoat)  
        parametros.add("S");
      else
        parametros.add("N");  
      
      //dubilluz 19.09.2015
      parametros.add(VariablesModuloVentas.VPacienteDni);  
      
      // medico asociado
      if(VariablesModuloVentas.VNUM_DNI_TECNOLOGO_IN.trim().length()>0)  
        parametros.add(VariablesModuloVentas.VNUM_DNI_TECNOLOGO_IN);
      else
        parametros.add("");  
          
      if(VariablesModuloVentas.VDATOS_TECNOLOGO_IN.trim().length()>0)  
        parametros.add(VariablesModuloVentas.VDATOS_TECNOLOGO_IN);
      else
        parametros.add("");  


      if(VariablesCentroMedico.vCodPaciente.trim().length()>0)  
        parametros.add(VariablesCentroMedico.vCodPaciente);
      else
        parametros.add("");        
      
      
      if(VariablesModuloVentas.VID_REF_IN.trim().length()>0)  
        parametros.add(VariablesModuloVentas.VID_REF_IN);
      else
        parametros.add("");  
          
      if(VariablesModuloVentas.VDES_REF_IN.trim().length()>0)  
        parametros.add(VariablesModuloVentas.VDES_REF_IN);
      else
        parametros.add("");  

      if(VariablesModuloVentas.VCOD_VISITADOR_IN.trim().length()>0)  
        parametros.add(VariablesModuloVentas.VCOD_VISITADOR_IN);
      else
        parametros.add("");  
      
      if(VariablesModuloVentas.VNOMBRE_VISITADOR_IN.trim().length()>0)  
        parametros.add(VariablesModuloVentas.VNOMBRE_VISITADOR_IN);
      else
        parametros.add("");  


      if(VariablesModuloVentas.is_cotizacion){
          parametros.add("S");  
      }
      else
          parametros.add("N");

   if(!vIsEnfermera){
           log.info("invocando a PTOVENTA_VTA.VTA_GRABAR_PEDIDO_VTA_CAB(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):" + parametros);
           FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_GRABAR_PEDIDO_VTA_CAB(" +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?," +
                                                    "?,?,?,?,?,?)",parametros, false);       
   }
   else{
       
       parametros.add(pNumPedVta.trim());    
       log.info("invocando a UTILITY_ENFERMERA.VTA_GRABAR_PEDIDO_VTA_CAB(" +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):" + parametros);
       FarmaDBUtility.executeSQLStoredProcedure(null,"UTILITY_ENFERMERA.VTA_GRABAR_PEDIDO_VTA_CAB(" +
                                                "?,?,?,?,?," +
                                                "?,?,?,?,?," +
                                                "?,?,?,?,?," +
                                                "?,?,?,?,?," +
                                                "?,?,?,?,?," +
                                                "?,?,?,?,?," +
                                                "?,?,?,?,?," +
                                                "?,?,?,?,?," +
                                                "?,?,?,?,?," +
                                                "?,?)",parametros, false);       
   }

  }
  /**
   * Para grabar la promocion  en el detalle
   * @author dubilluz
   * @since  28.02.2008
   */  
  public static void grabarDetallePedido() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
    parametros.add(VariablesModuloVentas.vSec_Ped_Vta_Det);
    parametros.add(VariablesModuloVentas.vCod_Prod);
        log.debug("grabarDetallePedido:::>" + VariablesModuloVentas.vCod_Prod);
    parametros.add(new Integer(VariablesModuloVentas.vCant_Ingresada.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta)));
    parametros.add(new Double(VariablesModuloVentas.vTotalPrecVtaProd));
    parametros.add(new Double(VariablesModuloVentas.vPorc_Dcto_1));
    // SOLO CUANDO ES UNA PROMOCION
    if(VariablesModuloVentas.vPorc_Dcto_2.trim().length()!=0)
      parametros.add(new Double(VariablesModuloVentas.vPorc_Dcto_2));
    else
      parametros.add(new Double(0));//porc dcto 2
    parametros.add(new Double(0));//porc dcto 3
    parametros.add(new Double(VariablesModuloVentas.vPorc_Dcto_Total));
    parametros.add(VariablesModuloVentas.vEst_Ped_Vta_Det);//estado de detalle
    parametros.add(new Double(VariablesModuloVentas.vVal_Total_Bono));
    parametros.add(new Integer(VariablesModuloVentas.vVal_Frac.trim()));
    parametros.add("");//secuncia de comprobante de pago
    parametros.add(VariablesModuloVentas.vSec_Usu_Local);
    parametros.add(new Double(VariablesModuloVentas.vVal_Prec_Lista));
    parametros.add(new Double(VariablesModuloVentas.vVal_Igv_Prod));
    parametros.add(VariablesModuloVentas.vUnid_Vta);
    parametros.add(VariablesModuloVentas.vNumeroARecargar);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Pub)));
    parametros.add(VariablesModuloVentas.vCodPromoDet);
    parametros.add(VariablesModuloVentas.vIndOrigenProdVta);
    parametros.add(VariablesModuloVentas.vCantxDia);
    parametros.add(VariablesModuloVentas.vCantxDias);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vAhorroPack))); //JCHAVEZ 20102009 
    log.debug("invocando  a PTOVENTA_VTA.VTA_GRABAR_PEDIDO_VTA_DET(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_GRABAR_PEDIDO_VTA_DET(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros,false);//JCHAVEZ 19102009
  }
  
  public static String obtieneFecModNumeraPed() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaConstants.COD_NUMERA_PEDIDO_DIARIO);
    ArrayList myArray = new ArrayList();    
    log.debug("invocando  a PTOVENTA_VTA.VTA_OBTIENE_FEC_MOD_NUMERA_PED(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(myArray,"PTOVENTA_VTA.VTA_OBTIENE_FEC_MOD_NUMERA_PED(?,?,?)",parametros);
    return (String)((ArrayList)myArray.get(0)).get(0);
  }
  
  /**
   * 
   * @param pCodProd
   * @param pCantIngresada
   * @throws SQLException
   * @deprecated
   */
  public static void actualizaStkProd_Fis_Comp(String pCodProd, String pCantIngresada) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(new Integer(pCantIngresada.trim()));
    parametros.add(FarmaVariables.vIdUsu);
    log.debug("invocando  a PTOVENTA_VTA.VTA_ACTUALIZA_STK_PROD(?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_ACTUALIZA_STK_PROD(?,?,?,?,?)",parametros,false);
  }
  
  public static String obtieneUltimoPedidoDiario() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vNuSecUsu);
      log.debug("invocando  a PTOVENTA_VTA.VTA_OBTIENE_ULTIMO_PED_DIARIO(?,?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_OBTIENE_ULTIMO_PED_DIARIO(?,?,?)",parametros);
  }
  
  public static void cargaListaProductosVenta_Filtro(FarmaTableModel pTableModel, String pTipoFiltro,
                                                     String pCodFiltro) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pTipoFiltro);
    parametros.add(pCodFiltro);
    
    log.debug(pTipoFiltro + "  "+pCodFiltro);
    
    //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_PROMOCION_DUBILLUZ.VTA_LISTA_PROD_FILTRO(?,?,?,?)",parametros,true);
    log.debug("invocando  a PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_FILTRO(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_FILTRO(?,?,?,?)",parametros,true);
  }
  
  public static void cargaListaProductosAlternativos(FarmaTableModel pTableModel, String pCodProd) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
      log.debug("invocando  a PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_ALTERNATIVOS(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_ALTERNATIVOS(?,?,?)",parametros,true);
  }
  
  public static void cargaListaRelacionAccionTerap(FarmaTableModel pTableModel, 
                                                   String pCodProd) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
      log.debug("invocando  a PTOVENTA_VTA.VTA_OBTIENE_REL_PRINC_ACT_PROD(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA.VTA_OBTIENE_REL_PRINC_ACT_PROD(?,?,?)",parametros,false);
  }
  
  public static void cargaListaProductosComplementarios(FarmaTableModel pTableModel, 
                                                        String pCodProd,
                                                        String pCodPrincAct) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(pCodPrincAct);
      log.debug("invocando  a PTOVENTA_VTA.VTA_LISTA_PROD_COMPLEMENTARIOS(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA.VTA_LISTA_PROD_COMPLEMENTARIOS(?,?,?,?)",parametros,false);
  }
  
  public static String obtieneCodigoProductoBarra() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(VariablesModuloVentas.vCodigoBarra);
      log.debug("invocando  a PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(?,?)",parametros);
  }
  
    public static String obtieneCodigoProductoBarra(String pCodBarra) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(pCodBarra.trim());
        log.debug("invocando  a PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_REL_COD_BARRA_COD_PROD(?,?)",parametros);
    }  
  
    public static void obtieneAccionesTerapXProducto(FarmaTableModel pTableModel, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
      log.debug("invocando  a PTOVENTA_VTA.VTA_OBTIENE_ACC_TERAP_PROD(?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA.VTA_OBTIENE_ACC_TERAP_PROD(?,?)",parametros, false);
  }
  
    public static void ingresaStockCero()throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesModuloVentas.vCod_Prod);
    parametros.add(FarmaVariables.vNuSecUsu);
    parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando  a PTOVENTA_VTA.VTA_FALTA_CERO(?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_FALTA_CERO(?,?,?,?,?)",parametros,false);
    }
    
    public static void listaBusquedaMedicos(FarmaTableModel pTableModel, String pCodigo, String pMatriculaApe, String pTipoBusqueda)throws SQLException
    {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(pCodigo);
    parametros.add(pMatriculaApe);
    //parametros.add(pApellido);
    parametros.add(pTipoBusqueda);
        log.debug("invocando  a PTOVENTA_VTA.VTA_BUSCA_MEDICO(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA.VTA_BUSCA_MEDICO(?,?,?)",parametros, false);    
    }
    
    public static void agrega_medico_vta()throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
      parametros.add(VariablesModuloReceta.vNum_Ped_Rec);
      parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando  a PTOVENTA_VTA.VTA_AGREGA_MEDICO_VTA(?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_AGREGA_MEDICO_VTA(?,?,?,?,?)",parametros,false);
    }

  /**
   * Se graba la cabecera de la receta.
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 07.12.2006
   */
  public static void grabarCabeceraReceta()
    throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesModuloReceta.vNum_Ped_Rec);
      parametros.add(VariablesModuloVentas.vCodListaMed);
      parametros.add(VariablesModuloVentas.vMatriListaMed);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Bruto_Ped)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Neto_Ped)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Redondeo_Ped)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Igv_Ped)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Dcto_Ped)));
    parametros.add(new Double(FarmaVariables.vTipCambio));
    parametros.add(VariablesModuloReceta.vCant_Items_Ped);
    parametros.add(VariablesModuloReceta.vEst_Ped_Vta_Cab); //estado
    parametros.add(FarmaVariables.vIdUsu);
      log.debug("invocando  a PTOVENTA_VTA.VTA_GRABAR_PED_RECETA_CAB(?,?,?,?,?,?,?,?,?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null, 
                                             "PTOVENTA_VTA.VTA_GRABAR_PED_RECETA_CAB(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                                             parametros, false);
  }

  /**
   * Se graba el detalle de la receta.
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 07.12.2006
   */
  public static void grabarDetalleReceta()
    throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesModuloReceta.vNum_Ped_Rec);
    parametros.add(VariablesModuloReceta.vSec_Ped_Vta_Det);
    parametros.add(VariablesModuloReceta.vCod_Prod);
    parametros.add(new Integer(VariablesModuloReceta.vCant_Ingresada.trim()));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloReceta.vVal_Prec_Vta)));
    parametros.add(new Double(VariablesModuloReceta.vTotalPrecVtaProd));
    parametros.add(new Double(VariablesModuloReceta.vPorc_Dcto_1));
    parametros.add(new Double(0)); //porc dcto 2
    parametros.add(new Double(0)); //porc dcto 3
    parametros.add(new Double(VariablesModuloReceta.vPorc_Dcto_Total));
    parametros.add(VariablesModuloReceta.vEst_Ped_Vta_Det); //estado de detalle
    parametros.add(new Integer(VariablesModuloReceta.vVal_Frac.trim()));
    parametros.add(new Double(VariablesModuloReceta.vVal_Prec_Lista));
    parametros.add(new Double(VariablesModuloReceta.vVal_Igv_Prod));
    parametros.add(VariablesModuloReceta.vUnid_Vta);
      parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando  a PTOVENTA_VTA.VTA_GRABAR_PED_RECETA_DET(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null, 
                                             "PTOVENTA_VTA.VTA_GRABAR_PED_RECETA_DET(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
                                             parametros, false);
    }
  
  public static void obtieneInfoProductoVirtual(ArrayList pArrayList, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
      log.debug("invocando  a PTOVENTA_VTA.VTA_OBTIENE_INFO_PROD_VIRTUAL(?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_INFO_PROD_VIRTUAL(?,?)",parametros);
  }
    
  /**
   * Se lista las Promociones (Maestro General).
   * @author Jorge Cortez Alvarez<br>
   * @since   14.06.2007
   */
  public static void listaPromociones(FarmaTableModel pTableModel,String pCodBarra)throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    // Se a�adieron nuevas columnas
    // 28.02.2008 dubilluz
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodBarra);
      log.debug("invocando  a PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADO(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADO(?,?,?)",parametros, false);
  }
  
  /**
   * Se lista las Promociones deacuerdo al producto
   * @author Jorge Cortez Alvarez<br>
   * @since   22.06.2007
   */
    public static void listaPromocionesPorProducto(FarmaTableModel pTableModel,String vCodProd)throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    // Se a�adieron nuevas columnas
    // 28.02.2008 dubilluz    
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(vCodProd);
      log.debug("invocando  a PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADOPORPRODUCTO(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADOPORPRODUCTO(?,?,?)",parametros, false);
  }
  
  /**
   * Se lista el paquete 1 de la promocion 
   * @author Jorge Cortez Alvarez<br>
   * @since   15.06.2007
   */ 
  public static void listaPaquete1(FarmaTableModel pTableModel, String vCodProm) throws SQLException {
     pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(vCodProm);
      log.debug("invocando  a PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADO_PAQUETE1(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADO_PAQUETE1(?,?,?)",parametros,false);
  }
  
  /**
   * Se lista el paquete 2 de la promocion
   * @author Jorge Cortez Alvarez<br>
   * @since   15.06.2007
   */
  public static void listaPaquete2(FarmaTableModel pTableModel, String vCodProm) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(vCodProm);
      log.debug("invocando  a PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADO_PAQUETE2(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADO_PAQUETE2(?,?,?)",parametros,false);
  }
  
  /**
   * Se lista el detalle general de promocion
   * @author Jorge Cortez Alvarez<br>
   * @since   18.06.2007
   */
   public static void obtieneListadoPaquetes(ArrayList pArrayList, String pCodProm) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProm);
      log.debug("invocando  a PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADO_PAQUETES(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_PROMOCIONES.PROMOCIONES_LISTADO_PAQUETES(?,?,?)",parametros);//JCHAVEZ 20102009
  }
  
  
  /**
   * Lista Stock de Locales
   * @author Jorge Cortez Alvarez<br>
   * @since   18.06.2007   * 
   */
  public static void cargaListaStockLocalesPreferidos(FarmaTableModel pTableModel,
                                                      String pCodprod,
                                                      String pIndCloseConecction) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodprod);
    parametros.add("S");
      log.debug("invocando  a PTOVENTA_INV.INV_STOCK_LOCALES_PREFERIDOS(?,?,?,?):"+parametros);
   FarmaDBUtilityRemoto.executeSQLStoredProcedure(pTableModel,
                                                  "PTOVENTA_INV.INV_STOCK_LOCALES_PREFERIDOS(?,?,?,?)",
                                                  parametros,
                                                  false,
                                                  FarmaConstants.CONECTION_DELIVERY,
                                                  pIndCloseConecction); 
  }
  /**
   * Lista los stock en locales en general del producto
   * @author dubilluz
   * @since  05.11.2007
   */
  public static void cargaListaStockLocalesPreferidos(FarmaTableModel pTableModel,
                                                      String pCodprod,
                                                      String pIndVerTodosLocales,
                                                      String pIndCloseConecction) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodprod);
    parametros.add(pIndVerTodosLocales.trim());
      log.debug("invocando  a PTOVENTA_INV.INV_STOCK_LOCALES_PREFERIDOS(?,?,?,?):"+parametros);
    FarmaDBUtilityRemoto.executeSQLStoredProcedure(pTableModel,
                                                   "PTOVENTA_INV.INV_STOCK_LOCALES_PREFERIDOS(?,?,?,?)",
                                                   parametros,
                                                   false,
                                                   FarmaConstants.CONECTION_DELIVERY,
                                                   pIndCloseConecction); 
  }
  
  public static void cargaListaStockDemasLocales(FarmaTableModel pTableModel,
                                                 String pCodprod) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodprod);
      log.debug("invocando  a PTOVENTA_INV.INV_STOCK_LOCAL_NO_PREFERIDOS(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_INV.INV_STOCK_LOCAL_NO_PREFERIDOS(?,?,?)",parametros,false);
  }

 /*  public static String obtieneIndicadorStock(String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(pCodProd);
    log.debug(parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_OBTIENE_IND_STOCK(?)",parametros);
  }*/
    /**
     * Modificado para conectarse directamente al Matriz
     * @author : dubilluz
     * @since  : 20.08.2007
     */
    public static String obtieneInfoStock(String pCodProd,
                                          String pIndCloseConecction) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(VariablesModuloVentas.vCodLocalDestino);
    parametros.add(pCodProd);
      log.debug("invocando  a PTOVENTA_INV.INV_OBTIENE_STOCK_EN_LINEA(?,?,?):"+parametros);
    return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_INV.INV_OBTIENE_STOCK_EN_LINEA(?,?,?)",
                                                             parametros,
                                                             FarmaConstants.CONECTION_MATRIZ,
                                                             pIndCloseConecction);
    
  }
  
   public static String obtieneIndVerStockLocales() throws SQLException {   
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
      log.debug("invocando  a PTOVENTA_VTA.VTA_OBT_IND_VER_STK_LOCALES(?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_OBT_IND_VER_STK_LOCALES(?,?)",parametros);
  }

 /**
  * Indicador de Stock Lima, Provincia y Almacen
  * @author : dubilluz
  * @since  : 20.08.2007
  */
  public static String obtieneIndicadorStock(String pCodProd,
                                             String pIndCloseConecction) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
      log.debug("invocando  a PTOVENTA_REP.INV_OBTIENE_IND_STOCK(?,?):"+parametros);
    //return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_OBTIENE_IND_STOCK(?,?)",parametros);
    return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_REP.INV_OBTIENE_IND_STOCK(?,?)",
                                                             parametros,
                                                             FarmaConstants.CONECTION_DELIVERY,
                                                             pIndCloseConecction);
  }
  
 /**
  * Retorna el numero de Dias para el Reporte de Dias sin Ventas
  * @author : dubilluz
  * @since  : 20.08.2007
  */
  public static String obtieneNumeroDiasSinVentas() throws SQLException {
    parametros = new ArrayList();
      log.debug("invocando  a PTOVENTA_REPORTE.NUMERO_DIAS_SIN_VENTAS:"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REPORTE.NUMERO_DIAS_SIN_VENTAS",parametros);
  }

 /**
  * Retorna el mensaje para la fecha actual
  * @author dubilluz
  * @since  29.02.2008
  */
  public static String obtieneMensajeActual() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
      log.debug("invocando  a FARMA_GRAL.GET_MENSAJE_PERSONALIZADO(?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.GET_MENSAJE_PERSONALIZADO(?,?)",parametros);
  }
 /** 
  * Retorna la maxima longitud para el numero de telefono de recarga virtual
  * @author dubilluz
  * @since  26.03.2008
  */
  public static String obtieneMaxLongNumTelf() throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
     log.debug("invocando  a FARMA_GRAL.GET_MAXIMO_LOG_NUMERO_TELF(?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.GET_MAXIMO_LOG_NUMERO_TELF(?)",parametros);
 }

  /**
   * Se carga la lista de productos sustitutos.
   * @param pTableModel
   * @param pCodProd
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 07.04.2008
   */
  public static void cargaListaProductosSustitutos(FarmaTableModel pTableModel, 
                                                     String pCodProd)
    throws SQLException
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
      log.debug("invocando a PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_SUSTITUTOS(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                             "PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_SUSTITUTOS(?,?,?)", 
                                             parametros, true);
  }
  
  /**
   * Obtiene informacion del pedido actual tiene productos de un encarte
   * y si este encarte esta activo
   * @author dubilluz
   * @since  09.04.2008
   */
  public static void analizaProdEncarte(ArrayList pArray,String pCodEncarte) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vIpPc);
    parametros.add(pCodEncarte.trim());  
      //dubilluz 14.10.2011
      //los codigos de productos del resumen de pedido.separados por @
      parametros.add(UtilityModuloVenta.getProdVendidos());
      log.debug("invocando a PTOVENTA_VTA.VTA_PERMITE_PROD_REGALO(?,?,?,?,?):"+parametros);      
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PTOVENTA_VTA.VTA_PERMITE_PROD_REGALO(?,?,?,?,?)",parametros);
  }

 /** 
  * Retorna la descripcion del productos
  * @author dubilluz
  * @since  09.04.2008
  */
  public static String obtieneDescProducto(String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
     log.debug("invocando a FARMA_GRAL.GET_DESCRIPCION_PROD(?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.GET_DESCRIPCION_PROD(?,?)",parametros);
 }
 
 /** 
  * Retorna la descripcion del productos
  * @author dubilluz
  * @since  09.04.2008
  */
  public static String obtieneInfoProdRegalo(String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
     log.debug("invocando a FARMA_GRAL.GET_DESCRIPCION_PROD(?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.GET_DESCRIPCION_PROD(?,?)",parametros);
 } 
 
  /**
   * Graba el detalle del pedido regalo
   * @author dubilluz
   * @since  09.04.2008
   */
  public static void grabaProductoRegalo(String pNumPed,String pCodprod,int pSecProd,int pCantAtend,
                                         int pValPrecVta) throws SQLException {
    
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPed+"");
    parametros.add(pSecProd+"");
    parametros.add(pCodprod);
    parametros.add(pCantAtend+"");
    parametros.add(pValPrecVta+"");
    parametros.add(VariablesModuloVentas.vSec_Usu_Local);
    parametros.add(FarmaVariables.vIdUsu);
      log.debug("invocando a Ptoventa_Vta.VTA_GRABAR_PED_REGALO_DET(?,?,?,?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"Ptoventa_Vta.VTA_GRABAR_PED_REGALO_DET(?,?,?,?,?,?,?,?,?)",parametros,false);
  }
  

  /**
   * Obtiene informacion del pedido actual tiene productos para acceder a
   * obtener uno o mas cupones de regalo
   * @author dubilluz
   * @since  10.04.2008
   */
  public static void analizaProdCampCupon(ArrayList pArray,String pNumped,String pCodCampCupon) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vIpPc);
    parametros.add(pNumped.trim());
    parametros.add(pCodCampCupon.trim());
    //dubilluz 15.10.2011
    parametros.add(UtilityModuloVenta.getProdVendidos());
      log.debug("invocando a PTOVENTA_VTA.VTA_PERMITE_CUPON(?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PTOVENTA_VTA.VTA_PERMITE_CUPON(?,?,?,?,?,?)",parametros);
  }

  public static void cargaListaProductosComplementarios1(FarmaTableModel pTableModel, String pCodProd) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vIpPc);
    //dubilluz 14.10.2011
    //los codigos de productos del resumen de pedido.separados por @
    parametros.add(UtilityModuloVenta.getProdVendidos());
    log.debug("invocando a PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_COMP1(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_COMP1(?,?,?,?)",parametros,true);
  }  
  
  /**
    * Graba la cantidad de cupones del pedido
    * @author dubilluz
    * @since  10.04.2008
    */
   public static void grabaCuponPedido(String pNumPed,String pCodCupon,int pCant,
                                       String pTipo, String pMonto) throws SQLException {  
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pNumPed+"");
     parametros.add(pCodCupon);
     parametros.add(pCant+"");
     parametros.add(FarmaVariables.vIdUsu);
     parametros.add(pTipo);
     parametros.add(pMonto.trim());
       log.debug("invocando a PTOVENTA_VTA.VTA_GRABAR_PED_CUPON(?,?,?,?,?,?,?,?):"+parametros);
     FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_GRABAR_PED_CUPON(?,?,?,?,?,?,?,?)",parametros,false);
   }

   public static void cargaListaProductosOferta(FarmaTableModel pTableModel, String pCodProd) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaVariables.vIpPc);
      //dubilluz 15.10.2011
      parametros.add(UtilityModuloVenta.getProdVendidos());
      log.debug("invocando a PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_OFERTA(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA_LISTA.VTA_LISTA_PROD_OFERTA(?,?,?,?)",parametros,true);
  }
  
  /**
   * Se obtiene informacion del producto en oferta
   * @throws SQLException
   * @author JCORTEZ
   * @since 11.05.2008
   */
   public static void obtieneInfoOfertProducto(ArrayList pArrayList, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
      log.debug("invocando a PTOVENTA_VTA_LISTA.VTA_OBTIENE_INFO_PROD_OFER(?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA_LISTA.VTA_OBTIENE_INFO_PROD_OFER(?,?,?)",parametros);
  }
  
   /**
   * Se obtiene informacion del producto en oferta
   * @throws SQLException
   * @author JCORTEZ
   * @since 15.04.2008
   */
   public static void obtieneInfoProdOrigenComp(ArrayList pArrayList, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
      log.debug("invocando a PTOVENTA_VTA_LISTA.VTA_OBTIENE_INFO_PROD_COMP(?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA_LISTA.VTA_OBTIENE_INFO_PROD_COMP(?,?)",parametros);
  }

  /**
   * Se lista el tipo de filtro desde el resumen
   * @author Jorge Cortez Alvarez<br>
   * @since   18.04.2008
   */
  public static void listaTipoFiltro(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    
      log.debug("invocando a PTOVENTA_VTA_LISTA.VTA_LISTA_FILTRO_PROD(?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA_LISTA.VTA_LISTA_FILTRO_PROD(?)",parametros,false);
  }
  
  /**
     * Obtiene los codigos de los encartes permitidos en la fecha actual
     * @param pArrayList
     * @throws SQLException
     */
  public static void obtieneEncartesAplicables(ArrayList pArrayList) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
      log.debug("invocando a FARMA_GRAL.GET_ENCARTES_APLICABLES(?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"FARMA_GRAL.GET_ENCARTES_APLICABLES(?,?)",parametros);
  }  
  
  /**
   * Obtiene informacion del producto para la venta
   * @param pArrayList
   * @param pCodProd
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 29.05.2008
   */
  public static void obtieneInfoProductoVta(ArrayList pArrayList, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(FarmaConstants.INDICADOR_S);
      log.debug("invocando a PTOVENTA_VTA_DOS.VTA_OBTIENE_INFO_PROD(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA_DOS.VTA_OBTIENE_INFO_PROD(?,?,?,?)",parametros);
  }  

  /**
   * Obtiene informacion complementario del producto para la venta
   * @param pArrayList
   * @param pCodProd
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 29.05.2008
   */
  public static void obtieneInfoDetalleProductoVta(ArrayList pArrayList, String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(FarmaConstants.INDICADOR_S);
      log.debug("invocando a PTOVENTA_VTA.VTA_OBTIENE_INFO_COMPL_PROD(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_INFO_COMPL_PROD(?,?,?,?)",parametros);
  }  
  
  /**
   * Se obtiene el producto sugerido
   * @param pArrayList
   * @param pCodProd
   * @param cantVta
   * @throws SQLException
   * @author Jorge Cortez Alvarez<br>
   * @since  29.08.2008
   */
  public static void obtieneInfoProductoSug(ArrayList pArrayList, String pCodProd,String cantVta) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(new Integer(cantVta));
      log.debug("invocando a PTOVENTA_VTA.VTA_OBTIENE_PROD_SUG(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_PROD_SUG(?,?,?,?)",parametros);
  }
  
  public static void procesoPackRegalo(String pNumPedVta) throws SQLException {
    parametros = new ArrayList();

    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta.trim());
    parametros.add(FarmaVariables.vNuSecUsu);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(FarmaVariables.vIpPc);
      log.debug("invocando a PTOVENTA_PROMOCIONES.PROCESO_PROM_REGALO(?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_PROMOCIONES.PROCESO_PROM_REGALO(?,?,?,?,?,?)",parametros,false);//JCHAVEZ 19102009
      /*PTOVENTA_JCG*/

  }
  
  /**
   * Obtiene los codigos de las campa�as cupones permitidos en la fecha actual
   * @param pArrayList
   * @throws SQLException
   */
  public static void obtieneCampCuponAplicables(ArrayList pArrayList,
                                                String pTipCupon,
                                                String pNumPed) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pTipCupon.trim());
    parametros.add(pNumPed);
      log.debug("invocando a FARMA_GRAL.GET_CAMP_CUPO_APLICABLES(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"FARMA_GRAL.GET_CAMP_CUPO_APLICABLES(?,?,?,?)",parametros);
  }   
  
  /**
   * Se verifica y se recupera los datos del cupon.
   * @param pCupon
   * @param pArreglo
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 03.07.2008
   */
   //Modificado por DVELIZ 04.10.08
  public static void verificaCupon(String pCupon, 
                                   ArrayList pArreglo,
                                   String indMultiUso,
                                   String vDniCliente) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCupon);
    parametros.add(indMultiUso);
    parametros.add(vDniCliente);
    //FarmaDBUtility.executeSQLStoredProcedureArrayList(pArreglo,"PTOVENTA_VTA.VERIFICA_DATOS_CUPON(?,?,?,?)",parametros);
    //Se validara el cupon en el local
    //01.09.2008 dubilluz Modificacion
    log.debug("invocando a PTOVENTA_CUPON.CUP_F_CUR_VALIDA_CUPON(?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(
            pArreglo,"PTOVENTA_CUPON.CUP_F_CUR_VALIDA_CUPON(?,?,?,?,?)",
            parametros);
  }
  
  /**
   * Verifica que el producto este asociado a la campanha.
   * @param pCodCamp
   * @param pCodProd
   * @return
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 03.07.2008
   */
  public static String verificaProdCamp(String pCodCamp, String pCodProd) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodCamp);
    parametros.add(pCodProd);
      log.debug("invocando a PTOVENTA_VTA.VERIFICA_CAMP_PROD(?,?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VERIFICA_CAMP_PROD(?,?,?)",parametros);
  }
  
  /**
   * Se graba el pedido cupon de uso.
   * @param pNumPedVta
   * @param pCodCupon
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 04.07.2008
   */
  public static void grabaPedidoCupon(String pNumPedVta, String pCodCupon, String pCodCamp, String pIndUso) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta);
    parametros.add(pCodCupon);
    parametros.add(pCodCamp);
    parametros.add(pIndUso);
    parametros.add(FarmaVariables.vIdUsu);
      log.debug("invocando a PTOVENTA_VTA.GRABA_CUPON_PEDIDO_USO(?,?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.GRABA_CUPON_PEDIDO_USO(?,?,?,?,?,?,?)",parametros,false);
  }
  
  /**
   * Se procesa los cupones de uso del pedido.
   * @param pNumPedVta
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 04.07.2008
   */
  public static void procesaPedidoCupon(String pNumPedVta) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta);
    parametros.add(FarmaVariables.vIdUsu);
      log.debug("invocando a PTOVENTA_VTA.PROCESA_CAMP_PED_CUPON(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.PROCESA_CAMP_PED_CUPON(?,?,?,?)",parametros,false);
  }

  /**
   * Se verifica la validez del cupon en matriz.
   * @param pCupon
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 10.07.2008
   */
  public static String verificaCuponMatriz(String pCupon,String indMultiUso,
                                           String pIndCloseConecction) throws SQLException
  {
      log.debug("15/07/09 verifica CUPON");
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCupon);
    parametros.add(indMultiUso);
      log.debug("invocando a PTOVENTA_MATRIZ_CUPON.VERIFICA_DATOS_CUPON(?,?,?,?):"+parametros);
    return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_CUPON.VERIFICA_DATOS_CUPON(?,?,?,?)",
                                                             parametros,
                                                             FarmaConstants.CONECTION_MATRIZ,
                                                             pIndCloseConecction);
  } 
  
  
  /**
     * Proceso para agregar campa�as cupon al pedido
     * @param pNumPedVta
     * @param pTipoCampana
     * @throws SQLException
     */
   //Modificado por DVELIZ  04.10.08
  public static void procesaCampanaCupon(String pNumPedVta,
                                         String pTipoCampana,
                                         String pDniCliente) throws SQLException  
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta.trim());
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(pTipoCampana.trim());
    parametros.add(pDniCliente.trim());
      log.debug("invocando a PTOVENTA_VTA.VTA_PROCESO_CAMPANA_CUPON(?,?,?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,
        "PTOVENTA_VTA.VTA_PROCESO_CAMPANA_CUPON(?,?,?,?,?,?)",
        parametros,false);
    
  }
  
  /**
   * Se obtiene la descripcion de la campa�a
   * @param pNumPedVta
   * @param pTipoCampana
   * @throws SQLException
   */
   public static void obtieneInfoCamp(ArrayList pArrayList, String CodCamp) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(CodCamp);
      log.debug("invocando a PTOVENTA_VTA.VTA_OBTIENE_INFO_CAMP(?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_INFO_CAMP(?,?)",parametros);
  }
  
  /**
   * Se obtiene el indicador de multiuso
   * @author JCORTEZ
   * @since 15.08.2008
   */
    public static void obtieneIndMultiuso(ArrayList pArrayList,String cadena) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(cadena);
      log.debug("invocando a PTOVENTA_VTA.VTA_OBTIENE_IND_CAMP(?,?,?):"+parametros);
   FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_IND_CAMP(?,?,?)",parametros);
    //return (String)((ArrayList)myArray.get(0)).get(1);
  }
  
 
  /**
   * Se graba los productos ingresados
   * @throws SQLException
   * @author JCORTEZ
   * @since 20.08.2008
   */
  public static void insertProdCamp(String pCodProd, 
                                   String pValPrec) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pValPrec)));
      log.debug("invocando a PTOVENTA_VTA.GRABA_PROD_CAMP(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.GRABA_PROD_CAMP(?,?,?,?)",parametros,false);
  }
  
   /**
   * Se graba las campa�as ingresadas
   * @throws SQLException
   * @author JCORTEZ
   * @since 20.08.2008
   */
  public static void insertCampCupon(String pCodCamp, 
                                   String pCodCupon) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodCamp);
    parametros.add(pCodCupon);
    log.debug("invocando a PTOVENTA_VTA.GRABA_CAMP(?,?,?,?):"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.GRABA_CAMP(?,?,?,?)",parametros,false);
  }
  
  
  /**
   * Se obtiene el total de ahorro
   * @throws SQLException
   * @author JCORTEZ
   * @since 20.08.2008
   */
   public static String obtieneTotalAhorro(String totalPrecio) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(totalPrecio)));
      log.debug("invocando a PTOVENTA_VTA.OBTIENE_TOTAL_AHORRO(?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.OBTIENE_TOTAL_AHORRO(?,?)",parametros);
   //return FarmaDBUtility.executeSQLStoredProcedureInt("PTOVENTA_VTA.OBTIENE_TOTAL_AHORRO(?,?)",parametros);
   
  }
  
 /**
   * Se verifica la existencia del codigo de barra en el local
   * @author JCORTEZ
   * @since 28.08.2008
   */
  public static String verificaCodBarraLocal(String cadena) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(cadena);
      log.debug("invocando a PTOVENTA_VTA.VALIDA_COD_BARRA(?,?,?):"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VALIDA_COD_BARRA(?,?,?)",parametros);
  }
  
  /**
   * obtiene los parametros del local como un arreglo
   * @autor JCALLO
   * @since 01.10.2008
   * 
   * */
  
  public static void getParametrosLocal(ArrayList parametrosLocal) throws SQLException {
          parametros = new ArrayList();
          parametros.add(FarmaVariables.vCodGrupoCia);
          parametros.add(FarmaVariables.vCodLocal);
            log.debug("invocando a PTOVENTA_ADMIN_MANT.GET_PARAMETROS_LOCAL(?,?):"+parametros);
          FarmaDBUtility.executeSQLStoredProcedureArrayList(parametrosLocal,"PTOVENTA_ADMIN_MANT.GET_PARAMETROS_LOCAL(?,?)", parametros);
  }

    /**
     * 
     * @param pCodProd
     * @param pCodCampana
     * @return
     * @throws SQLException
     */
    public static String getNuevoPrecio(String pCodProd, 
                                        String pCodCampana,
                                        String pPrecioVenta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodCampana.trim());
        parametros.add(pCodProd.trim());
        parametros.add(pPrecioVenta.trim());
        
        log.debug("invocando a PTOVENTA_FIDELIZACION.FID_F_VAR2_GET_PRECIO_PROD(?,?,?,?,?):"+parametros);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_VAR2_GET_PRECIO_PROD(?,?,?,?,?)", 
                                                           parametros);
    }


    /**
     * Actualiza el detalle del pedido de venta con los descuentos
     * @author dveliz
     * @since  09.10.08
     * @param codProd
     * @param porcDcto1
     * @param codCampCupon
     * @param ahorro
     * @param porcDctoCalc
     * @throws SQLException
     */
    public static void guardaDctosDetPedVta(String codProd,
                                              String codCampCupon,
                                              String porcDcto1,
                                              String ahorro,
                                              String porcDctoCalc,
                                           String secPedVtaDet)throws SQLException{
        parametros = new ArrayList();
        parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(codProd);
        parametros.add(codCampCupon);
        parametros.add(new Double(porcDcto1));
        parametros.add(new Double(ahorro));
        parametros.add(new Double(porcDctoCalc));
        parametros.add(new Integer(secPedVtaDet)); //JMIRANDA 30.10.2009 SE ENVIA EL SEC_PED_VTA_DET
        
        log.debug("invocando a PTOVENTA_VTA.VTA_P_UPDATE_DET_PED_VTA (?,?,?,?,?,?,?,?,?):"+parametros);
        
        FarmaDBUtility.executeSQLStoredProcedure(null,
                "PTOVENTA_VTA.VTA_P_UPDATE_DET_PED_VTA (?,?,?,?,?,?,?,?,?)",
                parametros,false);
    }
    
    
    /***
     * metodo encargado de validar la consistencia del valor de la venta entre la cabecera y detalle
     * valor de redondeo
     * @since 04.11.2008
     * @autor jcallo
     * @param pNumPedidoVenta     
     * @throws SQLException
     */
    public static void validarValorVentaNeto(String pNumPedidoVenta)throws SQLException{
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);//codigo del grupo de la compa�ia
        parametros.add(FarmaVariables.vCodLocal);//codigo del local
        parametros.add(pNumPedidoVenta);//numero de pedido generado
        
        log.debug("invocando a PTOVENTA_VTA.VTA_P_VALIDAR_VALOR_VTA (?,?,?):"+parametros);
        
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_P_VALIDAR_VALOR_VTA (?,?,?)",
                                                 parametros,false);
    }
    
    public static String getPrecioNormal(String pCodProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd.trim());
        log.debug("invocando a PTOVENTA_FIDELIZACION.FID_F_VAR2_GET_PRECIO_NORMAL(?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_FIDELIZACION.FID_F_VAR2_GET_PRECIO_NORMAL(?,?,?)", 
                                                           parametros);
    }
    
    /* ********************************************************************* */
    /**
     * @author dubilluz
     * @since  18.12.2008
     * @param  pNumPedidoVenta
     * @param  pDniCli
     * @throws SQLException
     */
    public static void operaAcumulaUnidadesCampa�a(String pNumPedidoVenta,
                                                   String pDniCli)throws SQLException{
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVenta.trim());
        parametros.add(pDniCli.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_P_ACUMULA_UNIDADES (?,?,?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CA_CLIENTE.CA_P_ACUMULA_UNIDADES (?,?,?,?,?)",
                                                 parametros,false);
    }
    
    
    /**
     * Retorna las unidades acumuladas en el pedido que actualmente se esta generando
     * luego estos mismos se insertaran en Matriz
     * @authod Dubilluz
     * @since  18.12.2008
     * @param  pListaAcumulacion
     * @param  pNumPedVta
     * @param  pDniCli
     * @throws SQLException
     */
    public static void getListaUnidadesAcumuladas(ArrayList pListaAcumulacion,
                                          String pNumPedVta,
                                          String pDniCli) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        parametros.add(pDniCli);
        /*
        cCodGrupoCia_in IN CHAR,
        cCodLocal_in    IN CHAR,
        cNumPedVta_in   IN CHAR,
            cDni_in         IN CHAR
         * */
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CUR_UNID_ACUMULADAS(?,?,?,?):"+parametros);
       FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaAcumulacion,
                                   "PTOVENTA_CA_CLIENTE.CA_F_CUR_UNID_ACUMULADAS(?,?,?,?)",
                                                         parametros);
    }    
    

    public static void insertaAcumuladosEnMatriz(String pDni,
                                                 String pCodCia,
                                                 String pCodCamp,
                                                 String pCodLocal,
                                                 String pNumPed,
                                                 String pFechPed,
                                                 String pSecDet,
                                                 String pCodProd,
                                                 String pCantPed,
                                                 String pValFrac,
                                                 String pEstado,
                                                 String pValFracMin ,
                                                 String pUsuCrea,
                                                 String pCantRestante
                                                 )throws SQLException{
        parametros = new ArrayList();        
        /*parametros.add(pDni.trim());
        parametros.add(pCodCia.trim());
        parametros.add(pCodCamp.trim());
        parametros.add(pCodLocal.trim());
        parametros.add(pNumPed.trim());
        parametros.add(pFechPed.trim());
        parametros.add(pSecDet.trim());
        parametros.add(pCodProd.trim());
        parametros.add(pCantPed.trim());
        parametros.add(pValFrac.trim());
        parametros.add(pEstado.trim());
        parametros.add(pValFracMin.trim());
        parametros.add(pUsuCrea.trim());
*/
        
                parametros.add(pCodCia.trim());
                parametros.add(pCodLocal.trim());
                parametros.add(pNumPed.trim());
                parametros.add(pDni.trim());
                parametros.add(pCodCamp.trim());
                parametros.add(pSecDet.trim());
                parametros.add(pCodProd.trim());
                parametros.add(pCantPed.trim());
                parametros.add(pValFrac.trim());
                parametros.add(pEstado.trim());
                parametros.add(pValFracMin.trim());
                parametros.add(pUsuCrea.trim());        
                parametros.add(pCantRestante.trim());        
        /*
        cCodGrupoCia_in   IN CHAR,
                                             cCodLocal_in      IN CHAR,
                                             cNumPed_in        IN CHAR,
                                             cDni_in           IN CHAR,
                                             cCodCamp_in       IN CHAR,
                                             nSecPedVta_in     IN NUMBER,
                                             cCodProd_in       IN CHAR,
                                             nCantPedido_in    IN NUMBER,
                                             nValFracPedido_in IN NUMBER,
                                             cEstado_in        IN CHAR,
                                             nCantRest_in      IN NUMBER,
                                             nValFracMin_in    IN NUMBER,
                                             cUsuCrea_in       IN CHAR
         * */
        //log.info("inserta acumulado " + parametros);
        
     /*
     pDni, pCodCia, pCodCamp, pCodLocal, pNumPed, pFechPed, pSecDet,
     pCodProd, pCantPed, pValFrac, pEstado, pValFracMin, pUsuCrea
      */
        log.debug("invocando a PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_HIS_PED_CLI(?,?,?,?,?,?,?,?,?,?,?,?,?):"+parametros);
        /*FarmaDBUtilityRemoto.executeSQLStoredProcedure(null, 
                                                       "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_HIS_PED_CLI(?,?,?,?,?," +
                                                                                                         "?,?,?,?,?," +
                                                                                                         "?,?,?)", 
                                                       parametros, false, 
                                                       FarmaConstants.CONECTION_MATRIZ, 
                                                       FarmaConstants.INDICADOR_N);
        */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null,
                        "PTOVENTA_MATRIZ_CA_CLI.CA_P_INSERT_HIS_PED_CLI(?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros,false);
        
    }

    /**
     * @author Dubilluz 
     * @param  pListaDatos
     * @param  pNumPedVta
     * @param  pDniCli
     * @throws SQLException
     */
    public static void operaIntentoRegaloCampa�aAcumulada(
                                                          ArrayList pListaDatos,
                                                          String pNumPedVta, 
                                                          String pDniCli
                                                         ) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta.trim());
        parametros.add(pDniCli.trim());
        parametros.add(FarmaVariables.vIdUsu);

        log.debug("invocando a PTOVENTA_MATRIZ_CA_CLI.CA_F_OPERA_BENEFICIO_CAMPANA(?,?,?,?,?):"+parametros);
        /*
        FarmaDBUtilityRemoto.executeSQLStoredProcedureArrayList(pListaDatos, 
                                                          "PTOVENTA_MATRIZ_CA_CLI.CA_F_OPERA_BENEFICIO_CAMPANA(?,?,?,?,?)", 
                                                          parametros, 
                                                          FarmaConstants.CONECTION_MATRIZ, 
                                                          FarmaConstants.INDICADOR_N);
        */
        //JMIRANDA 16/07/09
        
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pListaDatos,
                        "PTOVENTA_MATRIZ_CA_CLI.CA_F_OPERA_BENEFICIO_CAMPANA(?,?,?,?,?)",parametros);
        
    }
    
    public static void a�adeRegaloCampa�a(String pNumPedidoVenta,
                                          String pDniCli,
                                          String pCodCamp
                                         )throws SQLException{
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVenta.trim());
        parametros.add(pDniCli.trim());
        parametros.add(pCodCamp.trim());
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIpPc);
        parametros.add(FarmaVariables.vIdUsu);
        
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_P_ADD_PROD_REGALO_CAMP(?,?,?,?,?,?,?,?):"+parametros);
        
        FarmaDBUtility.executeSQLStoredProcedure(
                                                 null,
                                                 "PTOVENTA_CA_CLIENTE.CA_P_ADD_PROD_REGALO_CAMP(?,?,?,?,?,?,?,?)",
                                                 parametros,
                                                 false
                                                );
    } 
    
    public static void a�adePedidosOrigenCanje(
                                                String pDniCli,
                                                String pCodCamp,
                                                String pNumPedidoVenta,
                                                String pCodLocalOrigen,
                                                String pNumPedOrigen,
                                                String pSecPedOrigen,
                                                String pCodProdOrigen,
                                                String pCantUsoOrigen,
                                                String pValFracMinOrigen
                                               )throws SQLException{
        parametros = new ArrayList();        

        parametros.add(pDniCli.trim());
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodCamp.trim());
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVenta.trim());
        parametros.add(pCodLocalOrigen.trim());
        parametros.add(pNumPedOrigen.trim());
        parametros.add(pSecPedOrigen.trim());
        parametros.add(pCodProdOrigen.trim());
        parametros.add(pCantUsoOrigen.trim());
        parametros.add(pValFracMinOrigen.trim());
        parametros.add(FarmaVariables.vIdUsu);
        
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_P_INSERT_PED_ORIGEN(?,?,?,?,?,?,?,?,?,?,?,?):"+parametros);
        
        FarmaDBUtility.executeSQLStoredProcedure(
                                                 null,
                                                 "PTOVENTA_CA_CLIENTE.CA_P_INSERT_PED_ORIGEN(?,?,?,?,?,?,?,?,?,?,?,?)",
                                                 parametros,
                                                 false
                                                );
    } 
    
    public static void actualizaPedidoXCampanaAcumulada(
                                                String pDniCli,
                                                String pNumPedidoVenta
                                               )throws SQLException{
        parametros = new ArrayList();        

        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVenta.trim());
        parametros.add(pDniCli.trim());
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_P_UPDATE_DATA_PED_CAB(?,?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(
                                                 null,
                                                 "PTOVENTA_CA_CLIENTE.CA_P_UPDATE_DATA_PED_CAB(?,?,?,?)",
                                                 parametros,
                                                 false
                                                );
    } 

    /**
     * 
     * @author dubilluz
     * @param pCodProd
     * @throws SQLException
     */
    public static String existeProdEnCampa�aAcumulada(String pCodProd,String pDNI)throws SQLException{
        
       parametros = new ArrayList();        
       parametros.add(FarmaVariables.vCodGrupoCia);
       parametros.add(FarmaVariables.vCodLocal);
       parametros.add(pCodProd.trim());
        parametros.add(pDNI.trim());       
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_F_CHAR_EXIST_PROD_CAMP (?,?,?,?):"+parametros);
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CA_CLIENTE.CA_F_CHAR_EXIST_PROD_CAMP (?,?,?,?)",
                                                parametros);
    }   
    
    
    public static void actualizaProcesoMatrizHistorico(String pNumPedido,String pDniCli,String pIndProcesoMatriz)throws SQLException{
        
       parametros = new ArrayList();        
       parametros.add(FarmaVariables.vCodGrupoCia);
       parametros.add(FarmaVariables.vCodLocal);
       parametros.add(pNumPedido.trim());
        parametros.add(pDniCli.trim());       
        parametros.add(pIndProcesoMatriz.trim());               
        log.debug("invocando a PTOVENTA_CA_CLIENTE.CA_P_UPDATE_PROCESO_MATRIZ_HIS (?,?,?,?,?):"+parametros);
        
       FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CA_CLIENTE.CA_P_UPDATE_PROCESO_MATRIZ_HIS (?,?,?,?,?)",
                                                parametros,true);
    }
    
    public static void revertirCanjeMatriz(
                                            String  pDniCli,
                                            String  pcodCampana,
                                            String  pNumPed
                                             )throws SQLException{
        
       parametros = new ArrayList();        
       parametros.add(FarmaVariables.vCodGrupoCia);
       parametros.add(FarmaVariables.vCodLocal);
       parametros.add(pNumPed.trim());       
       parametros.add(pDniCli.trim());
       parametros.add(pcodCampana.trim());
       parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVENTA_MATRIZ_CA_CLI.CA_P_REVERTIR_CANJE_MATRIZ (?,?,?,?,?,?):"+parametros);
        /*
       FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,"PTOVENTA_MATRIZ_CA_CLI.CA_P_REVERTIR_CANJE_MATRIZ (?,?,?,?,?,?)",
                                                parametros,true,FarmaConstants.CONECTION_MATRIZ,FarmaConstants.INDICADOR_N);
       */
        //JMIRANDA 16/07/09
        FarmaDBUtility.executeSQLStoredProcedure(null,
                        "PTOVENTA_MATRIZ_CA_CLI.CA_P_REVERTIR_CANJE_MATRIZ (?,?,?,?,?,?)",parametros,false);
    }
    
    
    /**
        * @author asolis 
        * @fecha  14-01-09
        * VerificarRecargaPedido
        * @throws SQLException
        * */

       public static ArrayList verificaRecargaPedido(String correlativo,String monto) throws SQLException {
         ArrayList pOutParams = new ArrayList();
             parametros = new ArrayList();
             parametros.add(FarmaVariables.vCodGrupoCia);
             parametros.add(FarmaVariables.vCodLocal);
             parametros.add(correlativo);
             parametros.add(new Double(monto));
           
           log.debug("invocando a Ptoventa_Recarga.RE_F_VERIFICA_RECARGA_PEDIDO(?,?,?,?):"+parametros);
           
         FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"Ptoventa_Recarga.RE_F_VERIFICA_RECARGA_PEDIDO(?,?,?,?)",parametros); 
         return pOutParams;
       }
       
       /**
        * @author asolis 
        * @fecha  15-01-09
        *Muestra Mensaje
        * @throws SQLException
        * */
       public static String mostrarMensajeError(String pCodError)throws SQLException{
           
          parametros = new ArrayList();  
          parametros.add(pCodError.trim());       
           log.debug("invocando a Ptoventa_Recarga.RE_F_MOSTRAR_MENSAJE_ERROR (?):"+parametros);
          return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Recarga.RE_F_MOSTRAR_MENSAJE_ERROR (?)",parametros);
       }   
       
    /**
     * @author asolis 
     * @fecha  15-01-09
     *Muestra Mensaje
     * @throws SQLException
     * */
    public static ArrayList verificaRecargaComprobante(String tipoComp,String numComp,String monto) throws SQLException
        {
            ArrayList pOutParams = new ArrayList();
          parametros = new ArrayList();
          parametros.add(FarmaVariables.vCodGrupoCia);
          parametros.add(FarmaVariables.vCodLocal);
          parametros.add(tipoComp);
          parametros.add(numComp);
          parametros.add(new Double(monto));
            log.debug("invocando a Ptoventa_Recarga.RE_F_VERIFICA_RECARGA_COMPROB(?,?,?,?,?):"+parametros);
          FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"Ptoventa_Recarga.RE_F_VERIFICA_RECARGA_COMPROB(?,?,?,?,?)", parametros);
          return pOutParams;
        }
    
    /**
     * Retorna el numero de Pedido RecargaComprobante
     * @author  asolis
     * @since   16.01.2009
     */
    public static String getNumeroPedidoRecargaComprobante(String tipoComp,String numComp,String monto) throws SQLException
    {
      parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(tipoComp);
        parametros.add(numComp);
        parametros.add(new Double(monto));
        log.debug("invocando a Ptoventa_Recarga.RE_F_GET_NUMERO_PEDIDO(?,?,?,?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("Ptoventa_Recarga.RE_F_GET_NUMERO_PEDIDO(?,?,?,?,?)", parametros);
    } 
    
    /**
     * Retorna el precio final despues de validar con costro promedio y precio de venta
     * @author  jcallo
     * @since   05.02.2009
     */
    public static String getPrecioFinalCampania(String pCodProd,String pCodCamp,double pPrecioConDcto, double pPrecioVenta, int fraccion) throws SQLException
    {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        parametros.add(pCodCamp);
        parametros.add(new Double(pPrecioConDcto));
        parametros.add(new Double(pPrecioVenta));
        parametros.add(new Integer(fraccion));
        log.debug("invocando a PTOVENTA_VTA.VTA_F_GET_PRECIO_FINAL_VTA(?,?,?,?,?,?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_F_GET_PRECIO_FINAL_VTA(?,?,?,?,?,?,?)", parametros);
    } 
    
    /**
     * retorna el parametro de monto de venta minimo para pedir los 
     * datos del cliente
     * @param pCodProd
     * @param pCodCamp
     * @param pPrecioConDcto
     * @param pPrecioVenta
     * @param fraccion
     * @author Dubilluz
     * @return
     * @throws SQLException
     */
    public static String getMontoMinimoDatosCliente() throws SQLException
    {
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_VTA.VTA_F_GET_MONTO_VALIDA_DATOS():" + parametros);
        
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_F_GET_MONTO_VALIDA_DATOS", parametros);
    }
    
    /**
     * obtener detalle de datos cupon
     * @author  Javier Callo Quispe
     * @since   04.03.2009
     */
    public static Map getDatosCupon(String codCampCupon,String pNumeroCupon) throws SQLException
    {
    	prmts = new ArrayList();
    	prmts.add(FarmaVariables.vCodGrupoCia);
    	prmts.add(FarmaVariables.vCodLocal);
    	prmts.add(codCampCupon.trim());
        prmts.add(pNumeroCupon.trim());
        //datos de forma de PAGO
        prmts.add(VariablesFidelizacion.vIndUsoEfectivo.trim());
        prmts.add(VariablesFidelizacion.vIndUsoTarjeta.trim());
        prmts.add(VariablesFidelizacion.vCodFPagoTarjeta.trim());        
        log.debug("invocando a PTOVENTA_VTA.VTA_F_CURSOR_DATOS_CUPON(?,?,?,?,?,?,?):"+prmts);
        
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_VTA.VTA_F_CURSOR_DATOS_CUPON(?,?,?,?,?,?,?)", prmts);
    }
  
    /**
     * obtener detalle campa�a por precio
     * @author  TCT
     * @since   04-DIC-13
     */
    public static Map getDatosCampPrec(String codProd) throws SQLException
    {
        prmts = new ArrayList();
        prmts.add(FarmaVariables.vCodGrupoCia);
        prmts.add(codProd);
        
        log.debug("invocando a pkg_camp_precio.fn_get_mejor_prec_prod(?,?)"+ prmts);
        
        return FarmaDBUtility.executeSQLStoredProcedureMap("pkg_camp_precio.fn_get_mejor_prec_prod(?,?)", prmts);
    }  
    
    
    public static String validarCuponEnBD(String pCupon,
                                        String indMultiUso,
                                        String vDniCliente) throws SQLException
    {
    	prmts = new ArrayList();
    	prmts.add(FarmaVariables.vCodGrupoCia);
    	prmts.add(FarmaVariables.vCodLocal);
    	prmts.add(pCupon);
    	prmts.add(indMultiUso);
    	prmts.add(vDniCliente);    	
    	//el procedimiento por defect
    	log.debug("invocando a PTOVENTA_CUPON.CUP_F_CHAR_VALIDA_CUPON(?,?,?,?,?):"+prmts);
    	return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_CUPON.CUP_F_CHAR_VALIDA_CUPON(?,?,?,?,?)",
    												prmts);
    }
    
    /**
     * ver si el producto es aplicable a campania especificado
     * @author  Javier Callo Quispe
     * @since   09.03.2009
     */
    /*public static String prodAplicaCampania(String codCampCupon) throws SQLException
    {
    	prmts = new ArrayList();
    	prmts.add(FarmaVariables.vCodGrupoCia);
    	prmts.add(FarmaVariables.vCodLocal);
    	prmts.add(codCampCupon);
        log.debug("invocando a PTOVENTA_VTA.VTA_F_CURSOR_DATOS_CUPON(?,?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_VTA.VTA_F_CURSOR_DATOS_CUPON(?,?,?)", prmts);
    }*/
    
    /**
     * ver si el producto es aplicable a campania especificado
     * @author  Javier Callo Quispe
     * @since   09.03.2009
     */
     public static List prodsCampaniasAplicables(List listProds, List listCamps) throws SQLException
     {
         String selectSQL = "";
         String codProds = "";
         String p1=    "select * from (select cc.COD_PROD,cc.COD_CAMP_CUPON,cc.IND_EXCLUYE_ACUM_AHORRO ," +
             "CC.VALOR_CUPON,RANK() OVER (PARTITION BY cod_prod ORDER BY CC.VALOR_CUPON desc) orden " +
             "" + 
         "from (SELECT U.IND_EXCLUYE_ACUM_AHORRO,COD_PROD, v.COD_REAL as \"COD_CAMP_CUPON\" ,V.MONT_MIN_USO,v.unid_min_uso,v.valor_cupon, "+
         "(select sum(a.monto) from ("  
             ;
         
         String precioProducto = "";
         //agrega para que mont min uso sea por conjunto de productos.
         for(int i=0;i<listProds.size();i++){
                 codProds = "'"+((Map)listProds.get(i)).get("COD_PROD").toString()+"'";
                 precioProducto = ((Map)listProds.get(i)).get("TOTAL_PROD").toString().trim();
                 
                 if(i+1==listProds.size())
                    p1 = p1 + "select " + codProds +" prod ,round("+ precioProducto + ",1) monto from dual ";
                 else 
                  p1 = p1 + "select " + codProds +" prod ,round("+ precioProducto + ",1) monto from dual union "; 
         }
         
         p1 = p1 + ")a," + "vta_campana_prod_uso cu where a.prod =  cu.cod_prod " + 
         "and   cu.cod_grupo_cia  = u.cod_grupo_cia " + 
         "and   cu.cod_camp_cupon = u.cod_camp_cupon " + 
         // ----------------------------------
         // DUBILLUZ 10.10.2013     
         " AND   nvl(cu.ind_suma_uso ,'S') = '" + FarmaConstants.INDICADOR_S+"' "+
         // DUBILLUZ 10.10.2013     
         // ----------------------------------     
         ")suma_pedido,"+
         "(select sum(a.unidad_total) from ("  ;
         
         
          String unidadesProducto = "";
         //agrega para que mont min uso sea por conjunto de productos.
         for(int i=0;i<listProds.size();i++){
                 codProds = "'"+((Map)listProds.get(i)).get("COD_PROD").toString()+"'";
                 unidadesProducto = ((Map)listProds.get(i)).get("UNID_TOTAL_PROD").toString().trim();
                 
                 if(i+1==listProds.size())
                    p1 = p1 + "select " + codProds +" prod ,round("+ unidadesProducto + ",1) unidad_total from dual ";
                 else 
                  p1 = p1 + "select " + codProds +" prod ,round("+ unidadesProducto + ",1) unidad_total from dual union "; 
         }

         p1 = p1 + ")a," + "vta_campana_prod_uso cu where a.prod =  cu.cod_prod " + 
         "and   cu.cod_grupo_cia  = u.cod_grupo_cia " + 
         "and   cu.cod_camp_cupon = u.cod_camp_cupon " + 
         // ----------------------------------
         // DUBILLUZ 10.10.2013     
         " AND   nvl(cu.ind_suma_uso ,'S') = '" + FarmaConstants.INDICADOR_S+"' "+
         // DUBILLUZ 10.10.2013     
         // ----------------------------------     
         ")suma_unidad_pedido";         
         
         codProds = "";
         //fin dubilluz 21.06.2011
         
         String p2=    " decode(COD_PROD,"; 
         for(int i=0;i<listProds.size();i++){
                 if(i!=0){
                         codProds += ",";
                 }
                 codProds += "'"+((Map)listProds.get(i)).get("COD_PROD").toString()+"',"+i;
         }
         
         p2 = p2 +  codProds + ") ";
         /*
         (
                SELECT NVL(CP.COD_CAMP_CUPON_PROD_USO, CP.COD_CAMP_CUPON) COD_CAMPANA,CP.COD_CAMP_CUPON COD_REAL
                   FROM VTA_CAMPANA_CUPON CP
                  WHERE CP.COD_GRUPO_CIA = '001'
                    AND CP.COD_CAMP_CUPON IN ('A0001', 'A0016', '11543')
                ) V
          * */
         String p3= " FROM VTA_CAMPANA_PROD_USO u, ";
         String p4=        " ( SELECT NVL(CP.COD_CAMP_CUPON_PROD_USO,CP.COD_CAMP_CUPON) COD_CAMPANA,CP.COD_CAMP_CUPON COD_REAL," +
                                                     //agregado 04.03.2010
                                                     "cp.prioridad,cp.ranking,CP.MONT_MIN_USO,cp.unid_min_uso,cp.valor_cupon "+
                                                     " FROM VTA_CAMPANA_CUPON CP "+
                                                     " WHERE CP.COD_GRUPO_CIA = '"+FarmaVariables.vCodGrupoCia+"' "+
                                                     " AND   CP.COD_CAMP_CUPON IN (";
         
         /*
         String p5=        " WHERE COD_GRUPO_CIA = '"+FarmaVariables.vCodGrupoCia+"' "+
                           " AND   COD_CAMP_CUPON IN ( SELECT NVL(CP.COD_CAMP_CUPON_PROD_USO,CP.COD_CAMP_CUPON) "+
                                                     " FROM VTA_CAMPANA_CUPON CP "+
                                                     " WHERE CP.COD_GRUPO_CIA = '"+FarmaVariables.vCodGrupoCia+"' "+
                                                     " AND   CP.COD_CAMP_CUPON IN (";
         */
         String p5=        " WHERE COD_GRUPO_CIA = '"+FarmaVariables.vCodGrupoCia+"' "+
                           " AND   COD_CAMP_CUPON IN (V.COD_CAMPANA) " +
                             // ----------------------------------
                             // DUBILLUZ 10.10.2013     
                           " AND   nvl(U.ind_dcto_uso,'S') = '" + FarmaConstants.INDICADOR_S+"' ";
                             // ----------------------------------
                             // DUBILLUZ 10.10.2013             
         selectSQL = p1 + p3 + p4;
         codProds = "";    
         for(int i=0;i<listProds.size();i++){
                 if(i!=0){
                         codProds += ",";
                 }
                 codProds += "'"+((Map)listProds.get(i)).get("COD_PROD").toString()+"'";
         }
         
         String codCamps = "";
         for(int i=0;i<listCamps.size();i++){
                 if(i!=0){
                         codCamps += ",";
                 }
                 codCamps += "'"+((Map)listCamps.get(i)).get("COD_CAMP_CUPON").toString()+"'";                   
         }
         
         selectSQL += codCamps+")) V "+ p5 +
                                          " AND  COD_PROD IN ("+codProds+")"+
                                         // " ORDER BY COD_PROD, COD_CAMP_CUPON ";
                                         //comentado por el de la linea abajo.
                                         //" ORDER BY COD_PROD,COD_CAMP_CUPON,COD_PROD,"+p2+" asc ";
                                         " ORDER BY COD_PROD,v.prioridad asc ,v.ranking asc,COD_PROD,"+p2+" asc ) cc " + 
                                         " where  suma_pedido >= mont_min_uso" +
             " and   suma_unidad_pedido >= unid_min_uso) where orden  = 1";
                                         
         
         log.debug("ejecuntado query: "+selectSQL);
         log.debug("tamano de consulta: "+selectSQL.trim().length());
         return FarmaDBUtility.selectSQLList(selectSQL.trim(), prmts);
     }
    
    public static String getIndVenderDebajoCostoPromedio() throws SQLException{
    	log.debug("invocando a PTOVENTA_CUPON.CUP_F_CHAR_VALIDA_CUPON(?,?,?,?,?):"+prmts);
    	return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_CUPON.CUP_F_CHAR_VALIDA_CUPON(?,?,?,?,?)",
				prmts); 
    }
    
    public static String getIndImprimeRojo() throws SQLException{
        prmts = new ArrayList();
        log.debug("invocando a Farma_Gral.F_CHAR_GET_IND_IMP_COLOR:"+prmts);
        return FarmaDBUtility.executeSQLStoredProcedureString("Farma_Gral.F_CHAR_GET_IND_IMP_COLOR",
                                prmts); 
    }
    
    /**
     * Validar Rol
     * @param SecUsu
     * @param CodRol
     * @throws SQLException
     * @author JCORTEZ
     * @since 03.07.2009
     */
     public static String verificaRolUsuario(String SecUsu,String CodRol) throws SQLException
    {
       parametros = new ArrayList();
       parametros.add(FarmaVariables.vCodGrupoCia);
       parametros.add(FarmaVariables.vCodLocal);
       parametros.add(SecUsu);
       parametros.add(CodRol);
       log.debug("verifica que el usuario tenga el rol adecuado: " + parametros);
       return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VERIFICA_ROL_USU(?,?,?,?)",parametros);
    }
//JMIRANDA 04/08/09
    public static String getDestinatarioErrorCobro() throws SQLException{
        parametros = new ArrayList();
        log.debug("invocando a Farma_Gral.F_VAR2_GET_EMAIL_COBRO:"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("Farma_Gral.F_VAR2_GET_EMAIL_COBRO",
                                parametros); 
    }
    public static String getDestinatarioErrorAnulacion() throws SQLException{
        parametros = new ArrayList();
        log.debug("invocando a Farma_Gral.F_VAR2_GET_EMAIL_ANULACION:"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("Farma_Gral.F_VAR2_GET_EMAIL_ANULACION",
                                parametros); 
    }
    public static String getDestinatarioErrorImpresion() throws SQLException{
        parametros = new ArrayList();
        log.debug("invocando a Farma_Gral.F_VAR2_GET_EMAIL_IMPRESION:"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("Farma_Gral.F_VAR2_GET_EMAIL_IMPRESION",
                                parametros); 
    }
 /**
     * obtiene cupones posibles a usar por el cliente
     * @param Dni
     * @throws SQLException
     * @author JCORTEZ
     * @since 04.08.2009
     */ 
   /* public static void obtieneCuponesCliente(ArrayList pArrayList,String Dni) throws SQLException
    {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Dni);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_F_CUPON_CLI(?,?,?)", parametros);
        
    }*/
    
     public static void obtieneCuponesCliente(FarmaTableModel pTableModel,String Dni) throws SQLException {
      pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Dni);
      log.info("PTOVENTA_FIDELIZACION.VTA_F_CUPON_CLI(?,?,?) :"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_FIDELIZACION.VTA_F_CUPON_CLI(?,?,?)",parametros,true);
    }
 //JMIRANDA 11/08/09
    public static void actualizarCabeceraPedido() throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
      if(VariablesModuloVentas.vNom_Cli_Ped.length()>0)
          parametros.add(VariablesModuloVentas.vNom_Cli_Ped);
      else
          parametros.add(VariablesFidelizacion.vNomCliente+" "+VariablesFidelizacion.vApePatCliente+" "+VariablesFidelizacion.vApeMatCliente);
      parametros.add(VariablesModuloVentas.vDir_Cli_Ped);
      parametros.add(VariablesModuloVentas.vRuc_Cli_Ped);
 
      //Indicador Pedido-Convenio
      log.debug("invocando a PTOVENTA_VTA.VTA_P_UPDATE_PEDIDO_VTA_CAB(?,?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_VTA.VTA_P_UPDATE_PEDIDO_VTA_CAB(?,?,?,?,?,?)",parametros,false);
    }

    public static String getIndVerStockLocales() throws SQLException{
        parametros = new ArrayList();
        log.debug("invocando a Farma_Gral.F_VAR2_GET_IND_VER_STOCK:"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("Farma_Gral.F_VAR2_GET_IND_VER_STOCK",
                                parametros); 
    }
 
    /**
        * obtiene cupones posibles a usar por el cliente
        * @param codigo producto
        * @throws SQLException
        * @author JCHAVEZ
        * @since 31.09.2009
        */
    public static String getDescPaquete(String pCodProd) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_VTA.VTA_F_GET_DESC_PAQUETE(?,?):"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_VTA.VTA_F_GET_DESC_PAQUETE(?,?)",
                                parametros); 
    }
/**
     * Obtiene Indicador Codigo de Barra para un producto.
     * @param pCodProd
     * @return
     * @throws SQLException
     * @author JMIRANDA
     * @since 18.09.2009
     */
    public static String getIndCodBarra(String pCodProd) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_VTA.VTA_F_GET_IND_COD_BARRA(?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_F_GET_IND_COD_BARRA(?,?)",parametros);
    }
    
    /**
     * Obtiene Indicador si se va a Solicitar ID USUARIO para vender
     * @param pCodProd
     * @return
     * @throws SQLException
     * @author JMIRANDA
     * @since 18.09.2009
     */
    public static String getIndSolIdUsu(String pCodProd) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_VTA.VTA_F_GET_IND_SOL_ID_USU(?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_F_GET_IND_SOL_ID_USU(?,?)",parametros);
    }
    
    /**
     * Obtiene mensaje del Producto
     * @param pCodProd
     * @return
     * @throws SQLException
     * @author JMIRANDA
     * @since 18.09.2009
     */
    public static String getMensajeProd(String pCodProd) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_VTA.VTA_F_GET_MENS_PROD(?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.VTA_F_GET_MENS_PROD(?,?)",parametros);
    }
   
    /** 
     * Obtiene Delimitador para los mensajes mostrados en el Listado de productos
     * @return
     * @author JMIRANDA
     * @since  18.09.2009
      */
    public static String getDelimitadorMensaje() throws SQLException{
        parametros = new ArrayList();
        log.debug("invocando a PTOVENTA_VTA.VTA_F_GET_DELIM_MENS:"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_VTA.VTA_F_GET_DELIM_MENS",
                                parametros); 
    }
    
    /**
    * obtiene indicador para visualizar la opcion Ver Cupones
    * @return indicador de ver supones: S muestra y N no se muestra
    * @throws SQLException
    * @author JCHAVEZ
    * @since 08.10.2009
    */
    public static boolean getIndVerCupones() throws SQLException{
        String indVerCupones;
        parametros = new ArrayList();
        log.debug("invocando a  Farma_Gral.F_GET_CHAR_IND_VER_CUPONES: "+parametros);
        indVerCupones = FarmaDBUtility.executeSQLStoredProcedureString("Farma_Gral.F_GET_CHAR_IND_VER_CUPONES",parametros);
        if ( indVerCupones.equalsIgnoreCase("S") ) 
            return true;
        else return false;
    }    
    
    /**
    * obtiene indicador para visualizar la opcion Pedido Delivery
    * @return indicador de ver supones: S muestra y N no se muestra
    * @throws SQLException
    * @author JCHAVEZ
    * @since 08.10.2009
    */
    public static boolean getIndVerPedidoDelivery() throws SQLException{
        String indVerCupones;
        parametros = new ArrayList();
        log.debug("invocando a  Farma_Gral.F_GET_CHAR_IND_VER_PED_DELIV: "+parametros);
        indVerCupones = FarmaDBUtility.executeSQLStoredProcedureString("Farma_Gral.F_GET_CHAR_IND_VER_PED_DELIV",parametros);
        if ( indVerCupones.equalsIgnoreCase("S") ) 
            return true;
        else return false;
    }
    
    /**
    * graba los promociones por pedido en la tabla pedido_pack
    * @throws SQLException
    * @author JCHAVEZ
    * @since 19.10.2009
    */
     public static void grabaPromXPedidoNoAutomaticos(String pCodProm,int pCantidad ) throws SQLException {
       parametros = new ArrayList();
       parametros.add(FarmaVariables.vCodGrupoCia);
       parametros.add(FarmaVariables.vCodLocal);
       parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
       parametros.add(pCodProm);
       parametros.add(new Integer(pCantidad));
       parametros.add(FarmaVariables.vIdUsu);

       log.debug("invocando a PTOVENTA_PROMOCIONES.VTA_P_GRABA_PROM_NO_AUTOMAT(?,?,?,?,?,?):"+parametros);
       FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_PROMOCIONES.VTA_P_GRABA_PROM_NO_AUTOMAT(?,?,?,?,?,?)",parametros,false);
     }
    
    
    /**
    * otiene precio redondeado
    * @throws SQLException
    * @author JCHAVEZ
    * @since 29.10.2009
    */
     public static double getPrecioRedondeado(double  pPrecio) throws SQLException {
       parametros = new ArrayList();       
       parametros.add(new Double(pPrecio));

       log.debug("invocando a Ptoventa_vta.VTA_F_NUMBER_PREC_REDONDEADO(?):"+parametros);
       return FarmaDBUtility.executeSQLStoredProcedureDouble("Ptoventa_vta.VTA_F_NUMBER_PREC_REDONDEADO(?)",parametros);
         }
             
             
     /**
      * Devuelve el precio minimo para venta de un producto
      * @auth : JCT
      * @date : 27-DIC-12
      * @throws SQLException
      */
     public static String getPrecioMinimoVta() throws SQLException {
       parametros = new ArrayList();       
       //parametros.add(new Double(pPrecio));

       log.debug("invocando a ptoventa.ptoventa_vta.fn_get_min_prec_vta:"+parametros);
       return FarmaDBUtility.executeSQLStoredProcedureString("ptoventa.ptoventa_vta.fn_get_min_prec_vta",parametros);
     }
    /**@author JCHAVEZ 
    * Obtiene indicador de redondeado, para apliocarlo o no
    * @fecha  29.10.09
    * @throws SQLException
    **/
    
    public static String getIndicadorAplicaRedondedo() throws SQLException
    {
     parametros = new ArrayList();
     log.debug("invocando a PTOVENTA_vta.VTA_F_CHAR_IND_DE_REDONDEADO():"+parametros);
     return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_vta.VTA_F_CHAR_IND_DE_REDONDEADO",parametros);
    }
    
    /**
     * Devuelve listado de productos para la lista disponible al cliente
     * @author ASOSA
     * @since  02.02.2010
     * @param pTableModel
     * @param cadena
     * @throws SQLException
     */
    public static void getListaProdsCliente(FarmaTableModel pTableModel,
                                            String cadena,
                                            String tipo)throws SQLException{
        pTableModel.clearTable();
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cadena.trim());        
        parametros.add(tipo.trim());    //ASOSA, 28.09.2010
        log.debug("Parametros: PTOVENTA_VTA_LISTA_AS.VTA_LISTA_PROD_02_NEW"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA_LISTA_AS.VTA_LISTA_PROD_02_NEW(?,?,?,?)",parametros,false);
    }
    
    public static String getMensajeComsumidor()throws SQLException{
        //LTERRAZOS 01.03.2013 Se agrega los parametros COD_CIA
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vIPBD);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("PTOVENTA_VTA_LISTA_AS.VTA_RETORNAR_MENSAJE(?,?,?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA_LISTA_AS.VTA_RETORNAR_MENSAJE(?,?,?,?)",parametros);
    }
    
    public static String getNumCaracteres()throws SQLException{
        parametros = new ArrayList();
        log.debug("getNumCaracteres: "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA_LISTA_AS.IMP_GET_NUM_CARACTERES",parametros);
    }
    
    /**
     * Devuelve el mensaje formateado de acuerdo a si es para label y Message
     * @author ASOSA
     * @since 19.04.2010
     * @param codmsg
     * @return
     * @throws SQLException
     */
    public static String getMensajeRptaRecarga(String codmsg)throws SQLException{
        parametros =new ArrayList();
        parametros.add(codmsg.trim());
        log.debug("parametros getMensajeRptaRecarga: "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECARGA.REC_F_GET_MSG(?)",parametros);
    }
    
    /**
     * Devuelve el indicador de label para el codigo de mensaje indicado
     * @author ASOSA
     * @since 19.04.2010
     * @param codmsg
     * @return
     * @throws SQLException
     */
    public static String getIND_LABEL(String codmsg)throws SQLException{
        parametros=new ArrayList();
        parametros.add(codmsg.trim());
        log.debug("parametros getIND_LABEL: "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_RECARGA.REC_F_GET_IND_LABEL(?)",parametros);
    }
    
    /**
     * Compromete stock en el ptoventa, menos cuando cobra
     * @author ASOSA
     * @since 01.07.2010
     * @param codprod
     * @param cantMov
     * @param valFrac
     * @param secResp
     * @return
     * @throws SQLException
     */
    /*
    public static String operarResStkAntesDeCobrar(String codprod,
                                                   String cantMov,
                                                   String valFrac,
                                                   String secResp,
                                                   String modulo)throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codprod.trim());
        parametros.add(cantMov.trim());
        parametros.add(valFrac.trim());
        parametros.add(secResp.trim());
        //parametros.add(ConstantsPtoVenta.MODULO_VENTAS);
        parametros.add(modulo.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("parametros operarResStk PTOVTA_RESPALDO_STK.PVTA_F_INS_DEL_UPD_STK_RES(?,?,?,?,?,?,?,?): "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVTA_RESPALDO_STK.PVTA_F_INS_DEL_UPD_STK_RES(?,?,?,?,?,?,?,?)",parametros);
    }
    */
    /**
     * Graba los detalles del pedido con sus respectivos secuenciales de respaldo
     * @author ASOSA
     * @since 05.07.2010
     * @throws SQLException
     */
    public static void grabarDetallePedido_02(String secrespaldo,
                                              boolean vIsEnfermera) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
      parametros.add(VariablesModuloVentas.vSec_Ped_Vta_Det);
      parametros.add(VariablesModuloVentas.vCod_Prod);
      parametros.add(new Integer(VariablesModuloVentas.vCant_Ingresada.trim()));
      parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Vta)));
      parametros.add(new Double(VariablesModuloVentas.vTotalPrecVtaProd));
      parametros.add(new Double(VariablesModuloVentas.vPorc_Dcto_1));
      // SOLO CUANDO ES UNA PROMOCION
      if(VariablesModuloVentas.vPorc_Dcto_2.trim().length()!=0)
        parametros.add(new Double(VariablesModuloVentas.vPorc_Dcto_2));
      else
        parametros.add(new Double(0));//porc dcto 2
      parametros.add(new Double(0));//porc dcto 3
      parametros.add(new Double(VariablesModuloVentas.vPorc_Dcto_Total));
      parametros.add(VariablesModuloVentas.vEst_Ped_Vta_Det);//estado de detalle
      parametros.add(new Double(VariablesModuloVentas.vVal_Total_Bono));
      parametros.add(new Integer(VariablesModuloVentas.vVal_Frac.trim()));
      parametros.add("");//secuncia de comprobante de pago
      parametros.add(VariablesModuloVentas.vSec_Usu_Local);
      parametros.add(new Double(VariablesModuloVentas.vVal_Prec_Lista));
      parametros.add(new Double(VariablesModuloVentas.vVal_Igv_Prod));
      parametros.add(VariablesModuloVentas.vUnid_Vta);
      parametros.add(VariablesModuloVentas.vNumeroARecargar);
      parametros.add(FarmaVariables.vIdUsu);
      parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vVal_Prec_Pub)));
      parametros.add(VariablesModuloVentas.vCodPromoDet);
      parametros.add(VariablesModuloVentas.vIndOrigenProdVta);
      parametros.add(VariablesModuloVentas.vCantxDia);
      parametros.add(VariablesModuloVentas.vCantxDias);
      parametros.add(new Double(FarmaUtility.getDecimalNumber(VariablesModuloVentas.vAhorroPack))); //JCHAVEZ 20102009 
      parametros.add(secrespaldo.trim()); 
      parametros.add(VariablesModuloVentas.vLoteProd);
      
      if(!vIsEnfermera){
          log.debug("invocando  a PTOVTA_RESPALDO_STK.PVTA_P_GRAB_PED_VTA_DET(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):"+parametros);
          FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVTA_RESPALDO_STK.PVTA_P_GRAB_PED_VTA_DET(" +
                                                   "?," +
                                                   "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros,false);
      }
      else {
          log.debug("invocando  a UTILITY_ENFERMERA.PVTA_P_GRAB_PED_VTA_DET(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?):"+parametros);
          FarmaDBUtility.executeSQLStoredProcedure(null,"UTILITY_ENFERMERA.PVTA_P_GRAB_PED_VTA_DET(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros,false);
      }

    }
    
    /**
     * Actualiza respaldo stk con el nro de pedido correspondiente al momento de pasar a la venata de cobro que fuere
     * @author ASOSA
     * @since 06.07.2010
     * @param codprod
     * @param modulo
     * @param numped
     * @param secresp
     * @throws SQLException
     */
    /*
    public static void actualizarRespaldoNumPedido(String codprod,
                                                   String modulo,
                                                   String numped,
                                                   String secresp)throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codprod.trim());
        parametros.add(modulo.trim());
        parametros.add(numped.trim());
        parametros.add(secresp.trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando  a PTOVTA_RESPALDO_STK.PVTA_P_UPD_RESSTK_NUMPED(?,?,?,?,?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVTA_RESPALDO_STK.PVTA_P_UPD_RESSTK_NUMPED(?,?,?,?,?,?,?)",parametros,false);      
    }
    */
    /**
     * Procesa los pack que dan productos regalo
     * @author ASOSA
     * @since 06.07.2010
     * @param pNumPedVta
     * @throws SQLException
     */
    public static void procesoPackRegalo_02(String pNumPedVta) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pNumPedVta.trim());
      parametros.add(FarmaVariables.vNuSecUsu);
      parametros.add(FarmaVariables.vIdUsu);
      parametros.add(FarmaVariables.vIpPc);
        log.debug("invocando a PTOVTA_RESPALDO_STK.PROCESO_PROM_REGALO(?,?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVTA_RESPALDO_STK.PROCESO_PROM_REGALO(?,?,?,?,?,?)",parametros,false);
    }
    
    /**
     * A�ade prod Regalo
     * @author ASOSA
     * @since 07.07.2010
     * @param pNumPedidoVenta
     * @param pDniCli
     * @param pCodCamp
     * @throws SQLException
     */
    public static void a�adeRegaloCampa�a_02(String pNumPedidoVenta,
                                          String pDniCli,
                                          String pCodCamp
                                         )throws SQLException{
        parametros = new ArrayList();        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVenta.trim());
        parametros.add(pDniCli.trim());
        parametros.add(pCodCamp.trim());
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIpPc);
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PTOVTA_RESPALDO_STK.CA_P_ADD_PROD_REGALO_CAMP(?,?,?,?,?,?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(
                                                 null,
                                                 "PTOVTA_RESPALDO_STK.CA_P_ADD_PROD_REGALO_CAMP(?,?,?,?,?,?,?,?)",
                                                 parametros,
                                                 false
                                                );
    }
    
    /**
     * Graba el detalle del pedido regalo
     * @author ASOSA
     * @since 09.07.2010
     * @param pNumPed
     * @param pCodprod
     * @param pSecProd
     * @param pCantAtend
     * @param pValPrecVta
     * @param secResplado
     * @throws SQLException
     */
    public static void grabaProductoRegalo_02(String pNumPed,String pCodprod,int pSecProd,int pCantAtend,
                                           int pValPrecVta,String secResplado) throws SQLException {
      
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pNumPed+"");
      parametros.add(pSecProd+"");
      parametros.add(pCodprod);
      parametros.add(pCantAtend+"");
      parametros.add(pValPrecVta+"");
      parametros.add(VariablesModuloVentas.vSec_Usu_Local);
      parametros.add(FarmaVariables.vIdUsu);
      parametros.add(secResplado.trim());
        log.debug("invocando a PTOVTA_RESPALDO_STK.VTA_GRABAR_PED_REGALO_DET(?,?,?,?,?,?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVTA_RESPALDO_STK.VTA_GRABAR_PED_REGALO_DET(?,?,?,?,?,?,?,?,?,?)",parametros,false);
    }
    
    /**
     * Lista los principios activos para un producto
     * @param pTableModel
     * @param codProd
     * @throws SQLException
     */
    public static void getListaPrincAct(FarmaTableModel pTableModel,
                                        String codProd)throws SQLException{
        pTableModel.clearTable();
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(codProd.trim());
        log.debug("Parametros: "+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VTA_LISTA_AS.VTA_F_GET_PRINC_ACT(?,?)",parametros,false);
    } 
    
    /**
     * Se carga la lista de productos DCI.
     * @param pTableModel
     * @param pCodProd
     * @throws SQLException
     * @author John Miranda
     * @since 02.10.2010
     */
    public static void cargaListaProductosDCI(FarmaTableModel pTableModel, 
                                                       String pCodProd)
      throws SQLException
    {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_VTA_LISTA_AS.VTA_LISTA_PROD_DCI(?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                               "PTOVENTA_VTA_LISTA_AS.VTA_LISTA_PROD_DCI(?,?,?)", 
                                               parametros, false);
        
    }    
    
    /**
     * Obtiene el Indicador si el producto es farma
     * @author JMIRANDA
     * @since 05.10.2010
     * @param pCodProd     
     * @return S si es producto farma
     * @throws SQLException
     */
    public static String getIndProdFarma(String pCodProd) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(pCodProd.trim());        
        log.debug("parametros PTOVENTA_VTA_LISTA_AS.VTA_F_IND_PROD_FARMA(?,?): "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA_LISTA_AS.VTA_F_IND_PROD_FARMA(?,?)",parametros);
    }
    
    /**
     * Obtiene el Indicador si imprime Correlativo o no
     * @author JMIRANDA
     * @since 22.08.2011
     * @return S si imprime Correlativo N no imprime
     * @throws SQLException
     */
    public static String getIndImprimirCorrelativo() throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.debug("parametros PTOVENTA_VTA.F_GET_IND_IMP_CORRELATIVO(?): "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.F_GET_IND_IMP_CORRELATIVO(?)",parametros);
    }
    
    /**
     * Obtiene el Correlativo y Monto Neto del Pedido
     * @author JMIRANDA
     * @since 22.08.2011
     * @param pTipoComp  Tipo Comprobante
     * @param pMontoNeto  Monto Neto
     * @param pNumCompPago N�mero Comprobante Pago (Serie + Comp)
     * @return Concatenado Correlativo y Monto, separado por ';'
     * @throws SQLException
     */
    public static String getIndImprimirCorrelativo(String pTipoComp,
                                                   String pMontoNeto,
                                                   String pNumCompPago) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipoComp);
        parametros.add(pMontoNeto);
        parametros.add(pNumCompPago);
        log.debug("parametros PTOVENTA_VTA.F_GET_CORRELATIVO_MONTO_NETO(?,?,?,?,?): "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.F_GET_CORRELATIVO_MONTO_NETO(?,?,?,?,?)",parametros);
    }
    
    public static String getIndImprimirCorrelativo_DOS(String pCorrelativo,
                                                   String pMontoNeto) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCorrelativo);
        parametros.add(pMontoNeto);
        log.debug("parametros PTOVENTA_VTA.F_GE_PED_ENFD(?,?,?,?): "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.F_GE_PED_ENF(?,?,?,?)",parametros);
    }
    
    
    /**
     * Obtiene el regalo a dar segun el Monto Neto de prod de encarte
     * @author JQUISPE
     * @since 02.12.2011
     * @param pCodencarte Codigo de encarte
     * @param pMonto Monto de la venta
     
     * @throws SQLException
     */
     
    public static void getDatosRegaloxMonto(ArrayList pArray ,String pCodEncarte,
                                              double pMonto) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodEncarte);
        parametros.add(pMonto+"");        
        log.debug("parametros Farma_Gral.F_EMITE_REGALO_X_MONTO(?,?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"Farma_Gral.F_EMITE_REGALO_X_MONTO(?,?,?,?)",parametros);
        //FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_INFO_COMPL_PROD(?,?,?,?)",parametros);
    }   
    
    
    
    /**
     * Obtiene el regalo a dar segun el Monto Neto de prod de encarte
     * @author JQUISPE
     * @since 16.12.2011
     * @param pCodencarte Codigo de encarte
     * @param pMonto Monto de la venta
     
     * @throws SQLException
     */
     
    public static String getStockProdRegalo(String cod_prod) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cod_prod);              
        log.debug("parametros PTOVENTA_VTA.F_GETSTOCK_PROD_REGALO(?,?,?): "+parametros);
        //FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"Farma_Gral.F_EMITE_REGALO_X_MONTO(?,?,?,?)",parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.F_GETSTOCK_PROD_REGALO(?,?,?)",parametros);
        //FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.VTA_OBTIENE_INFO_COMPL_PROD(?,?,?,?)",parametros);
    }
    
    
    public static void getEncarteAplica(ArrayList pArrayList , double Monto , String cod_encarte) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(cod_encarte);
        parametros.add(String.valueOf((int)Monto));              
        log.debug("parametros PTOVENTA_VTA.F_GETDATOS_ENCARTE_AP(?,?,?,?): "+parametros);
        //FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"Farma_Gral.F_EMITE_REGALO_X_MONTO(?,?,?,?)",parametros);
        //return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA.F_GETSTOCK_PROD_REGALO(?,?,?)",parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_VTA.F_GETDATOS_ENCARTE_AP(?,?,?,?)",parametros);
    }
    
    public static String getIndVerRecetarioMagis() throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodCia); 
        log.debug("Farma_Gral.F_VAR_GET_IND_VER_RECETARIO(?)"+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureString("Farma_Gral.F_VAR_GET_IND_VER_RECETARIO(?)",
                                parametros); 
    }

 /**
      * Graba en BD los datos de una venta restringida
      * @author wvillagomez
      * @since 02.09.2013
      * @throws SQLException
      */
    public static void insertaDatosVentaRestringidos() throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
        parametros.add(VariablesRecetario.vMapDatosPacienteMedico.get("DNI").toString().trim());
        parametros.add(VariablesRecetario.vMapDatosPacienteMedico.get("PACIENTE").toString().trim());
        parametros.add(VariablesRecetario.vMapDatosPacienteMedico.get("CMP").toString().trim());
        parametros.add(VariablesRecetario.vMapDatosPacienteMedico.get("MEDICO").toString().trim());
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("PTOVENTA_PSICOTROPICOS.VTA_GRABA_PEDIDO(?,?,?,?,?,?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PTOVENTA_PSICOTROPICOS.VTA_GRABA_PEDIDO(?,?,?,?,?,?,?,?)", parametros, false);
    }

    /**
    * Verifica si hay productos con venta restringida en el pedido
    * @author wvillagomez
    * @since 03.09.2013
    * @param pNumPedVta - tipo String
    * @return 'S' Venta restringida, 'N' venta normal - tipo String
    * @throws SQLException
    */
    public static String getVentaRestringida(String pNumPedVta) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        log.debug("PTOVENTA_PSICOTROPICOS.F_GET_VENTA_RESTRINGIDA(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_PSICOTROPICOS.F_GET_VENTA_RESTRINGIDA(?,?,?)",parametros);
    }

    public static void listaTipoCopago(String pCodigoConvenio,FarmaTableModel pTableModel) throws SQLException {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(pCodigoConvenio);
        log.debug("invocando a ptoventa_conv_btlmf.VTA_LISTA_FILTRO_COPAGO(?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"ptoventa_conv_btlmf.VTA_LISTA_FILTRO_COPAGO(?)",parametros,false);
    }
    
    /**
     *
     * @param pNumPedVta
     * @return
     * @throws SQLException
     */
    public static String getPermiteVtaNegativa(String pCodProd,String pCantidad,String pValFrac) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        parametros.add(new Integer(pValFrac));
        parametros.add(new Integer(pCantidad));
        log.debug("PKG_SOL_STOCK.SOL_F_PERMITE_VTA_NEGATIVA(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PKG_SOL_STOCK.SOL_F_PERMITE_VTA_NEGATIVA(?,?,?,?,?)",parametros);
    }
    
    public static String getGrabaSolcitudVtaNegativa(String pCodSolicitud,
                                                     String pQfAprueba,
                                                     String pCodProd,String pCantidad,String pValFrac) throws SQLException{
        parametros = new ArrayList();

        
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pQfAprueba);
        parametros.add(pCodProd);
        parametros.add(new Integer(pCantidad));
        parametros.add(new Integer(pValFrac));
        if(pCodSolicitud.trim().length()==0)
           parametros.add("X");
        else
           parametros.add(pCodSolicitud.trim());        
        log.debug("PKG_SOL_STOCK.SP_GRABA_SOL(): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PKG_SOL_STOCK.SP_GRABA_SOL(?,?,?,?,?,?,?,?)",parametros);
    }
    
    public static String getIndPermiteVtaNegativa() throws SQLException{
        parametros = new ArrayList();
        log.debug("KG_SOL_STOCK.F_PERMITE_VTA_NEGATIVA: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PKG_SOL_STOCK.F_PERMITE_VTA_NEGATIVA()",parametros);
    }    

    /**
     * @author Dubilluz
     * @since  22.01.2014
     * */
    public static String getEsPedidoAntiguo(
                                         String pFecha,
                                         String pTipComp,
                                         String pNumComp,
                                         String pMontoComp) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecha+"");
        parametros.add(pTipComp);
        parametros.add(pNumComp);
        parametros.add(pMontoComp);
        log.debug("FARMA_GRAL.F_VAR_ES_PEDIDO_ANTIGUO: " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_GRAL.F_VAR_ES_PEDIDO_ANTIGUO(?,?,?,?,?,?)",parametros);
   
    }
    /**
     * @author Dubilluz
     * @since  22.01.2014
     * @param pFecha
     * @param pTipComp
     * @param pNumComp
     * @param pMontoComp
     * @throws SQLException
     */
    public static void grabaPedidoAntiguoMigracion(
                                         String pFecha,
                                         String pTipComp,
                                         String pNumComp,
                                         String pMontoComp) throws SQLException{
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFecha);
        parametros.add(pTipComp);
        parametros.add(pNumComp);
        parametros.add(pMontoComp);
        log.debug("PTOVENTA_MATRIZ_FASA_MIGRA.GEN_PEDIDO_ANTIGUO_LOCAL: " + parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,
                                                       "PTOVENTA_MATRIZ_FASA_MIGRA.GEN_PEDIDO_ANTIGUO_LOCAL(?,?,?,?,?,?)",
                                                       parametros,
                                                       true,
                                                       FarmaConstants.CONECTION_MATRIZ,
                                                       FarmaConstants.INDICADOR_S);
    }
    /**
     * Muestra el mensaje de impresion de Facturas 
     * @author Cesar Huanes<br>
     * @since   31.01.2014
     */ 
    public static Map obtieneMsgImpFactura() throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add("02");
        parametros.add(VariablesModuloVentas.vNumDocImpreso);

        log.debug("invocando  a PTOVENTA_CONV_BTLMF.MIFARMA_IMPR_MSG_GRAL(?,?,?,?) :" + 
                  parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_CONV_BTLMF.MIFARMA_IMPR_MSG_GRAL(?,?,?,?)", 
                                                           parametros);
    }
    
    /**
     * Valida si la el comprobante esta asociado a Convenio que genera GUIA 
     * @author Ricardo Herrera<br>
     * @since   12.03.2014
     */ 
    
    public static String validaNumComGuiaConvenio(  String cfecha_in,
                                                    String cTipComp_in,
                                                    String cNumComPago_in
                                                    ) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(cfecha_in);
      parametros.add(cTipComp_in);
      parametros.add(cNumComPago_in);
      
      log.debug("invocando a PTOVENTA_CONV_BTLMF.BTLMF_OBT_NU_COM_CONV_GUIA(?,?,?,?,?)");
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV_BTLMF.BTLMF_OBT_NU_COM_CONV_GUIA(?,?,?,?,?)",parametros);
    }
    
    
    
    public static void grabarCabRecetaNuevo(String vNumPed,
                                                 String vDni,
                                                 String vNombreMedico,
                                                 String vNombrePaciente)
      throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(vNumPed);
        parametros.add(vDni.trim());
        parametros.add(vNombreMedico.trim());
        parametros.add(vNombrePaciente.trim());
        log.debug("invocando  a PTOVENTA_VTA.VTA_GRABAR_PED_RECETA_CAB(?,?,?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null, 
                                               "PTOVENTA_VTA.VTA_GRABAR_PED_RECETA_CAB(?,?,?,?,?,?)", 
                                               parametros, false);
    }
    
    
    
    public static void cargaListaProductosVentaVerPrecioComp(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    log.debug("invocando a PTOVENTA_VER_PRECIO.VTA_LISTA_PROD(?,?)");
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VER_PRECIO.VTA_LISTA_PROD(?,?)",parametros,true);
    }
    
    
    public static void cargaListaProductosSustitutosVerComp(FarmaTableModel pTableModel, 
                                                       String pCodProd)
      throws SQLException
    {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodProd);
        log.debug("invocando a PTOVENTA_VER_PRECIO.VTA_LISTA_PROD_SUSTITUTOS(?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                               "PTOVENTA_VER_PRECIO.VTA_LISTA_PROD_SUSTITUTOS(?,?,?)", 
                                               parametros, true);
    }
    
    
    public static void cargaListaFiltroVerComp(FarmaTableModel pTableModel, 
                                        String pTipoFiltro,
                                        String pTipoProd) throws SQLException {
      parametros = new ArrayList();
      parametros.add(pTipoFiltro);
      parametros.add(pTipoProd);
        log.debug("invocando a PTOVENTA_VER_PRECIO.LISTA_FILTROS(?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VER_PRECIO.LISTA_FILTROS(?,?)",parametros,false);
    }
    
    public static Map obtieneMsgImp(String pTipComprobante) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipComprobante);
        parametros.add(VariablesCaja.vNumCompBoletaFactura_Impr);

        log.debug("invocando  a PTOVENTA_VTA.IMPR_MSG_GRAL(?,?,?,?) :" + 
                  parametros);
        return FarmaDBUtility.executeSQLStoredProcedureMap("PTOVENTA_VTA.IMPR_MSG_GRAL(?,?,?,?)", 
                                                           parametros);
    }
    
    
    public static String getDocParaRegularizar(String pTipoComp,
                                               String pMontoNeto,
                                               String pNumCompPago,
                                               String pFecha) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pTipoComp);
        parametros.add(pNumCompPago);
        parametros.add(pFecha);
        log.debug("parametros SVB_ATENCIONES.F_BUSQUEDA_COMP_REGULARIZA(?,?,?,?,?): "+parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("SVB_ATENCIONES.F_BUSQUEDA_COMP_REGULARIZA(?,?,?,?,?)",parametros);
    }
    
     
    public static void getDatoWord(ArrayList pArray ,String pNumPedidoVta) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);  
        log.info("parametros UTILITY_ENFERMERA.GET_DATO_WORD(?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"UTILITY_ENFERMERA.GET_DATO_WORD(?,?,?)",parametros);
    }  
    
    public static void getDatoGenerales(ArrayList pArray ,String pNumPedidoVta) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);  
        log.info("parametros PKG_VTA_ENFERMERA.F_GET_DATOS_GENERALES(?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PKG_VTA_ENFERMERA.F_GET_DATOS_GENERALES(?,?,?)",parametros);
    }   
    
    public static void getDatoCancer(ArrayList pArray ,String pNumPedidoVta) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);  
        log.info("parametros PKG_VTA_ENFERMERA.F_GET_DATOS_CANCER(?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PKG_VTA_ENFERMERA.F_GET_DATOS_CANCER(?,?,?)",parametros);
    } 
    
    public static void getDatoOperaciones(ArrayList pArray ,String pNumPedidoVta) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);  
        log.info("parametros PKG_VTA_ENFERMERA.F_GET_DATOS_OPERACIONES(?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PKG_VTA_ENFERMERA.F_GET_DATOS_OPERACIONES(?,?,?)",parametros);
    } 
    
    public static void getDatoExamenesEstudios(ArrayList pArray ,String pNumPedidoVta) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);  
        log.info("parametros PKG_VTA_ENFERMERA.F_GET_DATOS_EXAMENES(?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PKG_VTA_ENFERMERA.F_GET_DATOS_EXAMENES(?,?,?)",parametros);
    } 
    
    public static void getDatoResponsable(ArrayList pArray ,String pNumPedidoVta) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);  
        log.info("parametros PKG_VTA_ENFERMERA.F_GET_DATOS_EXAMENES(?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PKG_VTA_ENFERMERA.F_GET_DATOS_RESPONSABLE(?,?,?)",parametros);
    }    
    
    public static void getDatoInformeMedico(ArrayList pArray ,String pNumPedidoVta) throws SQLException{
        parametros=new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);  
        log.info("parametros PKG_VTA_ENFERMERA.F_GET_DATOS_INF_MEDICO(?,?,?): "+parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pArray,"PKG_VTA_ENFERMERA.F_GET_DATOS_INF_MEDICO(?,?,?)",parametros);
    }   
    
    public static String getCostoProducto(String vCodProd,String pValFrac) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(vCodProd);
      parametros.add(pValFrac);
        log.debug("invocando  a PTOVENTA_VTA_DOS.GET_COSTO_PRODUCTO(?,?,?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_VTA_DOS.GET_COSTO_PRODUCTO(?,?,?,?)",parametros);
    }

    public static String getNumComprobanteDefault(String pTipComp) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pTipComp);
        log.debug("invocando  a FARMA_UTILITY.GET_NUM_COMP_TIPO(?,?,?):"+parametros);
      return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.GET_NUM_COMP_TIPO(?,?,?)",parametros);
    }


    public static boolean isValidaAdmCambioPrecio() throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("invocando  a FARMA_UTILITY.IS_VALIDA_CAMBIO_PRECIO(?,?,?):"+parametros);
      if(FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.IS_VALIDA_CAMBIO_PRECIO(?,?,?)",parametros).trim().equalsIgnoreCase("S"))
          return true;
      else
          return false;
    }

    public static boolean isValidoVerPrecioMinimo() throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(FarmaVariables.vNuSecUsu);
        log.debug("invocando  a FARMA_UTILITY.IS_PERMITE_VER_PREC_MIN(?,?,?):"+parametros);
      if(FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.IS_PERMITE_VER_PREC_MIN(?,?,?)",parametros).trim().equalsIgnoreCase("S"))
          return true;
      else
          return false;
    }    
    

    public static void procesaDescuentoPedido(String pNumPedVta,String pDescuentoSoles) throws SQLException {
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
      parametros.add(pDescuentoSoles);
        log.info("invocando  a FARMA_UTILITY.P_OPERA_DESCUENTO(?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(null,"FARMA_UTILITY.P_OPERA_DESCUENTO(?,?,?,?)",parametros,false);
    }
    
    public static void cargaListaLaboratorio(FarmaTableModel pTableModel)
      throws SQLException
    {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      log.debug("invocando a HHC_VENTAS.F_CUR_LISTA_LAB(?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                               "HHC_VENTAS.F_CUR_LISTA_LAB(?,?)", 
                                               parametros, false);
    }

    public static void cargaListaEmpresaProductos(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    //Dflores: 27/08/19 * Falta revisar porque el vNuRucCia es 20555875828
    //parametros.add("20556100374");
    //parametros.add(FarmaVariables.vNuRucCia);
    log.debug("invocando a HHC_RECETA.F_LISTA_EMPRESA_PROD_RECETA2(?,?)");
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"HHC_RECETA.F_LISTA_EMPRESA_PROD_RECETA2(?,?)",parametros,true);
    }
    
    public static void cagaListaEmpresas(FarmaTableModel pTableModel)
      throws SQLException
    {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      log.debug("invocando a HHC_RECETA.F_LISTA_EMPRESA(?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                               "HHC_RECETA.F_LISTA_EMPRESA(?,?)", 
                                               parametros, false);
    }
    
    public static boolean isCodigoCampanaBD(String pValor) throws SQLException{
        String indVerCupones;
        parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pValor);
        log.debug("invocando a  PTOVENTA_PROMOCIONES.BUSQUEDA_COD_BARRA: "+parametros);
        indVerCupones = FarmaDBUtility.executeSQLStoredProcedureString("PTOVENTA_PROMOCIONES.BUSQUEDA_COD_BARRA(?,?,?)",parametros);
        if ( indVerCupones.equalsIgnoreCase("S") ) 
            return true;
        else return false;
    }   
    //Dflores: 16/08/2019
    public static void cargaListaEspecialidad(FarmaTableModel pTableModel,String pCodMed,String pFilVerTodos)
      throws SQLException
    {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodMed);
      parametros.add(pFilVerTodos);
      log.debug("invocando a HHC_LABORATORIO.F_CUR_LISTA_ESP(?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, 
                                               "HHC_LABORATORIO.F_CUR_LISTA_ESP(?,?,?,?)", 
                                               parametros, false);
    }

}
