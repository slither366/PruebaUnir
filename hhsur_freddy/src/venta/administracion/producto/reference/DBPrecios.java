package venta.administracion.producto.reference;

import java.sql.SQLException;

import java.util.ArrayList;

import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaVariables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (c) 2011 MIFARMA S.A.C.<br>
 * <br>
 * Entorno de Desarrollo : Oracle JDeveloper 10g<br>
 * Nombre de la Aplicaci�n : DlgListaIPSImpresora.java<br>
 * <br>
 * Hist�rico de Creaci�n/Modificaci�n<br>
 * JSANTIVANEZ 05.01.2011 Modificaci�n<br>
 * <br>
 * @version 1.0<br>
 *
 */
public class DBPrecios {
    
    private static final Logger log = LoggerFactory.getLogger(DBPrecios.class);
    
    private static ArrayList parametros = new ArrayList();
    public DBPrecios() {
    }
    
    public static void obtieneListaPrecProdCamb(FarmaTableModel pTableModel)throws SQLException
    {
      pTableModel.clearTable();
      parametros = new ArrayList();
      parametros.add(FarmaVariables.vCodGrupoCia);
      
      FarmaDBUtility.executeSQLStoredProcedure(pTableModel, "PTOVENTA_VIAJERO.VIAJ_LISTAR_PRECIOS_CAMBIADOS(?)",parametros, false);
    }    
    public static void cargaListaProdCambiados(FarmaTableModel pTableModel,
                                                String pFechaInicio,
                                                String pFechaFin,
                                                String pDescrProd) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     parametros.add(pFechaInicio);
     parametros.add(pFechaFin);
     parametros.add(pDescrProd);
     log.debug("",parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VIAJERO.V_LIST_PREC_CAMB_X_FECHA_PROD(?,?,?,?,?)",parametros,false);
    }
        
    public static void cargaListaProdCambiadosFaltantes(FarmaTableModel pTableModel) throws SQLException {
     pTableModel.clearTable();
     parametros = new ArrayList();
     parametros.add(FarmaVariables.vCodGrupoCia);
     parametros.add(FarmaVariables.vCodLocal);
     log.debug("",parametros);
     FarmaDBUtility.executeSQLStoredProcedure(pTableModel,"PTOVENTA_VIAJERO.V_LIST_PREC_CAMB_FALTANTE(?,?)",parametros,false);
    }
}
