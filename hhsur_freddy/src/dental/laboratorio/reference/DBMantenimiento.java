package dental.laboratorio.reference;


import common.FarmaColumnData;
import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaUtility;
import common.FarmaVariables;

import java.sql.SQLException;

import java.util.ArrayList;

import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBMantenimiento {
    private static final Logger log = LoggerFactory.getLogger(DBMantenimiento.class);
    private static ArrayList parametros = new ArrayList();

    public static void  getListaLaboratorios(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("PKG_ADM_PRODUCTOS_DOS.LISTA_LABORATORIOS(?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADM_PRODUCTOS_DOS.LISTA_LABORATORIOS(?,?)", parametros,
                                                 false);
    }
    
    public static void grabaLaboratorio(String pNombreLab, String pDireccionLab, String pTelefonoLab, String pIdLab, boolean pInsert, boolean pUpdate) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNombreLab);
        parametros.add(pDireccionLab);
        parametros.add(pTelefonoLab);
        parametros.add(pIdLab);
        if (pInsert) {
            parametros.add("S");
            parametros.add("N");
        } else {
            if (pUpdate) {
                parametros.add("N");
                parametros.add("S");
            }
        }
        parametros.add(FarmaVariables.vIdUsu);
        log.info("PKG_ADM_PRODUCTOS_DOS.LAB_ACCION_I_U(?,?,?,?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PKG_ADM_PRODUCTOS_DOS.LAB_ACCION_I_U(?,?,?,?,?,?,?,?,?)", parametros,
                                                 false);
    }
    
    public static String grabaProducto(String pDescripcion, String pCosto, String pUnidadEntera, 
                                     String codMarca, String codEstado, String codFraccionado, String ganancia,
                                     boolean pInsert, boolean pUpdate,String pCodProdUpdate_in,String cantPresentacion) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pDescripcion);
        parametros.add(pCosto);
        parametros.add(pUnidadEntera);
        
        parametros.add(codMarca);
        parametros.add(codEstado);
        parametros.add(codFraccionado);
        parametros.add(ganancia);
        if (pInsert) {
            parametros.add("S");
            parametros.add("N");
        } else {
            if (pUpdate) {
                parametros.add("N");
                parametros.add("S");
            }
        }
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pCodProdUpdate_in);    
        parametros.add(cantPresentacion);
        log.info("PKG_ADM_PRODUCTOS_DOS.PROD_ACCION_I_U(?,?,?,?,?,?,?,?,?,?,?,?,?,?) " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PKG_ADM_PRODUCTOS_DOS.PROD_ACCION_I_U(" +
                                                           "?,?,?,?,?," +
                                                           "?,?,?,?,?," +
                                                           "?,?,?,?)", parametros);
    }
    
    public static void grabaUnidadProd(String descripcion,String cantidad,String pPCTGanancia, String precioVenta, 
                                       String pPrecioMinimo,String pPrecioTercero,
                                       boolean pInsert, boolean pUpdate,String pCodProd) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(descripcion);
        parametros.add(cantidad);
        parametros.add(precioVenta);
        parametros.add(pPrecioMinimo);
        parametros.add(pPrecioTercero);
        
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pCodProd);
        parametros.add(pPCTGanancia);
        
        log.info("PKG_ADM_PRODUCTOS_DOS.UNIDAD_ACCION_I_U(?,?,?,?,?,?,?,?,?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, "PKG_ADM_PRODUCTOS_DOS.UNIDAD_ACCION_I_U(?,?,?,?,?,?,?,?,?,?)", parametros,
                                                 false);
    }

    public static void grabaLoteProd(String Lote, String FecVen,
                                       boolean pInsert, boolean pUpdate,String pCodProd) throws SQLException {
        ArrayList parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(Lote);
        parametros.add(FecVen);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(pCodProd);
        log.info("PKG_ADM_PRODUCTOS_DOS.LOTE_ACCION_I_U(?,?,?,?," +
                                                   "?,?) " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null, 
                                                 "PKG_ADM_PRODUCTOS_DOS.LOTE_ACCION_I_U(?,?,?,?," +
                                                   "?,?) ", parametros,
                                                 false);
    }    
    
    public static void getLabId(ArrayList pLista, String pId) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pId);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(pLista, "PKG_ADM_PRODUCTOS_DOS.GET_LAB(?)", parametros);
    }
    
    public static void getListaProductos(FarmaTableModel pTableModel) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("PKG_ADM_PRODUCTOS_DOS.LISTA_PRODUCTOS(?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADM_PRODUCTOS_DOS.LISTA_PRODUCTOS(?,?)", parametros,
                                                 true);
        
        for(int i=0;i<pTableModel.data.size();i++){
            if(FarmaUtility.getValueFieldArrayList(pTableModel.data, i, 6).toUpperCase().equalsIgnoreCase("ACTIVO"))
                ((ArrayList)(pTableModel.data.get(i))).set(0, true);
            else
                ((ArrayList)(pTableModel.data.get(i))).set(0, false);
        }
        
    }
    
    public static void getListaUnidadPrecio(FarmaTableModel pTableModel, String codProd) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        log.info("PKG_ADM_PRODUCTOS_DOS.LISTA_UNIDADPRECIO(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADM_PRODUCTOS_DOS.LISTA_UNIDADPRECIO(?,?,?)", parametros,
                                                 false);
    }
    
    public static void getListaLoteProducto(FarmaTableModel pTableModel, String codProd) throws SQLException {
        pTableModel.clearTable();
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        log.info("PKG_ADM_PRODUCTOS_DOS.LISTA_LOTEPRODUCTO(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PKG_ADM_PRODUCTOS_DOS.LISTA_LOTEPRODUCTO(?,?,?)", parametros,
                                                 false);
    }
    
    public static void setEstadoProducto(String codProd) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(codProd);
        parametros.add(FarmaVariables.vIdUsu);
        log.info("PKG_ADM_PRODUCTOS_DOS.SET_ESTADO_PRODUCTO(?,?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureStr("PKG_ADM_PRODUCTOS_DOS.SET_ESTADO_PRODUCTO(?,?,?,?)", parametros);
    }
    
    public static String getProducto(String pCodProd_in) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd_in);
        
        log.info("PKG_ADM_PRODUCTOS_DOS.GET_PRODUCTO(?,?,?)" + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PKG_ADM_PRODUCTOS_DOS.GET_PRODUCTO(?,?,?)", parametros);
    }
    
    public static void getArrayUnidadProducto(ArrayList Datos, String pCodProd_in) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd_in);
        
        log.info("PKG_ADM_PRODUCTOS_DOS.LISTA_UNIDADPRECIO(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(Datos,"PKG_ADM_PRODUCTOS_DOS.LISTA_UNIDADPRECIO(?,?,?)", parametros);
    }
    
    public static void getArrayLoteProducto(ArrayList Datos, String pCodProd_in) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd_in);
        
        log.info("PKG_ADM_PRODUCTOS_DOS.LISTA_LOTEPRODUCTO(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedureArrayList(Datos,"PKG_ADM_PRODUCTOS_DOS.LISTA_LOTEPRODUCTO(?,?,?)", parametros);
    }

    public static void inactivaProducto(String pCodProd)  throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pCodProd);
        
        log.info("PKG_ADM_PRODUCTOS_DOS.PROD_INACTIVAR(?,?,?)" + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PKG_ADM_PRODUCTOS_DOS.PROD_INACTIVAR(?,?,?)", parametros,false);
    }
}
