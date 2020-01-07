package venta.reportes.reference;
import java.sql.*;
import java.util.*;

import common.*;

import javax.swing.JDialog;

import venta.reference.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import printerUtil.FarmaUtil;

public class DBReportes 
{
    private static final Logger log = LoggerFactory.getLogger(DBReportes.class);
    
  private static ArrayList parametros = new ArrayList();
  public DBReportes()
  {
  }
  
    public static void cargaListaRegistroVentas_X_CAJERO(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pUsuarioLogin,
                                                         String pCodCAJA) throws SQLException {
     //pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pUsuarioLogin.trim());
     parametros.add(pCodCAJA.trim());
        
     log.info("",parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_REGISTRO_VENTA_X_CAJA(?,?,?,?,?,?)",parametros,false);
    }
  
   public static void cargaListaRegistroVentas(FarmaTableModel pTableModel,
                                               String pFechaInicio,
                                               String pFechaFin,
                                               String pCodCajero) throws SQLException {
    //pTableModel.clearTable();
    parametros = new ArrayList();
    parametros.add(FarmaVariables.vCodGrupoCia);
    parametros.add(FarmaVariables.vCodLocal);
    parametros.add(pFechaInicio);
    parametros.add(pFechaFin);
    parametros.add(pCodCajero.trim());
    log.info("",parametros);
    FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_REPORTE.REPORTE_REGISTRO_VENTA(?,?,?,?,?)",parametros,false);
  }
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
    }
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

  //Hist�rico de Creaci�n/Modificaci�n
  //ERIOS      27.03.2005   Creaci�n
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
  
  //Hist�rico de Creaci�n/Modificaci�n
  //ERIOS      06.07.2006   Creaci�n
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
}