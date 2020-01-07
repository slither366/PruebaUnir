package venta.convenio.reference;

import java.sql.SQLException;
import java.util.ArrayList;

import common.FarmaConstants;
import common.FarmaDBUtility;
import common.FarmaDBUtilityRemoto;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConvenio 
{
  private static final Logger log = LoggerFactory.getLogger(DBConvenio.class);
  private static ArrayList parametros = new ArrayList();

  public DBConvenio() {
	  
  }
  
  /**
   * @param pTableModel
   * @throws SQLException
   */
  public static void listaConvenios(FarmaTableModel pTableModel)throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);    
    parametros.add(FarmaVariables.vNuSecUsu); 
    log.debug("invocando  a PTOVENTA_CONV.CONV_LISTA_CONVENIOS(?,?,?) :"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CONV.CONV_LISTA_CONVENIOS(?,?,?)",parametros, false);    
    }

  /**
   * @param pTableModel
   * @throws SQLException
   */
  public static void listaCamposConvenio(FarmaTableModel pTableModel) throws SQLException {
    parametros = new ArrayList();
    parametros.add(VariablesConvenio.vCodConvenio);
    log.debug("invocando  a PTOVENTA_CONV.CONV_LISTA_CONVENIO_CAMPOS(?) :"+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CONV.CONV_LISTA_CONVENIO_CAMPOS(?)",parametros,false);
  }

  /**
   * @param pCodProd
   * @return
   * @throws SQLException
   */
  public static String obtieneIndPrecioControl(String pCodProd) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodProd);
    log.debug("invocando  a PTOVENTA_CONV.CON_OBTIENE_IND_PRECIO(?,?) :"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV.CON_OBTIENE_IND_PRECIO(?,?)",parametros);
  }
  
  /**
   * @param pCodConv
   * @param pCodProd
   * @param pValPrecVta
   * @return
   * @throws SQLException
   */
  public static String obtieneNvoPrecioConvenio(String pCodConv, 
                                                String pCodProd,
                                                String pValPrecVta) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodConv);
    parametros.add(pCodProd);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pValPrecVta)));
    log.debug("invocando a PTOVENTA_CONV.CON_OBTIENE_NVO_PRECIO(?,?,?,?,?) :"+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV.CON_OBTIENE_NVO_PRECIO(?,?,?,?,?)",parametros); //JCHAVEZ 29102009 obtiene precio de convenio
  }
  
  /**
   * Se obtiene el valor para determinar si dispone de credito.
   * @param pCodConv
   * @param pCodCli
   * @param pMontoPed
   * @return credito disponible
   * @throws SQLException
   */
  public static String validaCreditoCli(String pCodConv, 
                                        String pCodCli,
                                        String pMontoPed,
                                        String pIndCloseConecction) throws SQLException {
    parametros = new ArrayList();
    parametros.add(pCodConv);
    parametros.add(pCodCli);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMontoPed)));
    log.debug("invocando PTOVENTA_MATRIZ_CONV.CON_OBTIENE_DIF_CREDITO(?,?,?): "+parametros);
    return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_CONV.CON_OBTIENE_DIF_CREDITO(?,?,?)",
                                                             parametros,
                                                             FarmaConstants.CONECTION_MATRIZ,
                                                             pIndCloseConecction);
  }
  
  /**
   * @param pNumPedVta
   * @param pCodConvenio
   * @param pCodCli
   * @param pNumDocIden
   * @param pCodTrab
   * @param pApePatTit
   * @param pApeMatTit
   * @param pFecNacTit
   * @param pCodSolicitud
   * @param pValPorcDcto
   * @param pValPorcCoPago
   * @param pNumTelefCli
   * @param pDirecCli
   * @param pNomDistrito
   * @param pValCoPago
   * @param pCodInterno
   * @param pNomTrabajador
   * @param pCodCliDep
   * @param pCodTrabEmpDep
   * @throws SQLException
   */
  public static void grabarPedidoConvenio(String pNumPedVta,
                                          String pCodConvenio,
                                          String pCodCli,
                                          String pNumDocIden,//Cliente
                                          String pCodTrab,
                                          String pApePatTit,
                                          String pApeMatTit,
                                          String pFecNacTit,
                                          String pCodSolicitud,
                                          String pValPorcDcto,
                                          String pValPorcCoPago,
                                          String pNumTelefCli,
                                          String pDirecCli,
                                          String pNomDistrito,
                                          String pValCoPago,
                                          String pCodInterno,
                                          String pNomTrabajador,
                                          String pCodCliDep,
                                          String pCodTrabEmpDep) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta);
    parametros.add(pCodConvenio);
    parametros.add(pCodCli);
    parametros.add(FarmaVariables.vIdUsu);
    parametros.add(pNumDocIden);
    parametros.add(pCodTrab);
    parametros.add(pApePatTit);
    parametros.add(pApeMatTit);
    parametros.add(pFecNacTit);
    parametros.add(pCodSolicitud);
    parametros.add(pValPorcDcto);
    parametros.add(pValPorcCoPago);
    parametros.add(pNumTelefCli);
    parametros.add(pDirecCli);
    parametros.add(pNomDistrito);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pValCoPago.trim())));
    parametros.add(pCodInterno);
    parametros.add(pNomTrabajador);
    parametros.add(pCodCliDep.trim());
    parametros.add(pCodTrabEmpDep.trim());    
    log.debug("invocando PTOVENTA_CONV.CON_AGREGA_PEDIDO_CONVENIO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?): "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CONV.CON_AGREGA_PEDIDO_CONVENIO(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros,false);
  }
  
   /**
    * metodo encargado de actualizar el saldo del cliente en matriz
    * @param pCodConvenio
    * @param pCodCliente
    * @param pMonto
    * @param pIndCloseConecction
    * @throws SQLException
    */
 /* public static void actualizaConsumoClienteEnMatriz(String pCodConvenio,
                                             		String pCodCliente,
                                             		String pMonto, 
                                             		String pIndCloseConecction) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(pCodConvenio);
    parametros.add(pCodCliente);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonto)));
    log.debug("invocando PTOVENTA_MATRIZ_CONV.CON_ACTUALIZA_CONSUMO_CLI(?,?,?): "+parametros);
    FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,"PTOVENTA_MATRIZ_CONV.CON_ACTUALIZA_CONSUMO_CLI(?,?,?)",
    													parametros,false,
    													FarmaConstants.CONECTION_MATRIZ,
    													pIndCloseConecction);
  }
  */
  //JMIRANDA 25/08/2009
  public static void actualizaConsumoClienteEnMatriz(String pCodConvenio,
                                                          String pCodCliente,
                                                          String pMonto, 
                                                          String pIndCloseConecction,
                                                     String pNumPedVta,
                                                     String pCajero) throws SQLException
    {
      parametros = new ArrayList();
      parametros.add(pCodConvenio);
      parametros.add(pCodCliente);
      parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonto)));
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pNumPedVta);
      parametros.add(pCajero);      
      
      log.debug("invocando PTOVENTA_MATRIZ_CONV.CON_ACTUALIZA_CONSUMO_CLI(?,?,?,?,?,?): "+parametros);
      FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,"PTOVENTA_MATRIZ_CONV.CON_ACTUALIZA_CONSUMO_CLI(?,?,?,?,?,?)",
                                                                                                          parametros,false,
                                                                                                          FarmaConstants.CONECTION_MATRIZ,
                                                                                                          pIndCloseConecction);
    }
  /**
   * @param pCodConvenio
   * @param pCodCliente
   * @param pMonto
   * @return
   * @throws SQLException
   */
  public static String obtieneCoPagoConvenio(String pCodConvenio,
                                             String pCodCliente,
                                             String pMonto) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(pCodConvenio);
    parametros.add(pCodCliente);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonto)));
    log.debug("invocando PTOVENTA_CONV.CON_OBTIENE_COPAGO(?,?,?): "+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV.CON_OBTIENE_COPAGO(?,?,?)",parametros);
  }

  /**
   * Se obtiene el credito asignado al cliente.
   * @param pCodConvenio
   * @param pCodCliente
   * @return credito asignado
   * @throws SQLException
   */
  public static String obtieneCredito(String pCodConvenio,
                                             String pCodCliente,
                                      String pIndCloseConecction) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(pCodConvenio);
    parametros.add(pCodCliente);
    log.debug("invocando PTOVENTA_MATRIZ_CONV.CON_OBTIENE_CREDITO(?,?): "+parametros);
    return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_CONV.CON_OBTIENE_CREDITO(?,?)",
                                                             parametros,
                                                             FarmaConstants.CONECTION_MATRIZ,
                                                             pIndCloseConecction);
  }

  /**
   * Se obtiene el credito utilizado por el cliente.
   * @param pCodConvenio
   * @param pCodCliente
   * @return credito utilizado
   * @throws SQLException
   */
  public static String obtieneCreditoUtil(String pCodConvenio,
                                             String pCodCliente,
                                          String pIndCloseConecction) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(pCodConvenio);
    parametros.add(pCodCliente);
    log.debug("invocando a PTOVENTA_MATRIZ_CONV.CON_OBTIENE_CREDITO_UTIL(?,?): "+parametros);
    return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_CONV.CON_OBTIENE_CREDITO_UTIL(?,?)",
                                                             parametros, 
                                                             FarmaConstants.CONECTION_MATRIZ,
                                                             pIndCloseConecction);
  }  
  
  
  /**
   * @param pCodConvenio
   * @return
   * @throws SQLException
   */
  public static String obtieneFormaPagoXConvenio(String pCodConvenio) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(pCodConvenio);
    log.debug("invocando a PTOVENTA_CONV.CON_OBTIENE_FORMA_PAGO_CONV(?,?): "+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV.CON_OBTIENE_FORMA_PAGO_CONV(?,?)",parametros);
  }
  
  /**
   * @param pCodFormaPago
   * @param pNumPedVta
   * @param pImPago
   * @param pTipMoneda
   * @param pTipoCambio
   * @param pVuelto
   * @param pImTotalPago
   * @param pNumTarj
   * @param pFecVencTarj
   * @param pNomCliTarj
   * @param pCantCupon
   * @throws SQLException
   */
  public static void grabaFormaPagoPedido(String pCodFormaPago,
                                          String pNumPedVta,
                                          String pImPago,
                                          String pTipMoneda,
                                          String pTipoCambio,
                                          String pVuelto,
                                          String pImTotalPago,
                                          String pNumTarj,
                                          String pFecVencTarj,
                                          String pNomCliTarj,
                                          String pCantCupon) throws SQLException{
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodFormaPago);
    parametros.add(pNumPedVta);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pImPago)));
    parametros.add(pTipMoneda);
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pTipoCambio)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pVuelto)));
    parametros.add(new Double(FarmaUtility.getDecimalNumber(pImTotalPago)));
    parametros.add(pNumTarj);
    parametros.add(pFecVencTarj);
    parametros.add(pNomCliTarj);
    parametros.add(new Integer(pCantCupon));
    parametros.add(FarmaVariables.vIdUsu);
    log.debug("invocando a PTOVENTA_CONV.CON_GRABAR_FP_PED_CONV_LOCAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?): "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CONV.CON_GRABAR_FP_PED_CONV_LOCAL(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros,false);
  }
  
  /**
   * @param aInfoPedConv
   * @param pNumPedVta
   * @param pMontoPedido
   * @throws SQLException
   */
  public static void obtieneInfoPedidoConv(ArrayList aInfoPedConv,
                                           String    pNumPedVta,
                                           String    pMontoPedido) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta);
    parametros.add(new Double(pMontoPedido));
    log.debug("invocando a PTOVENTA_CONV.TMP_CON_OBTIENE_INFO_CONV_PED(?,?,?,?): "+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(aInfoPedConv,"PTOVENTA_CONV.TMP_CON_OBTIENE_INFO_CONV_PED(?,?,?,?)",parametros);
    
  }
  
  /**
   * @param pNumPedVta
   * @param pNumPedVtaDel
   * @throws SQLException
   */
  public static void actualizaNumPedido(String pNumPedVta,
                                        String pNumPedVtaDel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta);
    parametros.add(pNumPedVtaDel);
    log.debug("invocando a PTOVENTA_CONV.CON_ACTUALIZA_NUM_PED(?,?,?,?): "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CONV.CON_ACTUALIZA_NUM_PED(?,?,?,?)",parametros,false);
    
  }
  
  /**
   * Lista los clientes por convenio.
   * @param pTableModel
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 21.05.2007
   */
  public static void cargaListaClienteConv(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(VariablesConvenio.vCodConvenio);
    log.debug("invocando a PTOVENTA_CONV.CONV_LISTA_CLI_CONVENIO(?): "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CONV.CONV_LISTA_CLI_CONVENIO(?)",parametros,false);
  }
 
  /**
   * Se actualiza el credito disponible.
   * @param pCodConvenio
   * @param pCodCliente
   * @param pNumPedVta
   * @param pValCredDisp
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 06.03.2008
   */
  public static void actualizarCreditoDisp(String pCodConvenio,
                                             String pCodCliente,String pNumPedVta, double pValCredDisp) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(pCodConvenio);    
    parametros.add(pCodCliente);    
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pNumPedVta);    
    parametros.add(new Double(pValCredDisp));
    log.debug("invocando a PTOVENTA_CONV.CONV_ACTUALIZA_CRED_DISP(?,?,?,?,?,?): "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_CONV.CONV_ACTUALIZA_CRED_DISP(?,?,?,?,?,?)",parametros,false);
  }
  
  /**
   * Lista los clientes dependientes
   * @param  pTableModel
   * @throws SQLException
   * @author Diego Ubilluz Carrillo
   * @since  31.01.2008
   */
  public static void cargaListaDepClienteConv(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(VariablesConvenio.vCodConvenio);    
    parametros.add(VariablesConvenio.vCodClienteBusqueda);
    log.debug("invocando a PTOVENTA_CONV.CONV_LISTA_CLI_DEP_CONVENIO(?,?): "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CONV.CONV_LISTA_CLI_DEP_CONVENIO(?,?)",parametros,false);
  }
  
  /**
   * Obtiene el nombre del cliente.
   * @param pNumDoc
   * @return
   * @throws SQLException
   * @author Edgar Rios Navarro
   * @since 13.06.2008
   */
  public static ArrayList obtenerNombreClienteNumdoc(String pNumDoc) throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(VariablesConvenio.vCodConvenio);
    parametros.add(pNumDoc);
    log.debug("invocando a PTOVENTA_CONV.CONV_LISTA_CLI_DEP_CONVENIO(?,?): "+parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams, "PTOVENTA_CONV.GET_NOMBRE_CLIENTE_NUMDOC(?,?)",parametros);
    return pOutParams;
  }
  
  /**
   * Obtiene valor si el cliente posee  credito 
   * @author Ang�lica Sol�s
   * @since 15.12.2008
  */
  public static String obtieneConvenioCredito(String pCodConvenio,
                                              String pCodCliente,
                                              String pIndCloseConecction) throws SQLException {
	  parametros = new ArrayList();
      parametros.add(pCodConvenio);
      parametros.add(pCodCliente);
      log.debug("invocando a PTOVENTA_MATRIZ_CONV.CON_BUSCA_CLI_CONV_SALD_DISP(?,?): "+parametros);
      return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_CONV.CON_BUSCA_CLI_CONV_SALD_DISP(?,?)",
                                                                 parametros,
                                                                 FarmaConstants.CONECTION_MATRIZ,
                                                                 pIndCloseConecction);
  }
  
  /**
   * Obtiene el valor del porcentaje de credito.
   * @author Ang�lica Sol�s
   * @since 15.12.2008
   */
  public static String obtenerPorcentajeCopago(String pCodConvenio) throws SQLException {
	  parametros = new ArrayList();
	  parametros.add(pCodConvenio);
	  log.debug("invocando a PTOVENTA_CONV.CON_PORC_COPAGO_CONV(?): "+parametros);
	  return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV.CON_PORC_COPAGO_CONV(?)",parametros);
  }
  
  
  /**
   * Obtiene indicador si el cliente y el convenio est�n activos 
   * @since 22.06.2010
   * @author JMIRANDA
   * Devuelve E: Error, S: Activo, N: inactivo
   */
  public static String getIndClienteConvenioActivo(String pCodConvenio,
                                              String pDniCliente,
                                              String pIndCloseConection,
                                                   String pCodCli) throws SQLException {
      parametros = new ArrayList(); 
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodConvenio);
      parametros.add(pDniCliente);
      parametros.add(pCodCli);
      log.debug("invocando a PTOVENTA_MATRIZ_CONV_NUEVO.CON_F_GET_IND_CLI_ACTIVO(?,?,?,?,?): "+parametros);
      return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_CONV_NUEVO.CON_F_GET_IND_CLI_ACTIVO(?,?,?,?,?)",
                                                                 parametros,
                                                                 FarmaConstants.CONECTION_MATRIZ,
                                                                 pIndCloseConection);
  }
    
    /**
     * Obtiene indicador si el monto de la compra permite utilizar el convenio
     * @since 22.06.2010
     * @author JMIRANDA
     * Devuelve E: Error, S: Activo, N: inactivo
     */
    public static String getIndValidaMontoConvenio(String pCodConvenio,
                                                String pDniCliente,
                                                double pMonto,
                                                String pIndCloseConection,
                                                   String pCodCli) throws SQLException {
        parametros = new ArrayList(); 
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodConvenio);        
        parametros.add(pDniCliente);
        parametros.add(""+(pMonto));
        parametros.add(pCodCli.trim());
        log.debug("invocando a PTOVENTA_MATRIZ_CONV_NUEVO.CON_F_VALIDA_MONTO(?,?,?,?,?,?): "+parametros);
        return FarmaDBUtilityRemoto.executeSQLStoredProcedureStr("PTOVENTA_MATRIZ_CONV_NUEVO.CON_F_VALIDA_MONTO(?,?,?,?,?,?)",
                                                                   parametros,
                                                                   FarmaConstants.CONECTION_MATRIZ,
                                                                   pIndCloseConection);
    }   
    
    
    public static void actualizaConsumoClienteEnMatriz_v2(String pCodConvenio,
                                                            String pCodCliente,
                                                            String pMonto, 
                                                            String pIndCloseConecction,
                                                       String pNumPedVta,
                                                       String pCajero,
                                                          String pDniCliente) throws SQLException
      {
        parametros = new ArrayList();
          parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodConvenio);
        parametros.add(pCodCliente);
        parametros.add(new Double(FarmaUtility.getDecimalNumber(pMonto)));        
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedVta);
        parametros.add(pCajero); 
        parametros.add(pDniCliente);
        
        log.debug("invocando PTOVENTA_MATRIZ_CONV_NUEVO.CON_ACTUALIZA_CONSUMO_CLI(?,?,?,?,?,?,?,?): "+parametros);
        FarmaDBUtilityRemoto.executeSQLStoredProcedure(null,"PTOVENTA_MATRIZ_CONV_NUEVO.CON_ACTUALIZA_CONSUMO_CLI(?,?,?,?,?,?,?,?)",
                                                                                                            parametros,false,
                                                                                                            FarmaConstants.CONECTION_MATRIZ,
                                                                                                            pIndCloseConecction);
      }    
    /**
     * Obtiene el el porcentaje de credito segun convenio.
     * @author Luigy Terrazos
     * @since 26.03.2013
     */
    public static String obtenerPorcentajeConPago(String pCodConvenio) throws SQLException {
            parametros = new ArrayList();
            parametros.add(pCodConvenio);
            log.debug("invocando a PTOVENTA_CONV.CONSUL_PORC_FORM_PAG(?): "+parametros);
            return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CONV.CONSUL_PORC_FORM_PAG(?)",parametros);
    }
    
}