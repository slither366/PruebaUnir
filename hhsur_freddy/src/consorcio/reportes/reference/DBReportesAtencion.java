package consorcio.reportes.reference;

import java.sql.*;
import java.util.*;
import common.*;
import componentes.gs.encripta.FarmaEncripta;
import con_general.FarmaDBUtilityGeneral;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import venta.reference.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import venta.reportes.reference.VariablesReporte;
import consorcio.HSurVariablesBD;

import venta.EconoFar;

public class DBReportesAtencion 
{
    private static final Logger log = LoggerFactory.getLogger(DBReportesAtencion.class);
    
    private static ArrayList parametros = new ArrayList();
    
    public String prop2 = "";
    
  public DBReportesAtencion()
  {
  }
  
   /* 
  public static void main(String[] args) {
      FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(
                                            "appsfidelizacion",
                                            "jlp_000_1x3",
                                            "10.86.1.29",
                                            //"127.0.0.1",
                                            "1521","edbst008"
                                            );

        String executeSQL;

        try {
            executeSQL = dbGeneral.executeSQL("Select 1+1 from dual","S");
            System.out.println("executeSQL "+executeSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 */
   public static void cargaListaRegistroVentas(FarmaTableModel pTableModel,
                                               String pFechaInicio,
                                               String pFechaFin,
                                               String pCodCajero,
                                               String pIdConsultorio,
                                               String pIdBus) throws SQLException {
    //pTableModel.clearTable();
       log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
      log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
      log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
      log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
      log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);
    
    FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                HSurVariablesBD.vClaveBD,
                                                                HSurVariablesBD.vIpBD,
                                                                HSurVariablesBD.vPuertoBD,
                                                                HSurVariablesBD.vSID*/
                                          //"reportes","reportes",
                                          //"192.168.10.8",
                                          FarmaVariables.vUsuarioBD,
                                          FarmaVariables.vClaveBD,
                                          FarmaVariables.vIPBD,
                                          //"127.0.0.1",
                                          FarmaVariables.vPUERTO,FarmaVariables.vSID
                                          );
    
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    //parametros.add(pCodCajero.trim());
    parametros.add(FarmaVariables.vNuSecUsu);
    parametros.add(pIdConsultorio.trim());
    parametros.add(pIdBus.trim());
    log.info("",parametros);
      
    dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.REPORTE_REGISTRO_VENTA(?,?,?,?,?,?,?)",parametros,false,"S");
  }
   
    public static void cargaListaAgrupaAtencion(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     //"reportes","reportes",
     //"192.168.10.8",
     
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
     parametros.add(pIdConsultorio.trim());
     parametros.add(pIdBus.trim());
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_AGRUPA_ATENCION(?,?,?,?,?,?,?)",parametros,false,"S");
    }
   /*
    public static void cargaListaRegistroVentas(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pDocCliente,
                                                String pCodProd) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pCodCajero.trim());
     parametros.add(pDocCliente.trim());
     parametros.add(pCodProd.trim());
     log.debug("",parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_REGISTRO_VENTA_x_CLI(?,?,?,?,?,?,?)",parametros,false);
    }*/
  public static void obtieneDetalleRegistroVentas(FarmaTableModel pTableModel,
                                          String pCodigo) throws SQLException {
                                               
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodigo);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_REGISTRO_VENTA(?,?,?)",parametros,false);
  }
  
  public static void obtieneComprobantes_Venta(FarmaTableModel pTableModel,
                                               String pCodigo) throws SQLException {
                                               
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodigo);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_COMPROBANTES_VENTA(?,?,?)",parametros,false);
  }
  
  public static void obtieneComprobantes_Venta_Detalle(FarmaTableModel pTableModel,
                                               String pCodigo,
                                               String pNumPedVtaRep) throws SQLException {
                                               
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodigo);
    parametros.add(pNumPedVtaRep);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_COMPROBANTE(?,?,?,?)",parametros,false);
  }
   public static ArrayList obtieneInfoResumen(String pFechaIni,String pFechaFin) throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia.trim());
    parametros.add(FarmaVariables.vCodLocal.trim());
    parametros.add(pFechaIni);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_REPORTE.REPORTE_RESUMEN_VENTA(?,?,?,?)",parametros);
    return pOutParams;
  }
  
  public static ArrayList obtieneInfoResumenNotaCredito(String pFechaIni,String pFechaFin) throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia.trim());
    parametros.add(FarmaVariables.vCodLocal.trim());
    parametros.add(pFechaIni);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_REPORTE.REPORTE_RESUMEN_VTA_NOT_CREDIT(?,?,?,?)",parametros);
    return pOutParams;
  }
  
  public static void cargaListaFormadePago (FarmaTableModel pTableModel,
                                            String pFechaInicio,
                                            String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_FORMAS_DE_PAGO(?,?,?,?)",parametros,false);
  }
  
   public static void cargaListaDetalledeVentas(FarmaTableModel pTableModel,
                                               String pFechaInicio,
                                               String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_VENTAS(?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaResumenProductosVendidos(FarmaTableModel pTableModel,
                                                        String pFechaInicio,
                                                        String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_RESUMEN_PRODUCTOS_VEND(?,?,?,?)",parametros,false);
  }
  
    public static void cargaListaFiltroDetalleVentas(FarmaTableModel pTableModel,
                                                   String pFechaInicio,
                                                   String pFechaFin,
                                                   String pFiltro) throws SQLException
    {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaInicio);
        parametros.add(pFechaFin);
        parametros.add(pFiltro);
        log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_FILTRO_PRODUCTOS_VEND(?,?,?,?,?)",parametros,false);
    }
  
   public static void cargaListaVentasporVendedor(FarmaTableModel pTableModel,
                                                  String pFechaInicio,
                                                  String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    //log.debug("PTOVENTA_REPORTE.REPORTE_VENTAS_POR_VENDEDOR(?,?,?,?):"+parametros);
    //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_VENDEDOR(?,?,?,?)",parametros,false);
    
      log.debug("SVB_RPT_NVO.RPT_NVO_01(?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"SVB_RPT_NVO.RPT_NVO_01(?,?,?,?)",parametros,false);

    
  }
   
    
    public static void cargaListaFormaPago(FarmaTableModel pTableModel,
                                                   String pFechaInicio,
                                                   String pFechaFin) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //log.debug("PTOVENTA_REPORTE.REPORTE_VENTAS_POR_VENDEDOR(?,?,?,?):"+parametros);
     //FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_VENDEDOR(?,?,?,?)",parametros,false);
     
       log.debug("SVB_RPT_NVO.RPT_FORMA_PAGO(?,?,?,?):"+parametros);
       FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"SVB_RPT_NVO.RPT_FORMA_PAGO(?,?,?,?)",parametros,false);

     
    }
  
  public static void cargaListaDetalleVentasporVendedor(FarmaTableModel pTableModel,
                                                        String pFechaInicio,
                                                        String pFechaFin,
                                                        String pUsuario) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pUsuario);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_VENTAS_VEND(?,?,?,?,?)",parametros,false);
  }
  
    public static void cargaListaVentasporProducto(FarmaTableModel pTableModel,
                                                  String pFechaInicio,
                                                  String pFechaFin,
                                                   String pCodLab,
                                                   String pTipoFecha) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
      parametros.add(pCodLab);
      parametros.add(pTipoFecha);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_PRODUCTO(?,?,?,?,?,?)",parametros,false);
  }
  
   public static void cargaListaVentasporProductoFiltro(FarmaTableModel pTableModel,
                                                  String pFechaInicio,
                                                  String pFechaFin,
                                                  String pTipoFiltro,
                                                  String pCodFiltro) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pTipoFiltro);
    parametros.add(pCodFiltro);
    
    log.debug("",parametros);
    
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_PRODUCTO_F(?,?,?,?,?,?)",parametros,false);
  }

   public static void cargaListaVentasPorDia(FarmaTableModel pTableModel,
                                             String pFechaInicio,
                                             String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VETAS_POR_DIA(?,?,?,?)",parametros,false);
  }

    public static void cargaListaResumenVentasDetallado(FarmaTableModel pTableModel,
                                                        String pFechaInicio,
                                                        String pFechaFin,
                                                        String pCodProd) throws SQLException
    {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaInicio);
        parametros.add(pFechaFin);
        parametros.add(pCodProd);
        log.debug("",parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLADO_RESUMEN_VTA(?,?,?,?,?)",parametros,false);
    }

  //Histórico de Creación/Modificación
  //ERIOS      27.03.2005   Creación
  //DlgVentasPorHora 
  public static void cargaListaVentasporHora(FarmaTableModel pTableModel,String pFechaInicio,String pFechaFin, String filtroDia) throws SQLException 
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(filtroDia);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_VENTAS_POR_HORA(?,?,?,?,?)",parametros,false);      
  }

 public static void cargaListaVentasDiaMes(FarmaTableModel pTableModel) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesReporte.vFechaInicio );
    parametros.add(VariablesReporte.vFechaFin);
    parametros.add(VariablesReporte.vCodFiltro);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_MHR.REPORTE_VENTAS_DIA_MES(?,?,?,?,?)",parametros,false);
  }  
  
  //Histórico de Creación/Modificación
  //ERIOS      06.07.2006   Creación
  //DlgProductoFaltaCero
  public static void cargaListaFaltaCero(FarmaTableModel pTableModel,String pFechaInicio,String pFechaFin, String filtroDia) throws SQLException 
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(filtroDia);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_PRODUCTOS_FALTA_CERO(?,?,?,?,?)",parametros,false);      
  }
  
  public static void cargaListaDetFaltaCero(FarmaTableModel pTableModel,String codProd,String pFechaInicio,String pFechaFin) throws SQLException 
  {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(codProd);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_DET_FALTA_CERO(?,?,?,?,?)",parametros,false);      
  }
  
   public static void listaVentasporProductoLab(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VTA_PRODUCTO_LAB(?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaProductosABC(FarmaTableModel pTableModel,
                                            String filtro,
                                            String ind,
                                            String fechaIni,
                                            String fechaFin) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(filtro);
    parametros.add(ind);
    parametros.add(fechaIni);
    parametros.add(fechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_PRODUCTO_ABC(?,?,?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaProductosABCFiltro(FarmaTableModel pTableModel,
                                                  String filtro,
                                                  String ind, 
                                                  String filtroTipo,
                                                  String fechaIni,
                                                  String fechaFin) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(filtro);
    parametros.add(ind);
    parametros.add(filtroTipo);
    parametros.add(fechaIni);
    parametros.add(fechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"TMP_REP_ERN.REP_FILTRO_PRODUCTO_ABC(?,?,?,?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaConsolidadoVtasProd(FarmaTableModel pTableModel,
                                                   String fechaIni,
                                                   String fechaFin) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(fechaIni);
    parametros.add(fechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_CONSOLIDADO_VTA_PROD(?,?,?,?)",parametros,false);
  }
                                                  
  public static void cargaListaVentasporProductoVirtuales(FarmaTableModel pTableModel,
                                                          String pFechaInicio,
                                                          String pFechaFin) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VTA_PRODUCTO_VIRTUAL(?,?,?,?)",parametros,false);
  }  
                                                  
  public static void borrarRegistroDetFaltaCero(String pCodProd,
                                                String pSecUsuLocal,
                                                String pFecha) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pCodProd);
    parametros.add(pFecha);
    parametros.add(pSecUsuLocal);
    log.debug("borrarRegistroDetFaltaCero "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_REPORTE.REPORTE_BORRAR_DET_FALTA_CERO(?,?,?,?,?)",parametros,false);
  }
  
  public static void cargaListaPedidosAnulNoCob(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    log.debug("cargaListaPedidosAnulNoCob "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_PEDIDOS_ANUL_NO_COB(?,?,?,?)",parametros,false);
  }

  public static void cargaListaUnidadVtaLocal(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    log.debug("cargaListaUnidadVtaLocal "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_UNID_VTA_LOCAL",parametros,false);
  }
  
  public static void cargaListaUnidadVtaLocalXFiltro(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(VariablesPtoVenta.vTipoFiltro);
    parametros.add(VariablesPtoVenta.vCodFiltro);
    log.debug("cargaListaUnidadVtaLocalXFiltro "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_UNID_VTA_LOCAL_FILTRO(?,?)",parametros,false);
  }
  //
  public static void cargaListaProdSinVtaNDias(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodLocal);
    log.debug("cargaListaProdSinVtaNDias "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_PROD_SIN_VTA_N_DIAS(?)",parametros,false);
  }
  
  public static void cargaListaProdSinVtaNDiasXFiltro(FarmaTableModel pTableModel) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(VariablesPtoVenta.vTipoFiltro);
    parametros.add(VariablesPtoVenta.vCodFiltro);
    log.debug("cargaListaProdSinVtaNDiasXFiltro "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_PROD_SIN_VTA_NDIAS_FIL(?,?,?)",parametros,false);
  }
  
  /** Listado de formas de pago por pedido
   *@author: JCORTEZ 
   *@since: 05/08/07
   */
    
  public static void cargaListaFormasPago(FarmaTableModel pTableModel, String nroPedido) throws SQLException
  {
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(nroPedido);
    log.debug("carga lista de fornmas de pago "+parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DETALLE_FORMAS_PAGO(?,?,?)",parametros,false);
  }
  
  /**
   * Obtiene el Numero de  Dias Sin Ventas 
   * @author : dubilluz
   * @since  : 21.08.2007
   */
  public static String obtieneNumeroDiasSinVentas() throws SQLException {
    parametros = new ArrayList();
    return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_REPORTE.NUMERO_DIAS_SIN_VENTAS",parametros);
  }
  
    
  public static ArrayList cargaListaVV_Impr(String pFechaIni,String pFechaFin) throws SQLException {
    ArrayList pOutParams = new ArrayList();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia.trim());
    parametros.add(FarmaVariables.vCodLocal.trim());
    parametros.add(pFechaIni);
    parametros.add(pFechaFin);
    log.debug("",parametros);
    FarmaDBUtility.executeSQLStoredProcedureArrayList(pOutParams,"PTOVENTA_REPORTE.REPORTE_VENTAS_VENDEDOR_IMP(?,?,?,?)",parametros);
    return pOutParams;
  }

/**
 * Obtiene el Reporte Detalle del vendedor por tipo de Venta
 * @author : asolis-
 * @since  : 26.11.2008
 * */
  
    public static void cargaListaDetalleVentasporVendedorTipo(FarmaTableModel pTableModel,
                                                        String pFechaInicio,
                                                        String pFechaFin,
                                                        String pUsuario,
                                                        String pTipo) throws SQLException {
    pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pUsuario);
    parametros.add(pTipo);
      
    log.debug("",parametros);
   FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_DET_VENTAS_VEND_TIPO(?,?,?,?,?,?)",parametros,false);
 


  }

    public static void cargaListaVentasporVendedorTipo(FarmaTableModel pTableModel,
                                                   String pFechaInicio,
                                                   String pFechaFin,
                                                   String pTipo) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pTipo);
     log.debug("PTOVENTA_REPORTE.REPORTE_VENTAS_POR_VEND_TIPO(?,?,?,?,?):"+parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_VENTAS_POR_VEND_TIPO(?,?,?,?,?)",parametros,false);
    }

    public static void listaReporteGuias(FarmaTableModel pTableModel,
                                        String pFechaInicio,
                                        String pFechaFin,
                                        String pNumGuia) throws SQLException
    {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaInicio);
        parametros.add(pFechaFin);
        parametros.add(pNumGuia);
        parametros.add("");
        log.debug("PTOVENTA_REPORTE.REPORTE_GUIAS(?,?,?,?,?,?):"+parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel,
                                                 "PTOVENTA_REPORTE.REPORTE_GUIAS(?,?,?,?,?,?)",
                                                 parametros,
                                                 false);
    }
    
    
    public static void cargaListaReporte_01(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     log.debug("VENTA_RPT_NUEVO.(?,?,?,?)",parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"VENTA_RPT_NUEVO.REPORTE_REGISTRO_VENTA(?,?,?,?,?)",parametros,false);
    }


    public static void cargaListaRegistroVentas_OC(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pCodCajero.trim());
     log.debug("",parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"UTILITY_ENFERMERA.REPORTE_REGISTRO_VENTA(?,?,?,?,?)",parametros,false);
    }
    
    public static void obtieneDetalleRegistroVentas_OC(FarmaTableModel pTableModel,
                                            String pCodigo) throws SQLException {
                                                 
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add(pCodigo);
      log.debug("",parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"UTILITY_ENFERMERA.REPORTE_DETALLE_REGISTRO_VENTA(?,?,?)",parametros,false);
    }
    
    
    public static void cargaListaRegistroVentasComision(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pCodCajero.trim());
     log.debug("",parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.RPT_REGISTRO_VENTA_BONIFICA(?,?,?,?,?)",parametros,false);
    }
    
    public static void cargaListaRegistroVentasComisionDetalle(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pCodCajero.trim());
     log.debug("",parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.RPT_REG_VENTA_BONIFICA_DET(?,?,?,?,?)",parametros,false);
    }
    
    public static void cargaListaProductoPorCliente(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pDocCliente,
                                                String pCodProd) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pCodCajero.trim());
     parametros.add(pDocCliente.trim());
     parametros.add(pCodProd.trim());
     log.debug("",parametros);
   /*  FarmaUtility.showMessage(new JDialog(), 
                              "pCodCajero:"+pCodCajero+"\n"+
        "pDocCliente:"+pDocCliente+"\n"+
        "pCodProd:"+pCodProd+"\n"+
        "pFechaInicio:"+pFechaInicio+"\n"+
        "pFechaFin:"+pFechaFin+"\n"                              
                              , null);*/
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.RPT_CLIENTE_X_PROD(?,?,?,?,?,?,?)",parametros,false);
    }
    
    
    public static void getLista_rpt_01(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
     parametros.add(pIdConsultorio.trim());
     parametros.add(pIdBus.trim());
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");             

        parametros.add(FarmaVariables.vIpPc);

     log.info("",parametros);
     System.out.println("RPT_HH_PRODUCCION_01 parametros "+parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PRODUCCION_01(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }

    public static void getLista_rpt_02(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
     parametros.add(pIdConsultorio.trim());
     parametros.add(pIdBus.trim());
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");       

        parametros.add(FarmaVariables.vIpPc);
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PRODUCCION_02(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }

    public static void getLista_rpt_03(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
     parametros.add(pIdConsultorio.trim());
     parametros.add(pIdBus.trim());
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");       

        parametros.add(FarmaVariables.vIpPc);  
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PRODUCCION_03(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }    
    
    

    public static void getLista_rpt_04(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
     parametros.add(pIdConsultorio.trim());
     parametros.add(pIdBus.trim());
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");       

        parametros.add(FarmaVariables.vIpPc);
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PRODUCCION_04(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }
    
    
    

    public static void getLista_rpt_05(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
     parametros.add(pIdConsultorio.trim());
     parametros.add(pIdBus.trim());
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");       

        parametros.add(FarmaVariables.vIpPc);
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PRODUCCION_05(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }
    
    
    public static void getLista_rpt_vta_01(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
     
        if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");  
     
        if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");  
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");             
     

        parametros.add(FarmaVariables.vIpPc);
        
     log.info("",parametros);
     System.out.println("RPT_HH_PRODUCCION_01 parametros "+parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VTA_01(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?)",parametros,false,"S");
    }


    
    public static void getLista_rpt_vta_02(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);

        if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");
     

        if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");
     
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");             
        
     log.info("",parametros);
     System.out.println("RPT_HH_PRODUCCION_01 parametros "+parametros);

        parametros.add(FarmaVariables.vIpPc);
        
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VTA_02(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?)",parametros,false,"S");
    }
    
    
    
    public static void proceso_rtp_planilla(String pFechaInicio,
                                                String pFechaFin) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(FarmaVariables.vNuSecUsu);
     parametros.add(FarmaVariables.vIpPc);
     log.info("",parametros);
       
     //dbGeneral.executeSQLStoredProcedure(null,"VENTA.HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PROCESO_AUX(?,?,?,?,?,?)",parametros,false,"S");
     dbGeneral.executeSQLStoredProcedure(null,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PROCESO_AUX_DOS(?,?,?,?,?,?)",parametros,false,"S");
    }
    
    
    public static void proceso_rtp_venta(String pFechaInicio,
                                         String pFechaFin) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(FarmaVariables.vNuSecUsu);
     parametros.add(FarmaVariables.vIpPc);
     log.info("",parametros);
       
     //dbGeneral.executeSQLStoredProcedure(null,"VENTA.HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PROCESO_AUX(?,?,?,?,?,?)",parametros,false,"S");
     dbGeneral.executeSQLStoredProcedure(null,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_PROCESO_VENTA_DOS(?,?,?,?,?,?)",parametros,false,"S");
    }

    public static void getLista_VENTA_TOTALUNIDO(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
    if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");
         
         if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");             

        parametros.add(FarmaVariables.vIpPc);

     log.info("",parametros);
     System.out.println("RPT_HH_VENTA_01 parametros "+parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VENTA_TOTUNIDO(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }
    
    public static void getLista_VENTA_00(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
    if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");
         
         if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");             

        parametros.add(FarmaVariables.vIpPc);

     log.info("",parametros);
     System.out.println("RPT_HH_VENTA_01 parametros "+parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VENTA_00(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }
    
    
    public static void getLista_VENTA_01(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");
	 
	 if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");             

        parametros.add(FarmaVariables.vIpPc);

     log.info("",parametros);
     System.out.println("RPT_HH_VENTA_01 parametros "+parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VENTA_01(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }

    public static void getLista_VENTA_02(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");
	 
	 if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");       

        parametros.add(FarmaVariables.vIpPc);
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VENTA_02(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }

    public static void getLista_VENTA_03(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");
	 
	 if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");       

        parametros.add(FarmaVariables.vIpPc);  
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VENTA_03(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }    
    
    

    public static void getLista_VENTA_04(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");
	 
	 if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");       

        parametros.add(FarmaVariables.vIpPc);
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VENTA_04(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }
    
    
    

    public static void getLista_VENTA_05(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                         String pIniVenta,String pFinVenta,
                                                         String pMedico,
                                                         String pServicio,
                                                         String pTurno,
                                                         String pCondicion
                                                         ) throws SQLException {
     //pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
       log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
       log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
       log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
       log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);

        log.info("HSurVariablesBD.pIniVenta "+pIniVenta);
        log.info("HSurVariablesBD.pFinVenta "+pFinVenta);
        log.info("HSurVariablesBD.pMedico "+pMedico);
        log.info("HSurVariablesBD.pServicio "+pServicio);
       log.info("HSurVariablesBD.pTurno "+pTurno);
        log.info("HSurVariablesBD.pCondicion "+pCondicion);
     
     FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(/*HSurVariablesBD.vUsuarioBD,
                                                                 HSurVariablesBD.vClaveBD,
                                                                 HSurVariablesBD.vIpBD,
                                                                 HSurVariablesBD.vPuertoBD,
                                                                 HSurVariablesBD.vSID*/
     FarmaVariables.vUsuarioBD,
     FarmaVariables.vClaveBD,
     FarmaVariables.vIPBD,
     //"127.0.0.1",
     FarmaVariables.vPUERTO,FarmaVariables.vSID
                                           );
     
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     //parametros.add(pCodCajero.trim());
     parametros.add(FarmaVariables.vNuSecUsu);
     
	 if(pIdConsultorio.trim().length()>0)
            parametros.add(pIdConsultorio.trim());
        else
            parametros.add("T");
	 
	 if(pIdBus.trim().length()>0)
            parametros.add(pIdBus.trim());
        else
            parametros.add("T");
	 
        parametros.add(pIniVenta.trim());
        parametros.add(pFinVenta.trim());
        
        if(pMedico.trim().length()>0)
            parametros.add(pMedico.trim());
        else
            parametros.add("T");
        if(pServicio.trim().length()>0)
        parametros.add(pServicio.trim());
        else
            parametros.add("T");     
        if(pTurno.trim().length()>0)        
        parametros.add(pTurno.trim());
        else
            parametros.add("T");             
        if(pCondicion.trim().length()>0)        
        parametros.add(pCondicion.trim());
        else
            parametros.add("T");       

        parametros.add(FarmaVariables.vIpPc);
     log.info("",parametros);
       
     dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_VENTA_05(" +
                                         "?,?,?,?,?,?," +
                                         "?,?,?,?,?,?,?,?)",parametros,false,"S");
    }
    
    public static void getLista_VENTA_LAB_ANATOMIA(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin/*,
                                                String pCodCajero,
                                                String pIdConsultorio,
                                                String pIdBus,
                                                String pIniVenta,String pFinVenta,
                                                String pMedico,
                                                String pServicio,
                                                String pTurno,
                                                String pCondicion*/) throws SQLException {
        pTableModel.clearTable();
        log.info("HSurVariablesBD.vUsuarioBD "+HSurVariablesBD.vUsuarioBD);
        log.info("HSurVariablesBD.vClaveBD "+HSurVariablesBD.vClaveBD);
        log.info("HSurVariablesBD.vIpBD "+HSurVariablesBD.vIpBD);
        log.info("HSurVariablesBD.vPuertoBD "+HSurVariablesBD.vPuertoBD);
        log.info("HSurVariablesBD.vSID "+HSurVariablesBD.vSID);
        FarmaDBUtilityGeneral dbGeneral = new FarmaDBUtilityGeneral(FarmaVariables.vUsuarioBD,
                                                                 FarmaVariables.vClaveBD,
                                                                 FarmaVariables.vIPBD,
                                                                 FarmaVariables.vPUERTO,FarmaVariables.vSID);
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pFechaInicio);
        parametros.add(pFechaFin);
        parametros.add(FarmaVariables.vNuSecUsu);
        //parametros.add(FarmaVariables.vIpPc);
        log.info("",parametros);
        dbGeneral.executeSQLStoredProcedure(pTableModel,"HHSUR_NEW_RPT_CONSULTORIO.RPT_HH_RPT_LAB_ANTOMIA(" +
                                         "?,?,?,?,?)",parametros,false,"S");
    }
}