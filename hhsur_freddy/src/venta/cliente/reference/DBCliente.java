package venta.cliente.reference;

import java.sql.*;
import java.util.*;

import common.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2006 MiFarma S.A.C.<br>
 * <br>
 * Entorno de Desarrollo   : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicación : DBCliente.java<br>
 * <br>
 * Histórico de Creación/Modificación<br>
 * LMESIA         23/02/2006   Creación<br>
 * <br>
 * @author Luis Mesia Rivera<br>
 * @version 1.0<br>
 *
 */

public class DBCliente
{
    private static final Logger log = LoggerFactory.getLogger(DBCliente.class);
    
  private static ArrayList parametros = new ArrayList();

  public DBCliente(){
  }

  public static void cargaListaClienteJuridico(FarmaTableModel pTableModel,
                                               String pBusqueda,
                                               String pTipoBusqueda) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    //parametros.add(pBusqueda);
    //parametros.add(pTipoBusqueda);
    log.info("PTOVENTA_CLI.CLI_BUSCA_CLI_JURIDICO(?,?)",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CLI.CLI_BUSCA_CLI_JURIDICO(?,?)",parametros,false);
  }
  
  public static String agragaClienteJuridico(String pRazonSocial,
                                             String pTipoDocIdent,
                                             String pRuc,
                                             String pDirCliente) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaConstants.COD_NUMERA_CLIENTE_LOCAL);
    parametros.add(pRazonSocial);
    parametros.add(pTipoDocIdent);
    parametros.add(pRuc);
    parametros.add(pDirCliente);
    parametros.add(FarmaVariables.vIdUsu);
    log.debug("",parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_AGREGA_CLI_LOCAL(?,?,?,?,?,?,?,?)",parametros);
  }
  public static String agragaClienteNatural(String pNombre,
                                            String pAPellidoPat,
                                            String pApellidoMat,
                                            String pTipoDocIdent,
                                            String pDni,
                                            String pDirCliente,
                                            String vRazonSocial,
                                            String vTelefono,
                                            String vCorreo) throws SQLException {
      
      
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(FarmaConstants.COD_NUMERA_CLIENTE_LOCAL);
    
      if(pNombre.trim().length()==0)
          parametros.add(".");
      else
    parametros.add(pNombre);
      
      if(pAPellidoPat.trim().length()==0)
          parametros.add(".");
      else
    parametros.add(pAPellidoPat);
      
      if(pApellidoMat.trim().length()==0)
          parametros.add(".");
      else
    parametros.add(pApellidoMat);
      
    parametros.add(pTipoDocIdent);
    parametros.add(pDni);
    
      if(pDirCliente.trim().length()==0)
          parametros.add(".");
      else
    parametros.add(pDirCliente);
    parametros.add(FarmaVariables.vIdUsu);
     
     
     if(vRazonSocial.trim().length()==0)
         parametros.add(".");
     else
      parametros.add(vRazonSocial);
     
      if(vTelefono.trim().length()==0)
          parametros.add(".");
      else
      parametros.add(vTelefono);
      
      if(vCorreo.trim().length()==0)
          parametros.add(".");
      else
      parametros.add(vCorreo);
    log.info("PTOVENTA_CLI.CLI_AGREGA_CLI_NATURAL "+parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_AGREGA_CLI_NATURAL(?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros);
  }
  
  public static String verificaRucValido(String pRUC) throws SQLException {
    parametros = new ArrayList();
    parametros.add(pRUC);
    return FarmaDBUtility.executeSQLStoredProcedureStr("FARMA_UTILITY.VERIFICA_RUC_VALIDO(?)",parametros);
  }
  
  public static String actualizaClienteJuridico(String pCodCliente,
                                                String pRazonSocial,
                                                String pRuc,
                                                String pDirCliente) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodCliente);
    parametros.add(pRazonSocial);
    parametros.add(pRuc);
    parametros.add(pDirCliente);
    parametros.add(FarmaVariables.vIdUsu);
    log.debug("",parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_ACTUALIZA_CLI_LOCAL(?,?,?,?,?,?,?)",parametros);
  }
  public static void obtieneInfo_Cli_Natural(ArrayList pArrayList) throws SQLException {
    parametros = new ArrayList();
    //parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesCliente.vCodigo);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pArrayList,"PTOVENTA_CLI.CLI_OBTIENE_INFO_CLI_NATURAL(?,?,?)",parametros);
  }
  
  public static void obtieneTarjetas_Cliente(FarmaTableModel pTableModel,
                                             String pCodigo) throws SQLException {
                                               
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodigo);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel," PTOVENTA_CLI.CLI_OBTIENE_TARJETAS_CLIENTE(?,?,?)",parametros,false);
  }
       
   public static String actualizaClienteNatural(String pCodCliente,
                                                String pNombreCliente,
                                                String pApellidoPat,
                                                String pApellidoMat,
                                                String pDni,
                                                String pDirCliente,
                                            String pTipoDocIdent,
                                            String vRazonSocial,
                                            String vTelefono,
                                            String vCorreo) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodCliente);
    
      if(pNombreCliente.trim().length()==0)
          parametros.add(".");
      else
      parametros.add(pNombreCliente);
      
      if(pApellidoPat.trim().length()==0)
          parametros.add(".");
      else
      parametros.add(pApellidoPat);
      
      if(pApellidoMat.trim().length()==0)
          parametros.add(".");
      else
      parametros.add(pApellidoMat);
      
      
      parametros.add(pDni);
      
      if(pDirCliente.trim().length()==0)
          parametros.add(".");
      else
      parametros.add(pDirCliente);
      parametros.add(FarmaVariables.vIdUsu);
      
      parametros.add(pTipoDocIdent);
      
      if(vRazonSocial.trim().length()==0)
         parametros.add(".");
      else
      parametros.add(vRazonSocial);
      
      if(vTelefono.trim().length()==0)
          parametros.add(".");
      else
      parametros.add(vTelefono);
      
      if(vCorreo.trim().length()==0)
          parametros.add(".");
      else
      parametros.add(vCorreo);
    
    
    
    
    log.debug("",parametros);
    log.debug("realizo la actualizacion");
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_ACTUALIZA_CLI_NATURAL(?,?,?,?,?,?,?,?,?,?,?,?,?)",parametros);
  }
  
  public static String agragaTarjetaCliente(String pCodOperadorN,
                                            String pNumeroTarjeta,
                                            String pFechaVencimiento,
                                            String pPropietario
                                           ) throws SQLException {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesCliente.vCodigo);
    parametros.add(pCodOperadorN);
    parametros.add(pFechaVencimiento);
    parametros.add(pNumeroTarjeta);
    parametros.add(pPropietario);
    parametros.add(FarmaVariables.vIdUsu);
    log.debug("",parametros);
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_AGREGA_TARJETAS_CLI(?,?,?,?,?,?,?,?)",parametros);
  }
  
   public static String actualizaTarjetaCliente(String pCodCliente,
                                                String pCodOperadorN,
                                                String pCodOperadorA,
                                                String pFecVencimiento,
                                                String pNumeroTarjeta,
                                                String pNombreTarjeta) throws SQLException {
    parametros = new ArrayList();
    parametros.add(pCodCliente);
    parametros.add(pCodOperadorN);
    parametros.add(pCodOperadorA);
    parametros.add(pFecVencimiento);
    parametros.add(pNumeroTarjeta);
    parametros.add(pNombreTarjeta);
    parametros.add(FarmaVariables.vIdUsu);
    log.debug("",parametros);
    log.debug("realizo la actualizacion");
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_CLI.CLI_ACTUALIZA_TARJETA(?,?,?,?,?,?,?)",parametros);
  }
  
  

    public static void cargaListaCliente_Inicial(FarmaTableModel pTableModel,
                                                 String pBusqueda,
                                                 String pTipoBusqueda) throws SQLException {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      //parametros.add(pBusqueda);
      //parametros.add(pTipoBusqueda);
      log.info("PTOVENTA_CLI.CLI_BUSCA_CLI_INICIAL(?,?)",parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CLI.CLI_BUSCA_CLI_INICIAL(?,?)",parametros,false);
    }
  
    public static void cargaListaCliente_documento(FarmaTableModel pTableModel,
                                                 String pBusqueda) throws SQLException {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pBusqueda);
      //parametros.add(pTipoBusqueda);
      log.info("PTOVENTA_CLI.CLI_BUSCA_CLI_X_DOC(?,?,?)",parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CLI.CLI_BUSCA_CLI_X_DOC(?,?,?)",parametros,false);
    }  

    public static void cargaListaCliente_palabra(FarmaTableModel pTableModel,
                                                 String pBusqueda) throws SQLException {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pBusqueda);
      //parametros.add(pTipoBusqueda);
      log.info("PTOVENTA_CLI.CLI_BUSCA_CLI_X_PALABRA(?,?,?)",parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_CLI.CLI_BUSCA_CLI_X_PALABRA(?,?,?)",parametros,false);
    }      
  
}