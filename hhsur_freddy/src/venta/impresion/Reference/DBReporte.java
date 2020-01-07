package venta.impresion.Reference;

import common.FarmaDBUtility;
import common.FarmaTableModel;
import common.FarmaVariables;

import java.sql.SQLException;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBReporte {
    
    private static final Logger log = LoggerFactory.getLogger(DBReporte.class);
    private static ArrayList parametros = null;
    
    public DBReporte() {
    }

    public static void guardarDatosAdicionales(String pGlosa, String pNumPedidoVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pGlosa);
        parametros.add(pNumPedidoVta);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("parametros PTOVENTA_JASPER.PN_UPD_DATOS_ADICIONALES_JASP(?,?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_JASPER.PN_UPD_DATOS_ADICIONALES_JASP(?,?,?,?)",
                                                 parametros, false);
    }

    public static String obtenerDatosAdicionales(String pNumPedidoVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pNumPedidoVta);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("parametros PTOVENTA_JASPER.PN_GET_DATOS_ADICIONALES_JASP(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_JASPER.PN_GET_DATOS_ADICIONALES_JASP(?,?,?)",
                                                           parametros);
    }

    public static void obtenerDatosReporteJasperEmp(FarmaTableModel tableModelDatosEmp) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        log.info("parametros PTOVENTA_JASPER.FN_DATA_JASPER_COTIZACION_EMP(?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(tableModelDatosEmp,
                                                 "PTOVENTA_JASPER.FN_DATA_JASPER_COTIZACION_EMP(?)", parametros,
                                                 false);
    }

    public static void obtenerDatosReporteJasperCab(FarmaTableModel tableModelDatosCab,
                                                    String pNumPedidoVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);
        log.info("parametros PTOVENTA_JASPER.FN_DATA_JASPER_COTIZACION_CAB(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(tableModelDatosCab,
                                                 "PTOVENTA_JASPER.FN_DATA_JASPER_COTIZACION_CAB(?,?,?)", parametros,
                                                 false);
    }

    public static void obtenerDatosReporteJasperDet(FarmaTableModel tableModelDatosDet,
                                                    String pNumPedidoVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(pNumPedidoVta);
        log.info("parametros PTOVENTA_JASPER.FN_DATA_JASPER_COTIZACION_DET(?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(tableModelDatosDet,
                                                 "PTOVENTA_JASPER.FN_DATA_JASPER_COTIZACION_DET(?,?,?)", parametros,
                                                 false);
    }
    
    /* ******************************************************* */
    // DATO PDF DE guia 
    public static void guardarDatosAdicionales_guia(String pGlosa, String pNumPedidoVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pGlosa);
        parametros.add(pNumPedidoVta);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("parametros PTOVENTA_JASPER.PN_UPD_DATOS_ADIC_JASP_GUI(?,?,?,?): " + parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,
                                                 "PTOVENTA_JASPER.PN_UPD_DATOS_ADIC_JASP_GUI(?,?,?,?)",
                                                 parametros, false);
    }

    public static String obtenerDatosAdicionales_guia(String pNumPedidoVta) throws SQLException {
        parametros = new ArrayList();
        parametros.add(pNumPedidoVta);
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        log.info("parametros PTOVENTA_JASPER.PN_GET_DATOS_ADIC_JASP_GUI(?,?,?): " + parametros);
        return FarmaDBUtility.executeSQLStoredProcedureStr("PTOVENTA_JASPER.PN_GET_DATOS_ADIC_JASP_GUI(?,?,?)",
                                                           parametros);
    }
    
}
