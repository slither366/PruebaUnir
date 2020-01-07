package venta.reportes.repo_renova;

import common.FarmaVariables;

import java.sql.SQLException;

import java.util.ArrayList;

import common.*;

import venta.reference.VariablesPtoVenta;
import venta.reportes.reference.DBReportes;
import venta.reportes.reference.VariablesReporte;
import venta.modulo_venta.reference.VariablesModuloVentas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBEncuesta  {
    private static final Logger log = LoggerFactory.getLogger(DBEncuesta.class);

    private static ArrayList parametros = new ArrayList();

    public DBEncuesta() {
    }

    public static void grabaSeleccionEncuesta(String pCodOpcionEncuesta,String pCadena) throws SQLException {
        parametros = new ArrayList();
        parametros.add(FarmaVariables.vCodGrupoCia);
        parametros.add(FarmaVariables.vCodLocal);
        parametros.add(FarmaVariables.vIdUsu);
        parametros.add(FarmaVariables.vNuSecUsu);
        parametros.add(FarmaVariables.vIpPc);
        parametros.add(VariablesModuloVentas.vNum_Ped_Vta);
        parametros.add(pCodOpcionEncuesta);
        parametros.add(pCadena);
        log.debug("", parametros);
        FarmaDBUtility.executeSQLStoredProcedure(null,"PTOVENTA_REPORTE.SAVE_P_ENCUESTA(?,?,?,?," +
                                                 "?,?,?,?)",
                                                 parametros, false);
    }
}