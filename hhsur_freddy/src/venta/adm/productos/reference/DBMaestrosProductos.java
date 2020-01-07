package venta.adm.productos.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import venta.reference.VariablesPtoVenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBMaestrosProductos {
    private static final Logger log = LoggerFactory.getLogger(DBMaestrosProductos.class);

    public static void getListaMaeProductos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.debug("invocando a PKG_ADM_PRODUCTOS_DOS.INV_LISTA_MAE_PRODUCTOS(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADM_PRODUCTOS_DOS.INV_LISTA_MAE_PRODUCTOS(?,?)", parametros,
                                                 false);
    }
    
    public static void cargaListaMaeProductos_Filtro(FarmaTableModel pTableModel) throws SQLException {
      pTableModel.clearTable();
      ArrayList parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      parametros.add(FarmaVariables.vCodLocal);
      parametros.add( VariablesPtoVenta.vTipoFiltro);
      parametros.add(VariablesPtoVenta.vCodFiltro);
      log.debug("invocando a PKG_ADM_PRODUCTOS_DOS.INV_LISTA_PROD_FILTRO(?,?,?,?):"+parametros);
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PKG_ADM_PRODUCTOS_DOS.INV_LISTA_PROD_FILTRO(?,?,?,?)",parametros,false);
    }
    

    public static String getDatoProducto(String pCodProd) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd.trim());
        log.debug("invocando a PKG_ADM_PRODUCTOS_DOS.GET_DATOS_PRODUCTO(?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PKG_ADM_PRODUCTOS_DOS.GET_DATOS_PRODUCTO(?,?,?)", parametros);
    }
    
    public static void getListaHistPrecios(FarmaTableModel pTableModel,String pCodProd) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(pCodProd.trim());
        log.debug("invocando a PKG_ADM_PRODUCTOS_DOS.LISTA_HIST_PRECIO_PRODUCTO(?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADM_PRODUCTOS_DOS.LISTA_HIST_PRECIO_PRODUCTO(?,?)", parametros,
                                                 false);
    }

    public static void getListaFracciones(FarmaTableModel pTableModel,String pCodProd,
                                          String pTipoVenta) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd.trim());
        parametros.add(pTipoVenta.trim());
        log.debug("invocando a PKG_ADM_PRODUCTOS_DOS.LISTA_FRACCIONAMIENTO(?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADM_PRODUCTOS_DOS.LISTA_FRACCIONAMIENTO(?,?,?,?)", parametros,
                                                 false);
    }
    

    public static void cambiaPrecioProductoCadena(String pCodProd,double pPrecioFracNuevo) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd.trim());
        parametros.add(FarmaUtility.formatNumber(pPrecioFracNuevo));
        parametros.add(FarmaVariables.vIdUsu);
        log.debug("invocando a PKG_ADM_PRODUCTOS_DOS.P_CAMBIA_PRECIO_CADENA(?,?,?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PKG_ADM_PRODUCTOS_DOS.P_CAMBIA_PRECIO_CADENA(?,?,?,?,?)", parametros,false);
    }
    
    public static void getListaLoteProducto(FarmaTableModel pTableModel,String pCodProd) throws SQLException {
        pTableModel.clearTable();
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd.trim());
        log.debug("invocando a PKG_ADM_PRODUCTOS_DOS.GET_LISTA_LOTE_PROD(?,?,?):" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADM_PRODUCTOS_DOS.GET_LISTA_LOTE_PROD(?,?,?)", parametros,
                                                 false);
    }

    public static String isValidoUsuCambioPrecio(String vSecUsu) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(vSecUsu.trim());
        log.debug("invocando a PKG_ADM_PRODUCTOS_DOS.IS_VALIDO_USUARIO(?,?,?):" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PKG_ADM_PRODUCTOS_DOS.IS_VALIDO_USUARIO(?,?,?)", parametros);
    }    

}
